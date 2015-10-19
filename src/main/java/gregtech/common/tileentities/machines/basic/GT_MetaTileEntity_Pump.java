package gregtech.common.tileentities.machines.basic;

import static gregtech.api.enums.GT_Values.V;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_BasicTank;
import gregtech.api.gui.GT_GUIContainer_BasicTank;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;

public class GT_MetaTileEntity_Pump extends GT_MetaTileEntity_Hatch {

	public ArrayList<ChunkPosition> mPumpList = new ArrayList();
	public int mPumpTimer = 0;
	public int mPumpCountBelow = 0;
	public Block mPumpedBlock1 = null;
	public Block mPumpedBlock2 = null;

	public GT_MetaTileEntity_Pump(int aID, String aName, String aNameRegional, int aTier) {
		super(aID, aName, aNameRegional, aTier, 3, "The best way of emptying Oceans!");
	}

	public GT_MetaTileEntity_Pump(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 3, aDescription,aTextures);
	}

	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_Pump(this.mName, this.mTier, this.mDescription, this.mTextures);
	}

	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setString("mPumpedBlock1", this.mPumpedBlock1 == null ? "" : this.mPumpedBlock1.getUnlocalizedName());
		aNBT.setString("mPumpedBlock2", this.mPumpedBlock2 == null ? "" : this.mPumpedBlock2.getUnlocalizedName());
	}

	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		this.mPumpedBlock1 = Block.getBlockFromName(aNBT.getString("mPumpedBlock1"));
		this.mPumpedBlock2 = Block.getBlockFromName(aNBT.getString("mPumpedBlock2"));
	}
	
	@Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_BasicTank(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_BasicTank(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
	}
	@Override
	public boolean doesFillContainers() {
		return true;
	}
	@Override
	public boolean doesEmptyContainers() {
		return false;
	}
	@Override
	public boolean canTankBeFilled() {
		return false;
	}
	@Override
	public boolean canTankBeEmptied() {
		return true;
	}
	@Override
	public boolean displaysItemStack() {
		return true;
	}
	@Override
	public boolean displaysStackSize() {
		return false;
	}
	
	@Override
	public boolean isFluidInputAllowed(FluidStack aFluid) {
		return false;
	}

	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (getBaseMetaTileEntity().isServerSide()) {
			this.mPumpTimer -= 1;
			if ((getBaseMetaTileEntity() instanceof BaseTileEntity)) {
				((BaseTileEntity) getBaseMetaTileEntity()).ignoreUnloadedChunks = false;
			}
			this.doTickProfilingInThisTick = true;
			this.mPumpCountBelow = 0;
			IGregTechTileEntity tTileEntity;
			for (int i = 1; (i < 21) && ((tTileEntity = getBaseMetaTileEntity().getIGregTechTileEntityAtSideAndDistance((byte) 0, i)) != null)
					&& (tTileEntity.getMetaTileEntity() != null) && ((tTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_Pump)); i++) {
				getBaseMetaTileEntity().setActive(tTileEntity.isActive());
				this.mPumpCountBelow += 1;
				((GT_MetaTileEntity_Pump) tTileEntity.getMetaTileEntity()).mPumpTimer -= 1;
			}
			if (this.mPumpCountBelow <= 0) {
				if ((getBaseMetaTileEntity().isAllowedToWork()) && (getBaseMetaTileEntity().isUniversalEnergyStored(16*((int)Math.pow(4, this.mTier))))
						&& ((this.mFluid == null) || (this.mFluid.amount + 1000 <= getCapacity()))) {
					boolean tMovedOneDown = false;
					if ((this.mPumpList.isEmpty()) && (getBaseMetaTileEntity().getTimer() % 100L == 0L)) {
						tMovedOneDown = moveOneDown();
					}
					if ((GT_Utility.isBlockInvalid(this.mPumpedBlock1)) || (GT_Utility.isBlockInvalid(this.mPumpedBlock2))) {
						getFluidAt(getBaseMetaTileEntity().getXCoord(), getYOfPumpHead() - 1, getBaseMetaTileEntity().getZCoord());
						if ((GT_Utility.isBlockInvalid(this.mPumpedBlock1)) || (GT_Utility.isBlockInvalid(this.mPumpedBlock2))) {
							getFluidAt(getBaseMetaTileEntity().getXCoord(), getYOfPumpHead(), getBaseMetaTileEntity().getZCoord() + 1);
						}
						if ((GT_Utility.isBlockInvalid(this.mPumpedBlock1)) || (GT_Utility.isBlockInvalid(this.mPumpedBlock2))) {
							getFluidAt(getBaseMetaTileEntity().getXCoord(), getYOfPumpHead(), getBaseMetaTileEntity().getZCoord() - 1);
						}
						if ((GT_Utility.isBlockInvalid(this.mPumpedBlock1)) || (GT_Utility.isBlockInvalid(this.mPumpedBlock2))) {
							getFluidAt(getBaseMetaTileEntity().getXCoord() + 1, getYOfPumpHead(), getBaseMetaTileEntity().getZCoord());
						}
						if ((GT_Utility.isBlockInvalid(this.mPumpedBlock1)) || (GT_Utility.isBlockInvalid(this.mPumpedBlock2))) {
							getFluidAt(getBaseMetaTileEntity().getXCoord() - 1, getYOfPumpHead(), getBaseMetaTileEntity().getZCoord());
						}
					} else if (getYOfPumpHead() < getBaseMetaTileEntity().getYCoord()) {
						if ((tMovedOneDown) || ((this.mPumpList.isEmpty()) && (getBaseMetaTileEntity().getTimer() % 200L == 100L)) || (getBaseMetaTileEntity().getTimer() % 72000L == 100L)) {
							this.mPumpList.clear();
							int y = getBaseMetaTileEntity().getYCoord() - 1;
							for (int yHead = getYOfPumpHead(); (this.mPumpList.isEmpty()) && (y >= yHead); y--) {
							scanForFluid(getBaseMetaTileEntity().getXCoord(), y, getBaseMetaTileEntity().getZCoord(), this.mPumpList, getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getZCoord(), 10*((int)Math.pow(1.6, this.mTier)));
							}
						}
						if ((!tMovedOneDown) && (this.mPumpTimer <= 0)) {
							while ((!this.mPumpList.isEmpty())
									&& (!consumeFluid(((ChunkPosition) this.mPumpList.get(this.mPumpList.size() - 1)).chunkPosX,
											((ChunkPosition) this.mPumpList.get(this.mPumpList.size() - 1)).chunkPosY,
											((ChunkPosition) this.mPumpList.remove(this.mPumpList.size() - 1)).chunkPosZ))) {
							}
							this.mPumpTimer = 160/((int)Math.pow(2, this.mTier));
						}
					}
				}
				getBaseMetaTileEntity().setActive(!this.mPumpList.isEmpty());
			}
		}
	}

	private boolean moveOneDown() {
		if ((this.mInventory[0] == null) || (this.mInventory[0].stackSize < 1)
				|| (!GT_Utility.areStacksEqual(this.mInventory[0], GT_ModHandler.getIC2Item("miningPipe", 1L)))) {
			return false;
		}
		int yHead = getYOfPumpHead();
		if (yHead <= 0) {
			return false;
		}
		if ((!consumeFluid(getBaseMetaTileEntity().getXCoord(), yHead - 1, getBaseMetaTileEntity().getZCoord()))&& (!getBaseMetaTileEntity().getAir(getBaseMetaTileEntity().getXCoord(), yHead - 1, getBaseMetaTileEntity().getZCoord()))) {

			return false;
		}
		if (!(getBaseMetaTileEntity().getWorld().setBlock(getBaseMetaTileEntity().getXCoord(), yHead - 1, getBaseMetaTileEntity().getZCoord(),GT_Utility.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipeTip", 1L))))) {
			return false;
		}
		if (yHead != getBaseMetaTileEntity().getYCoord()) {
			getBaseMetaTileEntity().getWorld().setBlock(getBaseMetaTileEntity().getXCoord(), yHead, getBaseMetaTileEntity().getZCoord(),GT_Utility.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipe", 1L)));
		}
		getBaseMetaTileEntity().decrStackSize(0, 1);
		return true;
	}

	private int getYOfPumpHead() {
		int y = getBaseMetaTileEntity().getYCoord() - 1;
		while (getBaseMetaTileEntity().getBlock(getBaseMetaTileEntity().getXCoord(), y, getBaseMetaTileEntity().getZCoord()) == GT_Utility
				.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipe", 1L))) {
			y--;
		}
		if (y == getBaseMetaTileEntity().getYCoord() - 1) {
			if (getBaseMetaTileEntity().getBlock(getBaseMetaTileEntity().getXCoord(), y, getBaseMetaTileEntity().getZCoord()) != GT_Utility
					.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipeTip", 1L))) {
				return y + 1;
			}
		} else if (getBaseMetaTileEntity().getBlock(getBaseMetaTileEntity().getXCoord(), y, getBaseMetaTileEntity().getZCoord()) != GT_Utility
				.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipeTip", 1L))&&this.mInventory[0] != null&&this.mInventory[0].stackSize>0&&GT_Utility.areStacksEqual(this.mInventory[0], GT_ModHandler.getIC2Item("miningPipe", 1L))) {
			getBaseMetaTileEntity().getWorld().setBlock(getBaseMetaTileEntity().getXCoord(), y, getBaseMetaTileEntity().getZCoord(),
					GT_Utility.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipeTip", 1L)));
			getBaseMetaTileEntity().decrStackSize(0, 1);
			
		}
		return y;
	}

	private void scanForFluid(int aX, int aY, int aZ, ArrayList<ChunkPosition> aList, int mX, int mZ, int mDist) {
		doTickProfilingInThisTick = false;
        ArrayList tList1 = new ArrayList();
        ArrayList tList2 = new ArrayList();
        tList1.add(new ChunkPosition(aX, aY, aZ));
        while(!tList1.isEmpty()) 
        {
            Iterator i$ = tList1.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                ChunkPosition tPos = (ChunkPosition)i$.next();
                if(tPos.chunkPosX < mX + mDist)
                    addToFirstListIfFluidAndNotAlreadyAddedToAnyOfTheLists(tPos.chunkPosX + 1, tPos.chunkPosY, tPos.chunkPosZ, tList2, aList);
                if(tPos.chunkPosX > mX - mDist)
                    addToFirstListIfFluidAndNotAlreadyAddedToAnyOfTheLists(tPos.chunkPosX - 1, tPos.chunkPosY, tPos.chunkPosZ, tList2, aList);
                if(tPos.chunkPosZ < mZ + mDist)
                    addToFirstListIfFluidAndNotAlreadyAddedToAnyOfTheLists(tPos.chunkPosX, tPos.chunkPosY, tPos.chunkPosZ + 1, tList2, aList);
                if(tPos.chunkPosZ > mZ - mDist)
                    addToFirstListIfFluidAndNotAlreadyAddedToAnyOfTheLists(tPos.chunkPosX, tPos.chunkPosY, tPos.chunkPosZ - 1, tList2, aList);
                addToFirstListIfFluidAndNotAlreadyAddedToAnyOfTheLists(tPos.chunkPosX, tPos.chunkPosY + 1, tPos.chunkPosZ, tList2, aList);
                ChunkPosition tCoordinate = new ChunkPosition(aX, aY + 1, aZ);
                if(tPos.chunkPosX == mX && tPos.chunkPosZ == mZ && tPos.chunkPosY < getBaseMetaTileEntity().getYCoord() && !aList.contains(tCoordinate) && !tList2.contains(tCoordinate))
                    tList2.add(tCoordinate);
            } while(true);
            aList.addAll(tList2);
            tList1 = tList2;
            tList2 = new ArrayList();
        }
        for(int y = getBaseMetaTileEntity().getYCoord(); y >= aY; y--)
            aList.remove(new ChunkPosition(aX, y, aZ));
	}

	private boolean addToFirstListIfFluidAndNotAlreadyAddedToAnyOfTheLists(int aX, int aY, int aZ, ArrayList<ChunkPosition> aList1,
			ArrayList<ChunkPosition> aList2) {
		ChunkPosition tCoordinate = new ChunkPosition(aX, aY, aZ);
		if ((!aList1.contains(tCoordinate)) && (!aList2.contains(tCoordinate))) {
			Block aBlock = getBaseMetaTileEntity().getBlock(aX, aY, aZ);
			if ((this.mPumpedBlock1 == aBlock) || (this.mPumpedBlock2 == aBlock)) {
				aList1.add(tCoordinate);
				return true;
			}
		}
		return false;
	}

	private void getFluidAt(int aX, int aY, int aZ) {
		Block aBlock = getBaseMetaTileEntity().getBlock(aX, aY, aZ);
		if (GT_Utility.isBlockValid(aBlock)) {
			if ((aBlock == Blocks.water) || (aBlock == Blocks.flowing_water)) {
				this.mPumpedBlock1 = Blocks.water;
				this.mPumpedBlock2 = Blocks.flowing_water;
				return;
			}
			if ((aBlock == Blocks.lava) || (aBlock == Blocks.flowing_lava)) {
				this.mPumpedBlock1 = Blocks.lava;
				this.mPumpedBlock2 = Blocks.flowing_lava;
				return;
			}
			if ((aBlock instanceof IFluidBlock)) {
				this.mPumpedBlock1 = aBlock;
				this.mPumpedBlock2 = aBlock;
				return;
			}
		}
		this.mPumpedBlock1 = null;
		this.mPumpedBlock2 = null;
	}

	private boolean consumeFluid(int aX, int aY, int aZ) {
		Block aBlock = getBaseMetaTileEntity().getBlock(aX, aY, aZ);
		int aMeta = getBaseMetaTileEntity().getMetaID(aX, aY, aZ);
		if ((GT_Utility.isBlockValid(aBlock)) && ((this.mPumpedBlock1 == aBlock) || (this.mPumpedBlock2 == aBlock))) {
			if ((aBlock == Blocks.water) || (aBlock == Blocks.flowing_water)) {
				if (aMeta == 0) {
					if (this.mFluid == null) {
						getBaseMetaTileEntity().decreaseStoredEnergyUnits(16*((int)Math.pow(4, this.mTier)), true);
						this.mFluid = GT_ModHandler.getWater(1000L);
					} else if (GT_ModHandler.isWater(this.mFluid)) {
						getBaseMetaTileEntity().decreaseStoredEnergyUnits(16*((int)Math.pow(4, this.mTier)), true);
						this.mFluid.amount += 1000;
					} else {
						return false;
					}
				} else {
					getBaseMetaTileEntity().decreaseStoredEnergyUnits(4*((int)Math.pow(4, this.mTier)), true);
				}
			}
			if ((aBlock == Blocks.lava) || (aBlock == Blocks.flowing_lava)) {
				if (aMeta == 0) {
					if (this.mFluid == null) {
						getBaseMetaTileEntity().decreaseStoredEnergyUnits(16*((int)Math.pow(4, this.mTier)), true);
						this.mFluid = GT_ModHandler.getLava(1000L);
					} else if (GT_ModHandler.isLava(this.mFluid)) {
						getBaseMetaTileEntity().decreaseStoredEnergyUnits(16*((int)Math.pow(4, this.mTier)), true);
						this.mFluid.amount += 1000;
					} else {
						return false;
					}
				} else {
					getBaseMetaTileEntity().decreaseStoredEnergyUnits(4*((int)Math.pow(4, this.mTier)), true);
				}
			}
			if ((aBlock instanceof IFluidBlock)) {				
				if (this.mFluid == null) {
					this.mFluid = ((IFluidBlock) aBlock).drain(getBaseMetaTileEntity().getWorld(), aX, aY, aZ, true);
					getBaseMetaTileEntity().decreaseStoredEnergyUnits(this.mFluid == null ? 1000 : this.mFluid.amount, true);
				}else if(this.mFluid.isFluidEqual(((IFluidBlock) aBlock).drain(getBaseMetaTileEntity().getWorld(), aX, aY, aZ, false))){ 
					this.getBaseMetaTileEntity().getWorld().setBlockToAir( aX, aY, aZ);
					this.mFluid.amount += 1000;
					getBaseMetaTileEntity().decreaseStoredEnergyUnits(16*((int)Math.pow(4, this.mTier)), true);
				}else {
					return false;
				}
			}
			getBaseMetaTileEntity().getWorld().setBlock(aX, aY, aZ,Blocks.air,0,2);
			return true;
		}
		return false;
	}

	@Override
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
		if (aBaseMetaTileEntity.isClientSide()) return true;
		aBaseMetaTileEntity.openGUI(aPlayer);
		return true;
	}


	@Override public boolean isSimpleMachine()						{return false;}
	@Override public boolean isOverclockerUpgradable()				{return false;}
	@Override public boolean isTransformerUpgradable()				{return false;}
	@Override public boolean isElectric()							{return true;}
	@Override public boolean isFacingValid(byte aFacing)			{return true;}
	@Override public boolean isEnetInput() 							{return true;}
	@Override public boolean isInputFacing(byte aSide)				{return true;}
	@Override public boolean isOutputFacing(byte aSide)				{return false;}
	@Override public boolean isTeleporterCompatible()				{return false;}
	@Override public long getMinimumStoredEU()						{return V[mTier]*16;}
	@Override public long maxEUStore()								{return V[mTier]*64;}
    @Override public long maxEUInput()								{return V[mTier];}
    @Override public long maxSteamStore()							{return maxEUStore();}
    @Override public long maxAmperesIn()							{return 2;}
	@Override public int getStackDisplaySlot()						{return 2;}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
	
	
	@Override
	public int getCapacity() {
		return 16000*this.mTier;
	}
	@Override
	public int getTankPressure() {
		return 100;
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return new ITexture[] {Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex+1], (aSide == 0||aSide ==1) ? null : new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ADV_PUMP) };
	}

	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return getTexturesInactive(aBaseTexture);
	}

	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[] {
				new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ADV_PUMP),new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ADV_PUMP),
				new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ADV_PUMP),new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ADV_PUMP),};
	}
}
