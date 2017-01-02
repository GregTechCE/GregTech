package gregtech.common;

import gregtech.GT_Mod;
import gregtech.api.objects.XSTR;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
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

	static List<ChunkPos> tList = null;
	static int loops = 1;
	static XSTR tRan = new XSTR();

	public static void onWorldTick(World aWorld, int aTick){
		if(!GT_Mod.gregtechproxy.mPollution)return;
		if(aTick == 0 || (tList==null && GT_Proxy.chunkData!=null)){
			tList = new ArrayList<>(GT_Proxy.chunkData.keySet());
			loops = (tList.size()/1200) + 1;
//			System.out.println("new Pollution loop"+aTick);
		}
		if(tList!=null && tList.size() > 0){
			int i = 0;
			for(; i < loops ; i++){
				if(tList.size()>0){
					ChunkPos tPos = tList.get(0);
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
								List<ChunkPos> tNeighbor = new ArrayList<>();
								tNeighbor.add(new ChunkPos(tPos.chunkXPos + 1, tPos.chunkZPos));
								tNeighbor.add(new ChunkPos(tPos.chunkXPos - 1, tPos.chunkZPos));
								tNeighbor.add(new ChunkPos(tPos.chunkXPos, tPos.chunkZPos + 1));
								tNeighbor.add(new ChunkPos(tPos.chunkXPos, tPos.chunkZPos - 1));
								for(ChunkPos tNPos : tNeighbor){
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
										GT_Utility.getUndergroundOil(aWorld,tNPos.chunkXPos <<4, tNPos.chunkZPos<<4);
									}
								}}
							int[] tArray = GT_Proxy.chunkData.get(tPos);
							tArray[1] = tPollution;
							GT_Proxy.chunkData.remove(tPos);
							GT_Proxy.chunkData.put(tPos, tArray);
							//Create Pollution effects
//				Smog filter TODO
							if(tPollution > GT_Mod.gregtechproxy.mPollutionSmogLimit){
								AxisAlignedBB chunk = new AxisAlignedBB(tPos.chunkXPos<<4, 0, tPos.chunkZPos<<4, (tPos.chunkXPos<<4)+16, 256, (tPos.chunkZPos<<4)+16);
								List<EntityLivingBase> tEntitys = aWorld.getEntitiesWithinAABB(EntityLivingBase.class, chunk);
								for(EntityLivingBase tEnt : tEntitys){
									if(!GT_Utility.isWearingFullGasHazmat(tEnt) && tRan.nextInt(tPollution/2000) > 40){
										int ran = tRan.nextInt(3);
										if(ran==0)tEnt.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS,  Math.min(tPollution/2500,1000), tPollution/400000));
										if(ran==1)tEnt.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE,  Math.min(tPollution/2500,1000), tPollution/400000));
										if(ran==2)tEnt.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,  Math.min(tPollution/2500,1000), tPollution/400000));
									}
								}
//				Poison effects
								if(tPollution > GT_Mod.gregtechproxy.mPollutionPoisonLimit){
									for(EntityLivingBase tEnt : tEntitys){
										if(!GT_Utility.isWearingFullGasHazmat(tEnt) && tRan.nextInt(tPollution/2000) > 20){
											int ran = tRan.nextInt(3);
											if(ran==0)tEnt.addPotionEffect(new PotionEffect(MobEffects.POISON, Math.min(tPollution/2500,1000), tPollution/500000));
											if(ran==1)tEnt.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, Math.min(tPollution/2500,1000), 1));
											if(ran==2)tEnt.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, Math.min(tPollution/2500,1000), 1));
                                        }
									}
//				killing plants
									if(tPollution > GT_Mod.gregtechproxy.mPollutionVegetationLimit){
										int f = 20;
										for(;f<(tPollution/25000);f++){
											int x =tPos.chunkXPos<<4+(tRan.nextInt(16));;
											int y =60 +(-f+tRan.nextInt(f*2+1));
											int z =tPos.chunkZPos<<4+(tRan.nextInt(16));
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
		if (tBlockState.getBlock() == Blocks.AIR || tBlockState.getBlock() == Blocks.STONE || tBlockState.getBlock() == Blocks.SAND|| tBlockState.getBlock() == Blocks.DEADBUSH)return;

		if (tBlockState.getBlock() == Blocks.LEAVES || tBlockState.getBlock() == Blocks.LEAVES2 || tBlockState.getMaterial() == Material.LEAVES)
			world.setBlockToAir(pos);
		if (tBlockState.getBlock() == Blocks.REEDS) {
			tBlockState.getBlock().dropBlockAsItem(world, pos, tBlockState, 0);
			world.setBlockToAir(pos);
		}
		if (tBlockState.getBlock() == Blocks.TALLGRASS)
			world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
        if(tBlockState.getBlock() == Blocks.DOUBLE_PLANT) {
            if(tBlockState.getValue(BlockDoublePlant.HALF) == BlockDoublePlant.EnumBlockHalf.LOWER) {
                world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
                world.setBlockToAir(pos.up());
            }
        }

		if (tBlockState.getBlock() == Blocks.VINE) {
			tBlockState.getBlock().dropBlockAsItem(world, pos, tBlockState, 0);
			world.setBlockToAir(pos);
		}
		if (tBlockState.getBlock() == Blocks.WATERLILY || tBlockState.getBlock() == Blocks.WHEAT || tBlockState.getBlock() == Blocks.CACTUS ||
				tBlockState.getMaterial() == Material.CACTUS || tBlockState.getBlock() == Blocks.MELON_BLOCK || tBlockState.getBlock() == Blocks.MELON_STEM) {
			tBlockState.getBlock().dropBlockAsItem(world, pos, tBlockState, 0);
			world.setBlockToAir(pos);
		}
		if (tBlockState.getBlock() == Blocks.RED_FLOWER || tBlockState.getBlock() == Blocks.YELLOW_FLOWER || tBlockState.getBlock() == Blocks.CARROTS ||
				tBlockState.getBlock() == Blocks.POTATOES || tBlockState.getBlock() == Blocks.PUMPKIN || tBlockState.getBlock() == Blocks.PUMPKIN_STEM) {
			tBlockState.getBlock().dropBlockAsItem(world, pos, tBlockState, 0);
			world.setBlockToAir(pos);
		}
		if (tBlockState.getBlock() == Blocks.SAPLING || tBlockState.getMaterial() == Material.PLANTS)
			world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
		if (tBlockState.getBlock() == Blocks.COCOA) {
			tBlockState.getBlock().dropBlockAsItem(world, pos, tBlockState, 0);
			world.setBlockToAir(pos);
		}
		if (tBlockState.getBlock() == Blocks.MOSSY_COBBLESTONE)
			world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
		if (tBlockState.getBlock() == Blocks.GRASS || tBlockState.getMaterial() == Material.GRASS)
			world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
		if(tBlockState.getBlock() == Blocks.FARMLAND || tBlockState.getBlock() == Blocks.DIRT){
			world.setBlockState(pos, Blocks.SAND.getDefaultState());
		}

		if(sourRain && world.isRaining() && (tBlockState.getBlock() == Blocks.STONE || tBlockState.getBlock() == Blocks.GRAVEL || tBlockState.getBlock() == Blocks.COBBLESTONE) &&
				world.getBlockState(pos.add(0, 1, 0)) == Blocks.AIR && world.canBlockSeeSky(pos)){
			if(tBlockState.getBlock() == Blocks.STONE){world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());	}
			else if(tBlockState.getBlock() == Blocks.COBBLESTONE){world.setBlockState(pos, Blocks.GRAVEL.getDefaultState());	}
			else if(tBlockState.getBlock() == Blocks.GRAVEL){world.setBlockState(pos, Blocks.SAND.getDefaultState());	}
		}
	}

	public static void addPollution(BlockPos aPos, int aPollution){
		if(!GT_Mod.gregtechproxy.mPollution)return;
		try{
			int[] tData = new int[2];
            ChunkPos chunkPos = new ChunkPos(aPos.getX() / 16, aPos.getZ() / 16);
			if(GT_Proxy.chunkData.containsKey(chunkPos)){
				tData = GT_Proxy.chunkData.get(chunkPos);
				if(tData.length>1){
					tData[1] += aPollution;
				}
			}else{
				tData[1] += aPollution;
				GT_Proxy.chunkData.put(chunkPos, tData);
			}
		}catch(Exception e){
		}
	}
}