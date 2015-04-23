/*   1:    */ package gregtech.common.render;
/*   2:    */ 
/*   3:    */ import gregtech.api.interfaces.IIconContainer;
/*   4:    */ import gregtech.api.items.GT_MetaGenerated_Item;
/*   5:    */ import gregtech.api.util.GT_Utility;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import net.minecraft.client.Minecraft;
/*  10:    */ import net.minecraft.client.renderer.ItemRenderer;
/*  11:    */ import net.minecraft.client.renderer.Tessellator;
/*  12:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  13:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  14:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.util.IIcon;
/*  17:    */ import net.minecraftforge.client.IItemRenderer;
/*  18:    */ import net.minecraftforge.client.IItemRenderer.ItemRenderType;
/*  19:    */ import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
/*  20:    */ import net.minecraftforge.client.MinecraftForgeClient;
/*  21:    */ import net.minecraftforge.fluids.Fluid;
/*  22:    */ import net.minecraftforge.fluids.FluidStack;
/*  23:    */ import org.lwjgl.opengl.GL11;
/*  24:    */ 
/*  25:    */ public class GT_MetaGenerated_Item_Renderer
/*  26:    */   implements IItemRenderer
/*  27:    */ {
/*  28:    */   public GT_MetaGenerated_Item_Renderer()
/*  29:    */   {
/*  30:    */     GT_MetaGenerated_Item tItem;
/*  31: 21 */     for (Iterator i$ = GT_MetaGenerated_Item.sInstances.values().iterator(); i$.hasNext(); MinecraftForgeClient.registerItemRenderer(tItem, this))
/*  32:    */     {
/*  33: 21 */       tItem = (GT_MetaGenerated_Item)i$.next();
/*  34: 21 */       if ((tItem == null) || (!tItem.useStandardMetaItemRenderer())) {}
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean handleRenderType(ItemStack aStack, IItemRenderer.ItemRenderType aType)
/*  39:    */   {
/*  40: 26 */     if ((GT_Utility.isStackInvalid(aStack)) || (aStack.getItemDamage() < 0)) {
/*  41: 26 */       return false;
/*  42:    */     }
/*  43: 27 */     return (aType == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) || (aType == IItemRenderer.ItemRenderType.INVENTORY) || (aType == IItemRenderer.ItemRenderType.EQUIPPED) || (aType == IItemRenderer.ItemRenderType.ENTITY);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType aType, ItemStack aStack, IItemRenderer.ItemRendererHelper aHelper)
/*  47:    */   {
/*  48: 32 */     if (GT_Utility.isStackInvalid(aStack)) {
/*  49: 32 */       return false;
/*  50:    */     }
/*  51: 33 */     return aType == IItemRenderer.ItemRenderType.ENTITY;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack aStack, Object... data)
/*  55:    */   {
/*  56: 38 */     if (GT_Utility.isStackInvalid(aStack)) {
/*  57: 38 */       return;
/*  58:    */     }
/*  59: 39 */     short aMetaData = (short)aStack.getItemDamage();
/*  60: 40 */     if (aMetaData < 0) {
/*  61: 40 */       return;
/*  62:    */     }
/*  63: 41 */     GT_MetaGenerated_Item aItem = (GT_MetaGenerated_Item)aStack.getItem();
/*  64:    */     
/*  65:    */ 
/*  66: 44 */     GL11.glEnable(3042);
/*  67: 46 */     if (type == IItemRenderer.ItemRenderType.ENTITY) {
/*  68: 47 */       if (RenderItem.renderInFrame)
/*  69:    */       {
/*  70: 48 */         GL11.glScalef(0.85F, 0.85F, 0.85F);
/*  71: 49 */         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/*  72: 50 */         GL11.glTranslated(-0.5D, -0.42D, 0.0D);
/*  73:    */       }
/*  74:    */       else
/*  75:    */       {
/*  76: 52 */         GL11.glTranslated(-0.5D, -0.42D, 0.0D);
/*  77:    */       }
/*  78:    */     }
/*  79: 56 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*  80: 58 */     if (aMetaData < aItem.mOffset)
/*  81:    */     {
/*  82: 59 */       IIconContainer aIcon = aItem.getIconContainer(aMetaData);
/*  83: 60 */       IIcon tOverlay = null;IIcon tFluidIcon = null;
/*  84:    */       IIcon tIcon;
/*  86: 61 */       if (aIcon == null)
/*  87:    */       {
/*  88: 62 */         tIcon = aStack.getIconIndex();
/*  89:    */       }
/*  90:    */       else
/*  91:    */       {
/*  92: 64 */         tIcon = aIcon.getIcon();
/*  93: 65 */         tOverlay = aIcon.getOverlayIcon();
/*  94:    */       }
/*  95: 67 */       if (tIcon == null) {
/*  96: 67 */         return;
/*  97:    */       }
/*  98: 68 */       FluidStack tFluid = GT_Utility.getFluidForFilledItem(aStack, true);
/*  99: 69 */       if ((tOverlay != null) && (tFluid != null) && (tFluid.getFluid() != null)) {
/* 100: 69 */         tFluidIcon = tFluid.getFluid().getIcon(tFluid);
/* 101:    */       }
/* 102: 70 */       Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
/* 103: 71 */       GL11.glBlendFunc(770, 771);
/* 104: 72 */       if (tFluidIcon == null)
/* 105:    */       {
/* 106: 73 */         short[] tModulation = aItem.getRGBa(aStack);
/* 107: 74 */         GL11.glColor3f(tModulation[0] / 255.0F, tModulation[1] / 255.0F, tModulation[2] / 255.0F);
/* 108:    */       }
/* 109: 76 */       if (type.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/* 110: 76 */         GT_RenderUtil.renderItemIcon(tIcon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/* 111:    */       } else {
/* 112: 76 */         ItemRenderer.renderItemIn2D(Tessellator.instance, tIcon.getMaxU(), tIcon.getMinV(), tIcon.getMinU(), tIcon.getMaxV(), tIcon.getIconWidth(), tIcon.getIconHeight(), 0.0625F);
/* 113:    */       }
/* 114: 77 */       if (tFluidIcon != null)
/* 115:    */       {
/* 116: 78 */         assert (tFluid != null);
/* 117: 79 */         int tColor = tFluid.getFluid().getColor(tFluid);
/* 118: 80 */         GL11.glColor3f((tColor >> 16 & 0xFF) / 255.0F, (tColor >> 8 & 0xFF) / 255.0F, (tColor & 0xFF) / 255.0F);
/* 119: 81 */         Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/* 120: 82 */         GL11.glBlendFunc(770, 771);
/* 121: 83 */         GL11.glDepthFunc(514);
/* 122: 84 */         if (type.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/* 123: 84 */           GT_RenderUtil.renderItemIcon(tFluidIcon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/* 124:    */         } else {
/* 125: 84 */           ItemRenderer.renderItemIn2D(Tessellator.instance, tFluidIcon.getMaxU(), tFluidIcon.getMinV(), tFluidIcon.getMinU(), tFluidIcon.getMaxV(), tFluidIcon.getIconWidth(), tFluidIcon.getIconHeight(), 0.0625F);
/* 126:    */         }
/* 127: 85 */         GL11.glDepthFunc(515);
/* 128:    */       }
/* 129: 87 */       GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 130: 88 */       if (tOverlay != null)
/* 131:    */       {
/* 132: 89 */         Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
/* 133: 90 */         GL11.glBlendFunc(770, 771);
/* 134: 91 */         if (type.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/* 135: 91 */           GT_RenderUtil.renderItemIcon(tOverlay, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/* 136:    */         } else {
/* 137: 91 */           ItemRenderer.renderItemIn2D(Tessellator.instance, tOverlay.getMaxU(), tOverlay.getMinV(), tOverlay.getMinU(), tOverlay.getMaxV(), tOverlay.getIconWidth(), tOverlay.getIconHeight(), 0.0625F);
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:    */     else
/* 142:    */     {
/* 143:    */       IIcon tIcon;
/* 145: 94 */       if (aItem.mIconList[(aMetaData - aItem.mOffset)].length > 1)
/* 146:    */       {
/* 147: 95 */         Long[] tStats = (Long[])aItem.mElectricStats.get(Short.valueOf(aMetaData));
/* 148:    */ 
/* 149: 96 */         if ((tStats != null) && (tStats[3].longValue() < 0L))
/* 150:    */         {
/* 151: 97 */           long tCharge = aItem.getRealCharge(aStack);
/* 152:    */           
/* 153: 98 */           if (tCharge <= 0L)
/* 154:    */           {
/* 155: 99 */             tIcon = aItem.mIconList[(aMetaData - aItem.mOffset)][1];
/* 156:    */           }
/* 157:    */           else
/* 158:    */           {
/* 159:    */         
/* 160:100 */             if (tCharge >= tStats[0].longValue()) {
/* 161:101 */               tIcon = aItem.mIconList[(aMetaData - aItem.mOffset)][8];
/* 162:    */             } else {
/* 163:103 */               tIcon = aItem.mIconList[(aMetaData - aItem.mOffset)][(7 - (int)java.lang.Math.max(0L, java.lang.Math.min(5L, (tStats[0].longValue() - tCharge) * 6L / tStats[0].longValue())))];
/* 164:    */             }
/* 165:    */           }
/* 166:    */         }
/* 167:    */         else
/* 168:    */         {
/* 169:105 */           tIcon = aItem.mIconList[(aMetaData - aItem.mOffset)][0];
/* 170:    */         }
/* 171:    */       }
/* 172:    */       else
/* 173:    */       {
/* 174:108 */         tIcon = aItem.mIconList[(aMetaData - aItem.mOffset)][0];
/* 175:    */       }
/* 176:110 */       Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
/* 177:111 */       GL11.glBlendFunc(770, 771);
/* 178:112 */       if (type.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/* 179:112 */         GT_RenderUtil.renderItemIcon(tIcon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/* 180:    */       } else {
/* 181:112 */         ItemRenderer.renderItemIn2D(Tessellator.instance, tIcon.getMaxU(), tIcon.getMinV(), tIcon.getMinU(), tIcon.getMaxV(), tIcon.getIconWidth(), tIcon.getIconHeight(), 0.0625F);
/* 182:    */       }
/* 183:    */     }
/* 184:115 */     GL11.glDisable(3042);
/* 185:    */   }
/* 186:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.render.GT_MetaGenerated_Item_Renderer
 * JD-Core Version:    0.7.0.1
 */