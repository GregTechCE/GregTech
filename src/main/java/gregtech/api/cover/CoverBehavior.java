package gregtech.api.cover;


import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.capability.ConfigurationContext;
import gregtech.api.capability.IConfigurable;
import gregtech.api.gui.IUIHolder;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.render.SimpleSidedCubeRenderer.RenderSide;
import gregtech.api.render.Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents cover instance attached on the specific side of meta tile entity
 * Cover filters out interaction and logic of meta tile entity
 * <p>
 * Can implement {@link net.minecraft.util.ITickable} to listen to meta tile entity updates
 */
public abstract class CoverBehavior implements IUIHolder, IConfigurable {

    private CoverDefinition coverDefinition;
    public final ICoverable coverHolder;
    public final EnumFacing attachedSide;
    private int redstoneSignalOutput;

    public CoverBehavior(ICoverable coverHolder, EnumFacing attachedSide) {
        this.coverHolder = coverHolder;
        this.attachedSide = attachedSide;
    }

    final void setCoverDefinition(CoverDefinition coverDefinition) {
        this.coverDefinition = coverDefinition;
    }

    public final CoverDefinition getCoverDefinition() {
        return coverDefinition;
    }

    public final void setRedstoneSignalOutput(int redstoneSignalOutput) {
        this.redstoneSignalOutput = redstoneSignalOutput;
        coverHolder.notifyBlockUpdate();
        coverHolder.markDirty();
    }

    public final int getRedstoneSignalOutput() {
        return redstoneSignalOutput;
    }

    public final int getRedstoneSignalInput() {
        return coverHolder.getInputRedstoneSignal(attachedSide, true);
    }

    public void onRedstoneInputSignalChange(int newSignalStrength) {
    }

    public boolean canConnectRedstone() {
        return false;
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        if(redstoneSignalOutput > 0) {
            tagCompound.setInteger("RedstoneSignal", redstoneSignalOutput);
        }
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        this.redstoneSignalOutput = tagCompound.getInteger("RedstoneSignal");
    }

    @Override
    public ResourceLocation getConfigurationID() {
        return getCoverDefinition().getCoverId();
    }

    @Override
    public String getConfigurationName() {
        // FIXME: how to do this properly?
        final ItemStack coverStack = getCoverDefinition().getDropItemStack();
        final Item item = coverStack.getItem();
        if (item instanceof MetaItem) {
            MetaItem<?> metaItem = (MetaItem<?>) item;
            MetaItem<?>.MetaValueItem metaValueItem = metaItem.getItem(coverStack);
            return String.format("metaitem.%s.name", metaValueItem.unlocalizedName);
        }
        return String.format("%s.name", coverStack.getTranslationKey());
    }

    @Override
    public NBTTagCompound copyConfiguration(final ConfigurationContext context) {
        return new NBTTagCompound();
    }

    @Override
    public void pasteConfiguration(final ConfigurationContext context, final NBTTagCompound configuration) {
        // nothing by default
    }

    public void writeInitialSyncData(PacketBuffer packetBuffer) {
    }

    public void readInitialSyncData(PacketBuffer packetBuffer) {
    }

    public void readUpdateData(int id, PacketBuffer packetBuffer) {
    }

    public final void writeUpdateData(int id, Consumer<PacketBuffer> writer) {
        coverHolder.writeCoverData(this, id, writer);
    }

    /**
     * Called on server side to check whether cover can be attached to given meta tile entity
     *
     * @return true if cover can be attached, false otherwise
     */
    public abstract boolean canAttach();

    /**
     * Will be called on server side after the cover attachment to the meta tile entity
     * Cover can change it's internal state here and it will be synced to client with {@link #writeInitialSyncData(PacketBuffer)}
     *
     * @param itemStack the item cover was attached from
     */
    public void onAttached(ItemStack itemStack) {
    }

    public boolean shouldCoverInteractWithOutputside() {
        return false;
    }

    public ItemStack getPickItem() {
        return coverDefinition.getDropItemStack();
    }

    public List<ItemStack> getDrops() {
        return Lists.newArrayList(getPickItem());
    }

    /**
     * Called prior to cover removing on the server side
     * Will also be called during machine dismantling, as machine loses installed covers after that
     */
    public void onRemoved() {
    }
    
    public boolean shouldRenderConnected() {
        return true;
    }

    public boolean canPipePassThrough() {
        return false;
    }

    public boolean canRenderBackside() {
        return true;
    }

    public boolean onLeftClick(EntityPlayer entityPlayer, CuboidRayTraceResult hitResult) {
        return false;
    }

    public EnumActionResult onRightClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        return EnumActionResult.PASS;
    }

    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        return EnumActionResult.PASS;
    }

    /**
     * Will be called for each capability request to meta tile entity
     * Cover can override meta tile entity capabilities, modify their values, or deny accessing them
     *
     * @param defaultValue value of the capability from meta tile entity itself
     * @return result capability value external caller will receive
     */
    public <T> T getCapability(Capability<T> capability, T defaultValue) {
        return defaultValue;
    }

    /**
     * Called on client side to render this cover on the machine's face
     * It will be automatically translated to prevent Z-fighting with machine faces
     */
    @SideOnly(Side.CLIENT)
    public abstract void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer);

    @SideOnly(Side.CLIENT)
    public boolean canRenderInLayer(BlockRenderLayer renderLayer) {
        return renderLayer == BlockRenderLayer.CUTOUT;
    }

    @SideOnly(Side.CLIENT)
    public void renderCoverPlate(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer) {
        TextureAtlasSprite casingSide = getPlateSprite();
        for (EnumFacing coverPlateSide : EnumFacing.VALUES) {
            boolean isAttachedSide = attachedSide.getAxis() == coverPlateSide.getAxis();
            if (isAttachedSide) {
                Textures.renderFace(renderState, translation, pipeline, coverPlateSide, plateBox, casingSide);
            } else if (coverHolder.getCoverAtSide(coverPlateSide) == null) {
                Textures.renderFace(renderState, translation, pipeline, coverPlateSide, plateBox, casingSide);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    protected TextureAtlasSprite getPlateSprite() {
        return Textures.VOLTAGE_CASINGS[GTValues.LV].getSpriteOnSide(RenderSide.SIDE);
    }

    @Override
    public final boolean isValid() {
        return coverHolder.isValid() && coverHolder.getCoverAtSide(attachedSide) == this;
    }

    @Override
    public boolean isRemote() {
        return coverHolder.getWorld().isRemote;
    }

    @Override
    public final void markAsDirty() {
        coverHolder.markDirty();
    }
}
