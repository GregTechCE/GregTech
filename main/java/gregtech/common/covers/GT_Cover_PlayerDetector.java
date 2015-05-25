package gregtech.common.covers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fluids.Fluid;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;

public class GT_Cover_PlayerDetector extends GT_CoverBehavior{

	private String placer = "";
	private int range = 8;
	
	public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
	  {
		boolean playerDetected = false;
		
		if(aTileEntity instanceof IGregTechTileEntity){
			if(aTileEntity.isUniversalEnergyStored(20)){
				aTileEntity.decreaseStoredEnergyUnits(20, true);
				range = 32;
			}else{range = 8;}
			placer = ((IGregTechTileEntity) aTileEntity).getOwnerName();
		}
		for (Object tObject : aTileEntity.getWorld().playerEntities) {
            if ((tObject instanceof EntityPlayerMP))
            {
            	EntityPlayerMP tEntity = (EntityPlayerMP)tObject;
              int dist = Math.max(1, (int)tEntity.getDistance(aTileEntity.getXCoord() + 0.5D, aTileEntity.getYCoord() + 0.5D, aTileEntity.getZCoord() + 0.5D));
              if (dist < range)
              {
                if (aCoverVariable == 0)
                {
                	playerDetected=true;
                  break;
                }
                if (tEntity.getDisplayName().equalsIgnoreCase(placer))
                {
                  if (aCoverVariable == 1)
                  {
                	  playerDetected=true;
                    break;
                  }
                }
                else if (aCoverVariable == 2)
                {
                	playerDetected=true;
                  break;
                }
              }
            }
          }
		
		
		aTileEntity.setOutputRedstoneSignal(aSide, (byte)(playerDetected ? 15 : 0));
		return aCoverVariable;
	}
	
	public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
	/*  64:    */   {
	/*  65: 54 */     aCoverVariable = (aCoverVariable + 1) % 3;
	/*  66: 55 */     if (aCoverVariable == 0) {
	/*  67: 55 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if any Player is close");
	/*  68:    */     }
	/*  69: 56 */     if (aCoverVariable == 1) {
	/*  70: 56 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if you are close");
	/*  71:    */     }
	/*  72: 57 */     if (aCoverVariable == 2) {
	/*  73: 57 */       GT_Utility.sendChatToPlayer(aPlayer, "Emit if other player is close");
	/*  74:    */     }
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
	/* 136:105 */     return 20;
	/* 137:    */   }
	
}
