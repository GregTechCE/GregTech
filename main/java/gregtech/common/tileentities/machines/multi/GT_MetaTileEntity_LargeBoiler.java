/*   1:    */ package gregtech.common.tileentities.machines.multi;
/*   2:    */ 
/*   3:    */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*   4:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   5:    */ import gregtech.api.gui.GT_GUIContainer_MultiMachine;
/*   6:    */ import gregtech.api.interfaces.ITexture;
/*   7:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   8:    */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
/*   9:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  10:    */ import gregtech.api.util.GT_ModHandler;
/*  11:    */ import gregtech.api.util.GT_Recipe;
/*  12:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*  13:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map_Fuel;
/*  14:    */ import gregtech.api.util.GT_Utility;

/*  15:    */ import java.util.ArrayList;

/*  16:    */ import net.minecraft.block.Block;
/*  17:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  18:    */ import net.minecraft.item.ItemStack;
/*  19:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  20:    */ import net.minecraftforge.fluids.FluidStack;
/*  21:    */ 
/*  22:    */ public abstract class GT_MetaTileEntity_LargeBoiler
/*  23:    */   extends GT_MetaTileEntity_MultiBlockBase
/*  24:    */ {
/*  25:    */   public GT_MetaTileEntity_LargeBoiler(int aID, String aName, String aNameRegional)
/*  26:    */   {
/*  27: 25 */     super(aID, aName, aNameRegional);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public GT_MetaTileEntity_LargeBoiler(String aName)
/*  31:    */   {
/*  32: 29 */     super(aName);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String[] getDescription()
/*  36:    */   {
/*  37: 34 */     return new String[] { "Controller Block for the Large Boiler", "Size: 3x3x5", "Controller (front middle in Fireboxes)", "3x3 of Fire Boxes (bottom Layer, Min 3!)", "3x3x4 of Casing (above Fireboxes, hollow, Min 24!)", "3 Pipe Casing Blocks inside the Hollow Casing", "1x Input (one of Fireboxes)", "1x Maintenance Hatch (one of Fireboxes)", "1x Muffler Hatch (one of Fireboxes)", "1x Fluid Output (one of Main Casing)" };
/*  38:    */   }
/*  39:    */   
/*  40:    */   public abstract Block getCasingBlock();
/*  41:    */   
/*  42:    */   public abstract byte getCasingMeta();
/*  43:    */   
/*  44:    */   public abstract byte getCasingTextureIndex();
/*  45:    */   
/*  46:    */   public abstract Block getPipeBlock();
/*  47:    */   
/*  48:    */   public abstract byte getPipeMeta();
/*  49:    */   
/*  50:    */   public abstract Block getFireboxBlock();
/*  51:    */   
/*  52:    */   public abstract byte getFireboxMeta();
/*  53:    */   
/*  54:    */   public abstract byte getFireboxTextureIndex();
/*  55:    */   
/*  56:    */   public abstract int getEUt();
/*  57:    */   
/*  58:    */   public abstract int getEfficiencyIncrease();
/*  59:    */   
/*  60:    */   public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
/*  61:    */   {
/*  62: 53 */     if (aSide == aFacing) {
/*  63: 53 */       return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[getCasingTextureIndex()], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER) };
/*  64:    */     }
/*  65: 54 */     return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[getCasingTextureIndex()] };
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  69:    */   {
/*  70: 59 */     return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "LargeBoiler.png");
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean isCorrectMachinePart(ItemStack aStack)
/*  74:    */   {
/*  75: 64 */     return true;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isFacingValid(byte aFacing)
/*  79:    */   {
/*  80: 67 */     return aFacing > 1;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean checkRecipe(ItemStack aStack)
/*  84:    */   {
/*  85: 71 */     for (GT_Recipe tRecipe : GT_Recipe.GT_Recipe_Map.sDieselFuels.mRecipeList)
/*  86:    */     {
/*  87: 72 */       FluidStack tFluid = GT_Utility.getFluidForFilledItem(tRecipe.getRepresentativeInput(0), true);
/*  88: 73 */       if ((tFluid != null) && (tRecipe.mSpecialValue > 1))
/*  89:    */       {
/*  90: 74 */         tFluid.amount = 1000;
/*  91: 75 */         if (depleteInput(tFluid))
/*  92:    */         {
/*  93: 76 */           this.mMaxProgresstime = (tRecipe.mSpecialValue / 2);
/*  94: 77 */           this.mEUt = getEUt();
/*  95: 78 */           this.mEfficiencyIncrease = (this.mMaxProgresstime * getEfficiencyIncrease() * 4);
/*  96: 79 */           return true;
/*  97:    */         }
/*  98:    */       }
/*  99:    */     }
/* 100: 83 */     for (GT_Recipe tRecipe : GT_Recipe.GT_Recipe_Map.sDenseLiquidFuels.mRecipeList)
/* 101:    */     {
/* 102: 84 */       FluidStack tFluid = GT_Utility.getFluidForFilledItem(tRecipe.getRepresentativeInput(0), true);
/* 103: 85 */       if (tFluid != null)
/* 104:    */       {
/* 105: 86 */         tFluid.amount = 1000;
/* 106: 87 */         if (depleteInput(tFluid))
/* 107:    */         {
/* 108: 88 */           this.mMaxProgresstime = Math.max(1, tRecipe.mSpecialValue * 2);
/* 109: 89 */           this.mEUt = getEUt();
/* 110: 90 */           this.mEfficiencyIncrease = (this.mMaxProgresstime * getEfficiencyIncrease());
/* 111: 91 */           return true;
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115: 95 */     ArrayList<ItemStack> tInputList = getStoredInputs();
/* 116: 96 */     if (!tInputList.isEmpty()) {
/* 117: 97 */       for (ItemStack tInput : tInputList) {
/* 118: 98 */         if ((GT_Utility.getFluidForFilledItem(tInput, true) == null) && ((this.mMaxProgresstime = GT_ModHandler.getFuelValue(tInput) / 80) > 0))
/* 119:    */         {
/* 120: 99 */           this.mEUt = getEUt();
/* 121:100 */           this.mEfficiencyIncrease = (this.mMaxProgresstime * getEfficiencyIncrease());
/* 122:101 */           this.mOutputItems = new ItemStack[] { GT_Utility.getContainerItem(tInput, true) };
/* 123:102 */           tInput.stackSize -= 1;
/* 124:103 */           updateSlots();
/* 125:104 */           return true;
/* 126:    */         }
/* 127:    */       }
/* 128:    */     }
/* 129:108 */     this.mMaxProgresstime = 0;
/* 130:109 */     this.mEUt = 0;
/* 131:110 */     return false;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean onRunningTick(ItemStack aStack)
/* 135:    */   {
/* 136:115 */     if (this.mEUt > 0)
/* 137:    */     {
/* 138:116 */       int tGeneratedEU = (int)(this.mEUt * 2L * this.mEfficiency / 10000L);
/* 139:117 */       if (tGeneratedEU > 0) {
/* 140:118 */         if (depleteInput(Materials.Water.getFluid((tGeneratedEU + 160) / 160))) {
/* 141:119 */           addOutput(GT_ModHandler.getSteam(tGeneratedEU));
/* 142:    */         } else {
/* 143:121 */           explodeMultiblock();
/* 144:    */         }
/* 145:    */       }
/* 146:124 */       return true;
/* 147:    */     }
/* 148:126 */     return true;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack)
/* 152:    */   {
/* 153:131 */     int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
/* 154:    */     
/* 155:133 */     int tCasingAmount = 0;int tFireboxAmount = 0;
/* 156:135 */     for (int i = -1; i < 2; i++) {
/* 157:135 */       for (int j = -1; j < 2; j++) {
/* 158:136 */         if ((i != 0) || (j != 0))
/* 159:    */         {
/* 160:137 */           for (int k = 1; k <= 4; k++) {
/* 161:138 */             if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, k, zDir + j), getCasingTextureIndex()))
/* 162:    */             {
/* 163:139 */               if (aBaseMetaTileEntity.getBlockOffset(xDir + i, k, zDir + j) != getCasingBlock()) {
/* 164:139 */                 return false;
/* 165:    */               }
/* 166:140 */               if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, k, zDir + j) != getCasingMeta()) {
/* 167:140 */                 return false;
/* 168:    */               }
/* 169:141 */               tCasingAmount++;
/* 170:    */             }
/* 171:    */           }
/* 172:    */         }
/* 173:    */         else
/* 174:    */         {
/* 175:145 */           for (int k = 1; k <= 3; k++)
/* 176:    */           {
/* 177:146 */             if (aBaseMetaTileEntity.getBlockOffset(xDir + i, k, zDir + j) != getPipeBlock()) {
/* 178:146 */               return false;
/* 179:    */             }
/* 180:147 */             if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, k, zDir + j) != getPipeMeta()) {
/* 181:147 */               return false;
/* 182:    */             }
/* 183:    */           }
/* 184:149 */           if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 4, zDir + j), getCasingTextureIndex()))
/* 185:    */           {
/* 186:150 */             if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 4, zDir + j) != getCasingBlock()) {
/* 187:150 */               return false;
/* 188:    */             }
/* 189:151 */             if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 4, zDir + j) != getCasingMeta()) {
/* 190:151 */               return false;
/* 191:    */             }
/* 192:152 */             tCasingAmount++;
/* 193:    */           }
/* 194:    */         }
/* 195:    */       }
/* 196:    */     }
/* 197:157 */     for (int i = -1; i < 2; i++) {
/* 198:157 */       for (int j = -1; j < 2; j++) {
/* 199:157 */         if ((xDir + i != 0) || (zDir + j != 0))
/* 200:    */         {
/* 201:158 */           IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j);
/* 202:159 */           if ((!addMaintenanceToMachineList(tTileEntity, getFireboxTextureIndex())) && (!addInputToMachineList(tTileEntity, getFireboxTextureIndex())) && (!addMufflerToMachineList(tTileEntity, getFireboxTextureIndex())))
/* 203:    */           {
/* 204:160 */             if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j) != getFireboxBlock()) {
/* 205:160 */               return false;
/* 206:    */             }
/* 207:161 */             if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) != getFireboxMeta()) {
/* 208:161 */               return false;
/* 209:    */             }
/* 210:162 */             tFireboxAmount++;
/* 211:    */           }
/* 212:    */         }
/* 213:    */       }
/* 214:    */     }
/* 215:166 */     return (tCasingAmount >= 24) && (tFireboxAmount >= 3);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public int getMaxEfficiency(ItemStack aStack)
/* 219:    */   {
/* 220:171 */     return 10000;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public int getPollutionPerTick(ItemStack aStack)
/* 224:    */   {
/* 225:176 */     return 10;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public int getDamageToComponent(ItemStack aStack)
/* 229:    */   {
/* 230:181 */     return 0;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public int getAmountOfOutputs()
/* 234:    */   {
/* 235:186 */     return 1;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public boolean explodesOnComponentBreak(ItemStack aStack)
/* 239:    */   {
/* 240:191 */     return false;
/* 241:    */   }
/* 242:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_LargeBoiler
 * JD-Core Version:    0.7.0.1
 */