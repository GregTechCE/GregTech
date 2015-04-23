/*  1:   */ package gregtech.common.items;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import gregtech.api.enums.GT_Values;
/*  6:   */ import gregtech.api.enums.ItemList;
/*  7:   */ import gregtech.api.items.GT_Generic_Item;
/*  8:   */ import gregtech.api.util.GT_Utility;
/*  9:   */ import java.util.List;
/* 10:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/* 11:   */ import net.minecraft.creativetab.CreativeTabs;
/* 12:   */ import net.minecraft.item.Item;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.nbt.NBTTagCompound;
/* 15:   */ import net.minecraft.util.EnumChatFormatting;
/* 16:   */ import net.minecraft.util.IIcon;
/* 17:   */ import net.minecraftforge.fluids.Fluid;
/* 18:   */ import net.minecraftforge.fluids.FluidRegistry;
/* 19:   */ 
/* 20:   */ public class GT_FluidDisplayItem
/* 21:   */   extends GT_Generic_Item
/* 22:   */ {
/* 23:   */   public GT_FluidDisplayItem()
/* 24:   */   {
/* 25:24 */     super("GregTech_FluidDisplay", "Fluid Display", null);
/* 26:25 */     ItemList.Display_Fluid.set(this);
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected void addAdditionalToolTips(List aList, ItemStack aStack)
/* 30:   */   {
/* 31:30 */     NBTTagCompound aNBT = aStack.getTagCompound();
/* 32:31 */     if (GT_Values.D1)
/* 33:   */     {
/* 34:32 */       Fluid tFluid = FluidRegistry.getFluid(aStack.getItemDamage());
/* 35:33 */       if (tFluid != null) {
/* 36:33 */         aList.add("Registry: " + tFluid.getName());
/* 37:   */       }
/* 38:   */     }
/* 39:35 */     if (aNBT != null)
/* 40:   */     {
/* 41:36 */       long tToolTipAmount = aNBT.getLong("mFluidDisplayAmount");
/* 42:37 */       if (tToolTipAmount > 0L) {
/* 43:38 */         aList.add(EnumChatFormatting.BLUE + "Amount: " + tToolTipAmount + EnumChatFormatting.GRAY);
/* 44:   */       }
/* 45:39 */       aList.add(EnumChatFormatting.RED + "Temperature: " + aNBT.getLong("mFluidDisplayHeat") + " K" + EnumChatFormatting.GRAY);
/* 46:40 */       aList.add(EnumChatFormatting.GREEN + "State: " + (aNBT.getBoolean("mFluidState") ? "Gas" : "Liquid") + EnumChatFormatting.GRAY);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   @SideOnly(Side.CLIENT)
/* 51:   */   public void registerIcons(IIconRegister aIconRegister) {}
/* 52:   */   
/* 53:   */   public IIcon getIconFromDamage(int aMeta)
/* 54:   */   {
/* 55:52 */     Fluid tFluid = FluidRegistry.getFluid(aMeta);
/* 56:53 */     return tFluid == null ? FluidRegistry.WATER.getStillIcon() : tFluid.getStillIcon();
/* 57:   */   }
/* 58:   */   
/* 59:   */   @SideOnly(Side.CLIENT)
/* 60:   */   public int getColorFromItemStack(ItemStack aStack, int aRenderPass)
/* 61:   */   {
/* 62:59 */     Fluid tFluid = FluidRegistry.getFluid(aStack.getItemDamage());
/* 63:60 */     return tFluid == null ? 16777215 : tFluid.getColor();
/* 64:   */   }
/* 65:   */   
/* 66:   */   public int getSpriteNumber()
/* 67:   */   {
/* 68:65 */     return 0;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public String getUnlocalizedName(ItemStack aStack)
/* 72:   */   {
/* 73:70 */     if (aStack != null) {
/* 74:70 */       return GT_Utility.getFluidName(FluidRegistry.getFluid(aStack.getItemDamage()), false);
/* 75:   */     }
/* 76:71 */     return "";
/* 77:   */   }
/* 78:   */   
/* 79:   */   public String getItemStackDisplayName(ItemStack aStack)
/* 80:   */   {
/* 81:76 */     if (aStack != null) {
/* 82:76 */       return GT_Utility.getFluidName(FluidRegistry.getFluid(aStack.getItemDamage()), true);
/* 83:   */     }
/* 84:77 */     return "";
/* 85:   */   }
/* 86:   */   
/* 87:   */   @SideOnly(Side.CLIENT)
/* 88:   */   public void getSubItems(Item aItem, CreativeTabs aTab, List aList)
/* 89:   */   {
/* 90:83 */     if (GT_Values.D1)
/* 91:   */     {
/* 92:83 */       int i = 0;
/* 93:83 */       for (int j = FluidRegistry.getMaxID(); i < j; i++)
/* 94:   */       {
/* 95:84 */         ItemStack tStack = GT_Utility.getFluidDisplayStack(FluidRegistry.getFluid(i));
/* 96:85 */         if (tStack != null) {
/* 97:85 */           aList.add(tStack);
/* 98:   */         }
/* 99:   */       }
/* :0:   */     }
/* :1:   */   }
/* :2:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.GT_FluidDisplayItem
 * JD-Core Version:    0.7.0.1
 */