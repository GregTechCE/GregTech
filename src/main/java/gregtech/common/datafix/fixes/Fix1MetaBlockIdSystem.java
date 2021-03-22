package gregtech.common.datafix.fixes;

import gregtech.api.GTValues;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.datafix.WorldDataHooks;
import gregtech.common.datafix.util.RemappedBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Fix1MetaBlockIdSystem implements IFixableData {

    private static final String COMP_BLOCK_NAME = "compressed_";
    private static final int COMP_BLOCK_NAME_LEN = COMP_BLOCK_NAME.length();
    private static final String COMP_BLOCK_PREFIX = GTValues.MODID + ":" + COMP_BLOCK_NAME;
    private static final int COMP_BLOCK_PREFIX_LEN = COMP_BLOCK_PREFIX.length();

    private static final String SURF_ROCK_BLOCK_NAME = "surface_rock_";
    private static final int SURF_ROCK_BLOCK_NAME_LEN = SURF_ROCK_BLOCK_NAME.length();
    private static final String SURF_ROCK_BLOCK_PREFIX = GTValues.MODID + ":" + SURF_ROCK_BLOCK_NAME;

    public Fix1MetaBlockIdSystem() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getFixVersion() {
        return 1;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        String regName = compound.getString("id");
        if (regName.startsWith(COMP_BLOCK_PREFIX)) {
            RemappedBlock remapped = remapCompressed(
                    Integer.parseInt(regName.substring(COMP_BLOCK_PREFIX_LEN)), compound.getShort("Damage"));
            compound.setString("id", GTValues.MODID + ":meta_block_compressed_" + remapped.id);
            compound.setShort("Damage", remapped.data);
        } else if (regName.startsWith(SURF_ROCK_BLOCK_PREFIX)) {
            RemappedBlock remapped = remapSurfaceRock(
                    Integer.parseInt(regName.substring(COMP_BLOCK_PREFIX_LEN)), compound.getShort("Damage"));
            compound.setString("id", GTValues.MODID + ":meta_block_surface_rock_" + remapped.id);
            compound.setShort("Damage", remapped.data);
        }
        return compound;
    }

    static RemappedBlock remapCompressed(int index, int data) {
        int matId = MetaBlocks.COMPRESSED_OLD.get(index)[data];
        return new RemappedBlock(matId / 16, (short) (matId % 16));
    }

    static RemappedBlock remapSurfaceRock(int index, int data) {
        int matId = MetaBlocks.SURFACE_ROCKS_OLD.get(index)[data];
        return new RemappedBlock(matId / 16, (short) (matId % 16));
    }

    @SubscribeEvent
    public void onMissingBlockMappings(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getMappings()) {
            String regName = mapping.key.getResourcePath();
            if (regName.startsWith(COMP_BLOCK_NAME)) {
                mapping.ignore();
                WorldDataHooks.addOldCompressedId(
                        mapping.id, Integer.parseInt(regName.substring(COMP_BLOCK_NAME_LEN)));
            } else if (regName.startsWith(SURF_ROCK_BLOCK_NAME)) {
                mapping.ignore();
                WorldDataHooks.addOldSurfaceRockId(
                        mapping.id, Integer.parseInt(regName.substring(SURF_ROCK_BLOCK_NAME_LEN)));
            }
        }
    }

    @SubscribeEvent
    public void onMissingItemMappings(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getMappings()) {
            String regName = mapping.key.getResourcePath();
            if (regName.startsWith(COMP_BLOCK_NAME) || regName.startsWith(SURF_ROCK_BLOCK_NAME)) {
                mapping.ignore();
            }
        }
    }

}
