package gregtech.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.surfacerock.BlockSurfaceRock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class MagnifyingGlassBehaviour implements IItemBehaviour {

    private final int cost;

    public MagnifyingGlassBehaviour(int cost) {
        this.cost = cost;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.format("behavior.magnifying_glass.description"));
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        IBlockState blockState = world.getBlockState(pos);
        ItemStack itemStack = player.getHeldItem(hand);
        if (blockState.getBlock() instanceof BlockSurfaceRock && GTUtility.doDamageItem(itemStack, cost, true)) {
            GTUtility.doDamageItem(itemStack, cost, false);
            if (world.isRemote) {
                return EnumActionResult.SUCCESS;
            }
            BlockSurfaceRock block = (BlockSurfaceRock) blockState.getBlock();
            Material rockMaterial = blockState.getValue(block.materialProperty);
            player.sendMessage(new TextComponentTranslation("behavior.magnifying_glass.inspect.primary", getNameForMaterial(rockMaterial)));
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    private static ITextComponent getNameForMaterial(Material material) {
        return new TextComponentTranslation(material.getUnlocalizedName()).setStyle(new Style().setColor(TextFormatting.GOLD));
    }
}
