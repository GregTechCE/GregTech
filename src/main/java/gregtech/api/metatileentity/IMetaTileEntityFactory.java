package gregtech.api.metatileentity;

import gregtech.common.blocks.BlockMachine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public interface IMetaTileEntityFactory {

    BlockMachine.ToolClass getHarvestTool();
    int getHarvestLevel();

    String getMetaName();
    String getUnlocalizedName();

    ResourceLocation getModelLocation();

    IBlockState getDefaultRenderState();

    /**
     * @return a short description of tile entity stack
     */
    String[] getDescription(ItemStack tileEntityStack);

    /**
     * @return a newly created and ready MetaTileEntity
     */
    IMetaTileEntity constructMetaTileEntity();

    /**
     * @return an ItemStack representing this MetaTileEntity.
     */
    ItemStack getStackForm(int amount);

    /**
     * Called when server starts
     */
    void onServerStart();

    /**
     * Called when world is loaded
     */
    void onWorldLoad(File saveDirectory);

    /**
     * Called when world is saved
     */
    void onWorldSave(File saveDirectory);

    /**
     * Called to set configuration values for this factory
     */
    void onConfigLoad(Configuration config);

    /**
     * The onCreated function of the item class redirects here
     */
    void onCreated(ItemStack stack, World world, EntityPlayer player);

}
