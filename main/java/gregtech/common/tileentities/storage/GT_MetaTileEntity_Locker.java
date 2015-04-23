/*   1:    */ package gregtech.common.tileentities.storage;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
/*   4:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   5:    */ import gregtech.api.interfaces.ITexture;
/*   6:    */ import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
/*   7:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   8:    */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
/*   9:    */ import gregtech.api.objects.GT_ItemStack;
/*  10:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  11:    */ import gregtech.api.util.GT_Utility;

/*  12:    */ import java.util.Map;

/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.inventory.Container;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.nbt.NBTTagCompound;
/*  17:    */ 
/*  18:    */ public class GT_MetaTileEntity_Locker
/*  19:    */   extends GT_MetaTileEntity_TieredMachineBlock
/*  20:    */ {
/*  21: 24 */   public byte mType = 0;
/*  22:    */   
/*  23:    */   public GT_MetaTileEntity_Locker(int aID, String aName, String aNameRegional, int aTier)
/*  24:    */   {
/*  25: 27 */     super(aID, aName, aNameRegional, aTier, 4, "Stores and recharges Armor", new ITexture[0]);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public GT_MetaTileEntity_Locker(String aName, int aTier, String aDescription, ITexture[][][] aTextures)
/*  29:    */   {
/*  30: 31 */     super(aName, aTier, 4, aDescription, aTextures);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String[] getDescription()
/*  34:    */   {
/*  35: 36 */     return new String[] { this.mDescription, "Click with Screwdriver to change Style" };
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ITexture[][][] getTextureSet(ITexture[] aTextures)
/*  39:    */   {
/*  40: 41 */     ITexture[][][] rTextures = new ITexture[3][17][];
/*  41: 42 */     for (byte i = -1; i < 16; i = (byte)(i + 1))
/*  42:    */     {ITexture[] tmp0 ={ Textures.BlockIcons.MACHINE_CASINGS[this.mTier][(i + 1)] };
/*  43: 43 */       rTextures[0][(i + 1)] = tmp0;
/*  44: 44 */       ITexture[] tmp1 ={ Textures.BlockIcons.MACHINE_CASINGS[this.mTier][(i + 1)], Textures.BlockIcons.OVERLAYS_ENERGY_IN[this.mTier] };
rTextures[1][(i + 1)] = tmp1;
/*  45: 45 */       ITexture[] tmp2 = { Textures.BlockIcons.MACHINE_CASINGS[this.mTier][(i + 1)], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_LOCKER) };
rTextures[2][(i + 1)] =tmp2;
/*  46:    */     }
/*  47: 47 */     return rTextures;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
/*  51:    */   {
/*  52: 52 */     if (aSide == aFacing) {
/*  53: 52 */       return new ITexture[] { this.mTextures[2][(aColorIndex + 1)][0], this.mTextures[2][(aColorIndex + 1)][1], Textures.BlockIcons.LOCKERS[java.lang.Math.abs(this.mType % Textures.BlockIcons.LOCKERS.length)] };
/*  54:    */     }
/*  55: 53 */     return this.mTextures[0][(aColorIndex + 1)];
/*  56:    */   }
/*  57:    */   
/*  58:    */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  59:    */   {
/*  60: 58 */     return new GT_MetaTileEntity_Locker(this.mName, this.mTier, this.mDescription, this.mTextures);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean isSimpleMachine()
/*  64:    */   {
/*  65: 61 */     return false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean isElectric()
/*  69:    */   {
/*  70: 62 */     return true;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean isValidSlot(int aIndex)
/*  74:    */   {
/*  75: 63 */     return true;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isFacingValid(byte aFacing)
/*  79:    */   {
/*  80: 64 */     return aFacing > 1;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean isEnetInput()
/*  84:    */   {
/*  85: 65 */     return true;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isInputFacing(byte aSide)
/*  89:    */   {
/*  90: 66 */     return aSide == getBaseMetaTileEntity().getBackFacing();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean isTeleporterCompatible()
/*  94:    */   {
/*  95: 67 */     return false;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public long maxEUStore()
/*  99:    */   {
/* 100: 68 */     return gregtech.api.enums.GT_Values.V[this.mTier] * maxAmperesIn();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public long maxEUInput()
/* 104:    */   {
/* 105: 69 */     return gregtech.api.enums.GT_Values.V[this.mTier];
/* 106:    */   }
/* 107:    */   
/* 108:    */   public long maxAmperesIn()
/* 109:    */   {
/* 110: 70 */     return this.mInventory.length * 2;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int rechargerSlotStartIndex()
/* 114:    */   {
/* 115: 71 */     return 0;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int rechargerSlotCount()
/* 119:    */   {
/* 120: 72 */     return getBaseMetaTileEntity().isAllowedToWork() ? this.mInventory.length : 0;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isAccessAllowed(EntityPlayer aPlayer)
/* 124:    */   {
/* 125: 73 */     return true;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void saveNBTData(NBTTagCompound aNBT)
/* 129:    */   {
/* 130: 77 */     aNBT.setByte("mType", this.mType);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void loadNBTData(NBTTagCompound aNBT)
/* 134:    */   {
/* 135: 82 */     this.mType = aNBT.getByte("mType");
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void onValueUpdate(byte aValue)
/* 139:    */   {
/* 140: 87 */     this.mType = aValue;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public byte getUpdateData()
/* 144:    */   {
/* 145: 92 */     return this.mType;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void doSound(byte aIndex, double aX, double aY, double aZ)
/* 149:    */   {
/* 150: 97 */     if (aIndex == 16) {
/* 151: 97 */       GT_Utility.doSoundAtClient((String)GregTech_API.sSoundList.get(Integer.valueOf(3)), 1, 1.0F);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 156:    */   {
/* 157:102 */     if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
/* 158:102 */       this.mType = ((byte)(this.mType + 1));
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean allowCoverOnSide(byte aSide, GT_ItemStack aStack)
/* 163:    */   {
/* 164:107 */     return aSide != getBaseMetaTileEntity().getFrontFacing();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ)
/* 168:    */   {
/* 169:112 */     if ((aBaseMetaTileEntity.isServerSide()) && (aSide == aBaseMetaTileEntity.getFrontFacing()))
/* 170:    */     {
/* 171:113 */       for (int i = 0; i < 4; i++)
/* 172:    */       {
/* 173:114 */         ItemStack tSwapStack = this.mInventory[i];
/* 174:115 */         this.mInventory[i] = aPlayer.inventory.armorInventory[i];
/* 175:116 */         aPlayer.inventory.armorInventory[i] = tSwapStack;
/* 176:    */       }
/* 177:118 */       aPlayer.inventoryContainer.detectAndSendChanges();
/* 178:119 */       sendSound((byte)16);
/* 179:    */     }
/* 180:121 */     return true;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 184:    */   {
/* 185:126 */     return false;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 189:    */   {
/* 190:131 */     return false;
/* 191:    */   }
/* 192:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.storage.GT_MetaTileEntity_Locker
 * JD-Core Version:    0.7.0.1
 */