/*   1:    */ package gregtech.common.render;
/*   2:    */ 
/*   3:    */ import gregtech.api.enums.Materials;
/*   4:    */ import gregtech.api.interfaces.IIconContainer;
/*   5:    */ import gregtech.api.interfaces.IToolStats;
/*   6:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   7:    */ import gregtech.api.util.GT_Utility;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import net.minecraft.client.Minecraft;
/*  10:    */ import net.minecraft.client.renderer.ItemRenderer;
/*  11:    */ import net.minecraft.client.renderer.Tessellator;
/*  12:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  13:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.util.IIcon;
/*  16:    */ import net.minecraftforge.client.IItemRenderer;
/*  17:    */ import net.minecraftforge.client.IItemRenderer.ItemRenderType;
/*  18:    */ import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
/*  19:    */ import net.minecraftforge.client.MinecraftForgeClient;
/*  20:    */ import org.lwjgl.opengl.GL11;
/*  21:    */ 
/*  22:    */ public class GT_MetaGenerated_Tool_Renderer
/*  23:    */   implements IItemRenderer
/*  24:    */ {
/*  25:    */   public GT_MetaGenerated_Tool_Renderer()
/*  26:    */   {
/*  27: 24 */     for (GT_MetaGenerated_Tool tItem : GT_MetaGenerated_Tool.sInstances.values()) {
/*  28: 24 */       if (tItem != null) {
/*  29: 24 */         MinecraftForgeClient.registerItemRenderer(tItem, this);
/*  30:    */       }
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean handleRenderType(ItemStack aStack, IItemRenderer.ItemRenderType aType)
/*  35:    */   {
/*  36: 29 */     if ((GT_Utility.isStackInvalid(aStack)) || (aStack.getItemDamage() < 0)) {
/*  37: 29 */       return false;
/*  38:    */     }
/*  39: 30 */     return (aType == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) || (aType == IItemRenderer.ItemRenderType.INVENTORY) || (aType == IItemRenderer.ItemRenderType.EQUIPPED) || (aType == IItemRenderer.ItemRenderType.ENTITY);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType aType, ItemStack aStack, IItemRenderer.ItemRendererHelper aHelper)
/*  43:    */   {
/*  44: 35 */     if (GT_Utility.isStackInvalid(aStack)) {
/*  45: 35 */       return false;
/*  46:    */     }
/*  47: 36 */     return aType == IItemRenderer.ItemRenderType.ENTITY;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void renderItem(IItemRenderer.ItemRenderType aType, ItemStack aStack, Object... data)
/*  51:    */   {
/*  52: 41 */     if (GT_Utility.isStackInvalid(aStack)) {
/*  53: 41 */       return;
/*  54:    */     }
/*  55: 42 */     GT_MetaGenerated_Tool aItem = (GT_MetaGenerated_Tool)aStack.getItem();
/*  56: 43 */     GL11.glEnable(3042);
/*  57: 45 */     if (aType == IItemRenderer.ItemRenderType.ENTITY) {
/*  58: 46 */       if (RenderItem.renderInFrame)
/*  59:    */       {
/*  60: 47 */         GL11.glScalef(0.85F, 0.85F, 0.85F);
/*  61: 48 */         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/*  62: 49 */         GL11.glTranslated(-0.5D, -0.42D, 0.0D);
/*  63:    */       }
/*  64:    */       else
/*  65:    */       {
/*  66: 51 */         GL11.glTranslated(-0.5D, -0.42D, 0.0D);
/*  67:    */       }
/*  68:    */     }
/*  69: 55 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*  70:    */     
/*  71: 57 */     IToolStats tToolStats = aItem.getToolStats(aStack);
/*  72: 58 */     if (tToolStats != null)
/*  73:    */     {
/*  74: 59 */       IIconContainer aIcon = tToolStats.getIcon(false, aStack);
/*  75: 60 */       if (aIcon != null)
/*  76:    */       {
/*  77: 61 */         IIcon tIcon = aIcon.getIcon();IIcon tOverlay = aIcon.getOverlayIcon();
/*  78: 62 */         if (tIcon != null)
/*  79:    */         {
/*  80: 63 */           Minecraft.getMinecraft().renderEngine.bindTexture(aIcon.getTextureFile());
/*  81: 64 */           GL11.glBlendFunc(770, 771);
/*  82: 65 */           short[] tModulation = tToolStats.getRGBa(false, aStack);
/*  83: 66 */           GL11.glColor3f(tModulation[0] / 255.0F, tModulation[1] / 255.0F, tModulation[2] / 255.0F);
/*  84: 67 */           if (aType.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/*  85: 67 */             GT_RenderUtil.renderItemIcon(tIcon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/*  86:    */           } else {
/*  87: 67 */             ItemRenderer.renderItemIn2D(Tessellator.instance, tIcon.getMaxU(), tIcon.getMinV(), tIcon.getMinU(), tIcon.getMaxV(), tIcon.getIconWidth(), tIcon.getIconHeight(), 0.0625F);
/*  88:    */           }
/*  89: 68 */           GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*  90:    */         }
/*  91: 70 */         if (tOverlay != null)
/*  92:    */         {
/*  93: 71 */           Minecraft.getMinecraft().renderEngine.bindTexture(aIcon.getTextureFile());
/*  94: 72 */           GL11.glBlendFunc(770, 771);
/*  95: 73 */           if (aType.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/*  96: 73 */             GT_RenderUtil.renderItemIcon(tOverlay, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/*  97:    */           } else {
/*  98: 73 */             ItemRenderer.renderItemIn2D(Tessellator.instance, tOverlay.getMaxU(), tOverlay.getMinV(), tOverlay.getMinU(), tOverlay.getMaxV(), tOverlay.getIconWidth(), tOverlay.getIconHeight(), 0.0625F);
/*  99:    */           }
/* 100:    */         }
/* 101:    */       }
/* 102: 77 */       aIcon = tToolStats.getIcon(true, aStack);
/* 103: 78 */       if (aIcon != null)
/* 104:    */       {
/* 105: 79 */         IIcon tIcon = aIcon.getIcon();IIcon tOverlay = aIcon.getOverlayIcon();
/* 106: 80 */         if (tIcon != null)
/* 107:    */         {
/* 108: 81 */           Minecraft.getMinecraft().renderEngine.bindTexture(aIcon.getTextureFile());
/* 109: 82 */           GL11.glBlendFunc(770, 771);
/* 110: 83 */           short[] tModulation = tToolStats.getRGBa(true, aStack);
/* 111: 84 */           GL11.glColor3f(tModulation[0] / 255.0F, tModulation[1] / 255.0F, tModulation[2] / 255.0F);
/* 112: 85 */           if (aType.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/* 113: 85 */             GT_RenderUtil.renderItemIcon(tIcon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/* 114:    */           } else {
/* 115: 85 */             ItemRenderer.renderItemIn2D(Tessellator.instance, tIcon.getMaxU(), tIcon.getMinV(), tIcon.getMinU(), tIcon.getMaxV(), tIcon.getIconWidth(), tIcon.getIconHeight(), 0.0625F);
/* 116:    */           }
/* 117: 86 */           GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 118:    */         }
/* 119: 88 */         if (tOverlay != null)
/* 120:    */         {
/* 121: 89 */           Minecraft.getMinecraft().renderEngine.bindTexture(aIcon.getTextureFile());
/* 122: 90 */           GL11.glBlendFunc(770, 771);
/* 123: 91 */           if (aType.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/* 124: 91 */             GT_RenderUtil.renderItemIcon(tOverlay, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/* 125:    */           } else {
/* 126: 91 */             ItemRenderer.renderItemIn2D(Tessellator.instance, tOverlay.getMaxU(), tOverlay.getMinV(), tOverlay.getMinU(), tOverlay.getMaxV(), tOverlay.getIconWidth(), tOverlay.getIconHeight(), 0.0625F);
/* 127:    */           }
/* 128:    */         }
/* 129:    */       }
/* 130: 94 */       if ((aType == IItemRenderer.ItemRenderType.INVENTORY) && (GT_MetaGenerated_Tool.getPrimaryMaterial(aStack) != Materials._NULL))
/* 131:    */       {
/* 132: 95 */         long tDamage = GT_MetaGenerated_Tool.getToolDamage(aStack);long tMaxDamage = GT_MetaGenerated_Tool.getToolMaxDamage(aStack);
/* 133: 96 */         if (tDamage <= 0L) {
/* 134: 97 */           aIcon = gregtech.api.enums.Textures.ItemIcons.DURABILITY_BAR[8];
/* 135: 98 */         } else if (tDamage >= tMaxDamage) {
/* 136: 99 */           aIcon = gregtech.api.enums.Textures.ItemIcons.DURABILITY_BAR[0];
/* 137:    */         } else {
/* 138:101 */           aIcon = gregtech.api.enums.Textures.ItemIcons.DURABILITY_BAR[((int)java.lang.Math.max(0L, java.lang.Math.min(7L, (tMaxDamage - tDamage) * 8L / tMaxDamage)))];
/* 139:    */         }
/* 140:103 */         if (aIcon != null)
/* 141:    */         {
/* 142:104 */           IIcon tIcon = aIcon.getIcon();IIcon tOverlay = aIcon.getOverlayIcon();
/* 143:105 */           if (tIcon != null)
/* 144:    */           {
/* 145:106 */             Minecraft.getMinecraft().renderEngine.bindTexture(aIcon.getTextureFile());
/* 146:107 */             GL11.glBlendFunc(770, 771);
/* 147:108 */             if (aType.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/* 148:108 */               GT_RenderUtil.renderItemIcon(tIcon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/* 149:    */             } else {
/* 150:108 */               ItemRenderer.renderItemIn2D(Tessellator.instance, tIcon.getMaxU(), tIcon.getMinV(), tIcon.getMinU(), tIcon.getMaxV(), tIcon.getIconWidth(), tIcon.getIconHeight(), 0.0625F);
/* 151:    */             }
/* 152:    */           }
/* 153:110 */           if (tOverlay != null)
/* 154:    */           {
/* 155:111 */             Minecraft.getMinecraft().renderEngine.bindTexture(aIcon.getTextureFile());
/* 156:112 */             GL11.glBlendFunc(770, 771);
/* 157:113 */             if (aType.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/* 158:113 */               GT_RenderUtil.renderItemIcon(tOverlay, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/* 159:    */             } else {
/* 160:113 */               ItemRenderer.renderItemIn2D(Tessellator.instance, tOverlay.getMaxU(), tOverlay.getMinV(), tOverlay.getMinU(), tOverlay.getMaxV(), tOverlay.getIconWidth(), tOverlay.getIconHeight(), 0.0625F);
/* 161:    */             }
/* 162:    */           }
/* 163:    */         }
/* 164:117 */         Long[] tStats = aItem.getElectricStats(aStack);
/* 165:118 */         if ((tStats != null) && (tStats[3].longValue() < 0L))
/* 166:    */         {
/* 167:119 */           long tCharge = aItem.getRealCharge(aStack);
/* 168:120 */           if (tCharge <= 0L) {
/* 169:121 */             aIcon = gregtech.api.enums.Textures.ItemIcons.ENERGY_BAR[0];
/* 170:122 */           } else if (tCharge >= tStats[0].longValue()) {
/* 171:123 */             aIcon = gregtech.api.enums.Textures.ItemIcons.ENERGY_BAR[8];
/* 172:    */           } else {
/* 173:125 */             aIcon = gregtech.api.enums.Textures.ItemIcons.ENERGY_BAR[(7 - (int)java.lang.Math.max(0L, java.lang.Math.min(6L, (tStats[0].longValue() - tCharge) * 7L / tStats[0].longValue())))];
/* 174:    */           }
/* 175:    */         }
/* 176:    */         else
/* 177:    */         {
/* 178:127 */           aIcon = null;
/* 179:    */         }
/* 180:130 */         if (aIcon != null)
/* 181:    */         {
/* 182:131 */           IIcon tIcon = aIcon.getIcon();IIcon tOverlay = aIcon.getOverlayIcon();
/* 183:132 */           if (tIcon != null)
/* 184:    */           {
/* 185:133 */             Minecraft.getMinecraft().renderEngine.bindTexture(aIcon.getTextureFile());
/* 186:134 */             GL11.glBlendFunc(770, 771);
/* 187:135 */             if (aType.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/* 188:135 */               GT_RenderUtil.renderItemIcon(tIcon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/* 189:    */             } else {
/* 190:135 */               ItemRenderer.renderItemIn2D(Tessellator.instance, tIcon.getMaxU(), tIcon.getMinV(), tIcon.getMinU(), tIcon.getMaxV(), tIcon.getIconWidth(), tIcon.getIconHeight(), 0.0625F);
/* 191:    */             }
/* 192:    */           }
/* 193:137 */           if (tOverlay != null)
/* 194:    */           {
/* 195:138 */             Minecraft.getMinecraft().renderEngine.bindTexture(aIcon.getTextureFile());
/* 196:139 */             GL11.glBlendFunc(770, 771);
/* 197:140 */             if (aType.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
/* 198:140 */               GT_RenderUtil.renderItemIcon(tOverlay, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
/* 199:    */             } else {
/* 200:140 */               ItemRenderer.renderItemIn2D(Tessellator.instance, tOverlay.getMaxU(), tOverlay.getMinV(), tOverlay.getMinU(), tOverlay.getMaxV(), tOverlay.getIconWidth(), tOverlay.getIconHeight(), 0.0625F);
/* 201:    */             }
/* 202:    */           }
/* 203:    */         }
/* 204:    */       }
/* 205:    */     }
/* 206:145 */     GL11.glDisable(3042);
/* 207:    */   }
/* 208:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.render.GT_MetaGenerated_Tool_Renderer
 * JD-Core Version:    0.7.0.1
 */