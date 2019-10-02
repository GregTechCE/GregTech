package gregtech.common.metatileentities.storage;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.ImmutableList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.gui.ModularUI;
import gregtech.api.items.metaitem.DefaultSubItemHandler;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.recipes.ModHandler;
import gregtech.api.render.TankRenderer;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.type.Material.MatFlags;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.util.ByteBufUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.WatchedFluidTank;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
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
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MetaTileEntityTank extends MetaTileEntity implements IFastRenderMetaTileEntity {

    private static final int FLUID_SYNC_THROTTLE = 0;
    private final int tankSize;
    private final int maxMultiblockSize;
    private final SolidMaterial material;
    private boolean addedToMultiblock = false;
    private boolean needsCoversUpdate = false;
    private boolean isRemoved = false;
    private int blockedSides = 0;
    private int connectionMask = 0;

    private final FluidTank multiblockFluidTank;
    private List<BlockPos> connectedTanks = new ArrayList<>();

    private BlockPos lowestPos = BlockPos.ORIGIN;
    private BlockPos highestPos = BlockPos.ORIGIN;
    private TIntList tankCountByLevel = new TIntArrayList();
    private TIntList sumCapacityByLevel = new TIntArrayList();

    private BlockPos controllerPos = null;
    private WeakReference<MetaTileEntityTank> controllerCache = new WeakReference<>(null);
    private NetworkStatus networkStatus;
    private int timeSinceLastFluidSync = Integer.MAX_VALUE;
    private FluidStack lastSentFluidStack;
    private boolean needsShapeResync;
    private boolean needsFluidResync;

    public MetaTileEntityTank(ResourceLocation metaTileEntityId, SolidMaterial material, int tankSize, int maxMultiblockSize) {
        super(metaTileEntityId);
        this.tankSize = tankSize;
        this.material = material;
        this.maxMultiblockSize = maxMultiblockSize == -1 ? Integer.MAX_VALUE : maxMultiblockSize;
        this.multiblockFluidTank = new WatchedFluidTank(tankSize) {
            @Override
            protected void onFluidChanged(FluidStack newFluidStack, FluidStack oldFluidStack) {
                MetaTileEntityTank.this.onFluidChangedInternal();
            }
        };
        initializeInventory();
    }

    @Override
    public void update() {
        this.fluidInventory = getActualFluidTank();
        super.update();
        if (!getWorld().isRemote) {
            if (!addedToMultiblock) {
                this.addedToMultiblock = true;
                rescanTankMultiblocks(Collections.emptySet());
            }
            if (networkStatus == NetworkStatus.ATTACHED_TO_MULTIBLOCK) {
                writeCustomData(1, buf -> buf.writeBlockPos(controllerPos));
                this.needsFluidResync = false;
                this.networkStatus = null;
                this.needsShapeResync = false;
            }
            if (networkStatus == NetworkStatus.DETACHED_FROM_MULTIBLOCK) {
                writeCustomData(2, buf -> {});
                this.networkStatus = null;
            }
            if (isTankController() && needsShapeResync) {
                writeCustomData(4, buf -> ByteBufUtils.writeRelativeBlockList(buf, getPos(), connectedTanks));
                this.needsShapeResync = false;
            }
            if (isTankController() && needsFluidResync) {
                if (timeSinceLastFluidSync++ >= FLUID_SYNC_THROTTLE) {
                    FluidStack fluidStack = multiblockFluidTank.getFluid();
                    writeCustomData(3, buf -> ByteBufUtils.writeFluidStackDelta(buf, lastSentFluidStack, fluidStack));
                    this.lastSentFluidStack = fluidStack == null ? null : fluidStack.copy();
                    this.timeSinceLastFluidSync = 0;
                    this.needsFluidResync = false;
                }
            }
        }
        if (needsCoversUpdate) {
            this.needsCoversUpdate = false;
            recheckBlockedSides();
            if (!getWorld().isRemote) {
                rescanTankMultiblocks(recomputeNearbyMultiblocks());
            }
        }
        if (getWorld().isRemote) {
            int newConnectionMask = getConnectionMaskForTank(getPos(), blockedSides);
            if (connectionMask != newConnectionMask) {
                this.connectionMask = newConnectionMask;
                getHolder().scheduleChunkForRenderUpdate();
            }
        }
    }

    private void recheckBlockedSides() {
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
    public void onAttached() {
        super.onAttached();
        recomputeTankSize();
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        this.isRemoved = true;
        setTankController(null);
        recomputeNearbyMultiblocks();
    }

    private Set<BlockPos> recomputeNearbyMultiblocks() {
        HashSet<BlockPos> handledSet = new HashSet<>();
        for (EnumFacing side : EnumFacing.VALUES) {
            MetaTileEntityTank tank = getTankTile(getPos().offset(side));
            if (tank == null) continue;
            handledSet.addAll(tank.rescanTankMultiblocks(handledSet));
        }
        return handledSet;
    }

    private double getFluidLevelForTank(BlockPos tankPos) {
        if (!isTankController()) {
            MetaTileEntityTank controller = getControllerEntity();
            return controller == null ? 0.0 : controller.getFluidLevelForTank(tankPos);
        }
        int tankLayer = tankPos.getY() - lowestPos.getY();
        if (tankLayer >= 0 && tankLayer < sumCapacityByLevel.size()) {
            int prevLayerTotalCapacity = tankLayer > 0 ? sumCapacityByLevel.get(tankLayer - 1) : 0;
            int thisLevelCapacity = sumCapacityByLevel.get(tankLayer) - prevLayerTotalCapacity;
            double levelFill = (multiblockFluidTank.getFluidAmount() - prevLayerTotalCapacity) / (thisLevelCapacity * 1.0);
            return MathHelper.clamp(levelFill, 0.0, 1.0);
        }
        return 0.0;
    }

    private int getConnectionMaskForTank(BlockPos blockPos, int excludeMask) {
        if (!isTankController()) {
            MetaTileEntityTank controller = getControllerEntity();
            return controller == null ? 0 : controller.getConnectionMaskForTank(blockPos, excludeMask);
        }
        int resultConnectionMask = 0;
        for (EnumFacing side : EnumFacing.VALUES) {
            if ((excludeMask & 1 << side.getIndex()) > 0) continue;
            BlockPos offsetPos = blockPos.offset(side);
            if (!connectedTanks.contains(offsetPos) && !getPos().equals(offsetPos)) continue;
            resultConnectionMask |= 1 << side.getIndex();
        }
        return resultConnectionMask;
    }

    private int getFluidStoredInTank(BlockPos blockPos) {
        double fluidLevel = getFluidLevelForTank(blockPos);
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

    private boolean isTankController() {
        return controllerPos == null;
    }

    private FluidTank getActualFluidTank() {
        if (controllerPos != null) {
            MetaTileEntityTank metaTileEntityTank = getControllerEntity();
            return metaTileEntityTank == null ? new FluidTank(0) :
                metaTileEntityTank.getActualFluidTank();
        } else {
            return multiblockFluidTank;
        }
    }

    private FluidStack getActualTankFluid() {
        FluidTank fluidTank = getActualFluidTank();
        return fluidTank == null ? null : fluidTank.getFluid();
    }

    private void removeTankFromMultiblock(MetaTileEntityTank removedTank) {
        int fluidInTank = getFluidStoredInTank(removedTank.getPos());
        this.connectedTanks.remove(removedTank.getPos());
        this.needsShapeResync = true;
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
        this.connectedTanks.add(addedTank.getPos());
        this.needsShapeResync = true;
        FluidStack tankFluid = addedTank.setTankControllerInternal(this);
        recomputeTankSize();
        if (tankFluid != null) {
            multiblockFluidTank.fill(tankFluid, true);
        }
    }

    private int computeTanksInLayer(List<BlockPos> connectedTanks, int layerPos) {
        return (int) connectedTanks.stream().filter(tank -> tank.getY() == layerPos).count();
    }

    private void recomputeTankSize() {
        List<BlockPos> connectedTanks = new ArrayList<>(this.connectedTanks);
        connectedTanks.add(getPos());
        this.lowestPos = connectedTanks.stream().min(Comparator.comparing(Vec3i::getY)).get();
        this.highestPos = connectedTanks.stream().max(Comparator.comparing(Vec3i::getY)).get();
        this.tankCountByLevel.clear();
        for (int i = lowestPos.getY(); i <= highestPos.getY(); i++) {
            this.tankCountByLevel.add(computeTanksInLayer(connectedTanks, i));
        }
        recomputeByLevelSumCapacity();
        this.multiblockFluidTank.setCapacity(connectedTanks.size() * tankSize);

        if (multiblockFluidTank.getFluid() != null &&
            multiblockFluidTank.getFluidAmount() > multiblockFluidTank.getCapacity()) {
            multiblockFluidTank.getFluid().amount = multiblockFluidTank.getCapacity();
        }
    }

    private void recomputeByLevelSumCapacity() {
        int capacity = 0;
        this.sumCapacityByLevel.clear();
        for (int i = 0; i < tankCountByLevel.size(); i++) {
            int layerCapacity = tankCountByLevel.get(i) * tankSize;
            this.sumCapacityByLevel.add(capacity + layerCapacity);
            capacity += layerCapacity;
        }
    }

    private MetaTileEntityTank getControllerEntity() {
        if (controllerPos == null) {
            return null;
        }
        MetaTileEntityTank cachedController = controllerCache.get();
        if (cachedController != null) {
            if (cachedController.isValid()) {
                return cachedController;
            } else {
                controllerCache.clear();
            }
        }
        MetaTileEntity metaTileEntity = BlockMachine.getMetaTileEntity(getWorld(), controllerPos);
        if (metaTileEntity instanceof MetaTileEntityTank) {
            this.controllerCache = new WeakReference<>((MetaTileEntityTank) metaTileEntity);
            return (MetaTileEntityTank) metaTileEntity;
        }
        return null;
    }

    private FluidStack setTankControllerInternal(MetaTileEntityTank controller) {
        this.controllerPos = controller == null ? null : controller.getPos();
        this.controllerCache = new WeakReference<>(controller);
        getHolder().markAsDirty();
        if (controller == null) {
            this.networkStatus = NetworkStatus.DETACHED_FROM_MULTIBLOCK;
            return null;
        } else {
            this.networkStatus = NetworkStatus.ATTACHED_TO_MULTIBLOCK;
            FluidStack fluidStack = multiblockFluidTank.drain(Integer.MAX_VALUE, true);
            this.multiblockFluidTank.setFluid(null);
            this.lastSentFluidStack = null;
            this.needsFluidResync = false;


            return fluidStack;
        }
    }

    private void onFluidChangedInternal() {
        if (getWorld() != null && !getWorld().isRemote && isTankController()) {
            //controller fluid update - sent in update() after network status
            this.needsFluidResync = true;
        }
    }

    @Override
    public void addDebugInfo(List<String> debugInfo) {
        debugInfo.add("IsController: " + isTankController());
        debugInfo.add("ControllerPos: " + controllerPos);
        List<BlockPos> connectedTanks = new ArrayList<>(this.connectedTanks);
        while (!connectedTanks.isEmpty()) {
            int startIndex = Math.min(4, connectedTanks.size());
            debugInfo.add("ConnectedBlocks: " + connectedTanks.subList(0, startIndex));
            if (connectedTanks.size() > startIndex) {
                connectedTanks = connectedTanks.subList(startIndex, connectedTanks.size());
            } else break;
        }
        debugInfo.add("HighestPos: " + highestPos);
        debugInfo.add("LowestPos: " + lowestPos);
        debugInfo.add("Tank fluid: " + multiblockFluidTank.getFluidAmount() + "/" + multiblockFluidTank.getCapacity() + " #" + multiblockFluidTank.hashCode());
        FluidTank actualTankFluid = getActualFluidTank();
        debugInfo.add("Actual Tank fluid: " + actualTankFluid.getFluidAmount() + "/" + actualTankFluid.getCapacity() + " #" + actualTankFluid.hashCode());
        debugInfo.add("FluidLevel: " + getFluidLevelForTank(getPos()));
        debugInfo.add("FluidInTank: " + getFluidStoredInTank(getPos()));
        debugInfo.add("TankCountByLevel: " + tankCountByLevel);
        debugInfo.add("CapacityByLevel: " + sumCapacityByLevel);
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
        for (BlockPos tankPos : ImmutableList.copyOf(connectedTanks)) {
            MetaTileEntityTank metaTileEntityTank = getTankTile(tankPos);
            if (metaTileEntityTank == null) continue;
            removeTankFromMultiblock(metaTileEntityTank);
        }
        this.connectedTanks.clear();
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
        return fluidStack1 == null || fluidStack2 == null || fluidStack1.isFluidEqual(fluidStack2);
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
        return findAllConnectedBlocks(bottomStartPos, ArrayUtils.add(EnumFacing.HORIZONTALS, EnumFacing.UP), allTanks::get, maxMultiblockSize).keySet();
    }

    private Map<BlockPos, MetaTileEntityTank> findConnectedTankBlocks(BlockPos startPos) {
        return findAllConnectedBlocks(startPos, EnumFacing.VALUES, this::getTankTile, Integer.MAX_VALUE);
    }

    private static Map<BlockPos, MetaTileEntityTank> findAllConnectedBlocks(BlockPos startPos, EnumFacing[] directions, Function<BlockPos, MetaTileEntityTank> blockProvider, int maxAmount) {
        HashMap<BlockPos, MetaTileEntityTank> observedSet = new HashMap<>();
        observedSet.put(startPos, blockProvider.apply(startPos));
        MetaTileEntityTank firstNode = observedSet.get(startPos);
        MutableBlockPos currentPos = new MutableBlockPos(startPos);
        Stack<EnumFacing> moveStack = new Stack<>();
        int currentAmount = 0;
        main: while (true) {
            if (currentAmount >= maxAmount) {
                break;
            }
            for (EnumFacing facing : directions) {
                currentPos.move(facing);
                MetaTileEntityTank metaTileEntity;
                if (!observedSet.containsKey(currentPos) && (metaTileEntity = blockProvider.apply(currentPos)) != null && canTanksConnect(firstNode, metaTileEntity, facing)) {
                    observedSet.put(metaTileEntity.getPos(), metaTileEntity);
                    firstNode = metaTileEntity;
                    moveStack.push(facing.getOpposite());
                    currentAmount++;
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
        return new MetaTileEntityTank(metaTileEntityId, material, tankSize, maxMultiblockSize);
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
    public void initFromItemStackData(NBTTagCompound itemStack) {
        super.initFromItemStackData(itemStack);
        if (itemStack.hasKey(FluidHandlerItemStack.FLUID_NBT_KEY, NBT.TAG_COMPOUND)) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(itemStack.getCompoundTag(FluidHandlerItemStack.FLUID_NBT_KEY));
            this.multiblockFluidTank.fill(fluidStack, true);
        }
    }

    @Override
    public void writeItemStackData(NBTTagCompound itemStack) {
        super.writeItemStackData(itemStack);
        FluidStack fluidStack = multiblockFluidTank.drain(Integer.MAX_VALUE, false);
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
        if (isTankController()) {
            buf.writeBoolean(true);
            ByteBufUtils.writeRelativeBlockList(buf, getPos(), connectedTanks);
            recomputeTankSize();
            FluidStack fluidStack = multiblockFluidTank.getFluid();
            ByteBufUtils.writeFluidStack(buf, fluidStack);
        } else {
            buf.writeBoolean(false);
            buf.writeBlockPos(controllerPos);
        }
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        if (buf.readBoolean()) {
            this.connectedTanks = ByteBufUtils.readRelativeBlockList(buf, getPos());
            recomputeTankSize();
            FluidStack fluidStack = ByteBufUtils.readFluidStack(buf);
            this.multiblockFluidTank.setFluid(fluidStack);
            recomputeByLevelSumCapacity();
        } else {
            this.controllerPos = buf.readBlockPos();
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == 1) {
            this.controllerPos = buf.readBlockPos();
            this.controllerCache = new WeakReference<>(null);
            this.multiblockFluidTank.setFluid(null);
        } else if (dataId == 2) {
            this.controllerPos = null;
            this.controllerCache = new WeakReference<>(null);
            this.multiblockFluidTank.setFluid(null);
            this.highestPos = getPos();
            this.lowestPos = getPos();
            this.tankCountByLevel.clear();
            this.sumCapacityByLevel.clear();
            this.connectedTanks.clear();
        } else if (dataId == 3) {
            if (controllerPos == null) {
                FluidStack fluidStack = ByteBufUtils.readFluidStackDelta(buf, multiblockFluidTank.getFluid());
                this.multiblockFluidTank.setFluid(fluidStack);
            }
        } else if (dataId == 4) {
            this.connectedTanks = ByteBufUtils.readRelativeBlockList(buf, getPos());
            recomputeTankSize();
        }
    }

    private IFluidTankProperties getTankProperties() {
        IFluidTankProperties[] properties = fluidInventory.getTankProperties();
        return properties.length == 0 ? null : properties[0];
    }

    @Override
    public int getActualComparatorValue() {
        IFluidTankProperties properties = getTankProperties();
        if (properties == null) {
            return 0;
        }
        FluidStack fluidStack = properties.getContents();
        int fluidAmount = fluidStack == null ? 0 : fluidStack.amount;
        int maxCapacity = properties.getCapacity();
        float f = fluidAmount / (maxCapacity * 1.0f);
        return MathHelper.floor(f * 14.0f) + (fluidAmount > 0 ? 1 : 0);
    }

    @Override
    public int getActualLightValue() {
        IFluidTankProperties properties = getTankProperties();
        if (properties == null) {
            return 0;
        }
        FluidStack fluidStack = properties.getContents();
        if (fluidStack == null) {
            return 0;
        }
        return fluidStack.getFluid().getLuminosity(fluidStack);
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        return getWorld().isRemote || FluidUtil.interactWithFluidHandler(playerIn, hand, fluidInventory);
    }

    @SideOnly(Side.CLIENT)
    private TankRenderer getTankRenderer() {
        if(ModHandler.isMaterialWood(material)) {
            return Textures.WOODEN_TANK;
        } else return Textures.METAL_TANK;
    }

    @SideOnly(Side.CLIENT)
    private int getActualPaintingColor() {
        int paintingColor = getPaintingColorForRendering();
        if (paintingColor == DEFAULT_PAINTING_COLOR) {
            return material.materialRGB;
        }
        return paintingColor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        return Pair.of(getTankRenderer().getParticleTexture(), getActualPaintingColor());
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        int baseColor = GTUtility.convertRGBtoOpaqueRGBA_CL(getActualPaintingColor());
        getTankRenderer().render(renderState, translation, ArrayUtils.add(pipeline, new ColourMultiplier(baseColor)), connectionMask);
    }

    @Override
    public void renderMetaTileEntityFast(CCRenderState renderState, Matrix4 translation, float partialTicks) {
        double fillPercent;
        FluidStack fluidStack = getFluidForRendering();
        if (getWorld() == null) {
            fillPercent = fluidStack == null ? 0 : fluidStack.amount / (tankSize * 1.0);
        } else {
            fillPercent = getFluidLevelForTank(getPos());
        }
        if (fillPercent > 0.0) {
            getTankRenderer().renderFluid(renderState, translation, connectionMask, fillPercent, fluidStack);
        }
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos());
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
        return getActualTankFluid();
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
        if (controllerPos != null) {
            data.setTag("ControllerPos", NBTUtil.createPosTag(controllerPos));
        } else {
            data.setTag("FluidInventory", multiblockFluidTank.writeToNBT(new NBTTagCompound()));
            NBTTagList connectedTanks = new NBTTagList();
            this.connectedTanks.forEach(pos -> connectedTanks.appendTag(NBTUtil.createPosTag(pos)));
            data.setTag("ConnectedTanks", connectedTanks);
        }
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if (data.hasKey("ControllerPos")) {
            this.controllerPos = NBTUtil.getPosFromTag(data.getCompoundTag("ControllerPos"));
        } else {
            NBTTagList connectedTanks = data.getTagList("ConnectedTanks", NBT.TAG_COMPOUND);
            connectedTanks.forEach(pos -> this.connectedTanks.add(NBTUtil.getPosFromTag((NBTTagCompound) pos)));
            recomputeTankSize();
            this.multiblockFluidTank.readFromNBT(data.getCompoundTag("FluidInventory"));
            this.lastSentFluidStack = this.multiblockFluidTank.getFluid();
        }
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

    private enum NetworkStatus {
        //retain contained fluid, reset controller pos
        DETACHED_FROM_MULTIBLOCK,
        //clear contained fluid, update controller pos
        ATTACHED_TO_MULTIBLOCK
    }
}
