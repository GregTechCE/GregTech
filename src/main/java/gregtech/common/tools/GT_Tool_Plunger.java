package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_Plunger_Fluid;
import gregtech.common.items.behaviors.Behaviour_Plunger_Item;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class GT_Tool_Plunger extends GT_Tool {

    @Override
    public float getBaseDamage() {
        return 1.25F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 0.25F;
    }

    @Override
    public String getCraftingSound() {
        return GregTech_API.sSoundList.get(101);
    }

    @Override
    public String getEntityHitSound() {
        return GregTech_API.sSoundList.get(101);
    }

    @Override
    public String getBreakingSound() {
        return GregTech_API.sSoundList.get(0);
    }

    @Override
    public String getMiningSound() {
        return GregTech_API.sSoundList.get(101);
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock) {
        //String tTool = aBlock.getHarvestTool(aMetaData);
        //return (tTool != null) && (tTool.equals("plunger"));
        //Really?
        return false;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.PLUNGER : null;
    }

    @Override
    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {
        aItem.addItemBehavior(aID, new Behaviour_Plunger_Item(getToolDamagePerDropConversion()));
        aItem.addItemBehavior(aID, new Behaviour_Plunger_Fluid(getToolDamagePerDropConversion()));
    }


    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(aEntity.getDisplayName())
                .appendText(TextFormatting.WHITE + " got stuck trying to escape through a Pipe while fighting " + TextFormatting.GREEN)
                .appendSibling(aPlayer.getDisplayName());
    }


}
