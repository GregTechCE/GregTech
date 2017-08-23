package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_Prospecting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class ToolHardHammer extends ToolBase {

    public static final List<String> mEffectiveList = Arrays.asList(EntityIronGolem.class.getName(), "EntityTowerGuardian");

    @Override
    public float getNormalDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase attacker) {
        String tName = entity.getClass().getName();
        tName = tName.substring(tName.lastIndexOf('.') + 1);
        return (mEffectiveList.contains(tName)) || (tName.contains("Golem")) ? 2.0F : 1.0F;
    }

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 200;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 400;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public int getHurtResistanceTime(int originalHurtResistance, Entity entity) {
        return originalHurtResistance * 2;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0.75F;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(1);
    }

    @Override
    public ResourceLocation getBreakingSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(2);
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tTool = block.getBlock().getHarvestTool(block);
        return ((tTool != null) && ((tTool.equals("hammer")) ||
                (tTool.equals("pickaxe")))) ||
                (block.getMaterial() == Material.ROCK) ||
                (block.getMaterial() == Material.GLASS) ||
                (block.getMaterial() == Material.ICE) ||
                (block.getMaterial() == Material.PACKED_ICE) ||
                (RecipeMap.HAMMER_RECIPES.containsInput(getBlockStack(block)));
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        int rConversions = 0;
        Recipe tRecipe = RecipeMap.HAMMER_RECIPES.findRecipe(null, true, 2147483647L, null, new ItemStack[]{getBlockStack(blockState)});
        if ((tRecipe == null) || blockState.getBlock().hasTileEntity(blockState)) {
            for (ItemStack tDrop : drops) {
                tRecipe = RecipeMap.HAMMER_RECIPES.findRecipe(null, true, 2147483647L, null, new ItemStack[]{GT_Utility.copyAmount(1, tDrop)});
                if (tRecipe != null) {
                    ItemStack tHammeringOutput = tRecipe.getOutputs().get(0);
                    if (tHammeringOutput != null) {
                        rConversions += tDrop.stackSize;
                        tDrop.stackSize *= tHammeringOutput.stackSize;
                        tHammeringOutput.stackSize = tDrop.stackSize;
                        GT_Utility.setStack(tDrop, tHammeringOutput);
                    }
                }
            }
        } else {
            drops.clear();
            drops.add(tRecipe.getOutputs().get(0));
            rConversions++;
        }
        return rConversions;
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        return 0;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadHammer.mTextureIndex] : ToolMetaItem.getHandleMaterial(aStack).mIconSet.mTextures[OrePrefixes.stick.mTextureIndex];
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem item, int ID) {
        item.addStats(new Behaviour_Prospecting(1, 1000));
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(entity.getDisplayName())
                .appendText(TextFormatting.WHITE + " was squashed by " + TextFormatting.GREEN)
                .appendSibling(player.getDisplayName());
    }

    @Override
    public void onToolCrafted(ItemStack stack, EntityPlayer player) {
        super.onToolCrafted(stack, player);
        GT_Mod.achievements.issueAchievement(player, "tools");
    }
}
