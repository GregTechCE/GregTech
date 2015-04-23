/*   1:    */ package gregtech.common.tileentities.boilers;
/*   2:    */ 
/*   3:    */ import gregtech.api.enums.Dyes;
/*   4:    */ import gregtech.api.enums.Materials;
/*   5:    */ import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
/*   6:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   7:    */ import gregtech.api.interfaces.ITexture;
/*   8:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   9:    */ import gregtech.api.metatileentity.MetaTileEntity;
/*  10:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  11:    */ import gregtech.api.util.GT_ModHandler;
/*  12:    */ import gregtech.api.util.GT_OreDictUnificator;
/*  13:    */ import gregtech.common.gui.GT_Container_Boiler;
/*  14:    */ import gregtech.common.gui.GT_GUIContainer_Boiler;
/*  15:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  16:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  17:    */ import net.minecraftforge.fluids.FluidStack;
/*  18:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  19:    */ 
/*  20:    */ public class GT_MetaTileEntity_Boiler_Steel
/*  21:    */   extends GT_MetaTileEntity_Boiler
/*  22:    */ {
/*  23:    */   public GT_MetaTileEntity_Boiler_Steel(int aID, String aName, String aNameRegional)
/*  24:    */   {
/*  25: 22 */     super(aID, aName, aNameRegional, "Faster than the Bronze Boiler", new ITexture[0]);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public GT_MetaTileEntity_Boiler_Steel(String aName, int aTier, String aDescription, ITexture[][][] aTextures)
/*  29:    */   {
/*  30: 26 */     super(aName, aTier, aDescription, aTextures);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ITexture[][][] getTextureSet(ITexture[] aTextures)
/*  34:    */   {
/*  35: 31 */     ITexture[][][] rTextures = new ITexture[5][17][];
/*  36: 32 */     for (byte i = -1; i < 16; i = (byte)(i + 1))
/*  37:    */     {ITexture[] tmp0 ={ new GT_RenderedTexture(Textures.BlockIcons.MACHINE_STEELBRICKS_BOTTOM, Dyes.getModulation(i, Dyes._NULL.mRGBa)) };
/*  38: 33 */       rTextures[0][(i + 1)] = tmp0;
/*  39: 34 */       ITexture[] tmp1 ={ new GT_RenderedTexture(Textures.BlockIcons.MACHINE_STEELBRICKS_TOP, Dyes.getModulation(i, Dyes._NULL.mRGBa)), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE) };
rTextures[1][(i + 1)] = tmp1;
/*  40: 35 */       ITexture[] tmp2 ={ new GT_RenderedTexture(Textures.BlockIcons.MACHINE_STEELBRICKS_SIDE, Dyes.getModulation(i, Dyes._NULL.mRGBa)), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE) };
rTextures[2][(i + 1)] = tmp2;
/*  41: 36 */       ITexture[] tmp4 ={ new GT_RenderedTexture(Textures.BlockIcons.MACHINE_STEELBRICKS_SIDE, Dyes.getModulation(i, Dyes._NULL.mRGBa)), new GT_RenderedTexture(Textures.BlockIcons.BOILER_FRONT) };
rTextures[3][(i + 1)] = tmp4;
/*  42: 37 */      ITexture[] tmp5 ={ new GT_RenderedTexture(Textures.BlockIcons.MACHINE_STEELBRICKS_SIDE, Dyes.getModulation(i, Dyes._NULL.mRGBa)), new GT_RenderedTexture(Textures.BlockIcons.BOILER_FRONT_ACTIVE) };
rTextures[4][(i + 1)] = tmp5;
/*  43:    */     }
/*  44: 39 */     return rTextures;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int maxProgresstime()
/*  48:    */   {
/*  49: 42 */     return 1000;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  53:    */   {
/*  54: 46 */     return new GT_Container_Boiler(aPlayerInventory, aBaseMetaTileEntity, 32000);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  58:    */   {
/*  59: 51 */     return new GT_GUIContainer_Boiler(aPlayerInventory, aBaseMetaTileEntity, "SteelBoiler.png", 32000);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  63:    */   {
/*  64: 56 */     return new GT_MetaTileEntity_Boiler_Steel(this.mName, this.mTier, this.mDescription, this.mTextures);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick)
/*  68:    */   {
/*  69: 61 */     if ((aBaseMetaTileEntity.isServerSide()) && (aTick > 20L))
/*  70:    */     {
/*  71: 62 */       if (this.mTemperature <= 20)
/*  72:    */       {
/*  73: 63 */         this.mTemperature = 20;
/*  74: 64 */         this.mLossTimer = 0;
/*  75:    */       }
/*  76: 67 */       if (++this.mLossTimer > 40)
/*  77:    */       {
/*  78: 68 */         this.mTemperature -= 1;
/*  79: 69 */         this.mLossTimer = 0;
/*  80:    */       }
/*  81: 72 */       for (byte i = 1; (this.mSteam != null) && (i < 6); i = (byte)(i + 1)) {
/*  82: 72 */         if (i != aBaseMetaTileEntity.getFrontFacing())
/*  83:    */         {
/*  84: 73 */           IFluidHandler tTileEntity = aBaseMetaTileEntity.getITankContainerAtSide(i);
/*  85: 74 */           if (tTileEntity != null)
/*  86:    */           {
/*  87: 75 */             FluidStack tDrained = aBaseMetaTileEntity.drain(ForgeDirection.getOrientation(i), Math.max(1, this.mSteam.amount / 2), false);
/*  88: 76 */             if (tDrained != null)
/*  89:    */             {
/*  90: 77 */               int tFilledAmount = tTileEntity.fill(ForgeDirection.getOrientation(i).getOpposite(), tDrained, false);
/*  91: 78 */               if (tFilledAmount > 0) {
/*  92: 79 */                 tTileEntity.fill(ForgeDirection.getOrientation(i).getOpposite(), aBaseMetaTileEntity.drain(ForgeDirection.getOrientation(i), tFilledAmount, true), true);
/*  93:    */               }
/*  94:    */             }
/*  95:    */           }
/*  96:    */         }
/*  97:    */       }
/*  98: 85 */       if (aTick % 10L == 0L) {
/*  99: 86 */         if (this.mTemperature > 100)
/* 100:    */         {
/* 101: 87 */           if ((this.mFluid == null) || (!GT_ModHandler.isWater(this.mFluid)) || (this.mFluid.amount <= 0))
/* 102:    */           {
/* 103: 88 */             this.mHadNoWater = true;
/* 104:    */           }
/* 105:    */           else
/* 106:    */           {
/* 107: 90 */             if (this.mHadNoWater)
/* 108:    */             {
/* 109: 91 */               aBaseMetaTileEntity.doExplosion(2048L);
/* 110: 92 */               return;
/* 111:    */             }
/* 112: 94 */             this.mFluid.amount -= 1;
/* 113: 95 */             if (this.mSteam == null) {
/* 114: 96 */               this.mSteam = GT_ModHandler.getSteam(150L);
/* 115: 98 */             } else if (GT_ModHandler.isSteam(this.mSteam)) {
/* 116: 99 */               this.mSteam.amount += 150;
/* 117:    */             } else {
/* 118:101 */               this.mSteam = GT_ModHandler.getSteam(150L);
/* 119:    */             }
/* 120:    */           }
/* 121:    */         }
/* 122:    */         else {
/* 123:106 */           this.mHadNoWater = false;
/* 124:    */         }
/* 125:    */       }
/* 126:110 */       if ((this.mSteam != null) && 
/* 127:111 */         (this.mSteam.amount > 32000))
/* 128:    */       {
/* 129:112 */         sendSound((byte)1);
/* 130:113 */         this.mSteam.amount = 24000;
/* 131:    */       }
/* 132:117 */       if ((this.mProcessingEnergy <= 0) && (aBaseMetaTileEntity.isAllowedToWork()) && 
/* 133:118 */         (this.mInventory[2] != null)) {
/* 134:119 */         if ((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.gem.get(Materials.Coal))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.dust.get(Materials.Coal))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.dustImpure.get(Materials.Coal))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.crushed.get(Materials.Coal))))
/* 135:    */         {
/* 136:123 */           this.mProcessingEnergy += 160;
/* 137:124 */           aBaseMetaTileEntity.decrStackSize(2, 1);
/* 138:125 */           if (aBaseMetaTileEntity.getRandomNumber(3) == 0) {
/* 139:126 */             aBaseMetaTileEntity.addStackToSlot(3, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 1L));
/* 140:    */           }
/* 141:    */         }
/* 142:129 */         else if ((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.gem.get(Materials.Charcoal))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.dust.get(Materials.Charcoal))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.dustImpure.get(Materials.Charcoal))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.crushed.get(Materials.Charcoal))))
/* 143:    */         {
/* 144:133 */           this.mProcessingEnergy += 160;
/* 145:134 */           aBaseMetaTileEntity.decrStackSize(2, 1);
/* 146:135 */           if (aBaseMetaTileEntity.getRandomNumber(3) == 0) {
/* 147:136 */             aBaseMetaTileEntity.addStackToSlot(3, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
/* 148:    */           }
/* 149:    */         }
/* 150:138 */         else if (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], "fuelCoke"))
/* 151:    */         {
/* 152:139 */           this.mProcessingEnergy += 640;
/* 153:140 */           aBaseMetaTileEntity.decrStackSize(2, 1);
/* 154:141 */           if (aBaseMetaTileEntity.getRandomNumber(2) == 0) {
/* 155:142 */             aBaseMetaTileEntity.addStackToSlot(3, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
/* 156:    */           }
/* 157:    */         }
/* 158:144 */         else if ((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.gem.get(Materials.Lignite))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.dust.get(Materials.Lignite))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.dustImpure.get(Materials.Lignite))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.crushed.get(Materials.Lignite))))
/* 159:    */         {
/* 160:148 */           this.mProcessingEnergy += 40;
/* 161:149 */           aBaseMetaTileEntity.decrStackSize(2, 1);
/* 162:150 */           if (aBaseMetaTileEntity.getRandomNumber(8) == 0) {
/* 163:151 */             aBaseMetaTileEntity.addStackToSlot(3, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 1L));
/* 164:    */           }
/* 165:    */         }
/* 166:    */       }
/* 167:157 */       if ((this.mTemperature < 1000) && (this.mProcessingEnergy > 0) && (aTick % 12L == 0L))
/* 168:    */       {
/* 169:158 */         this.mProcessingEnergy -= 2;
/* 170:159 */         this.mTemperature += 1;
/* 171:    */       }
/* 172:162 */       aBaseMetaTileEntity.setActive(this.mProcessingEnergy > 0);
/* 173:    */     }
/* 174:    */   }
/* 175:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.boilers.GT_MetaTileEntity_Boiler_Steel
 * JD-Core Version:    0.7.0.1
 */