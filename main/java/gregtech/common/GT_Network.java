package gregtech.common;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.internal.IGT_Mod;
import gregtech.api.net.GT_Packet;
import gregtech.api.net.GT_Packet_Block_Event;
import gregtech.api.net.GT_Packet_Sound;
import gregtech.api.net.GT_Packet_TileEntity;
import gregtech.api.net.IGT_NetworkHandler;
import gregtech.common.blocks.GT_Packet_Ores;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.Attribute;
import java.util.EnumMap;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

@ChannelHandler.Sharable
public class GT_Network
  extends MessageToMessageCodec<FMLProxyPacket, GT_Packet>
  implements IGT_NetworkHandler
{
  private final EnumMap<Side, FMLEmbeddedChannel> mChannel;
  private final GT_Packet[] mSubChannels;
  
  public GT_Network()
  {
    this.mChannel = NetworkRegistry.INSTANCE.newChannel("GregTech", new ChannelHandler[] { this, new HandlerShared() });
    this.mSubChannels = new GT_Packet[] { new GT_Packet_TileEntity(), new GT_Packet_Sound(), new GT_Packet_Block_Event(), new GT_Packet_Ores() };
  }
  
  protected void encode(ChannelHandlerContext aContext, GT_Packet aPacket, List<Object> aOutput)
    throws Exception
  {
    aOutput.add(new FMLProxyPacket(Unpooled.buffer().writeByte(aPacket.getPacketID()).writeBytes(aPacket.encode()).copy(), (String)aContext.channel().attr(NetworkRegistry.FML_CHANNEL).get()));
  }
  
  protected void decode(ChannelHandlerContext aContext, FMLProxyPacket aPacket, List<Object> aOutput)
    throws Exception
  {
    ByteArrayDataInput aData = ByteStreams.newDataInput(aPacket.payload().array());
    aOutput.add(this.mSubChannels[aData.readByte()].decode(aData));
  }
  
  public void sendToPlayer(GT_Packet aPacket, EntityPlayerMP aPlayer)
  {
    ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
    ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(aPlayer);
    ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).writeAndFlush(aPacket);
  }
  
  public void sendToAllAround(GT_Packet aPacket, NetworkRegistry.TargetPoint aPosition)
  {
    ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
    ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(aPosition);
    ((FMLEmbeddedChannel)this.mChannel.get(Side.SERVER)).writeAndFlush(aPacket);
  }
  
  public void sendToServer(GT_Packet aPacket)
  {
    ((FMLEmbeddedChannel)this.mChannel.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
    ((FMLEmbeddedChannel)this.mChannel.get(Side.CLIENT)).writeAndFlush(aPacket);
  }
  
  public void sendPacketToAllPlayersInRange(World aWorld, GT_Packet aPacket, int aX, int aZ)
  {
    if (!aWorld.isRemote) {
      for (Object tObject : aWorld.playerEntities)
      {
        if (!(tObject instanceof EntityPlayerMP)) {
          break;
        }
        EntityPlayerMP tPlayer = (EntityPlayerMP)tObject;
        Chunk tChunk = aWorld.getChunkFromBlockCoords(aX, aZ);
        if (tPlayer.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(tPlayer, tChunk.xPosition, tChunk.zPosition)) {
          sendToPlayer(aPacket, tPlayer);
        }
      }
    }
  }
  
  @ChannelHandler.Sharable
  static final class HandlerShared
    extends SimpleChannelInboundHandler<GT_Packet>
  {
    protected void channelRead0(ChannelHandlerContext ctx, GT_Packet aPacket)
      throws Exception
    {
      EntityPlayer aPlayer = GT_Values.GT.getThePlayer();
      aPacket.process(aPlayer == null ? null : GT_Values.GT.getThePlayer().worldObj);
    }
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_Network
 * JD-Core Version:    0.7.0.1
 */