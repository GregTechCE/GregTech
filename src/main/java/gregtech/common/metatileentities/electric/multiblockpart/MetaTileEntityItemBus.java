package gregtech.common.metatileentities.electric.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.render.SimpleOverlayRenderer;
import gregtech.api.render.Textures;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityItemBus extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IItemHandlerModifiable> {

    private static final int[] INVENTORY_SIZES = {1, 4, 9, 16};

    private final boolean isExportHatch;

    public MetaTileEntityItemBus(ResourceLocation metaTileEntityId, int tier, boolean isExportHatch) {
        super(metaTileEntityId, tier);
        this.isExportHatch = isExportHatch;
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityItemBus(metaTileEntityId, getTier(), isExportHatch);
    }

    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote && getTimer() % 5 == 0) {
            if(isExportHatch) {
                pushItemsIntoNearbyHandlers(getFrontFacing());
            } else {
                pullItemsFromNearbyHandlers(getFrontFacing());
            }
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if(shouldRenderOverlay()) {
            SimpleOverlayRenderer renderer = isExportHatch ? Textures.PIPE_OUT_OVERLAY : Textures.PIPE_IN_OVERLAY;
            renderer.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    private int getInventorySize() {
        return INVENTORY_SIZES[MathHelper.clamp(getTier(), 0, INVENTORY_SIZES.length - 1)];
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return isExportHatch ? new ItemStackHandler(getInventorySize()) : new ItemStackHandler(0);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return isExportHatch ? new ItemStackHandler(0) : new ItemStackHandler(getInventorySize());
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return isExportHatch ? MultiblockAbility.EXPORT_ITEMS : MultiblockAbility.IMPORT_ITEMS;
    }

    @Override
    public void registerAbilities(List<IItemHandlerModifiable> abilityList) {
        abilityList.add(isExportHatch ? this.exportItems : this.importItems);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return ModularUI.defaultBuilder()
            .label(6, 6, getMetaFullName())
            .squareOfSlots(isExportHatch ? exportItems : importItems, 0, getInventorySize(),
                true, !isExportHatch,
                GuiTextures.SLOT, isExportHatch ? GuiTextures.ARROW_OUTPUT_OVERLAY : GuiTextures.ARROW_INPUT_OVERLAY)
            .bindPlayerInventory(entityPlayer.inventory)
            .build(getHolder(), entityPlayer);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.universal.tooltip.item_storage_capacity", getInventorySize()));
    }
}
