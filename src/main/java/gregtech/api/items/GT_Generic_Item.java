package gregtech.api.items;

import gregtech.api.GregTech_API;
import gregtech.api.enums.SubTag;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.IProjectileItem;
import gregtech.api.util.GT_Config;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.render.items.IItemIconContainerProvider;
import gregtech.common.render.IIconRegister;
import gregtech.common.render.items.IItemIconProvider;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Method;
import java.util.List;

import static gregtech.api.enums.GT_Values.MOD_ID;
import static gregtech.api.enums.GT_Values.RES_PATH_ITEM;

/**
 * Extended by most Items, also used as a fallback Item, to prevent the accidental deletion when Errors occur.
 */
public class GT_Generic_Item extends Item implements IProjectileItem, IIconRegister, IItemIconProvider {

    private final String mName, mTooltip;

    @SideOnly(Side.CLIENT)
    protected TextureAtlasSprite mIcon;

    public GT_Generic_Item(String aUnlocalized, String aEnglish, String aEnglishTooltip) {
        this(aUnlocalized, aEnglish, aEnglishTooltip, true);
    }

    public GT_Generic_Item(String aUnlocalized, String aEnglish, String aEnglishTooltip, boolean aWriteToolTipIntoLangFile) {
        super();
        mName = "gt." + aUnlocalized;
        GT_LanguageManager.addStringLocalization(mName + ".name", aEnglish);
        if (GT_Utility.isStringValid(aEnglishTooltip))
            GT_LanguageManager.addStringLocalization(mTooltip = mName + ".tooltip_main", aEnglishTooltip, aWriteToolTipIntoLangFile);
        else mTooltip = null;
        setCreativeTab(GregTech_API.TAB_GREGTECH);
        setRegistryName(MOD_ID, mName);
        GameRegistry.register(this);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new GT_Item_Dispense());
    }

    @Override
    public final Item setUnlocalizedName(String aName) {
        return this;
    }

    @Override
    public final String getUnlocalizedName() {
        return mName;
    }

    @Override
    public String getUnlocalizedName(ItemStack aStack) {
        return getHasSubtypes() ? mName + "." + getDamage(aStack) : mName;
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack stack) {
        return getUnlocalizedName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap aIconRegister) {
        mIcon = aIconRegister.registerSprite(new ResourceLocation(RES_PATH_ITEM + (GT_Config.troll ? "troll" : mName)));
    }

    public int getTier(ItemStack aStack) {
        return 0;
    }

    @Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List<String> aList, boolean aF3_H) {
        if (getMaxDamage() > 0 && !getHasSubtypes())
            aList.add((aStack.getMaxDamage() - getDamage(aStack)) + " / " + aStack.getMaxDamage());
        if (mTooltip != null) aList.add(GT_LanguageManager.getTranslation(mTooltip));
        if (GT_ModHandler.isElectricItem(aStack)) aList.add("Tier: " + getTier(aStack));
        addAdditionalToolTips(aList, aStack, aPlayer);
    }

    protected void addAdditionalToolTips(List<String> aList, ItemStack aStack, EntityPlayer aPlayer) {
        //
    }

    @Override
    public void onCreated(ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        isItemStackUsable(aStack);
    }

    public boolean isItemStackUsable(ItemStack aStack) {
        return true;
    }

    public ItemStack onDispense(IBlockSource aSource, ItemStack aStack) {
        IBlockState state = Blocks.DISPENSER.getStateFromMeta(aStack.getItemDamage());
        EnumFacing enumfacing = state.getValue(BlockDispenser.FACING);
        IPosition iposition = BlockDispenser.getDispensePosition(aSource);
        ItemStack itemstack1 = aStack.splitStack(1);
        BehaviorDefaultDispenseItem.doDispense(aSource.getWorld(), itemstack1, 6, enumfacing, iposition);
        return aStack;
    }

    @Override
    public EntityArrow getProjectile(SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ) {
        return null;
    }

    @Override
    public EntityArrow getProjectile(SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed) {
        return null;
    }

    @Override
    public boolean hasProjectile(SubTag aProjectileType, ItemStack aStack) {
        return false;
    }

    @Override
    public ItemStack getContainerItem(ItemStack aStack) {
        return null;
    }

    @Override
    public boolean hasContainerItem(ItemStack aStack) {
        return getContainerItem(aStack) != null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(ItemStack stack, int pass) {
        if(this instanceof IItemIconContainerProvider) {
            IItemIconContainerProvider iItemIconContainerProvider = (IItemIconContainerProvider) this;
            IIconContainer iconContainer = iItemIconContainerProvider.getIconContainer(stack);
            if(iconContainer != null) {
                switch (pass) {
                    case 0:
                        return iconContainer.getIcon();
                    case 1:
                        return iconContainer.getOverlayIcon();
                }
            }
            return null;
        }
        return mIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(ItemStack stack) {
        if(this instanceof IItemIconContainerProvider) {
            return 1;
        }
        return 0;
    }

    public static class GT_Item_Dispense extends BehaviorProjectileDispense {
        @Override
        public ItemStack dispenseStack(IBlockSource aSource, ItemStack aStack) {
            return ((GT_Generic_Item) aStack.getItem()).onDispense(aSource, aStack);
        }

        @Override
        protected IProjectile getProjectileEntity(World aWorld, IPosition aPosition, ItemStack stack) {
            return null;
        }

    }

    public static <T> int id(IForgeRegistryEntry<T> registryEntry) {
        try {
            Method findRegistry = PersistentRegistryManager.class.getDeclaredMethod("findRegistry", IForgeRegistryEntry.class);
            findRegistry.setAccessible(true);
            FMLControlledNamespacedRegistry registry = (FMLControlledNamespacedRegistry) findRegistry.invoke(null, registryEntry);
            return registry.getId(registryEntry);
        } catch (ReflectiveOperationException fail) {
            throw new RuntimeException(fail);
        }
    }

    public void invokeOnClient(Runnable runnable) {
        if(FMLCommonHandler.instance().getSide().isClient()) {
            runnable.run();
        }
    }

}