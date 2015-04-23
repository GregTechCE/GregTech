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
/*  16:    */ import java.util.Map;

/*  17:    */ import net.minecraft.block.Block;
/*  18:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  21:    */ 
/*  22:    */ public class GT_MetaTileEntity_ImplosionCompressor
/*  23:    */   extends GT_MetaTileEntity_MultiBlockBase
/*  24:    */ {
/*  25:    */   public GT_MetaTileEntity_ImplosionCompressor(int aID, String aName, String aNameRegional)
/*  26:    */   {
/*  27: 25 */     super(aID, aName, aNameRegional);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public GT_MetaTileEntity_ImplosionCompressor(String aName)
/*  31:    */   {
/*  32: 29 */     super(aName);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  36:    */   {
/*  37: 34 */     return new GT_MetaTileEntity_ImplosionCompressor(this.mName);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String[] getDescription()
/*  41:    */   {
/*  42: 39 */     return new String[] { "Controller Block for the Implosion Compressor", "Size: 3x3x3 (Hollow)", "Controller (front centered)", "1x Input (anywhere)", "1x Output (anywhere)", "1x Energy Hatch (anywhere)", "1x Maintenance Hatch (anywhere)", "1x Muffler Hatch (anywhere)", "Solid Steel Casings for the rest (16 at least!)" };
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
/*  46:    */   {
/*  47: 44 */     if (aSide == aFacing) {
/*  48: 44 */       return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[16], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_IMPLOSION_COMPRESSOR_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_IMPLOSION_COMPRESSOR) };
/*  49:    */     }
/*  50: 45 */     return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[16] };
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  54:    */   {
/*  55: 50 */     return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "ImplosionCompressor.png");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public GT_Recipe.GT_Recipe_Map getRecipeMap()
/*  59:    */   {
/*  60: 55 */     return GT_Recipe.GT_Recipe_Map.sImplosionRecipes;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean isCorrectMachinePart(ItemStack aStack)
/*  64:    */   {
/*  65: 60 */     return true;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean isFacingValid(byte aFacing)
/*  69:    */   {
/*  70: 63 */     return aFacing > 1;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean checkRecipe(ItemStack aStack)
/*  74:    */   {
/*  75: 67 */     ArrayList<ItemStack> tInputList = getStoredInputs();
/*  76: 69 */     for (int i = 0; i < tInputList.size() - 1; i++) {
/*  77: 69 */       for (int j = i + 1; j < tInputList.size(); j++) {
/*  78: 70 */         if (GT_Utility.areStacksEqual((ItemStack)tInputList.get(i), (ItemStack)tInputList.get(j))) {
/*  79: 71 */           if (((ItemStack)tInputList.get(i)).stackSize >= ((ItemStack)tInputList.get(j)).stackSize)
/*  80:    */           {
/*  81: 71 */             tInputList.remove(j--);
/*  82:    */           }
/*  83:    */           else
/*  84:    */           {
/*  85: 71 */             tInputList.remove(i--); break;
/*  86:    */           }
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90: 75 */     ItemStack[] tInputs = (ItemStack[])Arrays.copyOfRange(tInputList.toArray(new ItemStack[tInputList.size()]), 0, 2);
/*  91: 77 */     if (tInputList.size() > 0)
/*  92:    */     {
/*  93: 78 */       GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sImplosionRecipes.findRecipe(getBaseMetaTileEntity(), false, 9223372036854775807L, null, tInputs);
/*  94: 79 */       if ((tRecipe != null) && (tRecipe.isRecipeInputEqual(true, null, tInputs)))
/*  95:    */       {
/*  96: 80 */         this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
/*  97: 81 */         this.mEfficiencyIncrease = 10000;
/*  98:    */         
/*  99: 83 */         this.mEUt = (-tRecipe.mEUt);
/* 100: 84 */         this.mMaxProgresstime = Math.max(1, tRecipe.mDuration);
/* 101: 85 */         this.mOutputItems = new ItemStack[] { tRecipe.getOutput(0), tRecipe.getOutput(1) };
/* 102: 86 */         sendLoopStart((byte)20);
/* 103: 87 */         updateSlots();
/* 104: 88 */         return true;
/* 105:    */       }
/* 106:    */     }
/* 107: 91 */     return false;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void startSoundLoop(byte aIndex, double aX, double aY, double aZ)
/* 111:    */   {
/* 112: 96 */     super.startSoundLoop(aIndex, aX, aY, aZ);
/* 113: 97 */     if (aIndex == 20) {
/* 114: 97 */       GT_Utility.doSoundAtClient((String)GregTech_API.sSoundList.get(Integer.valueOf(5)), 10, 1.0F, aX, aY, aZ);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack)
/* 119:    */   {
/* 120:102 */     int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
/* 121:104 */     if (!aBaseMetaTileEntity.getAirOffset(xDir, 0, zDir)) {
/* 122:104 */       return false;
/* 123:    */     }
/* 124:106 */     int tAmount = 0;
/* 125:108 */     for (int i = -1; i < 2; i++) {
/* 126:108 */       for (int j = -1; j < 2; j++) {
/* 127:108 */         for (int h = -1; h < 2; h++) {
/* 128:108 */           if ((h != 0) || (((xDir + i != 0) || (zDir + j != 0)) && ((i != 0) || (j != 0))))
/* 129:    */           {
/* 130:109 */             IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
/* 131:110 */             if ((!addMaintenanceToMachineList(tTileEntity, 16)) && (!addMufflerToMachineList(tTileEntity, 16)) && (!addInputToMachineList(tTileEntity, 16)) && (!addOutputToMachineList(tTileEntity, 16)) && (!addEnergyInputToMachineList(tTileEntity, 16)))
/* 132:    */             {
/* 133:111 */               Block tBlock = aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j);
/* 134:112 */               byte tMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j);
/* 135:113 */               if (((tBlock != GregTech_API.sBlockCasings2) || (tMeta != 0)) && ((tBlock != GregTech_API.sBlockCasings3) || (tMeta != 4))) {
/* 136:113 */                 return false;
/* 137:    */               }
/* 138:114 */               tAmount++;
/* 139:    */             }
/* 140:    */           }
/* 141:    */         }
/* 142:    */       }
/* 143:    */     }
/* 144:118 */     return tAmount >= 16;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int getMaxEfficiency(ItemStack aStack)
/* 148:    */   {
/* 149:123 */     return 10000;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public int getPollutionPerTick(ItemStack aStack)
/* 153:    */   {
/* 154:128 */     return 1000;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int getDamageToComponent(ItemStack aStack)
/* 158:    */   {
/* 159:133 */     return 0;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public int getAmountOfOutputs()
/* 163:    */   {
/* 164:138 */     return 2;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean explodesOnComponentBreak(ItemStack aStack)
/* 168:    */   {
/* 169:143 */     return false;
/* 170:    */   }
/* 171:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_ImplosionCompressor
 * JD-Core Version:    0.7.0.1
 */