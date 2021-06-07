package gregtech.common.items.behaviors;

import gregtech.api.block.machines.BlockMachine;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.multiblock.BlockPatternChecker;
import gregtech.api.net.CPacketMultiBlockStructure;
import gregtech.api.net.NetworkHandler;
import gregtech.api.render.scene.WorldSceneRenderer;
import gregtech.common.render.MultiHelperRenderer;
import gregtech.integration.jei.GTJeiOptional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultiAssemblerBehaviour implements IItemBehaviour {

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        NBTTagCompound nbt = player.getHeldItem(hand).getOrCreateSubCompound("GT.Detrav");
        byte mode = nbt.hasKey("mode") ? nbt.getByte("mode") : 0;
        if (pos != null && world.getTileEntity(pos) instanceof MetaTileEntityHolder) {
            MetaTileEntity mte = ((MetaTileEntityHolder) world.getTileEntity(pos)).getMetaTileEntity();
            if (mte instanceof MultiblockControllerBase && !player.isSneaking()) { // preview
                if (world.isRemote) { // client side only
                    if(!Loader.isModLoaded("jei")) { // check jei installed
                        player.sendMessage(new TextComponentTranslation("metaitem.multi_assembler.jei_missing"));
                    } else if (mode == 3) { // building mode
                        WorldSceneRenderer worldSceneRenderer = GTJeiOptional.getWorldSceneRenderer((MultiblockControllerBase) mte);
                        if (worldSceneRenderer != null) {
                            List<ItemStack> map = new ArrayList<>();
                            Set<BlockPos> exist = new HashSet<>();
                            List<CPacketMultiBlockStructure.BlockInfo> blockInfos = new ArrayList<>();
                            List<BlockPos> renderedBlocks = worldSceneRenderer.getRenderedBlocks();
                            if (renderedBlocks != null) {
                                EnumFacing refFacing = EnumFacing.EAST;
                                BlockPos refPos = BlockPos.ORIGIN;
                                for(BlockPos blockPos : renderedBlocks) {
                                    MetaTileEntity metaTE = BlockMachine.getMetaTileEntity(worldSceneRenderer.world, blockPos);
                                    if(metaTE instanceof MultiblockControllerBase && metaTE.metaTileEntityId.equals(mte.metaTileEntityId)) {
                                        refPos = blockPos;
                                        refFacing = metaTE.getFrontFacing();
                                        break;
                                    }
                                }
                                for(BlockPos blockPos : renderedBlocks) { // check blocks built
                                    if (blockPos.equals(refPos)) continue;
                                    EnumFacing frontFacing = mte.getFrontFacing();
                                    EnumFacing spin = BlockPatternChecker.getSpin((MultiblockControllerBase) mte);
                                    BlockPos realPos = BlockPatternChecker.getActualPos(refFacing, frontFacing, spin
                                            , blockPos.getX() - refPos.getX()
                                            , blockPos.getY() - refPos.getY()
                                            , blockPos.getZ() - refPos.getZ()).add(mte.getPos());
                                    if (!world.isAirBlock(realPos) || worldSceneRenderer.world.isAirBlock(blockPos)) continue;
                                    IBlockState blockState = worldSceneRenderer.world.getBlockState(blockPos);
                                    MetaTileEntity metaTileEntity = BlockMachine.getMetaTileEntity(worldSceneRenderer.world, blockPos);
                                    ItemStack stack = blockState.getBlock().getItem(worldSceneRenderer.world, blockPos, blockState);
                                    if (metaTileEntity != null) {
                                        stack = metaTileEntity.getStackForm();
                                    }
                                    if (map.stream().noneMatch(stack::isItemEqual)) {
                                        map.add(stack);
                                    }
                                    if(exist.contains(realPos)) continue;
                                    exist.add(realPos);
                                    blockInfos.add(new CPacketMultiBlockStructure.BlockInfo(realPos
                                            , map.indexOf(map.stream().filter(stack::isItemEqual).findFirst().orElse(stack))
                                            , metaTileEntity != null ? BlockPatternChecker.getActualFrontFacing(refFacing, frontFacing, spin, metaTileEntity.getFrontFacing()): EnumFacing.SOUTH));
                                }
                                // send CPacket to the server to build the structure.
                                NetworkHandler.channel.sendToServer(new CPacketMultiBlockStructure(map, blockInfos, world.provider.getDimension()).toFMLPacket());
                            }
                        }
                    } else { // projection, perspective, debug mode
                        MultiHelperRenderer.renderMultiBlockPreview((MultiblockControllerBase) mte, 60000, mode);
                    }
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        NBTTagCompound nbt = player.getHeldItem(hand).getOrCreateSubCompound("GT.Detrav");
        byte mode = nbt.hasKey("mode") ? nbt.getByte("mode") : 0;
        if (player.isSneaking()) {
            mode = (byte)((mode + 1) % 4);
            nbt.setByte("mode", mode);
            if (world.isRemote) {
                player.sendMessage(new TextComponentTranslation("metaitem.multi_assembler.mode." + mode));
            }
        } else {
            if (player.world.isRemote) {
                MultiHelperRenderer.renderMultiBlockPreview(null, 0, mode);
                player.sendMessage(new TextComponentTranslation("metaitem.multi_assembler.clear"));
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        NBTTagCompound nbt = itemStack.getOrCreateSubCompound("GT.Detrav");
        byte mode = nbt.hasKey("mode") ? nbt.getByte("mode") : 0;
        lines.add(I18n.format("metaitem.multi_assembler.mode." + mode));
        lines.add(I18n.format("metaitem.multi_assembler.info.0"));
        lines.add(I18n.format("metaitem.multi_assembler.info.1"));
        lines.add(I18n.format("metaitem.multi_assembler.info.2"));
    }
}
