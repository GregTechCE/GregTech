package gregtech.api.metatileentity.factory;

import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.WorkableSteamMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.machines.BlockMachine;
import gregtech.common.blocks.machines.BlockSteamMachine;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WorkableSteamMetaTileEntityFactory<T extends WorkableSteamMetaTileEntity> extends MetaTileEntityFactory<T>  {

    protected final RecipeMap<?> recipeMap;

    public WorkableSteamMetaTileEntityFactory(BlockMachine.ToolClass toolClass, int harvestLevel, String[] description, Class<T> metaTileEntityClass, ResourceLocation modelLocation, RecipeMap<?> recipeMap) {
        super(toolClass, harvestLevel, description, metaTileEntityClass, modelLocation);
        this.recipeMap = recipeMap;
    }

    @Override
    public IMetaTileEntity constructMetaTileEntity() {
        try {
            return metaTileEntityClass.getConstructor(WorkableSteamMetaTileEntityFactory.class).newInstance(this);
        } catch (Throwable exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Class<? extends Block> getBlockClass() {
        return BlockSteamMachine.class;
    }

    @Override
    public ItemStack getStackForm(int amount) {
        return new ItemStack(MetaBlocks.STEAM_MACHINE, amount, GregTechAPI.METATILEENTITY_REGISTRY.getIDForObject(this));
    }

    public RecipeMap<?> getRecipeMap() {
        return recipeMap;
    }
}
