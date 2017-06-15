package gregtech.common.blocks.itemblocks;

import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GenericBlock;
import gregtech.api.util.GT_ItsNotMyFaultException;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemMachines extends ItemBlock {
    public ItemMachines(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(GregTech_API.TAB_GREGTECH);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        try {
            int damage = getDamage(stack);
            if ((damage <= 0) || (damage >= GregTech_API.METATILEENTITIES.length)) {
                return;
            }
            TileEntity temp = GregTech_API.sBlockMachines.createNewTileEntity(player == null ? GT_Values.DW : player.worldObj, GregTech_API.METATILEENTITIES[damage] == null ? 0 : GregTech_API.METATILEENTITIES[damage].getTileEntityBaseType());
            if (temp != null) {
                temp.setWorldObj(player == null ? GT_Values.DW : player.worldObj);
                temp.setPos(BlockPos.ORIGIN);
                if ((temp instanceof IGregTechTileEntity)) {
                    IGregTechTileEntity tileEntity = (IGregTechTileEntity) temp;
                    tileEntity.setInitialValuesAsNBT(new NBTTagCompound(), (short) damage);
                    if (tileEntity.getDescription() != null) {
                        int i = 0;
                        for (String description : tileEntity.getDescription()) {
                            if (GT_Utility.isStringValid(description)) {
                                if (description.contains("%%%")) {
                                    String[] split = description.split("%%%");
                                    if (split.length>=2) {
                                        tooltip.add(GT_LanguageManager.addStringLocalization("TileEntity_DESCRIPTION_" + damage + "_Index_" + i++, split[0], !GregTech_API.sPostloadFinished )+" "+split[1]);
                                    }
                                } else {
                                    String translated = GT_LanguageManager.addStringLocalization("TileEntity_DESCRIPTION_" + damage + "_Index_" + i++, description, !GregTech_API.sPostloadFinished );
                                    tooltip.add(translated.equals("") ? description : translated);
                                }
                            } else i++;
                        }
                    }
                    if (tileEntity.getEUCapacity() > 0L) {
                        if (tileEntity.getInputVoltage() > 0L) {
                            tooltip.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_IN", "Voltage IN: ", !GregTech_API.sPostloadFinished) + TextFormatting.GREEN + tileEntity.getInputVoltage() + " (" + GT_Values.VN[GT_Utility.getTier(tileEntity.getInputVoltage())] + ")" + TextFormatting.GRAY);
                        }
                        if (tileEntity.getOutputVoltage() > 0L) {
                            tooltip.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_OUT", "Voltage OUT: ", !GregTech_API.sPostloadFinished) + TextFormatting.GREEN + tileEntity.getOutputVoltage() + " (" + GT_Values.VN[GT_Utility.getTier(tileEntity.getOutputVoltage())] + ")" + TextFormatting.GRAY);
                        }
                        if (tileEntity.getOutputAmperage() > 1L) {
                            tooltip.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_AMOUNT", "Amperage: ", !GregTech_API.sPostloadFinished) + TextFormatting.YELLOW + tileEntity.getOutputAmperage() + TextFormatting.GRAY);
                        }
                        tooltip.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_STORE", "Capacity: ", !GregTech_API.sPostloadFinished) + TextFormatting.BLUE + tileEntity.getEUCapacity() + TextFormatting.GRAY);
                    }
                }
            }
            NBTTagCompound tag = stack.getTagCompound();
            if (tag != null) {
                if (tag.getBoolean("mMuffler")) {
                    tooltip.add(GT_LanguageManager.addStringLocalization("GT_TileEntity_MUFFLER", "has Muffler Upgrade", !GregTech_API.sPostloadFinished));
                }
                if (tag.getBoolean("mSteamConverter")) {
                    tooltip.add(GT_LanguageManager.addStringLocalization("GT_TileEntity_STEAMCONVERTER", "has Steam Upgrade", !GregTech_API.sPostloadFinished));
                }
                int amount;
                if ((amount = tag.getByte("mSteamTanks")) > 0) {
                    tooltip.add(amount + " " + GT_LanguageManager.addStringLocalization("GT_TileEntity_STEAMTANKS", "Steam Tank Upgrades", !GregTech_API.sPostloadFinished));
                }
            }
        } catch (Throwable e) {
            e.printStackTrace(GT_Log.err);
        }
    }

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        return EnumActionResult.PASS;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return GT_LanguageManager.getTranslation(getUnlocalizedName(stack) + ".name");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        short damage = (short) getDamage(stack);
        if ((damage < 0) || (damage >= GregTech_API.METATILEENTITIES.length)) {
            return "";
        }
        if (GregTech_API.METATILEENTITIES[damage] != null) {
            return getUnlocalizedName() + "." + GregTech_API.METATILEENTITIES[damage].getMetaName();
        }
        return "";
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        super.onCreated(stack, world, player);
        short damage = (short) getDamage(stack);
        if ((damage < 0) || ((damage >= GregTech_API.METATILEENTITIES.length) && (GregTech_API.METATILEENTITIES[damage] != null))) {
            GregTech_API.METATILEENTITIES[damage].onCreated(stack, world, player);
        }
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        short damage = (short) getDamage(stack);
        System.out.println("Place block " + block + " " + damage);
        if (damage > 0) {
            if (GregTech_API.METATILEENTITIES[damage] == null) {
                return false;
            }
            int metaData = GregTech_API.METATILEENTITIES[damage].getTileEntityBaseType();
            if (!world.setBlockState(pos, block.getStateFromMeta(metaData))) {
                return false;
            }
            IBlockState placed = world.getBlockState(pos);
            if (placed != block.getStateFromMeta(metaData)) {
                throw new GT_ItsNotMyFaultException("Failed to place Block even though World.setBlockState returned true. It COULD be MCPC/Bukkit causing that. In case you really have that installed, don't report this Bug to me, I don't know how to fix it.");
            }
            if (placed.getValue(GenericBlock.METADATA) != metaData) {
                throw new GT_ItsNotMyFaultException("Failed to set the MetaValue of the Block even though World.setBlock returned true. It COULD be MCPC/Bukkit causing that. In case you really have that installed, don't report this Bug to me, I don't know how to fix it.");
            }
            IGregTechTileEntity tileEntity = (IGregTechTileEntity) world.getTileEntity(pos);
            if (tileEntity != null) {
                tileEntity.setInitialValuesAsNBT(tileEntity.isServerSide() ? stack.getTagCompound() : null, damage);
                if (player != null) {
                    tileEntity.setOwnerName(player.getName());
                }
                tileEntity.getMetaTileEntity().initDefaultModes(stack.getTagCompound());
            }
        } else if (!world.setBlockState(pos, block.getStateFromMeta(damage))) {
            return false;
        }
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == block) {
            this.block.onBlockPlacedBy(world, pos, state, player, stack);
        }
        return true;
    }

}