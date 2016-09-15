package gregtech.common.blocks;

import gregtech.api.GregTech_API;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GT_Item_Ores extends ItemBlock {

    public GT_Item_Ores(Block par1) {
        super(par1);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
    }

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        return EnumActionResult.PASS;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return GT_LanguageManager.getTranslation(getUnlocalizedName(stack) + ".name");
    }

    @Override
    public String getUnlocalizedName(ItemStack aStack) {
        return this.block.getUnlocalizedName() + "." + getDamage(aStack);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        short tDamage = (short) getDamage(stack);
        if (tDamage > 0) {
            if (!world.setBlockState(pos, block.getStateFromMeta(GT_TileEntity_Ores.getHarvestData(tDamage)))) {
                return false;
            }
            GT_TileEntity_Ores tTileEntity = (GT_TileEntity_Ores) world.getTileEntity(pos);
            tTileEntity.mMetaData = tDamage;
            tTileEntity.mNatural = false;
        } else if (!world.setBlockState(pos, block.getDefaultState())) {
            return false;
        }
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == block) {
            this.block.onBlockPlacedBy(world, pos, state, player, stack);
        }
        return true;
    }

}
