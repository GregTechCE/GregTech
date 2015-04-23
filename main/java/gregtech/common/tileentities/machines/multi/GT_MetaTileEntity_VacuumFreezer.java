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

/*  15:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  18:    */ 
/*  19:    */ public class GT_MetaTileEntity_VacuumFreezer
/*  20:    */   extends GT_MetaTileEntity_MultiBlockBase
/*  21:    */ {
/*  22:    */   public GT_MetaTileEntity_VacuumFreezer(int aID, String aName, String aNameRegional)
/*  23:    */   {
/*  24: 24 */     super(aID, aName, aNameRegional);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public GT_MetaTileEntity_VacuumFreezer(String aName)
/*  28:    */   {
/*  29: 28 */     super(aName);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  33:    */   {
/*  34: 33 */     return new GT_MetaTileEntity_VacuumFreezer(this.mName);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String[] getDescription()
/*  38:    */   {
/*  39: 38 */     return new String[] { "Controller Block for the Vacuum Freezer", "Size: 3x3x3 (Hollow)", "Controller (front centered)", "1x Input (anywhere)", "1x Output (anywhere)", "1x Energy Hatch (anywhere)", "1x Maintenance Hatch (anywhere)", "Frost Proof Casings for the rest (16 at least!)" };
/*  40:    */   }
/*  41:    */   
/*  42:    */   public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
/*  43:    */   {
/*  44: 43 */     if (aSide == aFacing) {
/*  45: 43 */       return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[17], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER) };
/*  46:    */     }
/*  47: 44 */     return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[17] };
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  51:    */   {
/*  52: 49 */     return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "VacuumFreezer.png");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public GT_Recipe.GT_Recipe_Map getRecipeMap()
/*  56:    */   {
/*  57: 54 */     return GT_Recipe.GT_Recipe_Map.sVacuumRecipes;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isCorrectMachinePart(ItemStack aStack)
/*  61:    */   {
/*  62: 59 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isFacingValid(byte aFacing)
/*  66:    */   {
/*  67: 62 */     return aFacing > 1;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean checkRecipe(ItemStack aStack)
/*  71:    */   {
/*  72: 66 */     ArrayList<ItemStack> tInputList = getStoredInputs();
/*  73: 68 */     for (ItemStack tInput : tInputList)
/*  74:    */     {
/*  75: 69 */       long tVoltage = getMaxInputVoltage();
/*  76: 70 */       byte tTier = (byte)Math.max(1, GT_Utility.getTier(tVoltage));
/*  77:    */       
/*  78: 72 */       GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sVacuumRecipes.findRecipe(getBaseMetaTileEntity(), false, gregtech.api.enums.GT_Values.V[tTier], null, new ItemStack[] { tInput });
/*  79: 73 */       if (tRecipe != null) {
/*  80: 73 */         if (tRecipe.isRecipeInputEqual(true, null, new ItemStack[] { tInput }))
/*  81:    */         {
/*  82: 74 */           this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
/*  83: 75 */           this.mEfficiencyIncrease = 10000;
/*  84: 77 */           if (tRecipe.mEUt <= 16)
/*  85:    */           {
/*  86: 78 */             this.mEUt = (tRecipe.mEUt * (1 << tTier - 1) * (1 << tTier - 1));
/*  87: 79 */             this.mMaxProgresstime = (tRecipe.mDuration / (1 << tTier - 1));
/*  88:    */           }
/*  89:    */           else
/*  90:    */           {
/*  91: 81 */             this.mEUt = tRecipe.mEUt;
/*  92: 82 */             this.mMaxProgresstime = tRecipe.mDuration;
/*  93: 83 */             while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)])
/*  94:    */             {
/*  95: 84 */               this.mEUt *= 4;
/*  96: 85 */               this.mMaxProgresstime /= 2;
/*  97:    */             }
/*  98:    */           }
/*  99: 89 */           if (this.mEUt > 0) {
/* 100: 89 */             this.mEUt = (-this.mEUt);
/* 101:    */           }
/* 102: 90 */           this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
/* 103: 91 */           this.mOutputItems = new ItemStack[] { tRecipe.getOutput(0) };
/* 104: 92 */           updateSlots();
/* 105: 93 */           return true;
/* 106:    */         }
/* 107:    */       }
/* 108:    */     }
/* 109: 96 */     return false;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack)
/* 113:    */   {
/* 114:101 */     int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
/* 115:103 */     if (!aBaseMetaTileEntity.getAirOffset(xDir, 0, zDir)) {
/* 116:103 */       return false;
/* 117:    */     }
/* 118:105 */     int tAmount = 0;
/* 119:107 */     for (int i = -1; i < 2; i++) {
/* 120:107 */       for (int j = -1; j < 2; j++) {
/* 121:107 */         for (int h = -1; h < 2; h++) {
/* 122:107 */           if ((h != 0) || (((xDir + i != 0) || (zDir + j != 0)) && ((i != 0) || (j != 0))))
/* 123:    */           {
/* 124:108 */             IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
/* 125:109 */             if ((!addMaintenanceToMachineList(tTileEntity, 17)) && (!addInputToMachineList(tTileEntity, 17)) && (!addOutputToMachineList(tTileEntity, 17)) && (!addEnergyInputToMachineList(tTileEntity, 17)))
/* 126:    */             {
/* 127:110 */               if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings2) {
/* 128:110 */                 return false;
/* 129:    */               }
/* 130:111 */               if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 1) {
/* 131:111 */                 return false;
/* 132:    */               }
/* 133:112 */               tAmount++;
/* 134:    */             }
/* 135:    */           }
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:116 */     return tAmount >= 16;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int getMaxEfficiency(ItemStack aStack)
/* 143:    */   {
/* 144:121 */     return 10000;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int getPollutionPerTick(ItemStack aStack)
/* 148:    */   {
/* 149:126 */     return 0;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public int getDamageToComponent(ItemStack aStack)
/* 153:    */   {
/* 154:131 */     return 0;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int getAmountOfOutputs()
/* 158:    */   {
/* 159:136 */     return 1;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean explodesOnComponentBreak(ItemStack aStack)
/* 163:    */   {
/* 164:141 */     return false;
/* 165:    */   }
/* 166:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_VacuumFreezer
 * JD-Core Version:    0.7.0.1
 */