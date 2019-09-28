package gregtech.common.metatileentities.storage;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.base.Preconditions;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.gui.ModularUI;
import gregtech.api.items.metaitem.DefaultSubItemHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.recipes.ModHandler;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.type.Material.MatFlags;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.util.GTUtility;
import gregtech.api.util.WatchedFluidTank;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MetaTileEntityTank extends MetaTileEntity {

    private final int tankSize;
    private final SolidMaterial material;
    private int oldLightValue = 0;
    private boolean addedToMultiblock = false;
    private boolean needsCoversUpdate = false;
    private boolean isRemoved = false;
    private int blockedSides = 0;

    private final FluidTank multiblockFluidTank;
    private List<BlockPos> connectedTanks = new ArrayList<>();
    private BlockPos lowestPos = null;
    private BlockPos highestPos = null;

    private BlockPos controllerPos = null;
    private WeakReference<MetaTileEntityTank> controllerCache = new WeakReference<>(null);

    public MetaTileEntityTank(ResourceLocation metaTileEntityId, SolidMaterial material, int tankSize) {
        super(metaTileEntityId);
        this.tankSize = tankSize;
        this.material = material;
        this.multiblockFluidTank = new FluidTank(tankSize);
        initializeInventory();
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            if (!addedToMultiblock) {
                this.addedToMultiblock = true;
                rescanTankMultiblocks(Collections.emptySet());
            }
            if (needsCoversUpdate) {
                this.needsCoversUpdate = false;
                rescanSurroundingMultiblocks();
                rescanTankMultiblocks(recomputeNearbyMultiblocks());
            }
        }
    }

    private void rescanSurroundingMultiblocks() {
        this.blockedSides = 0;
        for (EnumFacing side : EnumFacing.VALUES) {
            CoverBehavior coverBehavior = getCoverAtSide(side);
            if (coverBehavior == null) continue;
            if (coverBehavior.canPipePassThrough()) continue;
            this.blockedSides |= 1 << side.getIndex();
        }
    }

    @Override
    protected void onCoverPlacementUpdate() {
        super.onCoverPlacementUpdate();
        this.needsCoversUpdate = true;
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        this.isRemoved = true;
        recomputeNearbyMultiblocks();
    }

    private Set<BlockPos> recomputeNearbyMultiblocks() {
        HashSet<BlockPos> handledSet = new HashSet<>();
        for (EnumFacing side : EnumFacing.VALUES) {
            MetaTileEntityTank tank = getTankTile(getPos().offset(side));
            handledSet.addAll(tank.rescanTankMultiblocks(handledSet));
        }
        return handledSet;
    }

    private double getFluidLevelForTank(BlockPos tankPos) {
        Preconditions.checkArgument(connectedTanks.contains(tankPos), "Tank is not controlled by this controller");
        double fillPercent = multiblockFluidTank.getFluidAmount() / (multiblockFluidTank.getCapacity() * 1.0);
        double perLevelFill = fillPercent * (highestPos.getY() - lowestPos.getY() + 1) - tankPos.getY();
        return MathHelper.clamp(perLevelFill, 0.0, 1.0);
    }

    private int getFluidStoredInTank(BlockPos tankPos) {
        double fluidLevel = getFluidLevelForTank(tankPos);
        return MathHelper.floor(fluidLevel * tankSize);
    }

    private MetaTileEntityTank getTankTile(BlockPos blockPos) {
        MetaTileEntity metaTileEntity = BlockMachine.getMetaTileEntity(getWorld(), blockPos);
        if (!(metaTileEntity instanceof MetaTileEntityTank)) {
            return null;
        }
        MetaTileEntityTank metaTileEntityTank = (MetaTileEntityTank) metaTileEntity;
        if (metaTileEntityTank.isRemoved ||
            metaTileEntityTank.material != material ||
            metaTileEntityTank.tankSize != tankSize ||
            !isTankFluidEqual(metaTileEntityTank, this)) {
            return null;
        }
        return metaTileEntityTank;
    }

    private void recomputeTankSize() {
        this.lowestPos = connectedTanks.stream().min(Comparator.comparing(Vec3i::getY)).orElse(null);
        this.highestPos = connectedTanks.stream().max(Comparator.comparing(Vec3i::getY)).orElse(null);
        this.multiblockFluidTank.setCapacity((1 + connectedTanks.size()) * tankSize);
        if (multiblockFluidTank.getFluid() != null &&
            multiblockFluidTank.getFluidAmount() > multiblockFluidTank.getCapacity()) {
            multiblockFluidTank.getFluid().amount = multiblockFluidTank.getCapacity();
        }
    }

    private void removeTankFromMultiblock(MetaTileEntityTank removedTank) {
        int fluidInTank = getFluidStoredInTank(removedTank.getPos());
        this.connectedTanks.remove(removedTank.getPos());
        removedTank.setTankControllerInternal(null);
        FluidStack fluidStack = multiblockFluidTank.getFluid();
        //do not retain fluid to disconnected tank if it has less than 0 bucket
        if (fluidStack != null && fluidInTank >= Fluid.BUCKET_VOLUME) {
            FluidStack fillStack = GTUtility.copyAmount(fluidInTank, fluidStack);
            removedTank.multiblockFluidTank.fill(fillStack, true);
            this.multiblockFluidTank.drain(fillStack, true);
        }
        recomputeTankSize();
    }

    private void addTankToMultiblock(MetaTileEntityTank addedTank) {
        FluidStack tankFluid = addedTank.multiblockFluidTank.drain(Integer.MAX_VALUE, true);
        this.connectedTanks.add(addedTank.getPos());
        addedTank.setTankControllerInternal(this);
        recomputeTankSize();
        if (tankFluid != null) {
            multiblockFluidTank.fill(tankFluid, true);
        }
    }

    private boolean isTankController() {
        return controllerPos == null;
    }

    private FluidStack getActualTankFluid() {
        if (isTankController()) {
            FluidStack fluidStack = multiblockFluidTank.getFluid();
            return fluidStack == null ? null : fluidStack.copy();
        } else if (controllerPos != null) {
            MetaTileEntityTank controller = getControllerEntity();
            return controller == null ? null : controller.getActualTankFluid();
        }
        return null;
    }

    private MetaTileEntityTank getControllerEntity() {
        MetaTileEntityTank cachedController = controllerCache.get();
        if (cachedController != null) {
            if (cachedController.isValid()) {
                return cachedController;
            } else {
                controllerCache.clear();
            }
        }
        MetaTileEntityTank metaTileEntity = getTankTile(controllerPos);
        if (metaTileEntity != null) {
            this.controllerCache = new WeakReference<>(metaTileEntity);
            return metaTileEntity;
        }
        return null;
    }

    private void setTankControllerInternal(MetaTileEntityTank controller) {
        this.controllerPos = controller == null ? null : controller.getPos();
        this.controllerCache = new WeakReference<>(controller);
    }

    private void setTankController(MetaTileEntityTank controller) {
        MetaTileEntityTank oldController = getControllerEntity();
        if (oldController == controller) {
            return; //do not pointlessly update controller
        }
        if (oldController != null) {
            oldController.removeTankFromMultiblock(this);
        }
        if (controller != null) {
            controller.addTankToMultiblock(this);
        } else {
            setTankControllerInternal(null);
        }
        if (!connectedTanks.isEmpty()) {
            updateConnectedTanks();
            this.connectedTanks.clear();
            this.highestPos = null;
            this.lowestPos = null;
        }
    }

    private void updateConnectedTanks() {
        HashSet<BlockPos> handledSet = new HashSet<>(connectedTanks);
        while (!handledSet.isEmpty()) {
            MetaTileEntityTank tank = getTankTile(handledSet.iterator().next());
            handledSet.removeAll(tank.rescanTankMultiblocks(handledSet));
        }
    }

    private void buildTankStructure(Set<BlockPos> structureBlocks, Map<BlockPos, MetaTileEntityTank> allTanks) {
        //index all connected blocks
        List<MetaTileEntityTank> connectedTanks = structureBlocks.stream()
            .sorted(buildRootComparator())
            .map(allTanks::get)
            .collect(Collectors.toList());
        //first, attempt to search for an existing master block
        MetaTileEntityTank firstController = connectedTanks.stream()
            .filter(MetaTileEntityTank::isTankController)
            .findFirst().orElse(null);
        if (firstController == null) {
            firstController = connectedTanks.get(connectedTanks.size() - 1);
            firstController.setTankController(null);
        }
        //add all new tanks to the controller
        connectedTanks.remove(firstController);
        MetaTileEntityTank finalFirstController = firstController;
        connectedTanks.forEach(it -> it.setTankController(finalFirstController));
    }

    private static boolean isTankFluidEqual(MetaTileEntityTank tank1, MetaTileEntityTank tank2) {
        FluidStack fluidStack1 = tank1.getActualTankFluid();
        FluidStack fluidStack2 = tank2.getActualTankFluid();
        return fluidStack1 == null || fluidStack2 == null ||
            (fluidStack1 != null && fluidStack1.isFluidEqual(fluidStack2));
    }

    private static boolean canTanksConnect(MetaTileEntityTank tank1, MetaTileEntityTank tank2, EnumFacing side) {
        return side == null || ((tank1.blockedSides & 1 << side.getIndex()) <= 0 &&
            (tank2.blockedSides & 1 << side.getOpposite().getIndex()) <= 0);
    }

    private static Comparator<BlockPos> buildRootComparator() {
        //we compare on all axis to ensure stable order and independence from the tank who started
        //the tank multiblock update sequence
        return Comparator.comparing(BlockPos::getY)
            .thenComparing(BlockPos::getX)
            .thenComparing(BlockPos::getZ)
            .reversed();
    }

    private Set<BlockPos> rescanTankMultiblocks(Set<BlockPos> excludedBlocks) {
        Map<BlockPos, MetaTileEntityTank> allTanks = findConnectedTankBlocks(getPos());
        allTanks.keySet().removeAll(excludedBlocks);
        TreeSet<BlockPos> sortedTanks = new TreeSet<>(buildRootComparator());
        sortedTanks.addAll(allTanks.keySet());
        while (!sortedTanks.isEmpty()) {
            BlockPos rootTankPos = sortedTanks.pollLast();
            sortedTanks.remove(rootTankPos);
            Set<BlockPos> structureBlocks = findStructureBlocksFromBottom(rootTankPos, allTanks);
            sortedTanks.removeAll(structureBlocks);
            buildTankStructure(structureBlocks, allTanks);
        }
        return allTanks.keySet();
    }

    private Set<BlockPos> findStructureBlocksFromBottom(BlockPos bottomStartPos, Map<BlockPos, MetaTileEntityTank> allTanks) {
        return findAllConnectedBlocks(bottomStartPos, ArrayUtils.add(EnumFacing.HORIZONTALS, EnumFacing.UP), allTanks::get).keySet();
    }

    private Map<BlockPos, MetaTileEntityTank> findConnectedTankBlocks(BlockPos startPos) {
        return findAllConnectedBlocks(startPos, EnumFacing.VALUES, this::getTankTile);
    }

    private static Map<BlockPos, MetaTileEntityTank> findAllConnectedBlocks(BlockPos startPos, EnumFacing[] directions, Function<BlockPos, MetaTileEntityTank> blockProvider) {
        HashMap<BlockPos, MetaTileEntityTank> observedSet = new HashMap<>();
        observedSet.put(startPos, blockProvider.apply(startPos));
        MetaTileEntityTank firstNode = observedSet.get(startPos);
        MutableBlockPos currentPos = new MutableBlockPos(startPos);
        Stack<EnumFacing> moveStack = new Stack<>();
        main: while (true) {
            for (EnumFacing facing : directions) {
                currentPos.move(facing);
                MetaTileEntityTank metaTileEntity;
                if (!observedSet.containsKey(currentPos) && (metaTileEntity = blockProvider.apply(currentPos)) != null && canTanksConnect(firstNode, metaTileEntity, facing)) {
                    observedSet.put(metaTileEntity.getPos(), metaTileEntity);
                    firstNode = metaTileEntity;
                    moveStack.push(facing.getOpposite());
                    continue main;
                } else currentPos.move(facing.getOpposite());
            }
            if (!moveStack.isEmpty()) {
                currentPos.move(moveStack.pop());
                firstNode = observedSet.get(currentPos);
            } else break;
        }
        return observedSet;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityTank(metaTileEntityId, material, tankSize);
    }

    @Override
    public int getLightValue() {
        FluidStack fluidStack = fluidTank.getFluid();
        if (fluidStack == null) {
            return 0;
        }
        return fluidStack.getFluid().getLuminosity(fluidStack);
    }

    @Override
    public int getLightOpacity() {
        return 1; //let light pass trough us entirely
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public String getHarvestTool() {
        return ModHandler.isMaterialWood(material) ? "axe" : "pickaxe";
    }

    @Override
    public boolean hasFrontFacing() {
        return false;
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.fluidTank = new SyncFluidTank(tankSize);
        this.fluidInventory = fluidTank;
        updateComparatorValue();
    }

    @Override
    public void initFromItemStackData(NBTTagCompound itemStack) {
        super.initFromItemStackData(itemStack);
        if (itemStack.hasKey(FluidHandlerItemStack.FLUID_NBT_KEY, NBT.TAG_COMPOUND)) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(itemStack.getCompoundTag(FluidHandlerItemStack.FLUID_NBT_KEY));
            fluidTank.setFluid(fluidStack);
            fluidTank.onContentsChanged();
        }
    }

    @Override
    public void writeItemStackData(NBTTagCompound itemStack) {
        super.writeItemStackData(itemStack);
        FluidStack fluidStack = fluidTank.getFluid();
        if (fluidStack != null && fluidStack.amount > 0) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            fluidStack.writeToNBT(tagCompound);
            itemStack.setTag(FluidHandlerItemStack.FLUID_NBT_KEY, tagCompound);
        }
    }

    @Override
    public String getItemSubTypeId(ItemStack itemStack) {
        return DefaultSubItemHandler.getFluidContainerSubType(itemStack);
    }

    @Override
    public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> subItems) {
        super.getSubItems(creativeTab, subItems);
        if (creativeTab == CreativeTabs.SEARCH) {
            DefaultSubItemHandler.addFluidContainerVariants(getStackForm(), subItems);
        }
    }

    @Override
    public ICapabilityProvider initItemStackCapabilities(ItemStack itemStack) {
        return new FluidHandlerItemStack(itemStack, tankSize) {
            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return MetaTileEntityTank.this.canFillFluidType(fluid);
            }
        };
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        FluidStack fluidStack = fluidTank.getFluid();
        buf.writeBoolean(fluidStack != null);
        if (fluidStack != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            fluidStack.writeToNBT(tagCompound);
            buf.writeCompoundTag(tagCompound);
        }
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        FluidStack fluidStack = null;
        if (buf.readBoolean()) {
            try {
                NBTTagCompound tagCompound = buf.readCompoundTag();
                fluidStack = FluidStack.loadFluidStackFromNBT(tagCompound);
            } catch (IOException ignored) {
            }
        }
        fluidTank.setFluid(fluidStack);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == 200) {
            FluidStack fluidStack = null;
            if (buf.readBoolean()) {
                try {
                    NBTTagCompound tagCompound = buf.readCompoundTag();
                    fluidStack = FluidStack.loadFluidStackFromNBT(tagCompound);
                } catch (IOException ignored) {
                }
            }
            fluidTank.setFluid(fluidStack);
            //update light on client side
            updateLightValue();
            getHolder().scheduleChunkForRenderUpdate();
        } else if (dataId == 201) {
            int newFluidAmount = buf.readVarInt();
            FluidStack fluidStack = fluidTank.getFluid();
            if (fluidStack != null) {
                fluidStack.amount = newFluidAmount;
                getHolder().scheduleChunkForRenderUpdate();
                //update light on client side
                updateLightValue();
            }
        }
    }

    private void updateLightValue() {
        int newLightValue = getLightValue();
        if (oldLightValue != newLightValue) {
            MetaTileEntityTank.this.oldLightValue = newLightValue;
            getWorld().checkLight(getPos());
        }
    }

    @Override
    public int getActualComparatorValue() {
        FluidTank fluidTank = this.fluidTank;
        int fluidAmount = fluidTank.getFluidAmount();
        int maxCapacity = fluidTank.getCapacity();
        float f = fluidAmount / (maxCapacity * 1.0f);
        return MathHelper.floor(f * 14.0f) + (fluidAmount > 0 ? 1 : 0);
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        return getWorld().isRemote || FluidUtil.interactWithFluidHandler(playerIn, hand, fluidTank);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        if(ModHandler.isMaterialWood(material)) {
            return Pair.of(Textures.WOODEN_TANK.getParticleTexture(), getPaintingColor());
        } else {
            return Pair.of(Textures.METAL_TANK.getParticleTexture(), getPaintingColor());
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        FluidStack fluidStack = getFluidForRendering();
        int connectionsMask = 0;
        if (getWorld() != null) {
            for (EnumFacing side : EnumFacing.VALUES) {
                MetaTileEntity metaTileEntity = BlockMachine.getMetaTileEntity(getWorld(), getPos().offset(side));
                if (!(metaTileEntity instanceof MetaTileEntityTank)) continue;
                if (((MetaTileEntityTank) metaTileEntity).material != material) continue;
                connectionsMask |= 1 << side.getIndex();
            }
        }

        int paintingColor = getPaintingColor();
        if (paintingColor == DEFAULT_PAINTING_COLOR) {
            paintingColor = material.materialRGB;
        }
        int baseColor = GTUtility.convertRGBtoOpaqueRGBA_CL(paintingColor);

        if (ModHandler.isMaterialWood(material)) {
            Textures.WOODEN_TANK.render(renderState, translation, baseColor, pipeline, connectionsMask, tankSize, fluidStack);
        } else {
            Textures.METAL_TANK.render(renderState, translation, baseColor, pipeline, connectionsMask, tankSize, fluidStack);
        }
    }

    @SideOnly(Side.CLIENT)
    private FluidStack getFluidForRendering() {
        if (getWorld() == null && renderContextStack != null) {
            NBTTagCompound tagCompound = renderContextStack.getTagCompound();
            if (tagCompound != null && tagCompound.hasKey("Fluid", NBT.TAG_COMPOUND)) {
                return FluidStack.loadFluidStackFromNBT(tagCompound.getCompoundTag("Fluid"));
            }
            return null;
        }
        return fluidTank.getFluid();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_storage_capacity", tankSize));

        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound != null && tagCompound.hasKey("Fluid", NBT.TAG_COMPOUND)) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(tagCompound.getCompoundTag("Fluid"));
            if (fluidStack == null)
                return;
            tooltip.add(I18n.format("gregtech.machine.fluid_tank.fluid",
                fluidStack.amount, I18n.format(fluidStack.getUnlocalizedName())));
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("FluidInventory", ((FluidTank) fluidInventory).writeToNBT(new NBTTagCompound()));
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        ((FluidTank) this.fluidInventory).readFromNBT(data.getCompoundTag("FluidInventory"));
    }

    protected boolean canFillFluidType(FluidStack fluid) {
        return !ModHandler.isMaterialWood(material) &&
            !material.hasFlag(MatFlags.FLAMMABLE) ||
            fluid.getFluid().getTemperature(fluid) <= 325;
    }

    @Override
    protected boolean shouldSerializeInventories() {
        return false; //handled manually
    }

    private class SyncFluidTank extends WatchedFluidTank {


        public SyncFluidTank(int capacity) {
            super(capacity);
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return MetaTileEntityTank.this.canFillFluidType(fluid);
        }

        @Override
        protected void onFluidChanged(FluidStack newFluidStack, FluidStack oldFluidStack) {
            updateComparatorValue();
            if (getWorld() != null && !getWorld().isRemote) {
                onContentsChangedOnServer(newFluidStack, oldFluidStack);
            }
        }

        private void onContentsChangedOnServer(FluidStack newFluid, FluidStack oldFluidStack) {
            //update lightning value on server-side
            //clientside will update it with custom data packet
            updateLightValue();

            //send fluid amount/type change packet
            if (newFluid != null && newFluid.isFluidEqual(oldFluidStack)) {
                //if fluid wasn't removed completely or changed, but just reduced/added amount
                //compute new amount value and set it right back to the client
                writeCustomData(201, buf -> buf.writeVarInt(newFluid.amount));
            } else {
                //otherwise, write full data dump of fluid
                writeCustomData(200, buf -> {
                    buf.writeBoolean(newFluid != null);
                    if (newFluid != null) {
                        NBTTagCompound tagCompound = new NBTTagCompound();
                        newFluid.writeToNBT(tagCompound);
                        buf.writeCompoundTag(tagCompound);
                    }
                });
            }
        }

    }
}
