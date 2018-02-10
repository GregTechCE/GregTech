package gregtech.api.metatileentity;

import gregtech.common.blocks.machines.BlockMachine;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface IMetaTileEntityFactory {

    BlockMachine.ToolClass getHarvestTool();
    int getHarvestLevel();

    String getMetaName();
    String getUnlocalizedName();

    ResourceLocation getStateLocation();

    /**
     * @return a short description of tile entity stack
     */
    String[] getDescription(ItemStack tileEntityStack);

    /**
     * @return a newly created and ready MetaTileEntity
     */
    IMetaTileEntity constructMetaTileEntity();


    /**
     * @return class type that this MTE will reside in
     */
    Class<? extends Block> getBlockClass();

    /**
     * @return an ItemStack representing this MetaTileEntity.
     */
    ItemStack getStackForm(int amount);

    /**
     * The onCreated function of the Item class redirects here
     */
    void onCreated(ItemStack stack, World world, EntityPlayer player);

}
