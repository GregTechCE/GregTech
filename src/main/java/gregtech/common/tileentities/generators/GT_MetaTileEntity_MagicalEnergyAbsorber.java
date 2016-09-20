package gregtech.common.tileentities.generators;

import cpw.mods.fml.common.Loader;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.TC_Aspects;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicGenerator;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.api.visnet.VisNetHandler;

import java.util.ArrayList;
import java.util.Locale;

import static gregtech.api.enums.GT_Values.V;

public class GT_MetaTileEntity_MagicalEnergyAbsorber extends GT_MetaTileEntity_BasicGenerator {

    public static final ArrayList<EntityEnderCrystal> sUsedDragonCrystalList = new ArrayList();
    public static boolean sAllowMultipleEggs = true;
    public static GT_MetaTileEntity_MagicalEnergyAbsorber mActiveSiphon = null;
    public static int sEnergyPerEnderCrystal = 32;
    public static int sEnergyFromVis = 12800;
    public static int sDragonEggEnergyPerTick = 128;
    public static boolean isThaumcraftLoaded;
    public int mEfficiency;
    public EntityEnderCrystal mTargetedCrystal;

    public GT_MetaTileEntity_MagicalEnergyAbsorber(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, "Feasts on magic close to it", new ITexture[0]);
        onConfigLoad();
    }

    public GT_MetaTileEntity_MagicalEnergyAbsorber(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
        onConfigLoad();
    }

    public boolean isOutputFacing(byte aSide) {
        return aSide == getBaseMetaTileEntity().getFrontFacing();
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_MagicalEnergyAbsorber(this.mName, this.mTier, this.mDescription, this.mTextures);
    }

    public GT_Recipe.GT_Recipe_Map getRecipes() {
        return GT_Recipe.GT_Recipe_Map.sMagicFuels;
    }

    public int getCapacity() {
        return 16000;
    }

    public void onConfigLoad() {
        this.mEfficiency = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "MagicEnergyAbsorber.efficiency.tier." + this.mTier,
                100 - this.mTier * 10);
        this.sAllowMultipleEggs = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "MagicEnergyAbsorber.AllowMultipleEggs", false);
        this.sEnergyPerEnderCrystal = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "MagicEnergyAbsorber.EnergyPerTick.EnderCrystal", 32);
        this.sEnergyFromVis = (GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "MagicEnergyAbsorber.EnergyPerVisDivisor", 2500) * 10);
        this.sDragonEggEnergyPerTick = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "MagicEnergyAbsorber.EnergyPerTick", 2048);
        this.isThaumcraftLoaded = Loader.isModLoaded("Thaumcraft");
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.isAllowedToWork()
                && aBaseMetaTileEntity.getUniversalEnergyStored() < maxEUOutput() + aBaseMetaTileEntity.getEUCapacity()) {
            // Dragon Egg
            if (hasEgg() && aTick % 10 == 0) {
                getBaseMetaTileEntity().increaseStoredEnergyUnits(sDragonEggEnergyPerTick * getEfficiency() / 10, false);
                if ((mActiveSiphon != this) && (!sAllowMultipleEggs)) {
                    if ((mActiveSiphon == null) || (mActiveSiphon.getBaseMetaTileEntity() == null)
                            || (mActiveSiphon.getBaseMetaTileEntity().isInvalidTileEntity()) || (!mActiveSiphon.hasEgg())) {
                        mActiveSiphon = this;
                    } else {
                        Block tEgg = mActiveSiphon.getBaseMetaTileEntity().getBlockOffset(0, 1, 0);
                        if (!getBaseMetaTileEntity().getWorld().getChunkFromBlockCoords(mActiveSiphon.getBaseMetaTileEntity().getXCoord(), mActiveSiphon.getBaseMetaTileEntity().getZCoord()).isChunkLoaded && (tEgg == Blocks.dragon_egg || tEgg.getUnlocalizedName().equals("tile.dragonEgg"))) {
                            getBaseMetaTileEntity().doExplosion(Integer.MAX_VALUE);
                        } else {
                            mActiveSiphon = this;
                        }
                    }
                }
            }
            // Energyzed node
            if (isThaumcraftLoaded) {
                try {
                    World tmpWorld = this.getBaseMetaTileEntity().getWorld();
                    int tmpX = this.getBaseMetaTileEntity().getXCoord();
                    int tmpY = this.getBaseMetaTileEntity().getYCoord();
                    int tmpZ = this.getBaseMetaTileEntity().getZCoord();
                    int fire = VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.FIRE, 1000);
                    int earth = VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.EARTH, 1000);
                    int air = VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.AIR, 1000);
                    int destruction = VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.ENTROPY, 1000);
                    int order = VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.ORDER, 1000);
                    int water = VisNetHandler.drainVis(tmpWorld, tmpX, tmpY, tmpZ, Aspect.WATER, 1000);
                    int visEU = (int) (Math.pow(fire, 4) + Math.pow(earth, 4) + Math.pow(air, 4) + Math.pow(destruction, 4) + Math.pow(order, 4) + Math.pow(
                            water, 4));
                    int mult = 85;
                    if (fire > 4)
                        mult += 15;
                    if (earth > 4)
                        mult += 15;
                    if (air > 4)
                        mult += 15;
                    if (destruction > 4)
                        mult += 15;
                    if (order > 4)
                        mult += 15;
                    if (water > 4)
                        mult += 15;
                    visEU = (visEU * mult) / 100;
                    getBaseMetaTileEntity().increaseStoredEnergyUnits(Math.min(maxEUOutput(), visEU * getEfficiency() / this.sEnergyFromVis), false);
                } catch (Throwable e) {
                }
            }
            // EnderCrystal
            if (sEnergyPerEnderCrystal > 0) {
                if (this.mTargetedCrystal == null) {
                    ArrayList<EntityEnderCrystal> tList = (ArrayList) getBaseMetaTileEntity().getWorld().getEntitiesWithinAABB(
                            EntityEnderCrystal.class,
                            AxisAlignedBB.getBoundingBox(getBaseMetaTileEntity().getXCoord() - 64, getBaseMetaTileEntity().getYCoord() - 64,
                                    getBaseMetaTileEntity().getZCoord() - 64, getBaseMetaTileEntity().getXCoord() + 64,
                                    getBaseMetaTileEntity().getYCoord() + 64, getBaseMetaTileEntity().getZCoord() + 64));
                    if ((tList != null) && (!tList.isEmpty())) {
                        tList.removeAll(sUsedDragonCrystalList);
                        if (tList.size() > 0) {
                            this.mTargetedCrystal = ((EntityEnderCrystal) tList.get(0));
                            if (this.mTargetedCrystal != null) {
                                sUsedDragonCrystalList.add(this.mTargetedCrystal);
                            }
                        }
                    }
                } else if (this.mTargetedCrystal.isEntityAlive()) {
                    getBaseMetaTileEntity().increaseStoredEnergyUnits(sEnergyPerEnderCrystal * 10, false);
                } else {
                    sUsedDragonCrystalList.remove(this.mTargetedCrystal);
                    this.mTargetedCrystal = null;
                }
            }

            // Absorb entchantments and TC essentia
            try {
                if ((this.mInventory[0] != null) && (this.mInventory[1] == null)) {
                    if (isThaumcraftLoaded && this.mInventory[0].getItem() instanceof IEssentiaContainerItem) {
                        AspectList tAspect = ((IEssentiaContainerItem) this.mInventory[0].getItem()).getAspects(this.mInventory[0]);
                        TC_Aspects tValue = TC_Aspects.valueOf(tAspect.getAspects()[0].getTag().toUpperCase(Locale.ENGLISH));
                        int tEU = (tValue.mValue * tAspect.getAmount((Aspect) tValue.mAspect) * 100);
                        getBaseMetaTileEntity().increaseStoredEnergyUnits(tEU * getEfficiency() / 100, true);
                        ItemStack tStack = this.mInventory[0].copy();
                        tStack.setTagCompound(null);
                        tStack.setItemDamage(0);
                        tStack.stackSize = 1;
                        this.mInventory[1] = tStack;
                        this.mInventory[0].stackSize--;
                        if (this.mInventory[0].stackSize < 1) {
                            this.mInventory[0] = null;
                        }

                    } else {
                        if ((this.mInventory[0].isItemEnchanted()) && (this.mInventory[0].getItem().getItemEnchantability() > 0)) {
                            NBTTagList tEnchantments = this.mInventory[0].getEnchantmentTagList();
                            if (tEnchantments != null) {
                                for (int i = 0; i < tEnchantments.tagCount(); i++) {
                                    short tID = ((NBTTagCompound) tEnchantments.getCompoundTagAt(i)).getShort("id");
                                    short tLevel = ((NBTTagCompound) tEnchantments.getCompoundTagAt(i)).getShort("lvl");
                                    if ((tID > -1) && (tID < Enchantment.enchantmentsList.length)) {
                                        Enchantment tEnchantment = Enchantment.enchantmentsList[tID];
                                        if (tEnchantment != null) {
                                            getBaseMetaTileEntity().increaseStoredEnergyUnits(
                                                    1000000 * getEfficiency() * tLevel / (tEnchantment.getMaxLevel() * tEnchantment.getWeight() * 100), true);
                                        }
                                    }
                                }
                                this.mInventory[0].stackTagCompound.removeTag("ench");
                            }
                        } else if ((this.mInventory[0].getItem() instanceof ItemEnchantedBook)) {
                            NBTTagList tEnchantments = ((ItemEnchantedBook) this.mInventory[0].getItem()).func_92110_g(this.mInventory[0]);
                            if (tEnchantments != null) {
                                for (int i = 0; i < tEnchantments.tagCount(); i++) {
                                    short tID = ((NBTTagCompound) tEnchantments.getCompoundTagAt(i)).getShort("id");
                                    short tLevel = ((NBTTagCompound) tEnchantments.getCompoundTagAt(i)).getShort("lvl");
                                    if ((tID > -1) && (tID < Enchantment.enchantmentsList.length)) {
                                        Enchantment tEnchantment = Enchantment.enchantmentsList[tID];
                                        if (tEnchantment != null) {
                                            getBaseMetaTileEntity().increaseStoredEnergyUnits(
                                                    1000000 * tLevel / (tEnchantment.getMaxLevel() * tEnchantment.getWeight()), true);
                                        }
                                    }
                                }
                                this.mInventory[0] = new ItemStack(Items.book, 1);
                            }
                        }
                        this.mInventory[1] = this.mInventory[0];
                        this.mInventory[0] = null;
                    }
                }
            } catch (Throwable e) {
            }
        }
    }

    public void inValidate() {
        if (mActiveSiphon == this) {
            mActiveSiphon = null;
        }
    }

    public boolean hasEgg() {
        Block above = getBaseMetaTileEntity().getBlockOffset(0, 1, 0);
        if (above == null || Blocks.air == above) {
            return false;
        }
        return Blocks.dragon_egg == above || above.getUnlocalizedName().equals("tile.dragonEgg");
    }

    public int getEfficiency() {
        return this.mEfficiency;
    }

    @Override
    public long maxEUStore() {
        return Math.max(getEUVar(), V[mTier] * 16000 + getMinimumStoredEU());
    }

    public ITexture[] getFront(byte aColor) {
        return new ITexture[]{super.getFront(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC),
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier]};
    }

    public ITexture[] getBack(byte aColor) {
        return new ITexture[]{super.getBack(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC_FRONT)};
    }

    public ITexture[] getBottom(byte aColor) {
        return new ITexture[]{super.getBottom(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC)};
    }

    public ITexture[] getTop(byte aColor) {
        return new ITexture[]{super.getTop(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_DRAGONEGG)};
    }

    public ITexture[] getSides(byte aColor) {
        return new ITexture[]{super.getSides(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC)};
    }

    public ITexture[] getFrontActive(byte aColor) {
        return new ITexture[]{super.getFrontActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC_ACTIVE),
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier]};
    }

    public ITexture[] getBackActive(byte aColor) {
        return new ITexture[]{super.getBackActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC_FRONT_ACTIVE)};
    }

    public ITexture[] getBottomActive(byte aColor) {
        return new ITexture[]{super.getBottomActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC_ACTIVE)};
    }

    public ITexture[] getTopActive(byte aColor) {
        return new ITexture[]{super.getTopActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_DRAGONEGG)};
    }

    public ITexture[] getSidesActive(byte aColor) {
        return new ITexture[]{super.getSidesActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC_ACTIVE)};
    }

	@Override
	public int getPollution() {
		return 0;
	}
}