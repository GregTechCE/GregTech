/*  1:   */ package gregtech.common;
/*  2:   */ 
/*  3:   */ import com.google.common.io.ByteArrayDataInput;
/*  4:   */ import com.google.common.io.ByteStreams;
/*  5:   */ import cpw.mods.fml.common.network.FMLEmbeddedChannel;
/*  6:   */ import cpw.mods.fml.common.network.FMLOutboundHandler;
/*  7:   */ import cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget;
/*  8:   */ import cpw.mods.fml.common.network.NetworkRegistry;
/*  9:   */ import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
/* 10:   */ import cpw.mods.fml.common.network.internal.FMLProxyPacket;
/* 11:   */ import cpw.mods.fml.relauncher.Side;
/* 12:   */ import gregtech.api.enums.GT_Values;
/* 13:   */ import gregtech.api.interfaces.internal.IGT_Mod;
/* 14:   */ import gregtech.api.net.GT_Packet;
/* 15:   */ import gregtech.api.net.GT_Packet_Block_Event;
/* 16:   */ import gregtech.api.net.GT_Packet_Sound;
/* 17:   */ import gregtech.api.net.GT_Packet_TileEntity;
/* 18:   */ import gregtech.api.net.IGT_NetworkHandler;
/* 19:   */ import gregtech.common.blocks.GT_Packet_Ores;
/* 20:   */ import io.netty.buffer.ByteBuf;
/* 21:   */ import io.netty.buffer.Unpooled;
/* 22:   */ import io.netty.channel.Channel;
/* 23:   */ import io.netty.channel.ChannelHandler;
/* 24:   */ import io.netty.channel.ChannelHandler.Sharable;
/* 25:   */ import io.netty.channel.ChannelHandlerContext;
/* 26:   */ import io.netty.channel.SimpleChannelInboundHandler;
/* 27:   */ import io.netty.handler.codec.MessageToMessageCodec;
/* 28:   */ import io.netty.util.Attribute;
/* 29:   */ import java.util.EnumMap;
/* 30:   */ import java.util.List;
/* 31:   */ import net.minecraft.entity.player.EntityPlayer;
/* 32:   */ import net.minecraft.entity.player.EntityPlayerMP;
/* 33:   */ import net.minecraft.server.management.PlayerManager;
/* 34:   */ import net.minecraft.world.World;
/* 35:   */ import net.minecraft.world.WorldServer;
/* 36:   */ import net.minecraft.world.chunk.Chunk;
/* 37:   */ 
/* 38:   */ @ChannelHandler.Sharable
/* 39:   */ public class GT_Network
/* 40:   */   extends MessageToMessageCodec<FMLProxyPacket, GT_Packet>
/* 41:   */   implements IGT_NetworkHandler
/* 42:   */ {
/* 43:   */   private final EnumMap<Side, FMLEmbeddedChannel> mChannel;
/* 44:   */   private final GT_Packet[] mSubChannels;
/* 45:   */   
/* 46:   */   public GT_Network()
/* 47:   */   {
/* 48:32 */     this.mChannel = NetworkRegistry.INSTANCE.newChannel("GregTech", new ChannelHandler[] { this, new HandlerShared() });
/* 49:33 */     this.mSubChannels = new GT_Packet[] { new GT_Packet_TileEntity(), new GT_Packet_Sound(), new GT_Packet_Block_Event(), new GT_Packet_Ores() };
/* 50:   */   }
/* 51:   */   
/* 52:   */   protected void encode(ChannelHandlerContext aContext, GT_Packet aPacket, List<Object> aOutput)
/* 53:   */     throws Exception
/* 54:   */   {
/* 55:37 */     aOutput.add(new FMLProxyPacket(Unpooled.buffer().writeByte(aPacket.getPacketID()).writeBytes(aPacket.encode()).copy(), (String)aContext.channel().attr(NetworkRegistry.FML_CHANNEL).get()));
/* 56:   */   }
/* 57:   */   
/* 58:   */   protected void decode(ChannelHandlerContext aContext, FMLProxyPacket aPacket, List<Object> aOutput)
/* 59:   */     throws Exception
/* 60:   */   {
/* 61:42 */     ByteArrayDataInput aData = ByteStreams.newDataInput(aPacket.payload().array());
/* 62:43 */     aOutput.add(this.mSubChannels[aData.readByte()].decode(aData));
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void sendToPlayer(GT_Packet aPacket, EntityPlayerMP aPlayer)
/* 66:   */   {
/* 67:48 */     ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
/* 68:49 */     ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(aPlayer);
/* 69:50 */     ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).writeAndFlush(aPacket);
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void sendToAllAround(GT_Packet aPacket, NetworkRegistry.TargetPoint aPosition)
/* 73:   */   {
/* 74:55 */     ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
/* 75:56 */     ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(aPosition);
/* 76:57 */     ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).writeAndFlush(aPacket);
/* 77:   */   }
/* 78:   */   
/* 79:   */   public void sendToServer(GT_Packet aPacket)
/* 80:   */   {
/* 81:62 */     ((FMLEmbeddedChannel)this.mChannel.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
/* 82:63 */     ((FMLEmbeddedChannel)this.mChannel.get(Side.CLIENT)).writeAndFlush(aPacket);
/* 83:   */   }
/* 84:   */   
/* 85:   */   public void sendPacketToAllPlayersInRange(World aWorld, GT_Packet aPacket, int aX, int aZ)
/* 86:   */   {
/* 87:68 */     if (!aWorld.isRemote) {
/* 88:68 */       for (Object tObject : aWorld.playerEntities)
/* 89:   */       {
/* 90:69 */         if (!(tObject instanceof EntityPlayerMP)) {
/* 91:   */           break;
/* 92:   */         }
/* 93:70 */         EntityPlayerMP tPlayer = (EntityPlayerMP)tObject;
/* 94:71 */         Chunk tChunk = aWorld.getChunkFromBlockCoords(aX, aZ);
/* 95:72 */         if (tPlayer.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(tPlayer, tChunk.xPosition, tChunk.zPosition)) {
/* 96:74 */           sendToPlayer(aPacket, tPlayer);
/* 97:   */         }
/* 98:   */       }
/* 99:   */     }
/* :0:   */   }
/* :1:   */   
/* :2:   */   @ChannelHandler.Sharable
/* :3:   */   static final class HandlerShared
/* :4:   */     extends SimpleChannelInboundHandler<GT_Packet>
/* :5:   */   {
/* :6:   */     protected void channelRead0(ChannelHandlerContext ctx, GT_Packet aPacket)
/* :7:   */       throws Exception
/* :8:   */     {
/* :9:86 */       EntityPlayer aPlayer = GT_Values.GT.getThePlayer();
/* ;0:87 */       aPacket.process(aPlayer == null ? null : GT_Values.GT.getThePlayer().worldObj);
/* ;1:   */     }
/* ;2:   */   }
/* ;3:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_Network
 * JD-Core Version:    0.7.0.1
 */