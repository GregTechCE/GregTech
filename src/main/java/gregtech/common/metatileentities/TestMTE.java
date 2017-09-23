package gregtech.common.metatileentities;

import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import javax.annotation.Nullable;

public class TestMTE extends MetaTileEntity {

    public TestMTE(IMetaTileEntityFactory factory) {
        super(factory);
    }

    @Override
    public boolean onScrewdriverRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public boolean onWrenchRightClick(EnumFacing side, EnumFacing wrenchingSide, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public int getSlotsCount() {
        return 0;
    }

    @Override
    public ModularUI<? extends IMetaTileEntity> createUI(EntityPlayer player) {
        return null;
    }

    @Override
    public int getTanksCount() {
        return 0;
    }

    @Override
    public boolean onRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public int getComparatorValue() {
        return 0;
    }
}
