package gregtech.common.tools;

import gregtech.api.GregTechAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ToolChainsawLV extends ToolSaw {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 800;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return GregTechAPI.soundList.get(104);
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return GregTechAPI.soundList.get(105);
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return GregTechAPI.soundList.get(104);
    }

    @Override
    public void onToolCrafted(ItemStack stack, EntityPlayer player) {
        super.onToolCrafted(stack, player);
//        GregTechMod.achievements.issueAchievement(player, "brrrr"); // TODO ACHIEVEMENTS/ADVANCEMENTS
//        GregTechMod.achievements.issueAchievement(player, "buildChainsaw");
    }
    
//    @Override
//    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
//        int rAmount = 0;
//        ItemStack stack = harvester.getHeldItem(EnumHand.MAIN_HAND);
//        if ((blockState.getMaterial() == Material.LEAVES) && blockState.getBlock() instanceof IShearable) {
//            IShearable shearable = (IShearable) blockState.getBlock();
//            if (shearable.isShearable(stack, harvester.worldObj, blockPos)) {
//                List<ItemStack> tDrops = shearable.onSheared(stack, harvester.worldObj, blockPos, aFortune);
//                drops.clear();
//                drops.addAll(tDrops);
//                aEvent.setDropChance(1.0F);
//                for (ItemStack dropStack : tDrops) {
//                    Random itemRand = new Random();
//                    float f = 0.7F;
//                    double d = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
//                    double d1 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
//                    double d2 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
//                    EntityItem entityitem = new EntityItem(harvester.worldObj,
//                            blockPos.getX() + d,
//                            blockPos.getY() + d1,
//                            blockPos.getZ() + d2, dropStack);
//                    entityitem.setDefaultPickupDelay();
//                    harvester.worldObj.spawnEntityInWorld(entityitem);
//                }
//                harvester.addStat(StatList.MINE_BLOCK_STATS.get(Block.getIdFromBlock(blockState.getBlock())), 1);
//            }
//            harvester.worldObj.setBlockToAir(blockPos);
//        } else 
//        	if ((blockState.getMaterial() == Material.ICE ||
//                    blockState.getMaterial() == Material.PACKED_ICE) &&
//                    drops.isEmpty()) {
//            drops.add(getBlockStack(blockState));
//            harvester.worldObj.setBlockToAir(blockPos);
//            aEvent.setDropChance(1.0F);
//            return 1;
//        }
//        if (GregTechAPI.sTimber && !harvester.isSneaking() &&
//                OrePrefix.log.contains(getBlockStack(blockState))) {
//            for (int y = 0; y < harvester.worldObj.getHeight() - blockPos.up().getY(); y++) {
//                BlockPos block = blockPos.up(y);
//                if (!isStateEqual(harvester.worldObj.getBlockState(block), blockState) ||
//                        !harvester.worldObj.destroyBlock(block, true)) break;
//                rAmount++;
//            }
//        }
//        return rAmount;
//    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.GREEN + "")
//                .appendSibling(player.getDisplayName())
//                .appendText(TextFormatting.WHITE + " was massacred " + TextFormatting.RED)
//                .appendSibling(entity.getDisplayName());
//    }
}
