package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.enums.Textures;
import gregtech.api.items.IIconContainer;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class GT_Tool_JackHammer extends GT_Tool_Drill_LV {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return GT_Mod.gregtechproxy.mHardRock ? 200 : 400;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 400;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 3200;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 800;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 12.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock, ItemStack stack) {
        String tTool = aBlock.getBlock().getHarvestTool(aBlock);
        return ((tTool != null) && (tTool.equals("pickaxe"))) ||
                (aBlock.getMaterial() == Material.ROCK) ||
                (aBlock.getMaterial() == Material.GLASS) ||
                (aBlock.getMaterial() == Material.ICE) ||
                (aBlock.getMaterial() == Material.PACKED_ICE);
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        int rConversions = 0;
        Recipe tRecipe = RecipeMap.HAMMER_RECIPES.findRecipe(null, true, 2147483647L, null, new ItemStack[]{getBlockStack(blockState)});
        if ((tRecipe == null) || (blockState.getBlock().hasTileEntity(blockState))) {
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
    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
        GT_Mod.achievements.issueAchievement(aPlayer, "hammertime");

    }


    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.JACKHAMMER : null;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(aEntity.getDisplayName())
                .appendText(TextFormatting.WHITE + " has been jackhammered into pieces by " + TextFormatting.GREEN)
                .appendSibling(aPlayer.getDisplayName());
    }

}
