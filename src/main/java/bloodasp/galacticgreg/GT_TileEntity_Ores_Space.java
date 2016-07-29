package bloodasp.galacticgreg;

import gregtech.api.GregTech_API;
import gregtech.common.blocks.GT_TileEntity_Ores;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GT_TileEntity_Ores_Space extends GT_TileEntity_Ores {

	public static boolean setOreBlock(World aWorld, int aX, int aY, int aZ, int aMetaData) {
		return setOreBlock(aWorld, aX, aY, aZ, aMetaData, false);
	}

	public static boolean setOreBlock(World aWorld, int aX, int aY, int aZ, int aMetaData, boolean air) {
		// GalacticGreg.tmp1++;
		// System.out.println("start gen ore"+aMetaData);
		if (!air) {
			aY = Math.min(aWorld.getActualHeight(), Math.max(aY, 1));
		}
		Block tBlock = aWorld.getBlock(aX, aY, aZ);
		int tMeta=0;
		tMeta = aWorld.getBlockMetadata(aX, aY, aZ);
		if ((aMetaData > 0) && ((tBlock != Blocks.air) || air)) {
			if (aMetaData < 1000) {
				if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.netherrack)) {
					aMetaData += 1000;
				} else if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.end_stone)) {
					aMetaData += 2000;
				} else if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, GregTech_API.sBlockGranites)) {
					if (tBlock == GregTech_API.sBlockGranites) {
						if (tMeta < 8) {
							aMetaData += 3000;
						} else {
							aMetaData += 4000;
						}
					} else {
						aMetaData += 3000;
					}
				} 
				else 
					if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, GCBlocks.blockMoon)) {
					if (tMeta != 4) {
						return false;
					}
				} else if (tBlock == MarsBlocks.marsBlock) {
						aMetaData += 4000;
					if (tMeta != 9) {
						// GalacticGreg.tmp3++;
						return false;
					} else {
					}
				} else if (!tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.stone)) {
					if (!air) {
						return false;
					}
				}
			}
//				System.out.println("test "+aMetaData);
//				System.out.println("block: "+tBlock.getUnlocalizedName()+" meta: "+aWorld.getBlockMetadata(aX, aY, aZ));
			// GalacticGreg.tmp2++;
			// System.out.println("set ore block");
			if(tMeta==4||tMeta==9||tBlock==Blocks.end_stone||tBlock== GregTech_API.sBlockGranites){
				//if(aMetaData>1000&&aMetaData<5000){System.out.println("aMeta: "+aMetaData);}
			aWorld.setBlock(aX, aY, aZ, GregTech_API.sBlockOres1, getHarvestData((short) aMetaData), 0);
			TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
			if ((tTileEntity instanceof GT_TileEntity_Ores)) {
				// GalacticGreg.tmp4++;
				((GT_TileEntity_Ores) tTileEntity).mMetaData = ((short) aMetaData);
				((GT_TileEntity_Ores) tTileEntity).mNatural = true;
			}
			return true;}
		}
		return false;
	}
}
