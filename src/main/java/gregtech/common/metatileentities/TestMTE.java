package gregtech.common.metatileentities;

import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.metatileentity.WorkableMetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TestMTE extends WorkableMetaTileEntity<Recipe> {

    public TestMTE(IMetaTileEntityFactory factory, int tier, RecipeMap<Recipe, ?> recipeMap) {
        super(factory, tier, recipeMap);
    }

    @Override
    public boolean onScrewdriverRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public boolean onWrenchRightClick(EnumFacing side, EnumFacing wrenchingSide, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public IItemHandlerModifiable getImportItemHandler() {
        return new ItemStackHandler(2);
    }

    @Override
    public IItemHandlerModifiable getExportItemHandler() {
        return new ItemStackHandler(2);
    }

    @Override
    public FluidTankHandler getImportFluidHandler() {
        return new FluidTankHandler(0);
    }

    @Override
    public FluidTankHandler getExportFluidHandler() {
        return new FluidTankHandler(0);
    }

    @Override
    public ModularUI<? extends IMetaTileEntity> createUI(EntityPlayer player) {

        ModularUI.Builder<IMetaTileEntity> builder = ModularUI.builder(new GTResourceLocation("textures/gui/basicmachines/bronze_furnace.png"), 176, 166);

        builder.bindPlayerInventory(player.inventory, this::markDirty);

        return builder.build(this, player);
    }

    @Override
    public boolean onRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        if (player instanceof EntityPlayerMP) {
            MetaTileEntityUIFactory.INSTANCE.openUI(this, (EntityPlayerMP) player);
        }
        return true;
    }

    @Override
    public int getComparatorValue() {
        return 0;
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return false;
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return false;
    }

    @Override
    public long getEnergyCapacity() {
        return 0;
    }

    @Override
    public long getOutputAmperage() {
        return 0;
    }

    @Override
    public long getInputAmperage() {
        return 0;
    }
}
