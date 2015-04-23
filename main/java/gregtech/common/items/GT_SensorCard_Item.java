/*  1:   */ package gregtech.common.items;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import gregtech.api.interfaces.tileentity.IGregTechDeviceInformation;
/*  6:   */ import gregtech.api.items.GT_Generic_Item;
/*  7:   */ import gregtech.api.util.GT_LanguageManager;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.LinkedList;
/* 10:   */ import java.util.List;
/* 11:   */ import java.util.UUID;
/* 12:   */ import net.minecraft.creativetab.CreativeTabs;
/* 13:   */ import net.minecraft.item.Item;
/* 14:   */ import net.minecraft.item.ItemStack;
/* 15:   */ import net.minecraft.nbt.NBTTagCompound;
/* 16:   */ import net.minecraft.tileentity.TileEntity;
/* 17:   */ import net.minecraft.util.ChunkCoordinates;
/* 18:   */ import net.minecraft.world.World;
/* 19:   */ import shedar.mods.ic2.nuclearcontrol.api.CardState;
/* 20:   */ import shedar.mods.ic2.nuclearcontrol.api.ICardWrapper;
/* 21:   */ import shedar.mods.ic2.nuclearcontrol.api.IPanelDataSource;
/* 22:   */ import shedar.mods.ic2.nuclearcontrol.api.IRemoteSensor;
/* 23:   */ import shedar.mods.ic2.nuclearcontrol.api.PanelSetting;
/* 24:   */ import shedar.mods.ic2.nuclearcontrol.api.PanelString;
/* 25:   */ 
/* 26:   */ public class GT_SensorCard_Item
/* 27:   */   extends GT_Generic_Item
/* 28:   */   implements IRemoteSensor, IPanelDataSource
/* 29:   */ {
/* 30:   */   public GT_SensorCard_Item(String aUnlocalized, String aEnglish)
/* 31:   */   {
/* 32:26 */     super(aUnlocalized, aEnglish, "Insert into Display Panel");
/* 33:27 */     setMaxStackSize(1);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void addAdditionalToolTips(List aList, ItemStack aStack)
/* 37:   */   {
/* 38:32 */     super.addAdditionalToolTips(aList, aStack);
/* 39:33 */     if (aStack != null)
/* 40:   */     {
/* 41:34 */       NBTTagCompound tNBT = aStack.getTagCompound();
/* 42:35 */       if (tNBT == null)
/* 43:   */       {
/* 44:36 */         aList.add("Missing Coodinates!");
/* 45:   */       }
/* 46:   */       else
/* 47:   */       {
/* 48:38 */         aList.add("Device at:");
/* 49:39 */         aList.add(String.format("x: %d, y: %d, z: %d", new Object[] { Integer.valueOf(tNBT.getInteger("x")), Integer.valueOf(tNBT.getInteger("y")), Integer.valueOf(tNBT.getInteger("z")) }));
/* 50:   */       }
/* 51:   */     }
/* 52:   */   }
/* 53:   */   
/* 54:   */   public CardState update(TileEntity aPanel, ICardWrapper aCard, int aMaxRange)
/* 55:   */   {
/* 56:46 */     ChunkCoordinates target = aCard.getTarget();
/* 57:47 */     TileEntity tTileEntity = aPanel.getWorldObj().getTileEntity(target.posX, target.posY, target.posZ);
/* 58:48 */     if ((tTileEntity != null) && ((tTileEntity instanceof IGregTechDeviceInformation)) && (((IGregTechDeviceInformation)tTileEntity).isGivingInformation()))
/* 59:   */     {
/* 60:49 */       String[] tInfoData = ((IGregTechDeviceInformation)tTileEntity).getInfoData();
/* 61:50 */       for (int i = 0; i < tInfoData.length; i++) {
/* 62:50 */         aCard.setString("mString" + i, tInfoData[i]);
/* 63:   */       }
/* 64:51 */       return CardState.OK;
/* 65:   */     }
/* 66:53 */     return CardState.NO_TARGET;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public List<PanelString> getStringData(int aSettings, ICardWrapper aCard, boolean aLabels)
/* 70:   */   {
/* 71:58 */     List<PanelString> rList = new LinkedList();
/* 72:59 */     for (int i = 0; i < 8; i++) {
/* 73:60 */       if ((aSettings & 1 << i) != 0)
/* 74:   */       {
/* 75:61 */         PanelString line = new PanelString();
/* 76:62 */         line.textLeft = GT_LanguageManager.getTranslation(aCard.getString("mString" + i), "\\\\");
/* 77:63 */         rList.add(line);
/* 78:   */       }
/* 79:   */     }
/* 80:66 */     return rList;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public List<PanelSetting> getSettingsList()
/* 84:   */   {
/* 85:71 */     List<PanelSetting> rList = new ArrayList(30);
/* 86:72 */     for (int i = 0; i < 8; i++) {
/* 87:72 */       rList.add(new PanelSetting("" + (i + 1), 1 << i, getCardType()));
/* 88:   */     }
/* 89:73 */     return rList;
/* 90:   */   }
/* 91:   */   
/* 92:76 */   private static final UUID CARD_TYPE = new UUID(0L, 41L);
/* 93:   */   
/* 94:   */   public UUID getCardType()
/* 95:   */   {
/* 96:77 */     return CARD_TYPE;
/* 97:   */   }
/* 98:   */   
/* 99:   */   @SideOnly(Side.CLIENT)
/* :0:   */   public void getSubItems(Item var1, CreativeTabs aTab, List aList) {}
/* :1:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.GT_SensorCard_Item
 * JD-Core Version:    0.7.0.1
 */