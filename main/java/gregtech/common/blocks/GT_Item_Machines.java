/*   1:    */ package gregtech.common.blocks;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.GT_Values;
/*   5:    */ import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
/*   6:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   7:    */ import gregtech.api.util.GT_ItsNotMyFaultException;
/*   8:    */ import gregtech.api.util.GT_LanguageManager;
/*   9:    */ import gregtech.api.util.GT_Log;
/*  10:    */ import gregtech.api.util.GT_Utility;
/*  11:    */ import java.util.List;
/*  12:    */ import net.minecraft.block.Block;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.item.ItemBlock;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.nbt.NBTTagCompound;
/*  17:    */ import net.minecraft.tileentity.TileEntity;
/*  18:    */ import net.minecraft.util.EnumChatFormatting;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public class GT_Item_Machines
/*  22:    */   extends ItemBlock
/*  23:    */ {
/*  24:    */   public GT_Item_Machines(Block par1)
/*  25:    */   {
/*  26: 25 */     super(par1);
/*  27: 26 */     setMaxDamage(0);
/*  28: 27 */     setHasSubtypes(true);
/*  29: 28 */     setCreativeTab(GregTech_API.TAB_GREGTECH);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean par4)
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36: 34 */       int tDamage = getDamage(aStack);
/*  37: 36 */       if ((tDamage <= 0) || (tDamage >= GregTech_API.METATILEENTITIES.length)) {
/*  38: 36 */         return;
/*  39:    */       }
/*  40: 38 */       if (tDamage == 0)
/*  41:    */       {
/*  42: 39 */         aList.add("WARNING, THE EXISTENCE OF THIS ITEM IS A BUG");
/*  43: 40 */         aList.add("IF YOU GOT IT IN SURVIVAL THEN PLEASE REPORT IT");
/*  44:    */       }
/*  45:    */       else
/*  46:    */       {
/*  47: 42 */         TileEntity temp = GregTech_API.sBlockMachines.createTileEntity(aPlayer == null ? GT_Values.DW : aPlayer.worldObj, GregTech_API.METATILEENTITIES[tDamage] == null ? 0 : GregTech_API.METATILEENTITIES[tDamage].getTileEntityBaseType());
/*  48: 43 */         if (temp != null)
/*  49:    */         {
/*  50: 44 */           temp.setWorldObj(aPlayer == null ? GT_Values.DW : aPlayer.worldObj);temp.xCoord = 0;temp.yCoord = 0;temp.zCoord = 0;
/*  51: 45 */           if ((temp instanceof IGregTechTileEntity))
/*  52:    */           {
/*  53: 46 */             IGregTechTileEntity tTileEntity = (IGregTechTileEntity)temp;
/*  54: 47 */             tTileEntity.setInitialValuesAsNBT(new NBTTagCompound(), (short)tDamage);
/*  55: 48 */             if (tTileEntity.getDescription() != null)
/*  56:    */             {
/*  57: 48 */               int i = 0;
/*  58: 48 */               for (String tDescription : tTileEntity.getDescription()) {
/*  59: 48 */                 if (GT_Utility.isStringValid(tDescription)) {
/*  60: 48 */                   aList.add(GT_LanguageManager.addStringLocalization("TileEntity_DESCRIPTION_" + tDamage + "_Index_" + i++, tDescription, !GregTech_API.sPostloadFinished));
/*  61:    */                 }
/*  62:    */               }
/*  63:    */             }
/*  64: 49 */             if (tTileEntity.getEUCapacity() > 0L)
/*  65:    */             {
/*  66: 50 */               if (tTileEntity.getInputVoltage() > 0L) {
/*  67: 50 */                 aList.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_IN", "Voltage IN: ", !GregTech_API.sPostloadFinished) + EnumChatFormatting.GREEN + tTileEntity.getInputVoltage() + " (" + GT_Values.VN[GT_Utility.getTier(tTileEntity.getInputVoltage())] + ")" + EnumChatFormatting.GRAY);
/*  68:    */               }
/*  69: 51 */               if (tTileEntity.getOutputVoltage() > 0L) {
/*  70: 51 */                 aList.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_OUT", "Voltage OUT: ", !GregTech_API.sPostloadFinished) + EnumChatFormatting.GREEN + tTileEntity.getOutputVoltage() + " (" + GT_Values.VN[GT_Utility.getTier(tTileEntity.getOutputVoltage())] + ")" + EnumChatFormatting.GRAY);
/*  71:    */               }
/*  72: 52 */               if (tTileEntity.getOutputAmperage() > 1L) {
/*  73: 52 */                 aList.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_AMOUNT", "Amperage: ", !GregTech_API.sPostloadFinished) + EnumChatFormatting.YELLOW + tTileEntity.getOutputAmperage() + EnumChatFormatting.GRAY);
/*  74:    */               }
/*  75: 53 */               aList.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_STORE", "Capacity: ", !GregTech_API.sPostloadFinished) + EnumChatFormatting.BLUE + tTileEntity.getEUCapacity() + EnumChatFormatting.GRAY);
/*  76:    */             }
/*  77:    */           }
/*  78:    */         }
/*  79:    */       }
/*  80: 59 */       NBTTagCompound aNBT = aStack.getTagCompound();
/*  81: 61 */       if (aNBT != null)
/*  82:    */       {
/*  83: 62 */         if (aNBT.getBoolean("mMuffler")) {
/*  84: 62 */           aList.add(GT_LanguageManager.addStringLocalization("GT_TileEntity_MUFFLER", "has Muffler Upgrade", !GregTech_API.sPostloadFinished));
/*  85:    */         }
/*  86: 63 */         if (aNBT.getBoolean("mSteamConverter")) {
/*  87: 63 */           aList.add(GT_LanguageManager.addStringLocalization("GT_TileEntity_STEAMCONVERTER", "has Steam Upgrade", !GregTech_API.sPostloadFinished));
/*  88:    */         }
/*  89: 64 */         int tAmount = 0;
/*  90: 65 */         if ((tAmount = aNBT.getByte("mSteamTanks")) > 0) {
/*  91: 65 */           aList.add(tAmount + " " + GT_LanguageManager.addStringLocalization("GT_TileEntity_STEAMTANKS", "Steam Tank Upgrades", !GregTech_API.sPostloadFinished));
/*  92:    */         }
/*  93:    */       }
/*  94:    */     }
/*  95:    */     catch (Throwable e)
/*  96:    */     {
/*  97: 68 */       e.printStackTrace(GT_Log.err);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/* 102:    */   {
/* 103: 74 */     return false;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getUnlocalizedName(ItemStack aStack)
/* 107:    */   {
/* 108: 79 */     short tDamage = (short)getDamage(aStack);
/* 109: 80 */     if ((tDamage < 0) || (tDamage >= GregTech_API.METATILEENTITIES.length)) {
/* 110: 80 */       return "";
/* 111:    */     }
/* 112: 81 */     if (GregTech_API.METATILEENTITIES[tDamage] != null) {
/* 113: 82 */       return getUnlocalizedName() + "." + GregTech_API.METATILEENTITIES[tDamage].getMetaName();
/* 114:    */     }
/* 115: 84 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void onCreated(ItemStack aStack, World aWorld, EntityPlayer aPlayer)
/* 119:    */   {
/* 120: 89 */     super.onCreated(aStack, aWorld, aPlayer);
/* 121: 90 */     short tDamage = (short)getDamage(aStack);
/* 122: 91 */     if ((tDamage < 0) || ((tDamage >= GregTech_API.METATILEENTITIES.length) && (GregTech_API.METATILEENTITIES[tDamage] != null))) {
/* 123: 91 */       GregTech_API.METATILEENTITIES[tDamage].onCreated(aStack, aWorld, aPlayer);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean placeBlockAt(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int side, float hitX, float hitY, float hitZ, int aMeta)
/* 128:    */   {
/* 129: 96 */     short tDamage = (short)getDamage(aStack);
/* 130: 97 */     if (tDamage > 0)
/* 131:    */     {
/* 132: 98 */       if (GregTech_API.METATILEENTITIES[tDamage] == null) {
/* 133: 98 */         return false;
/* 134:    */       }
/* 135: 99 */       int tMetaData = GregTech_API.METATILEENTITIES[tDamage].getTileEntityBaseType();
/* 136:100 */       if (!aWorld.setBlock(aX, aY, aZ, this.field_150939_a, tMetaData, 3)) {
/* 137:100 */         return false;
/* 138:    */       }
/* 139:101 */       if (aWorld.getBlock(aX, aY, aZ) != this.field_150939_a) {
/* 140:101 */         throw new GT_ItsNotMyFaultException("Failed to place Block even though World.setBlock returned true. It COULD be MCPC/Bukkit causing that. In case you really have that installed, don't report this Bug to me, I don't know how to fix it.");
/* 141:    */       }
/* 142:102 */       if (aWorld.getBlockMetadata(aX, aY, aZ) != tMetaData) {
/* 143:102 */         throw new GT_ItsNotMyFaultException("Failed to set the MetaValue of the Block even though World.setBlock returned true. It COULD be MCPC/Bukkit causing that. In case you really have that installed, don't report this Bug to me, I don't know how to fix it.");
/* 144:    */       }
/* 145:103 */       IGregTechTileEntity tTileEntity = (IGregTechTileEntity)aWorld.getTileEntity(aX, aY, aZ);
/* 146:104 */       if (tTileEntity != null)
/* 147:    */       {
/* 148:105 */         tTileEntity.setInitialValuesAsNBT(tTileEntity.isServerSide() ? aStack.getTagCompound() : null, tDamage);
/* 149:106 */         if (aPlayer != null) {
/* 150:106 */           tTileEntity.setOwnerName(aPlayer.getDisplayName());
/* 151:    */         }
/* 152:107 */         tTileEntity.getMetaTileEntity().initDefaultModes(aStack.getTagCompound());
/* 153:    */       }
/* 154:    */     }
/* 155:110 */     else if (!aWorld.setBlock(aX, aY, aZ, this.field_150939_a, tDamage, 3))
/* 156:    */     {
/* 157:110 */       return false;
/* 158:    */     }
/* 159:113 */     if (aWorld.getBlock(aX, aY, aZ) == this.field_150939_a)
/* 160:    */     {
/* 161:114 */       this.field_150939_a.onBlockPlacedBy(aWorld, aX, aY, aZ, aPlayer, aStack);
/* 162:115 */       this.field_150939_a.onPostBlockPlaced(aWorld, aX, aY, aZ, tDamage);
/* 163:    */     }
/* 164:117 */     return true;
/* 165:    */   }
/* 166:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Item_Machines
 * JD-Core Version:    0.7.0.1
 */