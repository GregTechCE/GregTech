package gregtech.common.covers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.Fluid;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;

public class GT_Cover_NeedMaintainance extends GT_CoverBehavior{

	public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
	  {
		boolean needsRepair = false;
		if(aTileEntity instanceof IGregTechTileEntity){
		IGregTechTileEntity tTileEntity = (IGregTechTileEntity) aTileEntity;
		IMetaTileEntity mTileEntity = tTileEntity.getMetaTileEntity();
		if(mTileEntity instanceof GT_MetaTileEntity_MultiBlockBase){
			GT_MetaTileEntity_MultiBlockBase multi = (GT_MetaTileEntity_MultiBlockBase) mTileEntity;
			int ideal = multi.getIdealStatus();
			int real = multi.getRepairStatus();
			if((aCoverVariable ==0||aCoverVariable==1)&&(ideal-real>0)){
				needsRepair=true;
			}
			if((aCoverVariable ==2||aCoverVariable==3)&&(ideal-real>1)){
				needsRepair=true;
			}if((aCoverVariable ==4||aCoverVariable==5)&&(ideal-real>2)){
				needsRepair=true;
			}if((aCoverVariable ==6||aCoverVariable==7)&&(ideal-real>3)){
				needsRepair=true;
			}
		}}
		if(aCoverVariable % 2 == 0){
			needsRepair = !needsRepair;
		}
		
		aTileEntity.setOutputRedstoneSignal(aSide, (byte)(needsRepair ? 0 : 15));
		return aCoverVariable;
	}
	
	public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
	/*  64:    */   {
	/*  65: 54 */     aCoverVariable = (aCoverVariable + 1) % 10;
	/*  66: 55 */     if (aCoverVariable == 0) {
	/*  67: 55 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if 1 Maintainance Needed");
	/*  68:    */     }
	/*  69: 56 */     if (aCoverVariable == 1) {
	/*  70: 56 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if 1 Maintainance Needed(inverted)");
	/*  71:    */     }
	/*  72: 57 */     if (aCoverVariable == 2) {
	/*  73: 57 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if 2 Maintainance Needed");
	/*  74:    */     }
	/*  75: 58 */     if (aCoverVariable == 3) {
	/*  76: 58 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if 2 Maintainance Needed(inverted)");
	/*  77:    */     }
	/*  78: 59 */     if (aCoverVariable == 4) {
	/*  79: 59 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if 3 Maintainance Needed");
	/*  80:    */     }
	/*  81: 60 */     if (aCoverVariable == 5) {
	/*  82: 60 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if 3 Maintainance Needed(inverted)");
	/*  83:    */     }
	/*  84: 61 */     if (aCoverVariable == 6) {
	/*  85: 61 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if 4 Maintainance Needed");
	/*  86:    */     }
	/*  87: 62 */     if (aCoverVariable == 7) {
	/*  88: 62 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if 4 Maintainance Needed(inverted)");
	/*  89:    */     }
	/*  90: 63 */     if (aCoverVariable == 8) {
	/*  91: 63 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if 5 Maintainance Needed");
	/*  92:    */     }
	/*  93: 64 */     if (aCoverVariable == 9) {
	/*  94: 64 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if 5 Maintainance Needed(inverted)");
	/*  95:    */     }
	/*  96: 65 */     return aCoverVariable;
	/*  97:    */   }
	/*  98:    */   
	/*  99:    */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
	/* 100:    */   {
	/* 101: 70 */     return true;
	/* 102:    */   }
	/* 103:    */   
	/* 104:    */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
	/* 105:    */   {
	/* 106: 75 */     return true;
	/* 107:    */   }
	/* 108:    */   
	/* 109:    */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
	/* 110:    */   {
	/* 111: 80 */     return true;
	/* 112:    */   }
	/* 113:    */   
	/* 114:    */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
	/* 115:    */   {
	/* 116: 85 */     return true;
	/* 117:    */   }
	/* 118:    */   
	/* 119:    */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
	/* 120:    */   {
	/* 121: 90 */     return true;
	/* 122:    */   }
	/* 123:    */   
	/* 124:    */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
	/* 125:    */   {
	/* 126: 95 */     return true;
	/* 127:    */   }
	/* 128:    */   
	/* 129:    */   public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
	/* 130:    */   {
	/* 131:100 */     return true;
	/* 132:    */   }
	/* 133:    */   
	/* 134:    */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
	/* 135:    */   {
	/* 136:105 */     return 60;
	/* 137:    */   }
	
}
