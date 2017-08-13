package gregtech.api.metatileentity.factory;

import com.google.common.base.Throwables;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GT_Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.io.File;

public class MetaTileEntityFactory<T extends MetaTileEntity> implements IMetaTileEntityFactory {

    private byte baseTileEntityType;
    private String[] description;
    protected Class<T> metaTileEntityClass;
    protected ResourceLocation modelLocation;
    protected IBlockState defaultState;

    public MetaTileEntityFactory(byte baseTileEntityType, Class<T> metaTileEntityClass, ResourceLocation modelLocation, IBlockState defaultState, String... description) {
        this.baseTileEntityType = baseTileEntityType;
        this.description = description;
        this.metaTileEntityClass = metaTileEntityClass;
        this.modelLocation = modelLocation;
        this.defaultState = defaultState;
    }

    @Override
    public byte getTileEntityBaseType() {
        return baseTileEntityType;
    }

    @Override
    public ResourceLocation getModelLocation() {
        return modelLocation;
    }

    @Override
    public IBlockState getDefaultRenderState() {
        return defaultState;
    }

    @Override
    public String[] getDescription(ItemStack tileEntityStack) {
        return description;
    }

    @Override
    public IMetaTileEntity constructMetaTileEntity() {
        try {
            return metaTileEntityClass.getConstructor(IMetaTileEntityFactory.class).newInstance(this);
        } catch (Throwable exception) {
            throw Throwables.propagate(exception);
        }
    }

    @Override
    public ItemStack getStackForm(int amount) {
        return null; //TODO fixme
    }

    @Override
    public void onServerStart() {

    }

    @Override
    public void onWorldLoad(File saveDirectory) {
    }

    @Override
    public void onWorldSave(File saveDirectory) {
    }

    @Override
    public void onConfigLoad(GT_Config config) {
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
    }

}
