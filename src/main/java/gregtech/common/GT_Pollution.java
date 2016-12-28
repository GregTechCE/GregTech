package gregtech.common;

import gregtech.GT_Mod;
import gregtech.api.objects.XSTR;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.List;

public class GT_Pollution {


	static List<BlockPos> tList = null;
	static int loops = 1;
	static XSTR tRan = new XSTR();

	public static void onWorldTick(World aWorld, int aTick){
		if(!GT_Mod.gregtechproxy.mPollution)return;
		if(aTick == 0 || (tList==null && GT_Proxy.chunkData!=null)){
			tList = new ArrayList<BlockPos>(GT_Proxy.chunkData.keySet());
			loops = (tList.size()/1200) + 1;
//			System.out.println("new Pollution loop"+aTick);
		}
		if(tList!=null && tList.size() > 0){
			int i = 0;
			for(; i < loops ; i++){
				if(tList.size()>0){
					BlockPos tPos = tList.get(0);
					tList.remove(0);
					if(tPos!=null && GT_Proxy.chunkData.containsKey(tPos)){
						int tPollution = GT_Proxy.chunkData.get(tPos)[1];
//				System.out.println("process: "+tPos.chunkPosX+" "+tPos.chunkPosZ+" "+tPollution);
						//Reduce pollution in chunk
						tPollution = (int)(0.99f*tPollution);
						tPollution -= 2000;
						if(tPollution<=0){tPollution = 0;}else{
							//Spread Pollution
							if(tPollution>50000){
								List<BlockPos> tNeighbor = new ArrayList();
								tNeighbor.add(new BlockPos(tPos.getX()+1, 1, tPos.getZ()));
								tNeighbor.add(new BlockPos(tPos.getX()-1, 1, tPos.getZ()));
								tNeighbor.add(new BlockPos(tPos.getX(), 1, tPos.getZ()+1));
								tNeighbor.add(new BlockPos(tPos.getX(), 1, tPos.getZ()-1));
								for(BlockPos tNPos : tNeighbor){
									if(GT_Proxy.chunkData.containsKey(tNPos)){
										int tNPol = GT_Proxy.chunkData.get(tNPos)[1];
										if(tNPol<tPollution && tNPol*120 < tPollution*100){
											int tDiff = tPollution - tNPol;
											tDiff = tDiff/10;
											tNPol += tDiff;
											tPollution -= tDiff;
											GT_Proxy.chunkData.get(tNPos)[1] = tNPol;
										}
									}else{
										GT_Utility.getUndergroundOil(aWorld,tPos.getX()*16,tPos.getZ()*16);
									}
								}}
							int[] tArray = GT_Proxy.chunkData.get(tPos);
							tArray[1] = tPollution;
							GT_Proxy.chunkData.remove(tPos);
							GT_Proxy.chunkData.put(tPos, tArray);
							//Create Pollution effects
//				Smog filter TODO
							if(tPollution > GT_Mod.gregtechproxy.mPollutionSmogLimit){

//				Poison effects
								if(tPollution > GT_Mod.gregtechproxy.mPollutionPoisonLimit){
									AxisAlignedBB chunk = new AxisAlignedBB(tPos.getX()*16, 0, tPos.getZ()*16, tPos.getX()*16+16, 256, tPos.getZ()*16+16);
									List<EntityLiving> tEntitys = aWorld.getEntitiesWithinAABB(EntityLiving.class, chunk);
									for(EntityLiving tEnt : tEntitys){
										if(tRan.nextInt(tPollution/25000) > 20){
											tEnt.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("poison"), tPollution/25000, 1));
										}
									}
//				killing plants
									if(tPollution > GT_Mod.gregtechproxy.mPollutionVegetationLimit){
										int f = 20;
										for(;f<(tPollution/25000);f++){
											int x =tPos.getX()*16+(tRan.nextInt(16));;
											int y =60 +(-f+tRan.nextInt(f*2+1));
											int z =tPos.getZ()*16+(tRan.nextInt(16));
											damageBlock(new BlockPos(x, y, z), tPollution > GT_Mod.gregtechproxy.mPollutionSourRainLimit);
										}}}}
						}
					}
				}
			}}
	}

	public static void damageBlock(BlockPos pos, boolean sourRain){
		World world = DimensionManager.getWorld(0);
		if (world.isRemote)	return;
		IBlockState tBlockState = world.getBlockState(pos);
		//int tMeta = world.getBlockMetadata(x, y, z);
		if (tBlockState == Blocks.AIR || tBlockState == Blocks.STONE || tBlockState == Blocks.SAND || tBlockState == Blocks.DEADBUSH)return;

		if (tBlockState == Blocks.LEAVES || tBlockState == Blocks.LEAVES2 || tBlockState.getMaterial() == Material.LEAVES)
			world.setBlockToAir(pos);
		if (tBlockState == Blocks.REEDS) {
			tBlockState.getBlock().dropBlockAsItem(world, pos, tBlockState, 0);
			world.setBlockToAir(pos);
		}
		if (tBlockState == Blocks.TALLGRASS)
			world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
		if (tBlockState == Blocks.VINE) {
			tBlockState.getBlock().dropBlockAsItem(world, pos, tBlockState, 0);
			world.setBlockToAir(pos);
		}
		if (tBlockState == Blocks.WATERLILY || tBlockState == Blocks.WHEAT || tBlockState == Blocks.CACTUS ||
				tBlockState.getMaterial() == Material.CACTUS || tBlockState == Blocks.MELON_BLOCK || tBlockState == Blocks.MELON_STEM) {
			tBlockState.getBlock().dropBlockAsItem(world, pos, tBlockState, 0);
			world.setBlockToAir(pos);
		}
		if (tBlockState == Blocks.RED_FLOWER || tBlockState == Blocks.YELLOW_FLOWER || tBlockState == Blocks.CARROTS ||
				tBlockState == Blocks.POTATOES || tBlockState == Blocks.PUMPKIN || tBlockState == Blocks.PUMPKIN_STEM) {
			tBlockState.getBlock().dropBlockAsItem(world, pos, tBlockState, 0);
			world.setBlockToAir(pos);
		}
		if (tBlockState == Blocks.SAPLING || tBlockState.getMaterial() == Material.PLANTS)
			world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
		if (tBlockState == Blocks.COCOA) {
			tBlockState.getBlock().dropBlockAsItem(world, pos, tBlockState, 0);
			world.setBlockToAir(pos);
		}
		if (tBlockState == Blocks.MOSSY_COBBLESTONE)
			world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
		if (tBlockState == Blocks.GRASS || tBlockState.getMaterial() == Material.GRASS )
			world.setBlockState(pos, Blocks.DIRT.getDefaultState());
		if(tBlockState == Blocks.FARMLAND || tBlockState == Blocks.DIRT){
			world.setBlockState(pos, Blocks.SAND.getDefaultState());
		}

		if(sourRain && world.isRaining() && (tBlockState == Blocks.STONE || tBlockState == Blocks.GRAVEL || tBlockState == Blocks.COBBLESTONE) &&
				world.getBlockState(pos.add(0, 1, 0)) == Blocks.AIR && world.canBlockSeeSky(pos)){
			if(tBlockState == Blocks.STONE) world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
			else if(tBlockState == Blocks.COBBLESTONE) world.setBlockState(pos, Blocks.GRAVEL.getDefaultState());
			else if(tBlockState == Blocks.GRAVEL) world.setBlockState(pos, Blocks.SAND.getDefaultState());
		}
	}

	public static void addPollution(BlockPos aPos, int aPollution){
		if(!GT_Mod.gregtechproxy.mPollution)return;
		try{
			//MURA: Unneeded in with BlockPos? ChunkPosition tPos = new ChunkPosition(aPos.chunkPosX/16, 1, aPos.chunkPosZ/16);
			//		System.out.println("add pollution x: "+ tPos.chunkPosX +" z: " + tPos.chunkPosZ +" poll: "+aPollution);
			int[] tData = new int[2];
			if(GT_Proxy.chunkData.containsKey(aPos)){
				tData = GT_Proxy.chunkData.get(aPos);
				if(tData.length>1){
					tData[1] += aPollution;
				}
			}else{
				tData[1] += aPollution;
				GT_Proxy.chunkData.put(aPos, tData);
			}
		}catch(Exception e){

		}
	}
}