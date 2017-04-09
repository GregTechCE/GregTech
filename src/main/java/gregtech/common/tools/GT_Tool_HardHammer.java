package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_Prospecting;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Arrays;
import java.util.List;

public class GT_Tool_HardHammer extends GT_Tool {

    public static final List<String> mEffectiveList = Arrays.asList(
            EntityIronGolem.class.getName(),
            "EntityTowerGuardian");

    @Override
    public float getNormalDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer) {
        String tName = aEntity.getClass().getName();
        tName = tName.substring(tName.lastIndexOf('.') + 1);
        return (mEffectiveList.contains(tName)) || (tName.contains("Golem")) ? aOriginalDamage * 2.0F : aOriginalDamage;
    }

    @Override
    public int getToolDamagePerBlockBreak() {
        return 50;
    }

    @Override
    public int getToolDamagePerDropConversion() {
        return 200;
    }

    @Override
    public int getToolDamagePerContainerCraft() {
        return 400;
    }

    @Override
    public int getToolDamagePerEntityAttack() {
        return 200;
    }

    @Override
    public int getBaseQuality() {
        return 0;
    }

    @Override
    public float getBaseDamage() {
        return 3.0F;
    }

    @Override
    public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity) {
        return aOriginalHurtResistance * 2;
    }

    @Override
    public float getSpeedMultiplier() {
        return 0.75F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    @Override
    public String getCraftingSound() {
        return GregTech_API.sSoundList.get(1);
    }

    @Override
    public String getEntityHitSound() {
        return null;
    }

    @Override
    public String getBreakingSound() {
        return GregTech_API.sSoundList.get(2);
    }

    @Override
    public String getMiningSound() {
        return null;
    }

    @Override
    public boolean isCrowbar() {
        return false;
    }

    @Override
    public boolean isWeapon() {
        return true;
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock) {
        String tTool = aBlock.getBlock().getHarvestTool(aBlock);
        return ((tTool != null) && ((tTool.equals("hammer")) ||
                (tTool.equals("pickaxe")))) ||
                (aBlock.getMaterial() == Material.ROCK) ||
                (aBlock.getMaterial() == Material.GLASS) ||
                (aBlock.getMaterial() == Material.ICE) ||
                (aBlock.getMaterial() == Material.PACKED_ICE) ||
                (GT_Recipe.GT_Recipe_Map.sHammerRecipes.containsInput(getBlockStack(aBlock)));
    }

    @Override
    public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, IBlockState aBlock, BlockPos pos, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent) {
        int rConversions = 0;
        GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sHammerRecipes.findRecipe(null, true, 2147483647L, null, getBlockStack(aBlock));
        if ((tRecipe == null) || aBlock.getBlock().hasTileEntity(aBlock)) {
            for (ItemStack tDrop : aDrops) {
                tRecipe = GT_Recipe.GT_Recipe_Map.sHammerRecipes.findRecipe(null, true, 2147483647L, null, GT_Utility.copyAmount(1L, new Object[]{tDrop}));
                if (tRecipe != null) {
                    ItemStack tHammeringOutput = tRecipe.getOutput(0);
                    if (tHammeringOutput != null) {
                        rConversions += tDrop.stackSize;
                        tDrop.stackSize *= tHammeringOutput.stackSize;
                        tHammeringOutput.stackSize = tDrop.stackSize;
                        GT_Utility.setStack(tDrop, tHammeringOutput);
                    }
                }
            }
        } else {
            aDrops.clear();
            aDrops.add(tRecipe.getOutput(0));
            rConversions++;
        }
        return rConversions;
    }

    @Override
    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadHammer.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.stick.mTextureIndex];
    }

    @Override
    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {
        aItem.addItemBehavior(aID, new Behaviour_Prospecting(1, 1000));
    }

       @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(aEntity.getDisplayName())
                .appendText(TextFormatting.WHITE + " was squashed by " + TextFormatting.GREEN)
                .appendSibling(aPlayer.getDisplayName());
    }


    @Override
    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
        GT_Mod.achievements.issueAchievement(aPlayer, "tools");
    }

}
