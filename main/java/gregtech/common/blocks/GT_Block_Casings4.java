/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.BlockIcons;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  5:   */ import gregtech.api.objects.GT_CopiedBlockTexture;
/*  6:   */ import gregtech.api.util.GT_LanguageManager;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_LargeTurbine;
/*  7:   */ import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
/*  8:   */ import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
/*  9:   */ 
/* 10:   */ public class GT_Block_Casings4
/* 11:   */   extends GT_Block_Casings_Abstract
/* 12:   */ {
	 			public static boolean mConnectedMachineTextures = true;
	 			
/* 13:   */   public GT_Block_Casings4()
/* 14:   */   {
/* 15:12 */     super(GT_Item_Casings4.class, "gt.blockcasings4", GT_Material_Casings.INSTANCE);
/* 16:13 */     for (byte i = 0; i < 16; i = (byte)(i + 1)) {
/* 17:13 */       Textures.BlockIcons.CASING_BLOCKS[(i + 48)] = new GT_CopiedBlockTexture(this, 6, i);
/* 18:   */     }
/* 19:14 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Robust Tungstensteel Casing");
/* 20:15 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Clean Stainless Steel Casing");
/* 21:16 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Stable Titanium Casing");
/* 22:17 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Titanium Firebox Casing");
///* 23:   */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Fusion Casing");
///* 23:   */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Fusion Casing");
/* 23:   */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Fusion Casing");
/* 23:   */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Fusion Coil");
/* 24:   */ 	GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Fusion Casing MK II");
/* 25:   */ 	GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Turbine Casing");
/* 25:   */ 
/* 35:30 */     ItemList.Casing_RobustTungstenSteel.set(new ItemStack(this, 1, 0));
/* 36:31 */     ItemList.Casing_CleanStainlessSteel.set(new ItemStack(this, 1, 1));
/* 37:32 */     ItemList.Casing_StableTitanium.set(new ItemStack(this, 1, 2));
/* 38:33 */     ItemList.Casing_Firebox_Titanium.set(new ItemStack(this, 1, 3));
				ItemList.Casing_Fusion.set(new ItemStack(this,1,6));
				ItemList.Casing_Fusion_Coil.set(new ItemStack(this,1,7));
				ItemList.Casing_Fusion2.set(new ItemStack(this,1,8));
				ItemList.Casing_Turbine.set(new ItemStack(this,1,9));
/* 39:   */   }
/* 40:   */   
/* 41:   */   public IIcon getIcon(int aSide, int aMeta)
/* 42:   */   {
/* 43:50 */     switch (aMeta)
/* 44:   */     {
/* 45:   */     case 0: 
/* 46:51 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 47:   */     case 1: 
/* 48:52 */       return Textures.BlockIcons.MACHINE_CASING_CLEAN_STAINLESSSTEEL.getIcon();
/* 49:   */     case 2: 
/* 50:53 */       return Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getIcon();
/* 51:   */     case 3: 
/* 52:54 */       return aSide > 1 ? Textures.BlockIcons.MACHINE_CASING_FIREBOX_TITANIUM.getIcon() : Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getIcon();
/* 53:   */     case 4: 
/* 54:55 */       return Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW.getIcon();
/* 55:   */     case 5: 
/* 56:56 */       return Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS.getIcon();
/* 57:   */     case 6: 
/* 58:57 */       return Textures.BlockIcons.MACHINE_CASING_FUSION.getIcon();
/* 59:   */     case 7: 
/* 60:58 */       return Textures.BlockIcons.MACHINE_CASING_FUSION_COIL.getIcon();
/* 61:   */     case 8: 
/* 62:59 */       return Textures.BlockIcons.MACHINE_CASING_FUSION_2.getIcon();
/* 63:   */     case 9: 
/* 64:60 */       return Textures.BlockIcons.MACHINE_CASING_TURBINE.getIcon();
/* 65:   */     case 10: 
/* 66:61 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 67:   */     case 11: 
/* 68:62 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 69:   */     case 12: 
/* 70:63 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 71:   */     case 13: 
/* 72:64 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 73:   */     case 14: 
/* 74:65 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 75:   */     case 15: 
/* 76:66 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 77:   */     }
/* 78:68 */     return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
/* 79:   */   }

@SideOnly(Side.CLIENT)
public IIcon getIcon(IBlockAccess aWorld, int xCoord, int yCoord, int zCoord, int aSide)
{
  int tMeta = aWorld.getBlockMetadata(xCoord, yCoord, zCoord);
  if (     ((tMeta != 6)&&(tMeta != 8)&&(tMeta != 9))      || (!mConnectedMachineTextures)) {
    return getIcon(aSide, tMeta);
  }
  int tStartIndex = tMeta == 6 ? 1 : 13;
  if (tMeta == 9) {
    if ((aSide == 2) || (aSide == 3))
    {
      TileEntity tTileEntity;
      IMetaTileEntity tMetaTileEntity;
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[0].getIcon();
        }
        return Textures.BlockIcons.TURBINE[0].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[3].getIcon();
        }
        return Textures.BlockIcons.TURBINE[3].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[6].getIcon();
        }
        return Textures.BlockIcons.TURBINE[6].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[1].getIcon();
        }
        return Textures.BlockIcons.TURBINE[1].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[7].getIcon();
        }
        return Textures.BlockIcons.TURBINE[7].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[8].getIcon();
        }
        return Textures.BlockIcons.TURBINE[8].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[5].getIcon();
        }
        return Textures.BlockIcons.TURBINE[5].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[2].getIcon();
        }
        return Textures.BlockIcons.TURBINE[2].getIcon();
      }
    }
    else if ((aSide == 4) || (aSide == 5))
    {
      TileEntity tTileEntity;
      Object tMetaTileEntity;
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord + (aSide == 4 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[0].getIcon();
        }
        return Textures.BlockIcons.TURBINE[0].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord, zCoord + (aSide == 4 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[3].getIcon();
        }
        return Textures.BlockIcons.TURBINE[3].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord + (aSide == 4 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[6].getIcon();
        }
        return Textures.BlockIcons.TURBINE[6].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[1].getIcon();
        }
        return Textures.BlockIcons.TURBINE[1].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[7].getIcon();
        }
        return Textures.BlockIcons.TURBINE[7].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord + (aSide == 5 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[8].getIcon();
        }
        return Textures.BlockIcons.TURBINE[8].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord, zCoord + (aSide == 5 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[5].getIcon();
        }
        return Textures.BlockIcons.TURBINE[5].getIcon();
      }
      if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord + (aSide == 5 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity)tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine)))
      {
        if (((IGregTechTileEntity)tTileEntity).isActive()) {
          return Textures.BlockIcons.TURBINE_ACTIVE[2].getIcon();
        }
        return Textures.BlockIcons.TURBINE[2].getIcon();
      }
    }return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
  }
  boolean[] tConnectedSides = { (aWorld.getBlock(xCoord, yCoord - 1, zCoord) == this) && (aWorld.getBlockMetadata(xCoord, yCoord - 1, zCoord) == tMeta), (aWorld.getBlock(xCoord, yCoord + 1, zCoord) == this) && (aWorld.getBlockMetadata(xCoord, yCoord + 1, zCoord) == tMeta), (aWorld.getBlock(xCoord + 1, yCoord, zCoord) == this) && (aWorld.getBlockMetadata(xCoord + 1, yCoord, zCoord) == tMeta), (aWorld.getBlock(xCoord, yCoord, zCoord + 1) == this) && (aWorld.getBlockMetadata(xCoord, yCoord, zCoord + 1) == tMeta), (aWorld.getBlock(xCoord - 1, yCoord, zCoord) == this) && (aWorld.getBlockMetadata(xCoord - 1, yCoord, zCoord) == tMeta), (aWorld.getBlock(xCoord, yCoord, zCoord - 1) == this) && (aWorld.getBlockMetadata(xCoord, yCoord, zCoord - 1) == tMeta)};
  switch (aSide)
  {
  case 0: 
    if (tConnectedSides[0]) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
    }
    if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
    }
    if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
    }
    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
    }
    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
    }
    if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
    }
    if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
    }
    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
    }
    if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
    }
    if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((!tConnectedSides[4]) && (!tConnectedSides[2])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
    }
    if ((!tConnectedSides[5]) && (!tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
    }
  case 1: 
    if (tConnectedSides[1] ) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((tConnectedSides[4] ) && (tConnectedSides[5] ) && (tConnectedSides[2] ) && (tConnectedSides[3] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
    }
    if ((!tConnectedSides[4]) && (tConnectedSides[5] ) && (tConnectedSides[2] ) && (tConnectedSides[3] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
    }
    if ((tConnectedSides[4] ) && (!tConnectedSides[5]) && (tConnectedSides[2] ) && (tConnectedSides[3] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
    }
    if ((tConnectedSides[4] ) && (tConnectedSides[5] ) && (!tConnectedSides[2]) && (tConnectedSides[3] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
    }
    if ((tConnectedSides[4] ) && (tConnectedSides[5] ) && (tConnectedSides[2] ) && (!tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
    }
    if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2] ) && (tConnectedSides[3] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
    }
    if ((tConnectedSides[4] ) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
    }
    if ((tConnectedSides[4] ) && (tConnectedSides[5] ) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
    }
    if ((!tConnectedSides[4]) && (tConnectedSides[5] ) && (tConnectedSides[2] ) && (!tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
    }
    if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
    }
    if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
    }
  case 2: 
    if (tConnectedSides[5] ) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((tConnectedSides[2] ) && (tConnectedSides[0] ) && (tConnectedSides[4] ) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
    }
    if ((!tConnectedSides[2]) && (tConnectedSides[0] ) && (tConnectedSides[4] ) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
    }
    if ((tConnectedSides[2] ) && (!tConnectedSides[0]) && (tConnectedSides[4] ) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
    }
    if ((tConnectedSides[2] ) && (tConnectedSides[0] ) && (!tConnectedSides[4]) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
    }
    if ((tConnectedSides[2] ) && (tConnectedSides[0] ) && (tConnectedSides[4] ) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
    }
    if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4] ) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
    }
    if ((tConnectedSides[2] ) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
    }
    if ((tConnectedSides[2] ) && (tConnectedSides[0] ) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
    }
    if ((!tConnectedSides[2]) && (tConnectedSides[0] ) && (tConnectedSides[4] ) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
    }
    if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
    }
    if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
    }
  case 3: 
    if (tConnectedSides[3] ) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((tConnectedSides[2] ) && (tConnectedSides[0] ) && (tConnectedSides[4] ) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
    }
    if ((!tConnectedSides[2]) && (tConnectedSides[0] ) && (tConnectedSides[4] ) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
    }
    if ((tConnectedSides[2] ) && (!tConnectedSides[0]) && (tConnectedSides[4] ) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
    }
    if ((tConnectedSides[2] ) && (tConnectedSides[0] ) && (!tConnectedSides[4]) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
    }
    if ((tConnectedSides[2] ) && (tConnectedSides[0] ) && (tConnectedSides[4] ) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
    }
    if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4] ) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
    }
    if ((tConnectedSides[2] ) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
    }
    if ((tConnectedSides[2] ) && (tConnectedSides[0] ) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
    }
    if ((!tConnectedSides[2]) && (tConnectedSides[0] ) && (tConnectedSides[4] ) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
    }
    if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
    }
    if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
    }
  case 4: 
    if (tConnectedSides[4] ) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((tConnectedSides[0] ) && (tConnectedSides[3] ) && (tConnectedSides[1] ) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
    }
    if ((!tConnectedSides[0]) && (tConnectedSides[3] ) && (tConnectedSides[1] ) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
    }
    if ((tConnectedSides[0] ) && (!tConnectedSides[3]) && (tConnectedSides[1] ) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
    }
    if ((tConnectedSides[0] ) && (tConnectedSides[3] ) && (!tConnectedSides[1]) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
    }
    if ((tConnectedSides[0] ) && (tConnectedSides[3] ) && (tConnectedSides[1] ) && (!tConnectedSides[5])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
    }
    if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1] ) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
    }
    if ((tConnectedSides[0] ) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
    }
    if ((tConnectedSides[0] ) && (tConnectedSides[3] ) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
    }
    if ((!tConnectedSides[0]) && (tConnectedSides[3] ) && (tConnectedSides[1] ) && (!tConnectedSides[5])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
    }
    if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
    }
    if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
    }
  case 5: 
    if (tConnectedSides[2] ) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((tConnectedSides[0] ) && (tConnectedSides[3] ) && (tConnectedSides[1] ) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
    }
    if ((!tConnectedSides[0]) && (tConnectedSides[3] ) && (tConnectedSides[1] ) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
    }
    if ((tConnectedSides[0] ) && (!tConnectedSides[3]) && (tConnectedSides[1] ) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
    }
    if ((tConnectedSides[0] ) && (tConnectedSides[3] ) && (!tConnectedSides[1]) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
    }
    if ((tConnectedSides[0] ) && (tConnectedSides[3] ) && (tConnectedSides[1] ) && (!tConnectedSides[5])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
    }
    if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1] ) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
    }
    if ((tConnectedSides[0] ) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5] )) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
    }
    if ((tConnectedSides[0] ) && (tConnectedSides[3] ) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
    }
    if ((!tConnectedSides[0]) && (tConnectedSides[3] ) && (tConnectedSides[1] ) && (!tConnectedSides[5])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
    }
    if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
    if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
    }
    if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
      return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
    }
    break;
  }
  return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
}}