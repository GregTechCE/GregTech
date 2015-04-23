/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import com.google.common.io.ByteArrayDataInput;
/*  4:   */ import com.google.common.io.ByteArrayDataOutput;
/*  5:   */ import com.google.common.io.ByteStreams;
/*  6:   */ import gregtech.api.net.GT_Packet;
/*  7:   */ import net.minecraft.tileentity.TileEntity;
/*  8:   */ import net.minecraft.world.IBlockAccess;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class GT_Packet_Ores
/* 12:   */   extends GT_Packet
/* 13:   */ {
/* 14:   */   private int mX;
/* 15:   */   private int mZ;
/* 16:   */   private short mY;
/* 17:   */   private short mMetaData;
/* 18:   */   
/* 19:   */   public GT_Packet_Ores()
/* 20:   */   {
/* 21:17 */     super(true);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public GT_Packet_Ores(int aX, short aY, int aZ, short aMetaData)
/* 25:   */   {
/* 26:21 */     super(false);
/* 27:22 */     this.mX = aX;
/* 28:23 */     this.mY = aY;
/* 29:24 */     this.mZ = aZ;
/* 30:25 */     this.mMetaData = aMetaData;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public byte[] encode()
/* 34:   */   {
/* 35:30 */     ByteArrayDataOutput tOut = ByteStreams.newDataOutput(12);
/* 36:   */     
/* 37:32 */     tOut.writeInt(this.mX);
/* 38:33 */     tOut.writeShort(this.mY);
/* 39:34 */     tOut.writeInt(this.mZ);
/* 40:35 */     tOut.writeShort(this.mMetaData);
/* 41:   */     
/* 42:37 */     return tOut.toByteArray();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public GT_Packet decode(ByteArrayDataInput aData)
/* 46:   */   {
/* 47:42 */     return new GT_Packet_Ores(aData.readInt(), aData.readShort(), aData.readInt(), aData.readShort());
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void process(IBlockAccess aWorld)
/* 51:   */   {
/* 52:47 */     if (aWorld != null)
/* 53:   */     {
/* 54:48 */       TileEntity tTileEntity = aWorld.getTileEntity(this.mX, this.mY, this.mZ);
/* 55:49 */       if ((tTileEntity instanceof GT_TileEntity_Ores)) {
/* 56:49 */         ((GT_TileEntity_Ores)tTileEntity).mMetaData = this.mMetaData;
/* 57:   */       }
/* 58:50 */       if (((aWorld instanceof World)) && (((World)aWorld).isRemote)) {
/* 59:50 */         ((World)aWorld).markBlockForUpdate(this.mX, this.mY, this.mZ);
/* 60:   */       }
/* 61:   */     }
/* 62:   */   }
/* 63:   */   
/* 64:   */   public byte getPacketID()
/* 65:   */   {
/* 66:56 */     return 3;
/* 67:   */   }
/* 68:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Packet_Ores
 * JD-Core Version:    0.7.0.1
 */