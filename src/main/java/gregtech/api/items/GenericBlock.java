package gregtech.api.items;

import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Log;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.Map;

public abstract class GenericBlock extends Block {

    private String unlocalizedName;

    /**
     *
     * @param registerItemModels if true this will register item models for all blockstates,
     *                          item metadata it will take from getMetaFromState
     */
    protected GenericBlock(String name, @Nullable Class<? extends ItemBlock> itemClass, Material material, boolean registerItemModels) {
        super(material);

        setUnlocalizedName("gt." + name);
        setRegistryName(name);
        GameRegistry.register(this);

        if (itemClass != null) {
            ItemBlock itemBlock = null;
            try {
                itemBlock = itemClass.getConstructor(Block.class).newInstance(this);
            } catch(ReflectiveOperationException e){
                e.printStackTrace(GT_Log.err);
                throw new LoaderException(e);
            }
            GameRegistry.register(itemBlock, getRegistryName());
        }

        if (registerItemModels) {
            invokeOnClient(() -> {
                for (IBlockState state : this.blockState.getValidStates()) {
                    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), getMetaFromState(state), new ModelResourceLocation(getRegistryName(), getPropertyString(state.getProperties())));
                }
            });
        }
    }

    protected GenericBlock(String name, @Nullable Class<? extends ItemBlock> itemClass, Material material) {
        this(name, itemClass, material, true);
    }

    @Override
    public String getLocalizedName() {
        return GT_LanguageManager.getTranslation(this.getUnlocalizedName() + ".name");
    }

    @Override
    public final Block setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        return this;
    }

    public final String getUnlocalizedNameWithoutPrefix() {
        return unlocalizedName;
    }

    @Override
    public final String getUnlocalizedName() {
        return "block." + unlocalizedName;
    }


    public int damageDropped(IBlockState state) {
        return createStackedBlock(state).getMetadata();
    }

//    @SideOnly(Side.CLIENT)
//    public int getColorMultiplier(IBlockAccess world, BlockPos pos, IBlockState state) {
//        return 0xFFFFFFFF;
//    }

    public static String getPropertyString(Map<IProperty<?>, Comparable<? >> values)
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (Map.Entry< IProperty<?>, Comparable<? >> entry : values.entrySet())
        {
            if (stringbuilder.length() != 0)
            {
                stringbuilder.append(",");
            }

            IProperty<?> property = entry.getKey();
            stringbuilder.append(property.getName());
            stringbuilder.append("=");
            stringbuilder.append(getPropertyName(property, entry.getValue()));
        }

        if (stringbuilder.length() == 0)
        {
            stringbuilder.append("normal");
        }

        return stringbuilder.toString();
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value)
    {
        return property.getName((T)value);
    }

    public void invokeOnClient(Runnable runnable) {
        if(FMLCommonHandler.instance().getSide().isClient()) {
            runnable.run();
        }
    }
}