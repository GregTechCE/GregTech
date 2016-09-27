	package gregtech.common;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.util.GT_Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.WorldManager;

public class GT_Pollution {
	

	static List<ChunkPosition> tList = null;
	static int loops = 1;

	public static void onWorldTick(int aTick){
		if(aTick == 0 || (tList==null && GT_Proxy.chunkData!=null)){
			tList = new ArrayList<ChunkPosition>(GT_Proxy.chunkData.keySet());
			loops = (tList.size()/1200) + 1;
		}
		if(tList!=null && tList.size() > 0){
			int i = 0;
			for(; i < loops ; i++){
				ChunkPosition tPos = tList.get(0);
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
				List<ChunkPosition> tNeighbor = new ArrayList();
				tNeighbor.add(new ChunkPosition(tPos.chunkPosX+1, 1, tPos.chunkPosZ+1));
				tNeighbor.add(new ChunkPosition(tPos.chunkPosX+1, 1, tPos.chunkPosZ-1));
				tNeighbor.add(new ChunkPosition(tPos.chunkPosX-1, 1, tPos.chunkPosZ+1));
				tNeighbor.add(new ChunkPosition(tPos.chunkPosX-1, 1, tPos.chunkPosZ-1));
				for(ChunkPosition tNPos : tNeighbor){
					if(GT_Proxy.chunkData.containsKey(tNPos)){
						int tNPol = GT_Proxy.chunkData.get(tNPos)[1];
						if(tNPol<tPollution){
							int tDiff = tPollution - tNPol;
							tDiff = tDiff/10;
							tNPol += tDiff;
							tPollution -= tDiff;
							GT_Proxy.chunkData.get(tNPos)[1] = tNPol;
						}
					}else{
						GT_Utility.getUndergroundOil(Minecraft.getMinecraft().theWorld,tPos.chunkPosX*16,tPos.chunkPosZ*16);
					}
				}}
				int[] tArray = GT_Proxy.chunkData.get(tPos);
				tArray[1] = tPollution;
				GT_Proxy.chunkData.remove(tPos);
				GT_Proxy.chunkData.put(tPos, tArray);
				//Create Pollution effects
					
					}
				}
			}
		}
	}
	
	public static void addPollution(ChunkPosition aPos, int aPollution){
		try{
		ChunkPosition tPos = new ChunkPosition(aPos.chunkPosX/16, 1, aPos.chunkPosZ/16);
//		System.out.println("add pollution x: "+ tPos.chunkPosX +" z: " + tPos.chunkPosZ +" poll: "+aPollution);
		int[] tData = new int[2];
		if(GT_Proxy.chunkData.containsKey(tPos)){
			tData = GT_Proxy.chunkData.get(tPos);
			if(tData.length>1){
				tData[1] += aPollution;
			}
		}else{
			tData[1] += aPollution;
			GT_Proxy.chunkData.put(tPos, tData);
		}
		}catch(Exception e){
			
		}
	}
}
