package gregtech.common;

import net.minecraft.world.ChunkPosition;

public class GT_Pollution {
	

//	List<ChunkPosition> list = new ArrayList<ChunkPosition>(chunkData.keySet());

	public static void onWorldTick(int aTick){
		
	}
	
	public static void addPollution(ChunkPosition aPos, int aPollution){
		int[] tData = new int[2];
		if(GT_Proxy.chunkData.containsKey(aPos)){
			tData = GT_Proxy.chunkData.get(aPos);
			if(tData.length>1){
				tData[1] += aPollution;
			}
			GT_Proxy.chunkData.replace(aPos, tData);
		}else{
			tData[1] += aPollution;
			GT_Proxy.chunkData.put(aPos, tData);
		}
		
	}
}
