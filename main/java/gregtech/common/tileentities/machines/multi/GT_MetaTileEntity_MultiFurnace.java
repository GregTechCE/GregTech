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
/*  11:    */ import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
/*  12:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*  13:    */ import gregtech.api.util.GT_Utility;

/*  14:    */ import java.util.ArrayList;

/*  15:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  18:    */ 
/*  19:    */ public class GT_MetaTileEntity_MultiFurnace
/*  20:    */   extends GT_MetaTileEntity_MultiBlockBase
/*  21:    */ {
/*  22: 22 */   private int mLevel = 0;
/*  23:    */   
/*  24:    */   public GT_MetaTileEntity_MultiFurnace(int aID, String aName, String aNameRegional)
/*  25:    */   {
/*  26: 25 */     super(aID, aName, aNameRegional);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public GT_MetaTileEntity_MultiFurnace(String aName)
/*  30:    */   {
/*  31: 29 */     super(aName);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  35:    */   {
/*  36: 34 */     return new GT_MetaTileEntity_MultiFurnace(this.mName);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String[] getDescription()
/*  40:    */   {
/*  41: 39 */     return new String[] { "Smelts up to 6-18 Items at once", "Controller Block for the Multi Smelter", "Size: 3x3x3 (Hollow)", "Controller (front middle at bottom)", "8x Heating Coils (middle Layer, hollow)", "1x Input (one of bottom)", "1x Output (one of bottom)", "1x Energy Hatch (one of bottom)", "1x Maintenance Hatch (one of bottom)", "1x Muffler Hatch (top middle)", "Heat Proof Machine Casings for the rest" };
/*  42:    */   }
/*  43:    */   
/*  44:    */   public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
/*  45:    */   {
/*  46: 44 */     if (aSide == aFacing) {
/*  47: 44 */       return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[11], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_MULTI_SMELTER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_MULTI_SMELTER) };
/*  48:    */     }
/*  49: 45 */     return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[11] };
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  53:    */   {
/*  54: 50 */     return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiFurnace.png");
/*  55:    */   }
/*  56:    */   
/*  57:    */   public GT_Recipe.GT_Recipe_Map getRecipeMap()
/*  58:    */   {
/*  59: 55 */     return GT_Recipe.GT_Recipe_Map.sFurnaceRecipes;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isCorrectMachinePart(ItemStack aStack)
/*  63:    */   {
/*  64: 60 */     return true;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean isFacingValid(byte aFacing)
/*  68:    */   {
/*  69: 63 */     return aFacing > 1;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean checkRecipe(ItemStack aStack)
/*  73:    */   {
/*  74: 67 */     ArrayList<ItemStack> tInputList = getStoredInputs();
/*  75: 69 */     if (!tInputList.isEmpty())
/*  76:    */     {
/*  77: 70 */       byte tTier = (byte)Math.max(1, GT_Utility.getTier(getMaxInputVoltage()));
/*  78:    */       
/*  79: 72 */       int j = 0;
/*  80: 73 */       this.mOutputItems = new ItemStack[6 * this.mLevel];
/*  81: 75 */       for (int i = 0; (i < 100) && (j < this.mOutputItems.length); i++) {
/*  82: 75 */         if (null != (this.mOutputItems[j] = GT_ModHandler.getSmeltingOutput((ItemStack)tInputList.get(i % tInputList.size()), true, null))) {
/*  83: 75 */           j++;
/*  84:    */         }
/*  85:    */       }
/*  86: 77 */       if (j > 0)
/*  87:    */       {
/*  88: 78 */         this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
/*  89: 79 */         this.mEfficiencyIncrease = 10000;
/*  90:    */         
/*  91: 81 */         this.mEUt = (-4 * (1 << tTier - 1) * (1 << tTier - 1) * this.mLevel);
/*  92: 82 */         this.mMaxProgresstime = Math.max(1, 512 / (1 << tTier - 1));
/*  93:    */       }
/*  94: 85 */       updateSlots();
/*  95: 86 */       return true;
/*  96:    */     }
/*  97: 88 */     return false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack)
/* 101:    */   {
/* 102: 93 */     int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
/* 103:    */     
/* 104: 95 */     this.mLevel = 0;
/* 105: 97 */     if (!aBaseMetaTileEntity.getAirOffset(xDir, 1, zDir)) {
/* 106: 97 */       return false;
/* 107:    */     }
/* 108: 99 */     addMufflerToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir, 2, zDir), 11);
/* 109:    */     
/* 110:101 */     byte tUsedMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir + 1, 1, zDir);
/* 111:103 */     switch (tUsedMeta)
/* 112:    */     {
/* 113:    */     case 12: 
/* 114:104 */       this.mLevel = 1; break;
/* 115:    */     case 13: 
/* 116:105 */       this.mLevel = 2; break;
/* 117:    */     case 14: 
/* 118:106 */       this.mLevel = 3; break;
/* 119:    */     default: 
/* 120:107 */       return false;
/* 121:    */     }
/* 122:110 */     for (int i = -1; i < 2; i++) {
/* 123:110 */       for (int j = -1; j < 2; j++) {
/* 124:110 */         if ((i != 0) || (j != 0))
/* 125:    */         {
/* 126:111 */           if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 1, zDir + j) != GregTech_API.sBlockCasings1) {
/* 127:111 */             return false;
/* 128:    */           }
/* 129:112 */           if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 1, zDir + j) != tUsedMeta) {
/* 130:112 */             return false;
/* 131:    */           }
/* 132:113 */           if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 2, zDir + j) != GregTech_API.sBlockCasings1) {
/* 133:113 */             return false;
/* 134:    */           }
/* 135:114 */           if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 2, zDir + j) != 11) {
/* 136:114 */             return false;
/* 137:    */           }
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:117 */     for (int i = -1; i < 2; i++) {
/* 142:117 */       for (int j = -1; j < 2; j++) {
/* 143:117 */         if ((xDir + i != 0) || (zDir + j != 0))
/* 144:    */         {
/* 145:118 */           IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j);
/* 146:119 */           if ((!addMaintenanceToMachineList(tTileEntity, 11)) && (!addInputToMachineList(tTileEntity, 11)) && (!addOutputToMachineList(tTileEntity, 11)) && (!addEnergyInputToMachineList(tTileEntity, 11)))
/* 147:    */           {
/* 148:120 */             if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j) != GregTech_API.sBlockCasings1) {
/* 149:120 */               return false;
/* 150:    */             }
/* 151:121 */             if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) != 11) {
/* 152:121 */               return false;
/* 153:    */             }
/* 154:    */           }
/* 155:    */         }
/* 156:    */       }
/* 157:    */     }
/* 158:125 */     return true;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getMaxEfficiency(ItemStack aStack)
/* 162:    */   {
/* 163:130 */     return 10000;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public int getPollutionPerTick(ItemStack aStack)
/* 167:    */   {
/* 168:135 */     return 20;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getDamageToComponent(ItemStack aStack)
/* 172:    */   {
/* 173:140 */     return 0;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int getAmountOfOutputs()
/* 177:    */   {
/* 178:145 */     return 18;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean explodesOnComponentBreak(ItemStack aStack)
/* 182:    */   {
/* 183:150 */     return false;
/* 184:    */   }
/* 185:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_MultiFurnace
 * JD-Core Version:    0.7.0.1
 */