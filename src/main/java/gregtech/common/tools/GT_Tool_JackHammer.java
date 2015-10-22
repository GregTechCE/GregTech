package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class GT_Tool_JackHammer
        extends GT_Tool_Drill_LV {
    public int getToolDamagePerBlockBreak() {
        return GT_Mod.gregtechproxy.mHardRock ? 200 : 400;
    }

    public int getToolDamagePerDropConversion() {
        return 400;
    }

    public int getToolDamagePerContainerCraft() {
        return 3200;
    }

    public int getToolDamagePerEntityAttack() {
        return 800;
    }

    public int getBaseQuality() {
        return 1;
    }

    public float getBaseDamage() {
        return 3.0F;
    }

    public float getSpeedMultiplier() {
        return 12.0F;
    }

    public float getMaxDurabilityMultiplier() {
        return 2.0F;
    }

    public boolean isMinableBlock(Block aBlock, byte aMetaData) {
        String tTool = aBlock.getHarvestTool(aMetaData);
        return ((tTool != null) && (tTool.equals("pickaxe"))) || (aBlock.getMaterial() == Material.rock) || (aBlock.getMaterial() == Material.glass) || (aBlock.getMaterial() == Material.ice) || (aBlock.getMaterial() == Material.packedIce);
    }

    public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent) {
        int rConversions = 0;
        GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sHammerRecipes.findRecipe(null, true, 2147483647L, null, new ItemStack[]{new ItemStack(aBlock, 1, aMetaData)});
        if ((tRecipe == null) || (aBlock.hasTileEntity(aMetaData))) {
            for (ItemStack tDrop : aDrops) {
                tRecipe = GT_Recipe.GT_Recipe_Map.sHammerRecipes.findRecipe(null, true, 2147483647L, null, new ItemStack[]{GT_Utility.copyAmount(1L, new Object[]{tDrop})});
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

    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
        try {
            GT_Mod.instance.achievements.issueAchievement(aPlayer, "hammertime");
        } catch (Exception e) {
        }
    }


    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.JACKHAMMER : null;
    }

    public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " has been jackhammered into pieces by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
    }
}
