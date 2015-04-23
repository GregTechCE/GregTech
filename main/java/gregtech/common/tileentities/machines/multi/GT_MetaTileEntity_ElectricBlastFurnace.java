/*   1:    */ package gregtech.common.tileentities.machines.multi;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
/*   4:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   5:    */ import gregtech.api.gui.GT_GUIContainer_MultiMachine;
/*   6:    */ import gregtech.api.interfaces.ITexture;
/*   7:    */ import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
/*   8:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   9:    */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
/*  10:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  11:    */ import gregtech.api.util.GT_Recipe;
/*  12:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*  13:    */ import gregtech.api.util.GT_Utility;

/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Arrays;

/*  16:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  19:    */ import net.minecraftforge.fluids.FluidStack;
/*  20:    */ 
/*  21:    */ public class GT_MetaTileEntity_ElectricBlastFurnace
/*  22:    */   extends GT_MetaTileEntity_MultiBlockBase
/*  23:    */ {
/*  24: 25 */   private int mHeatingCapacity = 0;
/*  25:    */   
/*  26:    */   public GT_MetaTileEntity_ElectricBlastFurnace(int aID, String aName, String aNameRegional)
/*  27:    */   {
/*  28: 28 */     super(aID, aName, aNameRegional);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public GT_MetaTileEntity_ElectricBlastFurnace(String aName)
/*  32:    */   {
/*  33: 32 */     super(aName);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  37:    */   {
/*  38: 37 */     return new GT_MetaTileEntity_ElectricBlastFurnace(this.mName);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String[] getDescription()
/*  42:    */   {
/*  43: 42 */     return new String[] { "Controller Block for the Blast Furnace", "Size: 3x3x4 (Hollow)", "Controller (front middle at bottom)", "16x Heating Coils (two middle Layers, hollow)", "1x Input (one of bottom)", "1x Output (one of bottom)", "1x Energy Hatch (one of bottom)", "1x Maintenance Hatch (one of bottom)", "1x Muffler Hatch (top middle)", "Heat Proof Machine Casings for the rest" };
/*  44:    */   }
/*  45:    */   
/*  46:    */   public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
/*  47:    */   {
/*  48: 47 */     if (aSide == aFacing) {
/*  49: 47 */       return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[11], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE) };
/*  50:    */     }
/*  51: 48 */     return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[11] };
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  55:    */   {
/*  56: 53 */     return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "ElectricBlastFurnace.png");
/*  57:    */   }
/*  58:    */   
/*  59:    */   public GT_Recipe.GT_Recipe_Map getRecipeMap()
/*  60:    */   {
/*  61: 58 */     return GT_Recipe.GT_Recipe_Map.sBlastRecipes;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isCorrectMachinePart(ItemStack aStack)
/*  65:    */   {
/*  66: 63 */     return true;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean isFacingValid(byte aFacing)
/*  70:    */   {
/*  71: 66 */     return aFacing > 1;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean checkRecipe(ItemStack aStack)
/*  75:    */   {
/*  76: 70 */     ArrayList<ItemStack> tInputList = getStoredInputs();
/*  77: 71 */     for (int i = 0; i < tInputList.size() - 1; i++) {
/*  78: 71 */       for (int j = i + 1; j < tInputList.size(); j++) {
/*  79: 72 */         if (GT_Utility.areStacksEqual((ItemStack)tInputList.get(i), (ItemStack)tInputList.get(j))) {
/*  80: 73 */           if (((ItemStack)tInputList.get(i)).stackSize >= ((ItemStack)tInputList.get(j)).stackSize)
/*  81:    */           {
/*  82: 73 */             tInputList.remove(j--);
/*  83:    */           }
/*  84:    */           else
/*  85:    */           {
/*  86: 73 */             tInputList.remove(i--); break;
/*  87:    */           }
/*  88:    */         }
/*  89:    */       }
/*  90:    */     }
/*  91: 76 */     ItemStack[] tInputs = (ItemStack[])Arrays.copyOfRange(tInputList.toArray(new ItemStack[tInputList.size()]), 0, 2);
/*  92:    */     
/*  93: 78 */     ArrayList<FluidStack> tFluidList = getStoredFluids();
/*  94: 79 */     for (int i = 0; i < tFluidList.size() - 1; i++) {
/*  95: 79 */       for (int j = i + 1; j < tFluidList.size(); j++) {
/*  96: 80 */         if (GT_Utility.areFluidsEqual((FluidStack)tFluidList.get(i), (FluidStack)tFluidList.get(j))) {
/*  97: 81 */           if (((FluidStack)tFluidList.get(i)).amount >= ((FluidStack)tFluidList.get(j)).amount)
/*  98:    */           {
/*  99: 81 */             tFluidList.remove(j--);
/* 100:    */           }
/* 101:    */           else
/* 102:    */           {
/* 103: 81 */             tFluidList.remove(i--); break;
/* 104:    */           }
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108: 84 */     FluidStack[] tFluids = (FluidStack[])Arrays.copyOfRange(tFluidList.toArray(new FluidStack[tInputList.size()]), 0, 1);
/* 109: 86 */     if (tInputList.size() > 0)
/* 110:    */     {
/* 111: 87 */       long tVoltage = getMaxInputVoltage();
/* 112: 88 */       byte tTier = (byte)Math.max(1, GT_Utility.getTier(tVoltage));
/* 113: 89 */       GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sBlastRecipes.findRecipe(getBaseMetaTileEntity(), false, gregtech.api.enums.GT_Values.V[tTier], tFluids, tInputs);
/* 114: 91 */       if ((tRecipe != null) && (this.mHeatingCapacity >= tRecipe.mSpecialValue) && (tRecipe.isRecipeInputEqual(true, tFluids, tInputs)))
/* 115:    */       {
/* 116: 92 */         this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
/* 117: 93 */         this.mEfficiencyIncrease = 10000;
/* 118: 95 */         if (tRecipe.mEUt <= 16)
/* 119:    */         {
/* 120: 96 */           this.mEUt = (tRecipe.mEUt * (1 << tTier - 1) * (1 << tTier - 1));
/* 121: 97 */           this.mMaxProgresstime = (tRecipe.mDuration / (1 << tTier - 1));
/* 122:    */         }
/* 123:    */         else
/* 124:    */         {
/* 125: 99 */           this.mEUt = tRecipe.mEUt;
/* 126:100 */           this.mMaxProgresstime = tRecipe.mDuration;
/* 127:101 */           while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)])
/* 128:    */           {
/* 129:102 */             this.mEUt *= 4;
/* 130:103 */             this.mMaxProgresstime /= 2;
/* 131:    */           }
/* 132:    */         }
/* 133:107 */         if (this.mEUt > 0) {
/* 134:107 */           this.mEUt = (-this.mEUt);
/* 135:    */         }
/* 136:108 */         this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
/* 137:109 */         this.mOutputItems = new ItemStack[] { tRecipe.getOutput(0), tRecipe.getOutput(1) };
/* 138:110 */         updateSlots();
/* 139:111 */         return true;
/* 140:    */       }
/* 141:    */     }
/* 142:114 */     return false;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack)
/* 146:    */   {
/* 147:119 */     int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
/* 148:    */     
/* 149:121 */     this.mHeatingCapacity = 0;
/* 150:123 */     if (!aBaseMetaTileEntity.getAirOffset(xDir, 1, zDir)) {
/* 151:123 */       return false;
/* 152:    */     }
/* 153:124 */     if (!aBaseMetaTileEntity.getAirOffset(xDir, 2, zDir)) {
/* 154:124 */       return false;
/* 155:    */     }
/* 156:126 */     addMufflerToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir, 3, zDir), 11);
/* 157:    */     
/* 158:128 */     byte tUsedMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir + 1, 2, zDir);
/* 159:130 */     switch (tUsedMeta)
/* 160:    */     {
/* 161:    */     case 12: 
/* 162:131 */       this.mHeatingCapacity = 1800; break;
/* 163:    */     case 13: 
/* 164:132 */       this.mHeatingCapacity = 2700; break;
/* 165:    */     case 14: 
/* 166:133 */       this.mHeatingCapacity = 3600; break;
/* 167:    */     default: 
/* 168:134 */       return false;
/* 169:    */     }
/* 170:137 */     for (int i = -1; i < 2; i++) {
/* 171:137 */       for (int j = -1; j < 2; j++) {
/* 172:137 */         if ((i != 0) || (j != 0))
/* 173:    */         {
/* 174:138 */           if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 2, zDir + j) != GregTech_API.sBlockCasings1) {
/* 175:138 */             return false;
/* 176:    */           }
/* 177:139 */           if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 2, zDir + j) != tUsedMeta) {
/* 178:139 */             return false;
/* 179:    */           }
/* 180:140 */           if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 1, zDir + j) != GregTech_API.sBlockCasings1) {
/* 181:140 */             return false;
/* 182:    */           }
/* 183:141 */           if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 1, zDir + j) != tUsedMeta) {
/* 184:141 */             return false;
/* 185:    */           }
/* 186:142 */           if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 3, zDir + j) != GregTech_API.sBlockCasings1) {
/* 187:142 */             return false;
/* 188:    */           }
/* 189:143 */           if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 3, zDir + j) != 11) {
/* 190:143 */             return false;
/* 191:    */           }
/* 192:    */         }
/* 193:    */       }
/* 194:    */     }
/* 195:146 */     for (int i = -1; i < 2; i++) {
/* 196:146 */       for (int j = -1; j < 2; j++) {
/* 197:146 */         if ((xDir + i != 0) || (zDir + j != 0))
/* 198:    */         {
/* 199:147 */           IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j);
/* 200:148 */           if ((!addMaintenanceToMachineList(tTileEntity, 11)) && (!addInputToMachineList(tTileEntity, 11)) && (!addOutputToMachineList(tTileEntity, 11)) && (!addEnergyInputToMachineList(tTileEntity, 11)))
/* 201:    */           {
/* 202:149 */             if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j) != GregTech_API.sBlockCasings1) {
/* 203:149 */               return false;
/* 204:    */             }
/* 205:150 */             if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) != 11) {
/* 206:150 */               return false;
/* 207:    */             }
/* 208:    */           }
/* 209:    */         }
/* 210:    */       }
/* 211:    */     }
/* 212:154 */     this.mHeatingCapacity += 100 * (GT_Utility.getTier(getMaxInputVoltage()) - 2);
/* 213:155 */     return true;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public int getMaxEfficiency(ItemStack aStack)
/* 217:    */   {
/* 218:160 */     return 10000;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public int getPollutionPerTick(ItemStack aStack)
/* 222:    */   {
/* 223:165 */     return 10;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public int getDamageToComponent(ItemStack aStack)
/* 227:    */   {
/* 228:170 */     return 0;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public int getAmountOfOutputs()
/* 232:    */   {
/* 233:175 */     return 2;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean explodesOnComponentBreak(ItemStack aStack)
/* 237:    */   {
/* 238:180 */     return false;
/* 239:    */   }
/* 240:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_ElectricBlastFurnace
 * JD-Core Version:    0.7.0.1
 */