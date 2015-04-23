/*   1:    */ package gregtech.common;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.profiler.Profiler;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ import net.minecraft.world.WorldProvider;
/*  12:    */ import net.minecraft.world.WorldSettings;
/*  13:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  14:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  15:    */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*  16:    */ import net.minecraft.world.storage.IPlayerFileData;
/*  17:    */ import net.minecraft.world.storage.ISaveHandler;
/*  18:    */ import net.minecraft.world.storage.WorldInfo;
/*  19:    */ 
/*  20:    */ public class GT_DummyWorld
/*  21:    */   extends World
/*  22:    */ {
/*  23: 25 */   public GT_IteratorRandom mRandom = new GT_IteratorRandom();
/*  24: 26 */   public ItemStack mLastSetBlock = null;
/*  25:    */   
/*  26:    */   public GT_DummyWorld(ISaveHandler par1iSaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, Profiler par5Profiler)
/*  27:    */   {
/*  28: 29 */     super(par1iSaveHandler, par2Str, par4WorldSettings, par3WorldProvider, par5Profiler);
/*  29: 30 */     this.rand = this.mRandom;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public GT_DummyWorld()
/*  33:    */   {
/*  34: 34 */     this(new ISaveHandler()
/*  35:    */     {
/*  36:    */       public void saveWorldInfoWithPlayer(WorldInfo var1, NBTTagCompound var2) {}
/*  37:    */       
/*  38:    */       public void saveWorldInfo(WorldInfo var1) {}
/*  39:    */       
/*  40:    */       public WorldInfo loadWorldInfo()
/*  41:    */       {
/*  42: 38 */         return null;
/*  43:    */       }
/*  44:    */       
/*  45:    */       public IPlayerFileData getSaveHandler()
/*  46:    */       {
/*  47: 39 */         return null;
/*  48:    */       }
/*  49:    */       
/*  50:    */       public File getMapFileFromName(String var1)
/*  51:    */       {
/*  52: 40 */         return null;
/*  53:    */       }
/*  54:    */       
/*  55:    */       public IChunkLoader getChunkLoader(WorldProvider var1)
/*  56:    */       {
/*  57: 41 */         return null;
/*  58:    */       }
/*  59:    */       
/*  60:    */       public void flush() {}
/*  61:    */       
/*  62:    */       public void checkSessionLock() {}
/*  63:    */       
/*  64:    */       public String getWorldDirectoryName()
/*  65:    */       {
/*  66: 44 */         return null;
/*  67:    */       }
/*  68:    */       
/*  69:    */       public File getWorldDirectory()
/*  70:    */       {
/*  71: 45 */         return null;
/*  72:    */       }
/*  73: 45 */     }, "DUMMY_DIMENSION", null,
//				new WorldProvider(),
///*  74:    */     
///*  75:    */ 
///*  76:    */ 
///*  77: 49 */       new WorldSettings(new WorldInfo(new NBTTagCompound()))
///*  78:    */       {
///*  79:    */         public String getDimensionName()
///*  80:    */         {
///*  81: 49 */           return "DUMMY_DIMENSION";
///*  82:    */         }
///*  83: 49 */       }
				new WorldSettings(new WorldInfo(new NBTTagCompound())), new Profiler());
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected IChunkProvider createChunkProvider()
/*  87:    */   {
/*  88: 58 */     return null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Entity getEntityByID(int aEntityID)
/*  92:    */   {
/*  93: 63 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean setBlock(int aX, int aY, int aZ, Block aBlock, int aMeta, int aFlags)
/*  97:    */   {
/*  98: 68 */     this.mLastSetBlock = new ItemStack(aBlock, 1, aMeta);
/*  99: 69 */     return true;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public float getSunBrightnessFactor(float p_72967_1_)
/* 103:    */   {
/* 104: 74 */     return 1.0F;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public BiomeGenBase getBiomeGenForCoords(int aX, int aZ)
/* 108:    */   {
/* 109: 79 */     if ((aX >= 16) && (aZ >= 16) && (aX < 32) && (aZ < 32)) {
/* 110: 79 */       return BiomeGenBase.plains;
/* 111:    */     }
/* 112: 80 */     return BiomeGenBase.ocean;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getFullBlockLightValue(int aX, int aY, int aZ)
/* 116:    */   {
/* 117: 85 */     return 10;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Block getBlock(int aX, int aY, int aZ)
/* 121:    */   {
/* 122: 90 */     if ((aX >= 16) && (aZ >= 16) && (aX < 32) && (aZ < 32)) {
/* 123: 90 */       return aY == 64 ? Blocks.grass : Blocks.air;
/* 124:    */     }
/* 125: 91 */     return Blocks.air;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getBlockMetadata(int aX, int aY, int aZ)
/* 129:    */   {
/* 130: 96 */     return 0;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean canBlockSeeTheSky(int aX, int aY, int aZ)
/* 134:    */   {
/* 135:101 */     if ((aX >= 16) && (aZ >= 16) && (aX < 32) && (aZ < 32)) {
/* 136:101 */       return aY > 64;
/* 137:    */     }
/* 138:102 */     return true;
/* 139:    */   }
/* 140:    */   
/* 141:    */   protected int func_152379_p()
/* 142:    */   {
/* 143:107 */     return 0;
/* 144:    */   }
/* 145:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_DummyWorld
 * JD-Core Version:    0.7.0.1
 */