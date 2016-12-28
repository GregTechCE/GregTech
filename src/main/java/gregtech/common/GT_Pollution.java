package gregtech.common;

import gregtech.GT_Mod;
import gregtech.api.objects.XSTR;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
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
	/**
	 * Pollution dispersion until effects start:
	 * Calculation: ((Limit * 0.01) + 2000) * (4 <- spreading rate)
	 *
	 * SMOG(500k) 466.7 pollution/sec
	 * Poison(750k) 633,3 pollution/sec
	 * Dying Plants(1mio) 800 pollution/sec
	 * Sour Rain(1.5mio) 1133.3 pollution/sec
	 *
	 * Pollution producers (pollution/sec)
	 * Bronze Boiler(20)
	 * Lava Boiler(20)
	 * High Pressure Boiler(20)
	 * Bronze Blast Furnace(50)
	 * Diesel Generator(14/28/75)
	 * Gas Turbine(7/14/37)
	 * Charcoal Pile(100)
	 *
	 * Large Diesel Generator(300)
	 * Electric Blast Furnace(100)
	 * Implosion Compressor(2000)
	 * Large Boiler(240)
	 * Large Gas Turbine(160)
	 * Multi Smelter(100)
	 * Pyrolyse Oven(400)
	 *
	 * Machine Explosion(100,000)
	 *
	 * Muffler Hatch Pollution reduction:
	 * LV (0%), MV (30%), HV (52%), EV (66%), IV (76%), LuV (84%), ZPM (89%), UV (92%), MAX (95%)
	 */

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
										if(tNPol<tPollution && tNPol*12 < tPollution*10){
											int tDiff = tPollution - tNPol;
											tDiff = tDiff/10;
											tNPol += tDiff;
											tPollution -= tDiff;
											GT_Proxy.chunkData.get(tNPos)[1] = tNPol;
										}
									}else{
										GT_Utility.getUndergroundOil(aWorld,tNPos.getX()<<4,tNPos.getZ()<<4);
									}
								}}
							int[] tArray = GT_Proxy.chunkData.get(tPos);
							tArray[1] = tPollution;
							GT_Proxy.chunkData.remove(tPos);
							GT_Proxy.chunkData.put(tPos, tArray);
							//Create Pollution effects
//				Smog filter TODO
							if(tPollution > GT_Mod.gregtechproxy.mPollutionSmogLimit){
								AxisAlignedBB chunk = new AxisAlignedBB(tPos.getX()<<4, 0, tPos.getZ()<<4, (tPos.getX()<<4)+16, 256, (tPos.getZ()<<4)+16);
								List<EntityLivingBase> tEntitys = aWorld.getEntitiesWithinAABB(EntityLivingBase.class, chunk);
								for(EntityLivingBase tEnt : tEntitys){
									if(!GT_Utility.isWearingFullGasHazmat(tEnt) && tRan.nextInt(tPollution/2000) > 40){
										int ran = tRan.nextInt(3);
										if(ran==0)tEnt.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("weakness"),  Math.min(tPollution/2500,1000), tPollution/400000));
										if(ran==1)tEnt.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("digSlowdown"),  Math.min(tPollution/2500,1000), tPollution/400000));
										if(ran==2)tEnt.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("moveSlowdown"),  Math.min(tPollution/2500,1000), tPollution/400000));
									}
								}
//				Poison effects
								if(tPollution > GT_Mod.gregtechproxy.mPollutionPoisonLimit){
									for(EntityLivingBase tEnt : tEntitys){
										if(!GT_Utility.isWearingFullGasHazmat(tEnt) && tRan.nextInt(tPollution/2000) > 20){
											int ran = tRan.nextInt(3);
											if(ran==0)tEnt.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("poison"), Math.min(tPollution/2500,1000), tPollution/500000));
											if(ran==1)tEnt.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("confusion"), Math.min(tPollution/2500,1000), 1));
											if(ran==2)tEnt.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("blindness"), Math.min(tPollution/2500,1000), 1));
										}
									}
//				killing plants
									if(tPollution > GT_Mod.gregtechproxy.mPollutionVegetationLimit){
										int f = 20;
										for(;f<(tPollution/25000);f++){
											int x =tPos.getX()<<4+(tRan.nextInt(16));;
											int y =60 +(-f+tRan.nextInt(f*2+1));
											int z =tPos.getZ()<<4+(tRan.nextInt(16));
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
		if (tBlockState == Blocks.AIR || tBlockState == Blocks.STONE || tBlockState == Blocks.SAND|| tBlockState == Blocks.DEADBUSH)return;

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
			if(tBlockState == Blocks.STONE){world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());	}
			else if(tBlockState == Blocks.COBBLESTONE){world.setBlockState(pos, Blocks.GRAVEL.getDefaultState());	}
			else if(tBlockState == Blocks.GRAVEL){world.setBlockState(pos, Blocks.SAND.getDefaultState());	}
		}
	}

	public static void addPollution(BlockPos aPos, int aPollution){
		if(!GT_Mod.gregtechproxy.mPollution)return;
		try{
			//MURA: Poointless with BlockPos? ChunkPosition tPos = new ChunkPosition(aPos.chunkPosX>>4, 1, aPos.chunkPosZ>>4);
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