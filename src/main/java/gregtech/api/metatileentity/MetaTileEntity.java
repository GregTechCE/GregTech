package gregtech.api.metatileentity;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import com.google.common.base.Preconditions;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerProxy;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.ModularUI;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.common.covers.CoverPump;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.*;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class MetaTileEntity implements ICoverable {

    public static final int DEFAULT_PAINTING_COLOR = 0xFFFFFF;
    public static final IndexedCuboid6 FULL_CUBE_COLLISION = new IndexedCuboid6(null, Cuboid6.full);
    public final ResourceLocation metaTileEntityId;
    MetaTileEntityHolder holder;

    protected IItemHandlerModifiable importItems;
    protected IItemHandlerModifiable exportItems;

    protected IItemHandler itemInventory;

    protected FluidTankList importFluids;
    protected FluidTankList exportFluids;

    protected IFluidHandler fluidInventory;

    protected List<MTETrait> mteTraits = new ArrayList<>();

    protected EnumFacing frontFacing = EnumFacing.NORTH;
    protected int paintingColor = DEFAULT_PAINTING_COLOR;

    private int[] sidedRedstoneOutput = new int[6];
    private int[] sidedRedstoneInput = new int[6];
    private int cachedComparatorValue;

    private CoverBehavior[] coverBehaviors = new CoverBehavior[6];

    public MetaTileEntity(ResourceLocation metaTileEntityId) {
        this.metaTileEntityId = metaTileEntityId;
        initializeInventory();
    }

    protected void initializeInventory() {
        this.importItems = createImportItemHandler();
        this.exportItems = createExportItemHandler();
        this.itemInventory = new ItemHandlerProxy(importItems, exportItems);

        this.importFluids = createImportFluidHandler();
        this.exportFluids = createExportFluidHandler();
        this.fluidInventory = new FluidHandlerProxy(importFluids, exportFluids);
    }

    public MetaTileEntityHolder getHolder() {
        return holder;
    }

    public abstract MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder);

    public World getWorld() {
        return holder == null ? null : holder.getWorld();
    }

    public BlockPos getPos() {
        return holder == null ? null : holder.getPos();
    }

    public void markDirty() {
        if (holder != null) {
            holder.markDirty();
        }
    }

    public long getTimer() {
        return holder == null ? 0L : holder.getTimer();
    }

    public void writeCustomData(int discriminator, Consumer<PacketBuffer> dataWriter) {
        if (holder != null) {
            holder.writeCustomData(discriminator, dataWriter);
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleTexture() {
        return TextureUtils.getMissingSprite();
    }

    /**
     * ItemStack currently being rendered by this meta tile entity
     * Use this to obtain itemstack-specific data like contained fluid, painting color
     * Generally useful in combination with {@link #writeItemStackData(net.minecraft.nbt.NBTTagCompound)}
     */
    @SideOnly(Side.CLIENT)
    protected ItemStack renderContextStack;

    @SideOnly(Side.CLIENT)
    public void setRenderContextStack(ItemStack itemStack) {
        this.renderContextStack = itemStack;
    }

    /**
     * Renders this meta tile entity
     * Note that you shouldn't refer to world-related information in this method, because it
     * will be called on ItemStacks too
     *
     * @param renderState render state (either chunk batched or item)
     * @param pipeline    default set of pipeline transformations
     */
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        TextureAtlasSprite atlasSprite = TextureUtils.getMissingSprite();
        IVertexOperation[] renderPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        for (EnumFacing face : EnumFacing.VALUES) {
            Textures.renderFace(renderState, translation, renderPipeline, face, Cuboid6.full, atlasSprite);
        }
    }

    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntityDynamic(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, float partialTicks) {

    }

    @SideOnly(Side.CLIENT)
    public int getPaintingColorForRendering() {
        if (getWorld() == null && renderContextStack != null) {
            NBTTagCompound tagCompound = renderContextStack.getTagCompound();
            if (tagCompound != null && tagCompound.hasKey("PaintingColor", NBT.TAG_INT)) {
                return tagCompound.getInteger("PaintingColor");
            }
        }
        return paintingColor;
    }

    /**
     * Called from ItemBlock to initialize this MTE with data contained in ItemStack
     *
     * @param itemStack itemstack of itemblock
     */
    public void initFromItemStackData(NBTTagCompound itemStack) {
        if (itemStack.hasKey("PaintingColor", NBT.TAG_INT)) {
            setPaintingColor(itemStack.getInteger("PaintingColor"));
        }
    }

    /**
     * Called to write MTE specific data when it is destroyed to save it's state
     * into itemblock, which can be placed later to get {@link #initFromItemStackData} called
     *
     * @param itemStack itemstack from which this MTE is being placed
     */
    public void writeItemStackData(NBTTagCompound itemStack) {
        if (this.paintingColor != DEFAULT_PAINTING_COLOR) { //for machines to stack
            itemStack.setInteger("PaintingColor", this.paintingColor);
        }
    }

    public ICapabilityProvider initItemStackCapabilities(ItemStack itemStack) {
        return null;
    }

    public final String getMetaName() {
        return String.format("%s.machine.%s", metaTileEntityId.getResourceDomain(), metaTileEntityId.getResourcePath());
    }

    public final String getMetaFullName() {
        return getMetaName() + ".name";
    }

    /**
     * Adds a trait to this meta tile entity
     * traits are objects linked with meta tile entity and performing certain
     * actions. usually traits implement capabilities
     * there can be only one trait for given name
     *
     * @param trait trait object to add
     */
    void addMetaTileEntityTrait(MTETrait trait) {
        mteTraits.removeIf(otherTrait -> {
            if (trait.getName().equals(otherTrait.getName())) {
                return true;
            }
            if (otherTrait.getNetworkID() == trait.getNetworkID()) {
                String message = "Trait %s is incompatible with trait %s, as they both use same network id %d";
                throw new IllegalArgumentException(String.format(message, trait, otherTrait, trait.getNetworkID()));
            }
            return false;
        });
        this.mteTraits.add(trait);
    }

    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(0);
    }

    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(0);
    }

    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false);
    }

    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(false);
    }

    protected boolean openGUIOnRightClick() {
        return true;
    }

    /**
     * Creates a UI instance for player opening inventory of this meta tile entity
     *
     * @param entityPlayer player opening inventory
     * @return freshly created UI instance
     */
    protected abstract ModularUI createUI(EntityPlayer entityPlayer);

    public final void onCoverLeftClick(EntityPlayer playerIn, CuboidRayTraceResult result) {
        EnumFacing coverSide = ICoverable.traceCoverSide(result);
        CoverBehavior coverBehavior = coverSide == null ? null : getCoverAtSide(coverSide);
        if (coverBehavior == null || !coverBehavior.onLeftClick(playerIn, result)) {
            onLeftClick(playerIn, result.sideHit, result);
        }
    }

    public final boolean onCoverRightClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult result) {
        EnumFacing coverSide = ICoverable.traceCoverSide(result);
        CoverBehavior coverBehavior = coverSide == null ? null : getCoverAtSide(coverSide);
        EnumActionResult coverResult = coverBehavior == null ? EnumActionResult.PASS :
            coverBehavior.onRightClick(playerIn, hand, result);
        if (coverResult != EnumActionResult.PASS) {
            return coverResult == EnumActionResult.SUCCESS;
        }
        return onRightClick(playerIn, hand, result.sideHit, result);
    }

    public final boolean onCoverScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult result) {
        EnumFacing coverSide = ICoverable.traceCoverSide(result);
        CoverBehavior coverBehavior = coverSide == null ? null : getCoverAtSide(coverSide);
        EnumActionResult coverResult = coverBehavior == null ? EnumActionResult.PASS :
            coverBehavior.onScrewdriverClick(playerIn, hand, result);
        if (coverResult != EnumActionResult.PASS) {
            return coverResult == EnumActionResult.SUCCESS;
        }
        return onScrewdriverClick(playerIn, hand, result.sideHit, result);
    }

    /**
     * Called when player clicks on specific side of this meta tile entity
     *
     * @return true if something happened, so animation will be played
     */
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (!playerIn.isSneaking() && openGUIOnRightClick()) {
            if (getWorld() != null && !getWorld().isRemote) {
                MetaTileEntityUIFactory.INSTANCE.openUI(getHolder(), (EntityPlayerMP) playerIn);
            }
            return true;
        }
        return false;
    }

    /**
     * Called when player clicks wrench on specific side of this meta tile entity
     *
     * @return true if something happened, so wrench will get damaged and animation will be played
     */
    public boolean onWrenchClick(EntityPlayer playerIn, EnumHand hand, EnumFacing wrenchSide, CuboidRayTraceResult hitResult) {
        if (playerIn.isSneaking()) {
            if (wrenchSide == getFrontFacing() || !isValidFrontFacing(wrenchSide) || !hasFrontFacing()) {
                return false;
            }
            if (wrenchSide != null) {
                setFrontFacing(wrenchSide);
            }
            return true;
        }
        return false;
    }

    /**
     * Called when player clicks screwdriver on specific side of this meta tile entity
     *
     * @return true if something happened, so screwdriver will get damaged and animation will be played
     */
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        return false;
    }

    public void onLeftClick(EntityPlayer player, EnumFacing facing, CuboidRayTraceResult hitResult) {
    }

    @Nullable
    public final CoverBehavior getCoverAtSide(EnumFacing side) {
        return (side != null)? coverBehaviors[side.getIndex()] : null;
    }

    public final boolean placeCoverOnSide(EnumFacing side, ItemStack itemStack, CoverDefinition coverDefinition) {
        Preconditions.checkNotNull(side, "side");
        Preconditions.checkNotNull(coverDefinition, "coverDefinition");
        CoverBehavior coverBehavior = coverDefinition.createCoverBehavior(this, side);
        if (!canPlaceCoverOnSide(side) || !coverBehavior.canAttach()) {
            return false;
        }
        if (coverBehaviors[side.getIndex()] != null) {
            removeCover(side);
        }
        this.coverBehaviors[side.getIndex()] = coverBehavior;
        coverBehavior.onAttached(itemStack);
        writeCustomData(-5, buffer -> {
            buffer.writeByte(side.getIndex());
            buffer.writeVarInt(CoverDefinition.getNetworkIdForCover(coverDefinition));
            coverBehavior.writeInitialSyncData(buffer);
        });
        if (getHolder() != null) {
            getHolder().notifyBlockUpdate();
            getHolder().markDirty();
        }
        return true;
    }

    public final boolean removeCover(EnumFacing side) {
        Preconditions.checkNotNull(side, "side");
        CoverBehavior coverBehavior = getCoverAtSide(side);
        if (coverBehavior == null) {
            return false;
        }
        List<ItemStack> drops = coverBehavior.getDrops();
        coverBehavior.onRemoved();
        this.coverBehaviors[side.getIndex()] = null;
        for (ItemStack dropStack : drops) {
            Block.spawnAsEntity(getWorld(), getPos(), dropStack);
        }
        writeCustomData(-6, buffer -> buffer.writeByte(side.getIndex()));
        if (getHolder() != null) {
            getHolder().notifyBlockUpdate();
            getHolder().markDirty();
        }
        return true;
    }

    public final void dropAllCovers() {
        for (EnumFacing coverSide : EnumFacing.VALUES) {
            CoverBehavior coverBehavior = coverBehaviors[coverSide.getIndex()];
            if (coverBehavior == null) continue;
            List<ItemStack> drops = coverBehavior.getDrops();
            coverBehavior.onRemoved();
            for (ItemStack dropStack : drops) {
                Block.spawnAsEntity(getWorld(), getPos(), dropStack);
            }
        }
    }

    public boolean canPlaceCoverOnSide(EnumFacing side) {
        if (hasFrontFacing() && side == getFrontFacing()) {
            //covers cannot be placed on this side
            return false;
        }
        ArrayList<IndexedCuboid6> collisionList = new ArrayList<>();
        addCollisionBoundingBox(collisionList);
        //noinspection RedundantIfStatement
        if (!ICoverable.checkCoverCollision(side, collisionList, getCoverPlateThickness())) {
            //cover collision box overlaps with meta tile entity collision box
            return false;
        }
        return true;
    }

    /**
     * @return the cover plate thickness. It is used to render cover's base plate
     * if this meta tile entity is not full block length, and also
     * to check whatever cover placement is possible on specified side,
     * because cover cannot be placed if collision boxes of machine and it's plate overlap
     * If zero, it is expected that machine is full block and plate doesn't need to be rendered
     */
    @Override
    public double getCoverPlateThickness() {
        return 0.0;
    }

    @Override
    public boolean shouldRenderBackSide() {
        return !isOpaqueCube();
    }

    public void onLoad() {
        this.cachedComparatorValue = getActualComparatorValue();
        for (EnumFacing side : EnumFacing.VALUES) {
            this.sidedRedstoneInput[side.getIndex()] = getRedstonePower(this, side);
        }
    }

    public void onUnload() {
    }

    public boolean canConnectRedstone(@Nullable EnumFacing side) {
        CoverBehavior coverBehavior = getCoverAtSide(side);
        return coverBehavior != null && coverBehavior.canConnectRedstone();
    }

    @Override
    public final int getInputRedstoneSignal(EnumFacing side, boolean ignoreCover) {
        if(!ignoreCover && getCoverAtSide(side) != null) {
            return 0; //covers block input redstone signal for machine
        }
        return sidedRedstoneInput[side.getIndex()];
    }

    public final boolean isBlockRedstonePowered() {
        for(EnumFacing side : EnumFacing.VALUES) {
            if(getInputRedstoneSignal(side, false) > 0) {
                return true;
            }
        }
        return false;
    }

    public void updateInputRedstoneSignals() {
        for (EnumFacing side : EnumFacing.VALUES) {
            int redstoneValue = getRedstonePower(this, side);
            int currentValue = sidedRedstoneInput[side.getIndex()];
            if(redstoneValue != currentValue) {
                this.sidedRedstoneInput[side.getIndex()] = redstoneValue;
                CoverBehavior coverBehavior = getCoverAtSide(side);
                if(coverBehavior != null) {
                    coverBehavior.onRedstoneInputSignalChange(redstoneValue);
                }
            }
        }
    }

    private static int getRedstonePower(MetaTileEntity metaTileEntity, EnumFacing side) {
        return metaTileEntity.getWorld().getRedstonePower(metaTileEntity.getPos().offset(side), side);
    }

    public int getActualComparatorValue() {
        return 0;
    }

    public final int getComparatorValue() {
        return cachedComparatorValue;
    }

    public void updateComparatorValue() {
        int newComparatorValue = getActualComparatorValue();
        if (cachedComparatorValue != newComparatorValue) {
            this.cachedComparatorValue = newComparatorValue;
            if (getWorld() != null && !getWorld().isRemote) {
                notifyBlockUpdate();
            }
        }
    }

    public void update() {
        for (MTETrait mteTrait : this.mteTraits) {
            if (shouldUpdate(mteTrait)) {
                mteTrait.update();
            }
        }
        if (!getWorld().isRemote) {
            for (CoverBehavior coverBehavior : coverBehaviors) {
                if (coverBehavior instanceof ITickable) {
                    ((ITickable) coverBehavior).update();
                }
            }
        }
    }

    protected boolean shouldUpdate(MTETrait trait) {
        return true;
    }

    public final ItemStack getStackForm(int amount) {
        int metaTileEntityIntId = GregTechAPI.META_TILE_ENTITY_REGISTRY.getIdByObjectName(metaTileEntityId);
        return new ItemStack(GregTechAPI.MACHINE, amount, metaTileEntityIntId);
    }

    public final ItemStack getStackForm() {
        return getStackForm(1);
    }

    /**
     * Add special drops which this meta tile entity contains here
     * Meta tile entity item is ALREADY added into this list
     * Do NOT add inventory contents in this list - it will be dropped automatically when breakBlock is called
     * This will only be called if meta tile entity is broken with proper tool (i.e wrench)
     *
     * @param dropsList list of meta tile entity drops
     * @param harvester harvester of this meta tile entity, or null
     */
    public void getDrops(NonNullList<ItemStack> dropsList, @Nullable EntityPlayer harvester) {
    }

    public ItemStack getPickItem(CuboidRayTraceResult result, EntityPlayer player) {
        IndexedCuboid6 hitCuboid = result.cuboid6;
        if (hitCuboid.data instanceof EnumFacing) {
            //data instanceof EnumFacing -> Cover plate hit
            CoverBehavior behavior = getCoverAtSide((EnumFacing) hitCuboid.data);
            return behavior == null ? ItemStack.EMPTY : behavior.getCoverDefinition().getDropItemStack();
        } else if (hitCuboid.data == null) {
            //data is null -> MetaTileEntity hull hit
            CoverBehavior behavior = getCoverAtSide(result.sideHit);
            if (behavior != null) {
                return behavior.getCoverDefinition().getDropItemStack();
            }
            return getStackForm();
        } else {
            return ItemStack.EMPTY;
        }
    }

    /**
     * Whether this tile entity represents completely opaque cube
     *
     * @return true if machine is opaque
     */
    public boolean isOpaqueCube() {
        return true;
    }

    /**
     * Whether this tile entity should get it's {@link #renderMetaTileEntityDynamic(CCRenderState, Matrix4, IVertexOperation[], float)} called
     * It will be called every render frame to render meta tile entity with fast TESR
     *
     * @return true if meta tile entity should use FastTESR
     */
    public boolean requiresDynamicRendering() {
        return true;
    }

    public boolean shouldRenderInPass(int pass) {
        return pass == 0;
    }

    public int getLightValue() {
        return 0;
    }

    public int getLightOpacity() {
        return 255;
    }

    /**
     * Called to obtain list of AxisAlignedBB used for collision testing, highlight rendering
     * and ray tracing this meta tile entity's block in world
     */
    public void addCollisionBoundingBox(List<IndexedCuboid6> collisionList) {
        collisionList.add(FULL_CUBE_COLLISION);
    }

    /**
     * Retrieves face shape on the current side of this meta tile entity
     */
    public BlockFaceShape getFaceShape(EnumFacing side) {
        return isOpaqueCube() ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    /**
     * @return tool required to dismantle this meta tile entity properly
     */
    public String getHarvestTool() {
        return "wrench";
    }

    /**
     * @return minimal level of tool required to dismantle this meta tile entity properly
     */
    public int getHarvestLevel() {
        return 1;
    }

    public void writeInitialSyncData(PacketBuffer buf) {
        buf.writeByte(this.frontFacing.getIndex());
        buf.writeInt(this.paintingColor);
        buf.writeShort(mteTraits.size());
        for (MTETrait trait : mteTraits) {
            buf.writeVarInt(trait.getNetworkID());
            trait.writeInitialData(buf);
        }
        for (EnumFacing coverSide : EnumFacing.VALUES) {
            CoverBehavior coverBehavior = getCoverAtSide(coverSide);
            if (coverBehavior != null) {
                int coverId = CoverDefinition.getNetworkIdForCover(coverBehavior.getCoverDefinition());
                buf.writeVarInt(coverId);
                coverBehavior.writeInitialSyncData(buf);
            } else {
                buf.writeVarInt(-1);
            }
        }
    }

    public void receiveInitialSyncData(PacketBuffer buf) {
        this.frontFacing = EnumFacing.VALUES[buf.readByte()];
        this.paintingColor = buf.readInt();
        int amountOfTraits = buf.readShort();
        for (int i = 0; i < amountOfTraits; i++) {
            int traitNetworkId = buf.readVarInt();
            MTETrait trait = mteTraits.stream().filter(otherTrait -> otherTrait.getNetworkID() == traitNetworkId).findAny().get();
            trait.receiveInitialData(buf);
        }
        for (EnumFacing coverSide : EnumFacing.VALUES) {
            int coverId = buf.readVarInt();
            if (coverId != -1) {
                CoverDefinition coverDefinition = CoverDefinition.getCoverByNetworkId(coverId);
                CoverBehavior coverBehavior = coverDefinition.createCoverBehavior(this, coverSide);
                coverBehavior.readInitialSyncData(buf);
                this.coverBehaviors[coverSide.getIndex()] = coverBehavior;
            }
        }
    }

    public void writeTraitData(MTETrait trait, int internalId, Consumer<PacketBuffer> dataWriter) {
        writeCustomData(-4, buffer -> {
            buffer.writeVarInt(trait.getNetworkID());
            buffer.writeVarInt(internalId);
            dataWriter.accept(buffer);
        });
    }

    public void writeCoverData(CoverBehavior cover, int internalId, Consumer<PacketBuffer> dataWriter) {
        writeCustomData(-7, buffer -> {
            buffer.writeByte(cover.attachedSide.getIndex());
            buffer.writeVarInt(internalId);
            dataWriter.accept(buffer);
        });
    }

    public void receiveCustomData(int dataId, PacketBuffer buf) {
        if (dataId == -2) {
            this.frontFacing = EnumFacing.VALUES[buf.readByte()];
            getHolder().scheduleChunkForRenderUpdate();
        } else if (dataId == -3) {
            this.paintingColor = buf.readInt();
            getHolder().scheduleChunkForRenderUpdate();
        } else if (dataId == -4) {
            int traitNetworkId = buf.readVarInt();
            MTETrait trait = mteTraits.stream().filter(otherTrait -> otherTrait.getNetworkID() == traitNetworkId).findAny().get();
            int internalId = buf.readVarInt();
            trait.receiveCustomData(internalId, buf);
        } else if (dataId == -5) {
            //cover placement event
            EnumFacing placementSide = EnumFacing.VALUES[buf.readByte()];
            int coverId = buf.readVarInt();
            CoverDefinition coverDefinition = CoverDefinition.getCoverByNetworkId(coverId);
            CoverBehavior coverBehavior = coverDefinition.createCoverBehavior(this, placementSide);
            this.coverBehaviors[placementSide.getIndex()] = coverBehavior;
            coverBehavior.readInitialSyncData(buf);
            getHolder().scheduleChunkForRenderUpdate();
        } else if (dataId == -6) {
            //cover removed event
            EnumFacing placementSide = EnumFacing.VALUES[buf.readByte()];
            this.coverBehaviors[placementSide.getIndex()] = null;
            getHolder().scheduleChunkForRenderUpdate();
        } else if (dataId == -7) {
            //cover custom data received
            EnumFacing coverSide = EnumFacing.VALUES[buf.readByte()];
            CoverBehavior coverBehavior = getCoverAtSide(coverSide);
            int internalId = buf.readVarInt();
            if (coverBehavior != null) {
                coverBehavior.readUpdateData(internalId, buf);
            }
        }
    }

    public BlockFaceShape getCoverFaceShape(EnumFacing side) {
        if (getCoverAtSide(side) != null) {
            return BlockFaceShape.SOLID; //covers are always solid
        }
        return getFaceShape(side);
    }

    public final <T> T getCoverCapability(Capability<T> capability, EnumFacing side) {
        boolean isCoverable = capability == GregtechTileCapabilities.CAPABILITY_COVERABLE;
        CoverBehavior coverBehavior = side == null ? null : getCoverAtSide(side);
        T originalCapability = getCapability(capability, side);
        if (coverBehavior != null && !isCoverable) {
            return coverBehavior.getCapability(capability, originalCapability);
        }
        return originalCapability;
    }

    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_COVERABLE) {
            return GregtechTileCapabilities.CAPABILITY_COVERABLE.cast(this);
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY &&
            getFluidInventory().getTankProperties().length > 0) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidInventory());
        } else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY &&
            getItemInventory().getSlots() > 0) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getItemInventory());
        }
        for (MTETrait mteTrait : this.mteTraits) {
            T capabilityResult = mteTrait.getCapability(capability);
            if(capabilityResult != null) {
                return capabilityResult;
            }
        }
        return null;
    }

    public boolean fillInternalTankFromFluidContainer(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, int inputSlot, int outputSlot) {
        ItemStack inputContainerStack = importItems.extractItem(inputSlot, 1, true);
        FluidActionResult result = FluidUtil.tryEmptyContainer(inputContainerStack, importFluids, Integer.MAX_VALUE, null, false);
        if (result.isSuccess()) {
            ItemStack remainingItem = result.getResult();
            if (ItemStack.areItemStacksEqual(inputContainerStack, remainingItem))
                return false; //do not fill if item stacks match
            if (!remainingItem.isEmpty() && !exportItems.insertItem(outputSlot, remainingItem, true).isEmpty())
                return false; //do not fill if can't put remaining item
            FluidUtil.tryEmptyContainer(inputContainerStack, importFluids, Integer.MAX_VALUE, null, true);
            importItems.extractItem(inputSlot, 1, false);
            exportItems.insertItem(outputSlot, remainingItem, false);
            return true;
        }
        return false;
    }

    public boolean fillContainerFromInternalTank(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, int inputSlot, int outputSlot) {
        ItemStack emptyContainer = importItems.extractItem(inputSlot, 1, true);
        FluidActionResult result = FluidUtil.tryFillContainer(emptyContainer, exportFluids, Integer.MAX_VALUE, null, false);
        if (result.isSuccess()) {
            ItemStack remainingItem = result.getResult();
            if (!remainingItem.isEmpty() && !exportItems.insertItem(outputSlot, remainingItem, true).isEmpty())
                return false;
            FluidUtil.tryFillContainer(emptyContainer, exportFluids, Integer.MAX_VALUE, null, true);
            importItems.extractItem(inputSlot, 1, false);
            exportItems.insertItem(outputSlot, remainingItem, false);
            return true;
        }
        return false;
    }

    public void pushFluidsIntoNearbyHandlers(EnumFacing... allowedFaces) {
        PooledMutableBlockPos blockPos = PooledMutableBlockPos.retain();
        for (EnumFacing nearbyFacing : allowedFaces) {
            blockPos.setPos(getPos()).move(nearbyFacing);
            TileEntity tileEntity = getWorld().getTileEntity(blockPos);
            if (tileEntity == null) {
                continue;
            }
            IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nearbyFacing.getOpposite());
            //use getCoverCapability so fluid tank index filtering and fluid filtering covers will work properly
            IFluidHandler myFluidHandler = getCoverCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nearbyFacing);
            if (fluidHandler == null || myFluidHandler == null) {
                continue;
            }
            CoverPump.moveHandlerFluids(myFluidHandler, fluidHandler, Integer.MAX_VALUE, CoverPump.ALWAYS_TRUE);
        }
        blockPos.release();
    }

    public void pullFluidsFromNearbyHandlers(EnumFacing... allowedFaces) {
        PooledMutableBlockPos blockPos = PooledMutableBlockPos.retain();
        for (EnumFacing nearbyFacing : allowedFaces) {
            blockPos.setPos(getPos()).move(nearbyFacing);
            TileEntity tileEntity = getWorld().getTileEntity(blockPos);
            if (tileEntity == null) {
                continue;
            }
            IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nearbyFacing.getOpposite());
            //use getCoverCapability so fluid tank index filtering and fluid filtering covers will work properly
            IFluidHandler myFluidHandler = getCoverCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nearbyFacing);
            if (fluidHandler == null || myFluidHandler == null) {
                continue;
            }
            CoverPump.moveHandlerFluids(fluidHandler, myFluidHandler, Integer.MAX_VALUE, CoverPump.ALWAYS_TRUE);
        }
        blockPos.release();
    }

    public void pushItemsIntoNearbyHandlers(EnumFacing... allowedFaces) {
        PooledMutableBlockPos blockPos = PooledMutableBlockPos.retain();
        for (EnumFacing nearbyFacing : allowedFaces) {
            blockPos.setPos(getPos()).move(nearbyFacing);
            TileEntity tileEntity = getWorld().getTileEntity(blockPos);
            if (tileEntity == null) {
                continue;
            }
            IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, nearbyFacing.getOpposite());
            //use getCoverCapability so item/ore dictionary filter covers will work properly
            IItemHandler myItemHandler = getCoverCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, nearbyFacing);
            if (itemHandler == null) {
                continue;
            }
            moveInventoryItems(myItemHandler, itemHandler);
        }
        blockPos.release();
    }

    public void pullItemsFromNearbyHandlers(EnumFacing... allowedFaces) {
        PooledMutableBlockPos blockPos = PooledMutableBlockPos.retain();
        for (EnumFacing nearbyFacing : allowedFaces) {
            blockPos.setPos(getPos()).move(nearbyFacing);
            TileEntity tileEntity = getWorld().getTileEntity(blockPos);
            if (tileEntity == null) {
                continue;
            }
            IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, nearbyFacing.getOpposite());
            //use getCoverCapability so item/ore dictionary filter covers will work properly
            IItemHandler myItemHandler = getCoverCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, nearbyFacing);
            if (itemHandler == null) {
                continue;
            }
            moveInventoryItems(itemHandler, myItemHandler);
        }
        blockPos.release();
    }

    protected static void moveInventoryItems(IItemHandler sourceInventory, IItemHandler targetInventory) {
        for (int srcIndex = 0; srcIndex < sourceInventory.getSlots(); srcIndex++) {
            ItemStack sourceStack = sourceInventory.extractItem(srcIndex, Integer.MAX_VALUE, true);
            if (sourceStack.isEmpty()) {
                continue;
            }
            ItemStack remainder = ItemHandlerHelper.insertItemStacked(targetInventory, sourceStack, true);
            int amountToInsert = sourceStack.getCount() - remainder.getCount();
            if (amountToInsert > 0) {
                sourceStack = sourceInventory.extractItem(srcIndex, amountToInsert, false);
                ItemHandlerHelper.insertItemStacked(targetInventory, sourceStack, false);
            }
        }
    }

    public static boolean isItemHandlerEmpty(IItemHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty())
                return false;
        }
        return true;
    }

    public static boolean addItemsToItemHandler(IItemHandler handler, boolean simulate, NonNullList<ItemStack> items) {
        boolean insertedAll = true;
        for (ItemStack stack : items) {
            insertedAll &= ItemHandlerHelper.insertItemStacked(handler, stack, simulate).isEmpty();
            if (!insertedAll && simulate) return false;
        }
        return insertedAll;
    }

    public static boolean addFluidsToFluidHandler(IFluidHandler handler, boolean simulate, List<FluidStack> items) {
        boolean filledAll = true;
        for (FluidStack stack : items) {
            int filled = handler.fill(stack, !simulate);
            filledAll &= filled == stack.amount;
            if (!filledAll && simulate) return false;
        }
        return filledAll;
    }

    public final int getOutputRedstoneSignal(@Nullable EnumFacing side) {
        if(side == null) {
            return getHighestOutputRedstoneSignal();
        }
        CoverBehavior behavior = getCoverAtSide(side);
        int sidedOutput = sidedRedstoneOutput[side.getIndex()];
        return behavior == null ? sidedOutput : behavior.getRedstoneSignalOutput();
    }

    public final int getHighestOutputRedstoneSignal() {
        int highestSignal = 0;
        for(EnumFacing side : EnumFacing.VALUES) {
            CoverBehavior behavior = getCoverAtSide(side);
            int sidedOutput = sidedRedstoneOutput[side.getIndex()];
            int sideResult = behavior == null ? sidedOutput : behavior.getRedstoneSignalOutput();
            highestSignal = Math.max(highestSignal, sideResult);
        }
        return highestSignal;
    }

    public final void setOutputRedstoneSignal(EnumFacing side, int strength) {
        Preconditions.checkNotNull(side, "side");
        this.sidedRedstoneOutput[side.getIndex()] = strength;
        if (getWorld() != null && !getWorld().isRemote && getCoverAtSide(side) == null) {
            notifyBlockUpdate();
            markDirty();
        }
    }

    @Override
    public void notifyBlockUpdate() {
        getHolder().notifyBlockUpdate();
    }

    @Override
    public void scheduleRenderUpdate() {
        getHolder().scheduleChunkForRenderUpdate();
    }

    public void setFrontFacing(EnumFacing frontFacing) {
        Preconditions.checkNotNull(frontFacing, "frontFacing");
        this.frontFacing = frontFacing;
        if (getWorld() != null && !getWorld().isRemote) {
            getHolder().notifyBlockUpdate();
            markDirty();
            writeCustomData(-2, buf -> buf.writeByte(frontFacing.getIndex()));
            mteTraits.forEach(trait -> trait.onFrontFacingSet(frontFacing));
        }
    }

    public void setPaintingColor(int paintingColor) {
        this.paintingColor = paintingColor;
        if (getWorld() != null && !getWorld().isRemote) {
            getHolder().notifyBlockUpdate();
            markDirty();
            writeCustomData(-3, buf -> buf.writeInt(paintingColor));
        }
    }

    public boolean isValidFrontFacing(EnumFacing facing) {
        return facing != EnumFacing.UP && facing != EnumFacing.DOWN;
    }

    public boolean hasFrontFacing() {
        return true;
    }

    /**
     * @return true if this meta tile entity should serialize it's export and import inventories
     * Useful when you use your own unified inventory and don't need these dummies to be saved
     */
    protected boolean shouldSerializeInventories() {
        return true;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("FrontFacing", frontFacing.getIndex());
        data.setInteger("PaintingColor", paintingColor);

        if (shouldSerializeInventories()) {
            GTUtility.writeItems(importItems, "ImportInventory", data);
            GTUtility.writeItems(exportItems, "ExportInventory", data);

            data.setTag("ImportFluidInventory", importFluids.serializeNBT());
            data.setTag("ExportFluidInventory", exportFluids.serializeNBT());
        }

        for (MTETrait mteTrait : this.mteTraits) {
            data.setTag(mteTrait.getName(), mteTrait.serializeNBT());
        }

        NBTTagList coversList = new NBTTagList();
        for (EnumFacing coverSide : EnumFacing.VALUES) {
            CoverBehavior coverBehavior = coverBehaviors[coverSide.getIndex()];
            if (coverBehavior != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                ResourceLocation coverId = coverBehavior.getCoverDefinition().getCoverId();
                tagCompound.setString("CoverId", coverId.toString());
                tagCompound.setByte("Side", (byte) coverSide.getIndex());
                coverBehavior.writeToNBT(tagCompound);
                coversList.appendTag(tagCompound);
            }
        }
        data.setTag("Covers", coversList);
        return data;
    }

    public void readFromNBT(NBTTagCompound data) {
        this.frontFacing = EnumFacing.VALUES[data.getInteger("FrontFacing")];
        this.paintingColor = data.getInteger("PaintingColor");

        if (shouldSerializeInventories()) {
            GTUtility.readItems(importItems, "ImportInventory", data);
            GTUtility.readItems(exportItems, "ExportInventory", data);

            importFluids.deserializeNBT(data.getCompoundTag("ImportFluidInventory"));
            exportFluids.deserializeNBT(data.getCompoundTag("ExportFluidInventory"));
        }

        for (MTETrait mteTrait : this.mteTraits) {
            NBTTagCompound traitCompound = data.getCompoundTag(mteTrait.getName());
            mteTrait.deserializeNBT(traitCompound);
        }

        NBTTagList coversList = data.getTagList("Covers", NBT.TAG_COMPOUND);
        for (int index = 0; index < coversList.tagCount(); index++) {
            NBTTagCompound tagCompound = coversList.getCompoundTagAt(index);
            if (tagCompound.hasKey("CoverId", NBT.TAG_STRING)) {
                EnumFacing coverSide = EnumFacing.VALUES[tagCompound.getByte("Side")];
                ResourceLocation coverId = new ResourceLocation(tagCompound.getString("CoverId"));
                CoverDefinition coverDefinition = CoverDefinition.getCoverById(coverId);
                CoverBehavior coverBehavior = coverDefinition.createCoverBehavior(this, coverSide);
                coverBehavior.readFromNBT(tagCompound);
                this.coverBehaviors[coverSide.getIndex()] = coverBehavior;
            }
        }
    }

    @Override
    public boolean isValid() {
        return getHolder() != null && getHolder().isValid();
    }

    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        clearInventory(itemBuffer, importItems);
        clearInventory(itemBuffer, exportItems);
    }

    public static void clearInventory(NonNullList<ItemStack> itemBuffer, IItemHandlerModifiable inventory) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stackInSlot = inventory.getStackInSlot(i);
            if (!stackInSlot.isEmpty()) {
                inventory.setStackInSlot(i, ItemStack.EMPTY);
                itemBuffer.add(stackInSlot);
            }
        }
    }

    /**
     * Called from breakBlock right before meta tile entity destruction
     * at this stage tile entity inventory is already dropped on ground, but drops aren't fetched yet
     * tile entity will still get getDrops called after this, if player broke block
     */
    public void onRemoval() {
    }

    public EnumFacing getFrontFacing() {
        return frontFacing;
    }

    @Override
    public int getPaintingColor() {
        return paintingColor;
    }

    public IItemHandler getItemInventory() {
        return itemInventory;
    }

    public IFluidHandler getFluidInventory() {
        return fluidInventory;
    }

    public IItemHandlerModifiable getImportItems() {
        return importItems;
    }

    public IItemHandlerModifiable getExportItems() {
        return exportItems;
    }

    public FluidTankList getImportFluids() {
        return importFluids;
    }

    public FluidTankList getExportFluids() {
        return exportFluids;
    }

}