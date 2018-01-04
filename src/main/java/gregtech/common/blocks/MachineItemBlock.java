package gregtech.common.blocks;

import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class MachineItemBlock extends ItemBlock {

    private BlockMachine block;

    public MachineItemBlock(BlockMachine block) {
        super(block);
        this.block = block;
    }

    public IMetaTileEntityFactory getFactory(ItemStack stack) {
        return GregTechAPI.METATILEENTITY_REGISTRY.getObjectById(stack.getItemDamage());
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getFactory(stack).getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(getUnlocalizedName(stack));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag tooltipFlag) {
        tooltip.addAll(Arrays.asList(getFactory(stack).getDescription(stack)));
    }
}
