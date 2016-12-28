package gregtech.common;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.world.GT_Worldgen;
import gregtech.common.blocks.GT_Block_GeneratedOres;
import gregtech.loaders.misc.GT_Achievements;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.ArrayList;
import java.util.Random;

public class GT_Worldgen_GT_Ore_Layer
        extends GT_Worldgen {
    public static ArrayList<GT_Worldgen_GT_Ore_Layer> sList = new ArrayList<>();
    public static int sWeight = 0;
    public final short mMinY;
    public final short mMaxY;
    public final short mWeight;
    public final short mDensity;
    public final short mSize;
    public final short mPrimaryMeta;
    public final short mSecondaryMeta;
    public final short mBetweenMeta;
    public final short mSporadicMeta;
    public final String mBiome;
    public final boolean mOverworld;
    public final boolean mNether;
    public final boolean mEnd;
    public final boolean mEndAsteroid;
    public final boolean mMoon;
    public final boolean mMars;
    public final boolean mAsteroid;

    private BlockPos.MutableBlockPos temp = new BlockPos.MutableBlockPos();

    public GT_Worldgen_GT_Ore_Layer(String aName, boolean aDefault, int aMinY, int aMaxY, int aWeight, int aDensity, int aSize, boolean aOverworld, boolean aNether, boolean aEnd, boolean aMoon, boolean aMars, boolean aAsteroid, Materials aPrimary, Materials aSecondary, Materials aBetween, Materials aSporadic) {
        super(aName, sList, aDefault);
        this.mOverworld = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Overworld", aOverworld);
        this.mNether = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Nether", aNether);
        this.mEnd = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "TheEnd", aEnd);
        this.mEndAsteroid = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "EndAsteroid", aEnd);
        this.mMoon = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Moon", aMoon);
        this.mMars = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Mars", aMars);
        this.mAsteroid = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Asteroid", aAsteroid);
        this.mMinY = ((short) GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "MinHeight", aMinY));
        this.mMaxY = ((short) Math.max(this.mMinY + 5, GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "MaxHeight", aMaxY)));
        this.mWeight = ((short) GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "RandomWeight", aWeight));
        this.mDensity = ((short) GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Density", aDensity));
        this.mSize = ((short) Math.max(1, GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Size", aSize)));
        this.mPrimaryMeta = ((short) GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OrePrimaryLayer", aPrimary.mMetaItemSubID));
        this.mSecondaryMeta = ((short) GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OreSecondaryLayer", aSecondary.mMetaItemSubID));
        this.mBetweenMeta = ((short) GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OreSporadiclyInbetween", aBetween.mMetaItemSubID));
        this.mSporadicMeta = ((short) GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OreSporaticlyAround", aSporadic.mMetaItemSubID));
        this.mBiome = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "BiomeName", "None");
        if (this.mEnabled) {
            GT_Achievements.registerOre(GregTech_API.sGeneratedMaterials[(mPrimaryMeta % 1000)], aMinY, aMaxY, aWeight, aOverworld, aNether, aEnd);
            GT_Achievements.registerOre(GregTech_API.sGeneratedMaterials[(mSecondaryMeta % 1000)], aMinY, aMaxY, aWeight, aOverworld, aNether, aEnd);
            GT_Achievements.registerOre(GregTech_API.sGeneratedMaterials[(mBetweenMeta % 1000)], aMinY, aMaxY, aWeight, aOverworld, aNether, aEnd);
            GT_Achievements.registerOre(GregTech_API.sGeneratedMaterials[(mSporadicMeta % 1000)], aMinY, aMaxY, aWeight, aOverworld, aNether, aEnd);
            sWeight += this.mWeight;
            /* TODO
            if(GregTech_API.mImmersiveEngineering && GT_Mod.gregtechproxy.mImmersiveEngineeringRecipes){
                blusunrize.immersiveengineering.api.tool.ExcavatorHandler.addMineral(aName.substring(8, 9).toUpperCase()+aName.substring(9), aWeight, 0.2f, new String[]{"ore"+aPrimary.mName,"ore"+aSecondary.mName,"ore"+aBetween.mName,"ore"+aSporadic.mName}, new float[]{.4f,.4f,.15f,.05f});
            }*/
        }
    }

    @Override
    public boolean executeWorldgen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkGenerator aChunkGenerator, IChunkProvider aChunkProvider) {
        //if (!this.mBiome.equals("None") && !(this.mBiome.equals(aBiome))) {
        //    return false; //Not the correct biome for ore mix
        //}

        if (!isDimensionAllowed(aWorld, aDimensionType, mNether, mOverworld, mEnd, mMoon, mMars)) {
            return false;
        }
        //StopWatch watch = new StopWatch();
        //watch.start();
        int tMinY = this.mMinY + aRandom.nextInt(this.mMaxY - this.mMinY - 5);

        int cX = aChunkX - aRandom.nextInt(this.mSize);
        int eX = aChunkX + 16 + aRandom.nextInt(this.mSize);
        for (int tX = cX; tX <= eX; tX++) {
            int cZ = aChunkZ - aRandom.nextInt(this.mSize);
            int eZ = aChunkZ + 16 + aRandom.nextInt(this.mSize);
            for (int tZ = cZ; tZ <= eZ; tZ++) {
                for (int i = tMinY + 3; i < tMinY + 6; i++) {
                    if ((aRandom.nextInt(Math.max(1, Math.max(MathHelper.abs_int(cZ - tZ), MathHelper.abs_int(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(MathHelper.abs_int(cX - tX), MathHelper.abs_int(eX - tX)) / this.mDensity)) == 0)) {
                        temp.setPos(tX, i, tZ);
                        GT_Block_GeneratedOres.setOreBlock(aWorld, temp, this.mPrimaryMeta, false);
                    }
                }
                if (this.mSecondaryMeta > 0) {
                    for (int i = tMinY - 1; i < tMinY + 2; i++) {
                        if ((aRandom.nextInt(Math.max(1, Math.max(MathHelper.abs_int(cZ - tZ), MathHelper.abs_int(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(MathHelper.abs_int(cX - tX), MathHelper.abs_int(eX - tX)) / this.mDensity)) == 0)) {
                            temp.setPos(tX, i, tZ);
                            GT_Block_GeneratedOres.setOreBlock(aWorld, temp, this.mSecondaryMeta, false);
                        }
                    }
                }
                if (this.mBetweenMeta > 0 && ((aRandom.nextInt(Math.max(1, Math.max(MathHelper.abs_int(cZ - tZ), MathHelper.abs_int(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(MathHelper.abs_int(cX - tX), MathHelper.abs_int(eX - tX)) / this.mDensity)) == 0))) {
                    temp.setPos(tX, tMinY + 2 + aRandom.nextInt(2), tZ);
                    GT_Block_GeneratedOres.setOreBlock(aWorld, temp, this.mBetweenMeta, false);
                }
                if (this.mSporadicMeta > 0 && ((aRandom.nextInt(Math.max(1, Math.max(MathHelper.abs_int(cZ - tZ), MathHelper.abs_int(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(MathHelper.abs_int(cX - tX), MathHelper.abs_int(eX - tX)) / this.mDensity)) == 0))) {
                    temp.setPos(tX, tMinY - 1 + aRandom.nextInt(7), tZ);
                    GT_Block_GeneratedOres.setOreBlock(aWorld, temp, this.mSporadicMeta, false);
                }
            }
        }
        //watch.stop();
        //if (GT_Values.D1) {
        //    System.out.println("Generated Orevein: " + watch.getTime() + " ms " + this.mWorldGenName + " X: " + (aChunkX * 16) + " Z: " + (aChunkZ * 16));
        //}
        return true;
    }
}