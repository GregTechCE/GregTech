package gregtech.common.tileentities.generators;

/*  3:   */ import java.util.ArrayList;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.visnet.VisNetHandler;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
/*  4:   */ import gregtech.api.interfaces.ITexture;
/*  5:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  6:   */ import gregtech.api.metatileentity.MetaTileEntity;
/*  7:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicGenerator;
/*  8:   */ import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
/*  9:   */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/* 10:   */ 
/* 11:   */ public class GT_MetaTileEntity_MagicalEnergyAbsorber
/* 12:   */   extends GT_MetaTileEntity_BasicGenerator
/* 13:   */ {

				  public int mEfficiency;
				  public static boolean sAllowMultipleEggs = true;
				  public static GT_MetaTileEntity_MagicalEnergyAbsorber mActiveSiphon = null;
				  public static int sEnergyPerEnderCrystal = 32;
				  public static int sEnergyFromVis = 12800;
				  public static final ArrayList<EntityEnderCrystal> sUsedDragonCrystalList = new ArrayList();
				  public EntityEnderCrystal mTargetedCrystal;
				  public static int sDragonEggEnergyPerTick = 128;
				  public static boolean isThaumcraftLoaded;
				  
/* 14:   */   public boolean isOutputFacing(byte aSide)
/* 15:   */   {
/* 16:12 */     return aSide == getBaseMetaTileEntity().getFrontFacing();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public GT_MetaTileEntity_MagicalEnergyAbsorber(int aID, String aName, String aNameRegional, int aTier)
/* 20:   */   {
/* 21:15 */     super(aID, aName, aNameRegional, aTier, "Feasts on magic close to it", new ITexture[0]);
				onConfigLoad();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public GT_MetaTileEntity_MagicalEnergyAbsorber(String aName, int aTier, String aDescription, ITexture[][][] aTextures)
/* 25:   */   {
/* 26:19 */     super(aName, aTier, aDescription, aTextures);
				onConfigLoad();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 30:   */   {
/* 31:24 */     return new GT_MetaTileEntity_MagicalEnergyAbsorber(this.mName, this.mTier, this.mDescription, this.mTextures);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public GT_Recipe.GT_Recipe_Map getRecipes()
/* 35:   */   {
/* 36:29 */     return GT_Recipe.GT_Recipe_Map.sMagicFuels;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int getCapacity()
/* 40:   */   {
/* 41:34 */     return 16000;
/* 42:   */   }

				public void onConfigLoad()
/* 39:   */   {
				this.mEfficiency =GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "MagicEnergyAbsorber.efficiency.tier."+this.mTier, 100-this.mTier*10);
			    this.sAllowMultipleEggs = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "MagicEnergyAbsorber.AllowMultipleEggs", false);
			    this.sEnergyPerEnderCrystal = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "MagicEnergyAbsorber.EnergyPerTick.EnderCrystal", 32);
			    this.sEnergyFromVis = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "MagicEnergyAbsorber.EnergyPerVisDivisor", 2500);
			    this.sDragonEggEnergyPerTick = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "MagicEnergyAbsorber.EnergyPerTick", 2048);
			    this.isThaumcraftLoaded = Loader.isModLoaded("Thaumcraft");
				}
				
			    @Override
			    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
			    	if (aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.isAllowedToWork() && aBaseMetaTileEntity.getUniversalEnergyStored() < maxEUOutput() + aBaseMetaTileEntity.getEUCapacity()) {
			    		//Dragon Egg
			    		if(hasEgg()&&aTick%10==0){
			    			getBaseMetaTileEntity().increaseStoredEnergyUnits(sDragonEggEnergyPerTick*getEfficiency()/10, false);
			    			if ((mActiveSiphon != this) && (!sAllowMultipleEggs)) {
			    		          if ((mActiveSiphon == null) || (mActiveSiphon.getBaseMetaTileEntity() == null) || (mActiveSiphon.getBaseMetaTileEntity().isInvalidTileEntity()) || (!mActiveSiphon.hasEgg())) {
			    		            mActiveSiphon = this;
			    		          } else {
			    		            getBaseMetaTileEntity().doExplosion(Integer.MAX_VALUE);
			    		          }
			    		     }
			    		}
			    		//Energyzed node
			    		if(isThaumcraftLoaded){
			    			try{
			    				World tmpWorld = this.getBaseMetaTileEntity().getWorld();
			    				int tmpX = this.getBaseMetaTileEntity().getXCoord();
			    				int tmpY = this.getBaseMetaTileEntity().getYCoord();
			    				int tmpZ = this.getBaseMetaTileEntity().getZCoord();
			    				int fire = 			VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.FIRE, 1000);
			    				int earth = 		VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.EARTH, 1000);
			    				int air = 			VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.AIR, 1000);
			    				int destruction =  	VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.ENTROPY, 1000);
			    				int order = 		VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.ORDER, 1000);
			    				int water =  		VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.WATER, 1000);
			    				int visEU = (int) (Math.pow(fire, 4)+Math.pow(earth, 4)+Math.pow(air, 4)+Math.pow(destruction, 4)+Math.pow(order, 4)+Math.pow(water, 4));
			    				getBaseMetaTileEntity().increaseStoredEnergyUnits(Math.min(maxEUOutput(), visEU*getEfficiency()/this.sEnergyFromVis), false);
			    			}catch (Throwable e){}
			    		}
			    		//EnderCrystal
			    		
			    		//GC Creeper Egg
			    		
					}
			    }
			    
			    public void inValidate()
			    {
			      if (mActiveSiphon == this) {
			        mActiveSiphon = null;
			      }
			    }
			    
			    public boolean hasEgg()
			    {
			    	Block above = getBaseMetaTileEntity().getBlockOffset(0, 1, 0);
			    	if(above==null||Blocks.air==above){return false;}
			      return Blocks.dragon_egg == above || above.getUnlocalizedName().equals("tile.dragonEgg");
			    }
/* 43:   */   
/* 44:   */   public int getEfficiency()
/* 45:   */   {
/* 46:39 */     return this.mEfficiency;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public ITexture[] getFront(byte aColor)
/* 50:   */   {
/* 51:42 */     return new ITexture[] { super.getFront(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier] };
/* 52:   */   }
/* 53:   */   
/* 54:   */   public ITexture[] getBack(byte aColor)
/* 55:   */   {
/* 56:43 */     return new ITexture[] { super.getBack(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC_FRONT) };
/* 57:   */   }
/* 58:   */   
/* 59:   */   public ITexture[] getBottom(byte aColor)
/* 60:   */   {
/* 61:44 */     return new ITexture[] { super.getBottom(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC) };
/* 62:   */   }
/* 63:   */   
/* 64:   */   public ITexture[] getTop(byte aColor)
/* 65:   */   {
/* 66:45 */     return new ITexture[] { super.getTop(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_DRAGONEGG) };
/* 67:   */   }
/* 68:   */   
/* 69:   */   public ITexture[] getSides(byte aColor)
/* 70:   */   {
/* 71:46 */     return new ITexture[] { super.getSides(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC) };
/* 72:   */   }
/* 73:   */   
/* 74:   */   public ITexture[] getFrontActive(byte aColor)
/* 75:   */   {
/* 76:47 */     return new ITexture[] { super.getFrontActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC_ACTIVE), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier] };
/* 77:   */   }
/* 78:   */   
/* 79:   */   public ITexture[] getBackActive(byte aColor)
/* 80:   */   {
/* 81:48 */     return new ITexture[] { super.getBackActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC_FRONT_ACTIVE) };
/* 82:   */   }
/* 83:   */   
/* 84:   */   public ITexture[] getBottomActive(byte aColor)
/* 85:   */   {
/* 86:49 */     return new ITexture[] { super.getBottomActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC_ACTIVE) };
/* 87:   */   }
/* 88:   */   
/* 89:   */   public ITexture[] getTopActive(byte aColor)
/* 90:   */   {
/* 91:50 */     return new ITexture[] { super.getTopActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_DRAGONEGG) };
/* 92:   */   }
/* 93:   */   
/* 94:   */   public ITexture[] getSidesActive(byte aColor)
/* 95:   */   {
/* 96:51 */     return new ITexture[] { super.getSidesActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC_ACTIVE) };
/* 97:   */   }
/* 98:   */ }