/*   1:    */ package gregtech.common.tileentities.boilers;
/*   2:    */ 
/*   3:    */ import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
/*   4:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   5:    */ import gregtech.api.interfaces.ITexture;
/*   6:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   7:    */ import gregtech.api.metatileentity.MetaTileEntity;
/*   8:    */ import gregtech.api.objects.GT_RenderedTexture;
/*   9:    */ import gregtech.api.util.GT_ModHandler;
/*  10:    */ import gregtech.common.gui.GT_Container_Boiler;
/*  11:    */ import gregtech.common.gui.GT_GUIContainer_Boiler;
/*  12:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  15:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  16:    */ import net.minecraftforge.fluids.FluidStack;
/*  17:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  18:    */ 
/*  19:    */ public class GT_MetaTileEntity_Boiler_Solar
/*  20:    */   extends GT_MetaTileEntity_Boiler
/*  21:    */ {
/*  22:    */   public GT_MetaTileEntity_Boiler_Solar(int aID, String aName, String aNameRegional)
/*  23:    */   {
/*  24: 19 */     super(aID, aName, aNameRegional, "Steam Power by the Sun", new ITexture[0]);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public GT_MetaTileEntity_Boiler_Solar(String aName, int aTier, String aDescription, ITexture[][][] aTextures)
/*  28:    */   {
/*  29: 23 */     super(aName, aTier, aDescription, aTextures);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public ITexture[][][] getTextureSet(ITexture[] aTextures)
/*  33:    */   {
/*  34: 28 */     ITexture[][][] rTextures = new ITexture[4][17][];
/*  35: 29 */     for (byte i = -1; i < 16; i = (byte)(i + 1))
/*  36:    */     {ITexture[] tmp0 ={ new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZEBRICKS_BOTTOM, Dyes.getModulation(i, Dyes._NULL.mRGBa)) };
/*  37: 30 */       rTextures[0][(i + 1)] = tmp0;
/*  38: 31 */       ITexture[] tmp1 ={ new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZEBRICKS_TOP, Dyes.getModulation(i, Dyes._NULL.mRGBa)), new GT_RenderedTexture(Textures.BlockIcons.BOILER_SOLAR) };
rTextures[1][(i + 1)] = tmp1;
/*  39: 32 */       ITexture[] tmp2 ={ new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZEBRICKS_SIDE, Dyes.getModulation(i, Dyes._NULL.mRGBa)) };
rTextures[2][(i + 1)] = tmp2;
/*  40: 33 */       ITexture[] tmp3 ={ new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZEBRICKS_SIDE, Dyes.getModulation(i, Dyes._NULL.mRGBa)), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE) };
rTextures[3][(i + 1)] = tmp3;
/*  41:    */     }
/*  42: 35 */     return rTextures;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
/*  46:    */   {
/*  47: 40 */     return this.mTextures[2][(aColorIndex + 1)];
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int maxProgresstime()
/*  51:    */   {
/*  52: 43 */     return 500;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  56:    */   {
/*  57: 47 */     return new GT_Container_Boiler(aPlayerInventory, aBaseMetaTileEntity, 16000);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  61:    */   {
/*  62: 52 */     return new GT_GUIContainer_Boiler(aPlayerInventory, aBaseMetaTileEntity, "SolarBoiler.png", 16000);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  66:    */   {
/*  67: 57 */     return new GT_MetaTileEntity_Boiler_Solar(this.mName, this.mTier, this.mDescription, this.mTextures);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick)
/*  71:    */   {
/*  72: 62 */     if ((aBaseMetaTileEntity.isServerSide()) && (aTick > 20L))
/*  73:    */     {
/*  74: 63 */       if (this.mTemperature <= 20)
/*  75:    */       {
/*  76: 64 */         this.mTemperature = 20;
/*  77: 65 */         this.mLossTimer = 0;
/*  78:    */       }
/*  79: 68 */       if (++this.mLossTimer > 45)
/*  80:    */       {
/*  81: 69 */         this.mTemperature -= 1;
/*  82: 70 */         this.mLossTimer = 0;
/*  83:    */       }
/*  84: 73 */       if (this.mSteam != null)
/*  85:    */       {
/*  86: 74 */         byte i = aBaseMetaTileEntity.getFrontFacing();
/*  87: 75 */         IFluidHandler tTileEntity = aBaseMetaTileEntity.getITankContainerAtSide(i);
/*  88: 76 */         if (tTileEntity != null)
/*  89:    */         {
/*  90: 77 */           FluidStack tDrained = aBaseMetaTileEntity.drain(ForgeDirection.getOrientation(i), Math.max(1, this.mSteam.amount / 2), false);
/*  91: 78 */           if (tDrained != null)
/*  92:    */           {
/*  93: 79 */             int tFilledAmount = tTileEntity.fill(ForgeDirection.getOrientation(i).getOpposite(), tDrained, false);
/*  94: 80 */             if (tFilledAmount > 0) {
/*  95: 81 */               tTileEntity.fill(ForgeDirection.getOrientation(i).getOpposite(), aBaseMetaTileEntity.drain(ForgeDirection.getOrientation(i), tFilledAmount, true), true);
/*  96:    */             }
/*  97:    */           }
/*  98:    */         }
/*  99:    */       }
/* 100: 87 */       if (aTick % 25L == 0L) {
/* 101: 88 */         if (this.mTemperature > 100)
/* 102:    */         {
/* 103: 89 */           if ((this.mFluid == null) || (!GT_ModHandler.isWater(this.mFluid)) || (this.mFluid.amount <= 0))
/* 104:    */           {
/* 105: 90 */             this.mHadNoWater = true;
/* 106:    */           }
/* 107:    */           else
/* 108:    */           {
/* 109: 92 */             if (this.mHadNoWater)
/* 110:    */             {
/* 111: 93 */               aBaseMetaTileEntity.doExplosion(2048L);
/* 112: 94 */               return;
/* 113:    */             }
/* 114: 96 */             this.mFluid.amount -= 1;
/* 115: 97 */             if (this.mSteam == null) {
/* 116: 98 */               this.mSteam = GT_ModHandler.getSteam(150L);
/* 117:100 */             } else if (GT_ModHandler.isSteam(this.mSteam)) {
/* 118:101 */               this.mSteam.amount += 150;
/* 119:    */             } else {
/* 120:103 */               this.mSteam = GT_ModHandler.getSteam(150L);
/* 121:    */             }
/* 122:    */           }
/* 123:    */         }
/* 124:    */         else {
/* 125:108 */           this.mHadNoWater = false;
/* 126:    */         }
/* 127:    */       }
/* 128:112 */       if ((this.mSteam != null) && 
/* 129:113 */         (this.mSteam.amount > 16000))
/* 130:    */       {
/* 131:114 */         sendSound((byte)1);
/* 132:115 */         this.mSteam.amount = 12000;
/* 133:    */       }
/* 134:119 */       if ((this.mProcessingEnergy <= 0) && (aBaseMetaTileEntity.isAllowedToWork()) && (aTick % 256L == 0L) && (!aBaseMetaTileEntity.getWorld().isThundering()))
/* 135:    */       {
/* 136:120 */         boolean bRain = (aBaseMetaTileEntity.getWorld().isRaining()) && (aBaseMetaTileEntity.getBiome().rainfall > 0.0F);
/* 137:121 */         this.mProcessingEnergy += (((!bRain) || (aBaseMetaTileEntity.getWorld().skylightSubtracted < 4)) && (aBaseMetaTileEntity.getSkyAtSide((byte)1)) ? 8 : (bRain) || (!aBaseMetaTileEntity.getWorld().isDaytime()) ? 1 : 0);
/* 138:    */       }
/* 139:124 */       if ((this.mTemperature < 500) && (this.mProcessingEnergy > 0) && (aTick % 12L == 0L))
/* 140:    */       {
/* 141:125 */         this.mProcessingEnergy -= 1;
/* 142:126 */         this.mTemperature += 1;
/* 143:    */       }
/* 144:129 */       aBaseMetaTileEntity.setActive(this.mProcessingEnergy > 0);
/* 145:    */     }
/* 146:    */   }
/* 147:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.boilers.GT_MetaTileEntity_Boiler_Solar
 * JD-Core Version:    0.7.0.1
 */