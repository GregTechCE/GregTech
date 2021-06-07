package gregtech.api.net;

import gregtech.api.GregTechAPI;
import gregtech.api.block.machines.MachineItemBlock;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;

public class CPacketMultiBlockStructure implements NetworkHandler.Packet {
    public List<ItemStack> map;
    public List<BlockInfo> blockInfos;
    public int dim;

    public CPacketMultiBlockStructure(List<ItemStack> map, List<BlockInfo> blockInfos, int dim) {
        this.map = map;
        this.blockInfos = blockInfos;
        this.dim = dim;
    }

    public static void registerPacket(int packetId) {
        NetworkHandler.registerPacket(packetId, CPacketMultiBlockStructure.class, new NetworkHandler.PacketCodec<>(
                (packet, buf) -> {
                    buf.writeVarInt(packet.dim);
                    buf.writeVarInt(packet.map.size());
                    packet.map.forEach(buf::writeItemStack);
                    buf.writeVarInt(packet.blockInfos.size());
                    packet.blockInfos.forEach(blockInfo -> blockInfo.writeBuf(buf));
                },
                (buf) -> {
                    int dim = buf.readVarInt();
                    List<ItemStack> map = new ArrayList<>();
                    List<BlockInfo> blockInfos = new ArrayList<>();
                    int size = buf.readVarInt();
                    try {
                        for (int i = 0; i < size; i++) {
                            map.add(buf.readItemStack());
                        }
                    } catch (Exception ignored) {
                        return new CPacketMultiBlockStructure(map, blockInfos, dim);
                    }
                    size = buf.readVarInt();
                    for (int i = 0; i < size; i++) {
                        blockInfos.add(new BlockInfo(buf));
                    }
                    return new CPacketMultiBlockStructure(map, blockInfos, dim);
                }
        ));
    }

    public static void registerExecutor() {
        NetworkHandler.registerServerExecutor(CPacketMultiBlockStructure.class, (packet, handler) -> {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(packet.dim);
            for (BlockInfo blockInfo : packet.blockInfos) {
                ItemStack stack = packet.map.get(blockInfo.stack);
                if (stack.getItem() instanceof ItemBlock && world.isAirBlock(blockInfo.pos)) {
                    if (!handler.player.isCreative()) {
                        boolean find = false;
                        for (ItemStack itemStack : handler.player.inventory.mainInventory) {
                            if (itemStack.isItemEqual(stack) && !itemStack.isEmpty()) {
                                itemStack.setCount(itemStack.getCount() - 1);
                                find = true;
                                break;
                            }
                        }
                        if (!find) { // match AbilityPart
                            for (ItemStack itemStack : handler.player.inventory.mainInventory) {
                                if(itemStack.getItem() instanceof MachineItemBlock && stack.getItem() instanceof MachineItemBlock && !itemStack.isEmpty()) {
                                    MetaTileEntity hold = MachineItemBlock.getMetaTileEntity(itemStack);
                                    MetaTileEntity expect = MachineItemBlock.getMetaTileEntity(stack);
                                    if (hold instanceof IMultiblockAbilityPart && expect instanceof IMultiblockAbilityPart){
                                        if (((IMultiblockAbilityPart<?>) hold).getAbility() == ((IMultiblockAbilityPart<?>) expect).getAbility()) {
                                            stack = itemStack.copy();
                                            itemStack.setCount(itemStack.getCount() - 1);
                                            find = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (!find) continue;
                    }
                    ItemBlock itemBlock = (ItemBlock) stack.getItem();
                    IBlockState state = itemBlock.getBlock().getStateFromMeta(itemBlock.getMetadata(stack.getMetadata()));
                    world.setBlockState(blockInfo.pos, state);
                    TileEntity holder = world.getTileEntity(blockInfo.pos);
                    if (holder instanceof MetaTileEntityHolder) {
                        MetaTileEntity sampleMetaTileEntity = GregTechAPI.META_TILE_ENTITY_REGISTRY.getObjectById(stack.getItemDamage());
                        if (sampleMetaTileEntity != null) {
                            MetaTileEntity metaTileEntity = ((MetaTileEntityHolder) holder).setMetaTileEntity(sampleMetaTileEntity);
                            if (stack.hasTagCompound()) {
                                metaTileEntity.initFromItemStackData(stack.getTagCompound());
                            }
                            if (metaTileEntity.isValidFrontFacing(blockInfo.facing)) {
                                metaTileEntity.setFrontFacing(blockInfo.facing);
                            }
                        }
                    }
                }
            }

        });
    }

    public static class BlockInfo {
        public BlockPos pos;
        public int stack;
        public EnumFacing facing;

        public BlockInfo(BlockPos pos, int stack, EnumFacing facing) {
            this.pos = pos;
            this.stack = stack;
            this.facing = facing;
        }

        public BlockInfo(PacketBuffer buffer) {
            this.pos = buffer.readBlockPos();
            this.stack = buffer.readVarInt();
            this.facing = EnumFacing.VALUES[buffer.readByte()];
        }

        public void writeBuf(PacketBuffer buffer) {
            buffer.writeBlockPos(pos);
            buffer.writeVarInt(stack);
            buffer.writeByte(facing.getIndex());
        }
    }

}
