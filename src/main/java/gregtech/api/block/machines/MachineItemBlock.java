package gregtech.api.block.machines;

import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class MachineItemBlock extends ItemBlock {

    public MachineItemBlock(BlockMachine block) {
        super(block);
        setHasSubtypes(true);
    }

    public static MetaTileEntity getMetaTileEntity(ItemStack itemStack) {
        return GregTechAPI.META_TILE_ENTITY_REGISTRY.getObjectById(itemStack.getItemDamage());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(stack);
        return metaTileEntity == null ? "unnamed" : metaTileEntity.getMetaName();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(stack);
        if(metaTileEntity == null) return;
        String descriptionLocale = metaTileEntity.getMetaName() + ".desc";
        if(I18n.hasKey(descriptionLocale)) {
            String[] lines = I18n.format(metaTileEntity.getMetaName() + ".desc").split("/n");
            tooltip.addAll(Arrays.asList(lines));
        }
        if(flagIn.isAdvanced()) {
            tooltip.add(String.format("MetaTileEntity Id: %s", metaTileEntity.metaTileEntityId));
        }
        metaTileEntity.addInformation(stack, worldIn, tooltip, flagIn.isAdvanced());
    }
}
