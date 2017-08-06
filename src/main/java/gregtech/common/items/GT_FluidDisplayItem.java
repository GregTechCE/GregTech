package gregtech.common.items;

import gregtech.api.GregTech_API;
import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.items.GenericItem;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class GT_FluidDisplayItem extends GenericItem {
    public GT_FluidDisplayItem() {
        super("GregTech_FluidDisplay", "Fluid Display", null);
        ItemList.Display_Fluid.set(this);
        setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
    }

    @Override
    protected void addAdditionalToolTips(List<String> aList, ItemStack aStack, EntityPlayer aPlayer) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (GT_Values.D1) {
            Fluid tFluid = FluidRegistry.getFluid(aStack.getItemDamage());
            if (tFluid != null) {
                aList.add("Registry: " + tFluid.getName());
            }
        }
        if (aNBT != null) {
            long tToolTipAmount = aNBT.getLong("mFluidDisplayAmount");
            if (tToolTipAmount > 0L) {
                aList.add(TextFormatting.BLUE + "Amount: " + tToolTipAmount + TextFormatting.GRAY);
            }
            aList.add(TextFormatting.RED + "Temperature: " + aNBT.getLong("mFluidDisplayHeat") + " K" + TextFormatting.GRAY);
            aList.add(TextFormatting.GREEN + "State: " + (aNBT.getBoolean("mFluidState") ? "Gas" : "Liquid") + TextFormatting.GRAY);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(ItemStack stack, int pass) {
        Fluid tFluid = FluidRegistry.getFluid(stack.getItemDamage());
        return GT_Utility.getTexture(tFluid.getStill());
    }

    @Override
    public void registerIcons(TextureMap aIconRegister) {}

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int tintIndex) {
        Fluid tFluid = FluidRegistry.getFluid(stack.getItemDamage());
        return tFluid == null ? 16777215 : tFluid.getColor();
    }

    @Override
    public String getUnlocalizedName(ItemStack aStack) {
        if (aStack != null) {
            return GT_Utility.getFluidName(FluidRegistry.getFluid(aStack.getItemDamage()), false);
        }
        return "";
    }

    @Override
    public String getItemStackDisplayName(ItemStack aStack) {
        if (aStack != null) {
            return GT_Utility.getFluidName(FluidRegistry.getFluid(aStack.getItemDamage()), true);
        }
        return "";
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item aItem, CreativeTabs aTab, List<ItemStack> aList) {
        if (GT_Values.D1) {
            int i = 0;
            for (int j = FluidRegistry.getMaxID(); i < j; i++) {
                ItemStack tStack = GT_Utility.getFluidDisplayStack(FluidRegistry.getFluid(i));
                if (tStack != null) {
                    aList.add(tStack);
                }
            }
        }

    }
    
}
