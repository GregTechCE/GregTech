package gregtech.common.metatileentities.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.capability.impl.EnergyContainerBatteryBuffer;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class MetaTileEntityBatteryBuffer extends TieredMetaTileEntity {

    private final int inventorySize;

    public MetaTileEntityBatteryBuffer(String metaTileEntityId, int tier, int inventorySize) {
        super(metaTileEntityId, tier);
        this.inventorySize = inventorySize;
        initializeInventory();
        reinitializeEnergyContainer();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityBatteryBuffer(metaTileEntityId, getTier(), inventorySize);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, pipeline);
        Textures.ENERGY_OUT.renderSided(getFrontFacing(), renderState, pipeline);
    }

    @Override
    protected void reinitializeEnergyContainer() {
        this.energyContainer = new EnergyContainerBatteryBuffer(this, getTier(), importItems);
    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return true;
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(inventorySize) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(((EnergyContainerBatteryBuffer) energyContainer).getBatteryContainer(stack) == null)
                    return stack; //do not allow to insert non-battery items
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return getImportItems();
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.defaultBuilder()
            .label(6, 6, getMetaName())
            .bindPlayerInventory(entityPlayer.inventory);
        int slotsPerRow = (int) Math.sqrt(inventorySize);
        int startX = 88 - (slotsPerRow * 9);
        int startY = 45 - (slotsPerRow * 9);
        for(int x = 0; x < slotsPerRow; x++) {
            for(int y = 0; y < slotsPerRow; y++) {
                builder.slot(importItems, slotsPerRow * y + x,
                    startX + 18 * x,
                    startY + 18 * y, GuiTextures.SLOT, GuiTextures.BATTERY_OVERLAY);
            }
        }
        return builder.build(getHolder(), entityPlayer);
    }
}
