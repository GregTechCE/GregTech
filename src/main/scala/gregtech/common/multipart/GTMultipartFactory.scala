package gregtech.common.multipart

import codechicken.multipart.{MultiPartRegistry, TMultiPart}
import codechicken.multipart.api.{IPartConverter, IPartFactory}
import gregtech.api.GTValues
import gregtech.common.cable.BlockCable
import gregtech.common.cable.tile.TileEntityCable
import net.minecraft.block.state.IBlockState
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object GTMultipartFactory extends IPartFactory with IPartConverter {

    val cablePartKey = new ResourceLocation(GTValues.MODID, "cable")

    def registerFactory(): Unit = {
        MultiPartRegistry.registerParts(this, Seq(cablePartKey))
        MultiPartRegistry.registerConverter(this)
    }

    override def createPart(identifier: ResourceLocation, client: Boolean): TMultiPart = identifier match {
        case i if i == cablePartKey => new CableMultiPart()
        case _ => null
    }

    override def canConvert(world: World, pos: BlockPos, state: IBlockState): Boolean = state match {
        case c if c.getBlock.isInstanceOf[BlockCable] => true
        case _ => false
    }

    override def convert(world: World, pos: BlockPos, state: IBlockState): TMultiPart = state match {
        case c if c.getBlock.isInstanceOf[BlockCable] =>
            val tile = world.getTileEntity(pos).asInstanceOf[TileEntityCable]
            new CableMultiPart(c, tile)
        case _ => null
    }
}
