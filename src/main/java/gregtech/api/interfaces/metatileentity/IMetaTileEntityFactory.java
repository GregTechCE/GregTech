package gregtech.api.interfaces.metatileentity;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.io.File;

public interface IMetaTileEntityFactory {

    /**
     * This determines the BaseMetaTileEntity belonging to this MetaTileEntity by using the Meta ID of the Block itself.
     *
     * 0 = BaseMetaTileEntity, Wrench lvl 0 to dismantle
     * 1 = BaseMetaTileEntity, Wrench lvl 1 to dismantle
     * 2 = BaseMetaTileEntity, Wrench lvl 2 to dismantle
     * 3 = BaseMetaTileEntity, Wrench lvl 3 to dismantle
     */
    byte getTileEntityBaseType();

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
    void onConfigLoad(GT_Config config);

    /**
     * The onCreated function of the item class redirects here
     */
    void onCreated(ItemStack stack, World world, EntityPlayer player);

}
