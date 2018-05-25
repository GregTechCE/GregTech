package gregtech.common.multipart

import java.lang.{Iterable => JIterable}

import codechicken.lib.data.{MCDataInput, MCDataOutput}
import codechicken.lib.raytracer.{CuboidRayTraceResult, IndexedCuboid6}
import codechicken.lib.render.{BlockRenderer, CCRenderState}
import codechicken.lib.vec.uv.IconTransformation
import codechicken.lib.vec.{Cuboid6, Translation, Vector3}
import codechicken.multipart._
import codechicken.multipart.minecraft.McBlockPart
import gregtech.api.capability.IEnergyContainer
import gregtech.api.unification.material.`type`.Material
import gregtech.common.blocks.MetaBlocks
import gregtech.common.cable.net.{EnergyNet, WorldENet}
import gregtech.common.cable.tile.{CableEnergyContainer, TileEntityCable}
import gregtech.common.cable.{BlockCable, ICableTile, Insulation, WireProperties}
import gregtech.common.render.CableRenderer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.{BlockRenderLayer, EnumFacing, EnumHand, ResourceLocation}
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.{Capability, ICapabilityProvider}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

class CableMultiPart private[gregtech]() extends TMultiPart with TNormalOcclusionPart with TPartialOcclusionPart with ICableTile with ICapabilityProvider {

    private var cableBlock: BlockCable = _
    private var insulation: Insulation = _
    private var insulationColor: Int = TileEntityCable.DEFAULT_INSULATION_COLOR
    private var blockedConnections: Int = 0 //bitmask of blocked connections
    private var activeConnections: Int = 0 //cache of active connections (taking blocked connections into account)

    private var centerBox: Cuboid6 = _
    private var sidedConnections: ListBuffer[Cuboid6] = ListBuffer()
    private lazy val energyContainer: CableEnergyContainer = new CableEnergyContainer(this)

    def this(blockState: IBlockState, tile: TileEntityCable) {
        this()
        cableBlock = blockState.getBlock.asInstanceOf[BlockCable]
        insulation = blockState.getValue(BlockCable.INSULATION)
        insulationColor = tile.getInsulationColor
        reinitializeShape()
    }

    override def getCableWorld: World = tile.getWorld
    override def getCablePos: BlockPos = tile.getPos
    override def getBlockedConnections: Int = blockedConnections
    override def getInsulationColor: Int = insulationColor
    override def getInsulation: Insulation = insulation
    override def getWireProperties: WireProperties = cableBlock.getProperties(insulation)

    override def getDrops: JIterable[ItemStack] = Seq(getDropStack)
    override def pickItem(hit: CuboidRayTraceResult): ItemStack = getDropStack
    private def getDropStack: ItemStack = cableBlock.getItem(insulation)


    override def hasCapability(capability: Capability[_], facing: EnumFacing): Boolean = capability match {
        case IEnergyContainer.CAPABILITY_ENERGY_CONTAINER => true
        case _ => false
    }

    override def getCapability[T](capability: Capability[T], facing: EnumFacing): T = capability match {
        case IEnergyContainer.CAPABILITY_ENERGY_CONTAINER => energyContainer.asInstanceOf[T]
        case _ => null.asInstanceOf[T]
    }

    private def reinitializeShape(): Unit = {
        centerBox = BlockCable.getSideBox(null, insulation.thickness)
        updateSidedConnections(false)
    }

    private def updateSidedConnections(notify: Boolean = true): Unit = {
        val thickness = insulation.thickness
        sidedConnections.clear()
        for(connection <- EnumFacing.VALUES) {
            if((activeConnections & (1 << connection.getIndex)) > 0 &&
                (blockedConnections & (1 << connection.getIndex)) == 0) {
                sidedConnections += BlockCable.getSideBox(connection, thickness)
            }
        }
        if(tile != null && notify) {
            tile.notifyPartChange(this)
            tile.markRender()
        }
    }

    private def updateBlockedConnections(): Unit = {
        if(tile == null) return
        val thickness = insulation.thickness
        val lastBlockedConnections = blockedConnections
        blockedConnections = 0
        for(connection <- EnumFacing.VALUES) {
            val sideBox = BlockCable.getSideBox(connection, thickness)
            if(!tile.canReplacePart(this, new NormallyOccludedPart(Seq(sideBox)))) {
                blockedConnections |= (1 << connection.getIndex)
            }
        }
        if(lastBlockedConnections != blockedConnections) {
            BlockCable.updateCableConnections(this, tile.getWorld, tile.getPos)
            updateActualConnections()
        }
    }

    private def updateActualConnections(): Unit = {
        val lastActualConnections = activeConnections
        if(tile != null && !tile.isInvalid)
            activeConnections = BlockCable.getActualConnections(this, tile.getWorld, tile.getPos)
        if(lastActualConnections != activeConnections)
            updateSidedConnections()
    }

    override def onAdded(): Unit = {
        updateBlockedConnections()
        updateActualConnections()
        BlockCable.attachNoNearbyNetwork(tile.getWorld, tile.getPos, this)
    }


    override def activate(player: EntityPlayer, hit: CuboidRayTraceResult, item: ItemStack, hand: EnumHand): Boolean = {
        val worldENet = WorldENet.getWorldENet(tile.getWorld)
        val energyNet = worldENet.getNetFromPos(pos)
        player.sendMessage(new TextComponentString("Energy net: " + energyNet))
        player.sendMessage(new TextComponentString("All nodes: " + energyNet.getAllNodes.keySet))
        player.sendMessage(new TextComponentString("Active nodes: " + energyNet.getActiveNodes))
        player.sendMessage(new TextComponentString("Last update: " + energyNet.getLastUpdatedTime))
        true
    }

    override def onRemoved(): Unit = {
        BlockCable.detachFromNetwork(tile.getWorld, tile.getPos)
    }

    override def onPartChanged(part: TMultiPart): Unit = {
        if(part != this) {
            //fire reaction not immediately, but after 1 tick when part is finally added or removed
            scheduleTick(1)
        }
    }

    override def scheduledTick(): Unit = {
        updateBlockedConnections()
    }

    override def onNeighborChanged(): Unit = {
        updateActualConnections()
        //also notify client about this to update connections map
        getWriteStream.writeByte(1)
        scheduleTick(1)
    }

    //override to allow updateActualConnections clientside
    override def read(packet: MCDataInput): Unit = {
        val packetType = packet.readByte()
        if(packetType == 0) {
            readDesc(packet)
        } else if(packetType == 1) {
            updateActualConnections()
        }
        tile.markRender()
    }

    override def sendDescUpdate(): Unit = {
        getWriteStream.writeByte(0)
        writeDesc(getWriteStream)
    }

    override def save(tag: NBTTagCompound): Unit = {
        tag.setString("CableMaterial", cableBlock.material.toString)
        tag.setInteger("Insulation", insulation.ordinal())
        tag.setInteger("InsulationColor", insulationColor)
        tag.setInteger("ActiveConnections", activeConnections)
        tag.setInteger("BlockedConnections", blockedConnections)
    }

    override def load(tag: NBTTagCompound): Unit = {
        val materialName = tag.getString("CableMaterial")
        cableBlock = MetaBlocks.CABLES.get(Material.MATERIAL_REGISTRY.getObject(materialName))
        insulation = Insulation.values()(tag.getInteger("Insulation"))
        insulationColor = tag.getInteger("InsulationColor")
        activeConnections = tag.getInteger("ActiveConnections")
        blockedConnections = tag.getInteger("BlockedConnections")
        reinitializeShape()
    }

    override def writeDesc(packet: MCDataOutput): Unit = {
        packet.writeString(cableBlock.material.toString)
        packet.writeEnum(insulation)
        packet.writeInt(insulationColor)
        packet.writeInt(activeConnections)
        packet.writeInt(blockedConnections)
    }

    override def readDesc(packet: MCDataInput): Unit = {
        val materialName = packet.readString()
        cableBlock = MetaBlocks.CABLES.get(Material.MATERIAL_REGISTRY.getObject(materialName))
        insulation = packet.readEnum(classOf[Insulation])
        insulationColor = packet.readInt()
        activeConnections = packet.readInt()
        blockedConnections = packet.readInt()
        reinitializeShape()
    }

    def setInsulationColor(color: Int): Unit = {
        insulationColor = color
        sendDescUpdate()
        BlockCable.updateCableConnections(this, tile.getWorld, tile.getPos)
    }


    @SideOnly(Side.CLIENT)
    override def renderStatic(pos: Vector3, layer: BlockRenderLayer, ccrs: CCRenderState): Boolean = {
        ccrs.setBrightness(tile.getWorld, tile.getPos)
        CableRenderer.INSTANCE.renderCableBlock(cableBlock.material, insulation, insulationColor, ccrs,
            Array(new Translation(pos)), activeConnections & ~blockedConnections)
        true
    }

    @SideOnly(Side.CLIENT)
    override def renderBreaking(pos: Vector3, texture: TextureAtlasSprite, ccrs: CCRenderState): Unit = {
        ccrs.setPipeline(pos.translation, new IconTransformation(texture))
        BlockRenderer.renderCuboid(ccrs, centerBox, 0)
        for(sideBox <- sidedConnections) {
            BlockRenderer.renderCuboid(ccrs, sideBox, 0)
        }
    }

    override def getType: ResourceLocation = GTMultipartFactory.cablePartKey

    override def getSubParts: JIterable[IndexedCuboid6] =
        (sidedConnections ++ Seq(centerBox)).map(new IndexedCuboid6(null, _))

    override def getCollisionBoxes: JIterable[Cuboid6] = sidedConnections ++ Seq(centerBox)

    override def getPartialOcclusionBoxes: JIterable[Cuboid6] = sidedConnections
    override def allowCompleteOcclusion: Boolean = true

    override def getOcclusionBoxes: JIterable[Cuboid6] = Seq(centerBox)

}