package gregtech.common.datafix.fixes;

import gregtech.api.GTValues;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Fix1MetaBlockIdSystem implements IFixableData {

    private static final String COMP_BLOCK_NAME = "compressed_";
    private static final String COMP_BLOCK_PREFIX = GTValues.MODID + ":" + COMP_BLOCK_NAME;
    private static final int COMP_BLOCK_PREFIX_LEN = COMP_BLOCK_PREFIX.length();

    private static final String SURF_ROCK_BLOCK_NAME = "surface_rock_";
    private static final String SURF_ROCK_BLOCK_PREFIX = GTValues.MODID + ":" + SURF_ROCK_BLOCK_NAME;
    private static final int SURF_ROCK_BLOCK_PREFIX_LEN = SURF_ROCK_BLOCK_PREFIX.length();

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
            int index = Integer.parseInt(regName.substring(COMP_BLOCK_PREFIX_LEN));
            short damage = compound.getShort("Damage");
            int matId = MetaBlocks.COMPRESSED_OLD.get(index)[damage];
            compound.setString("id", GTValues.MODID + ":meta_block_compressed_" + (matId / 16));
            compound.setShort("Damage", (short) (matId % 16));
        } else if (regName.startsWith(SURF_ROCK_BLOCK_PREFIX)) {
            int index = Integer.parseInt(regName.substring(SURF_ROCK_BLOCK_PREFIX_LEN));
            short damage = compound.getShort("Damage");
            int matId = MetaBlocks.SURFACE_ROCKS_OLD.get(index)[damage];
            compound.setString("id", GTValues.MODID + ":meta_block_surface_rock_" + (matId / 16));
            compound.setShort("Damage", (short) (matId % 16));
        }
        return compound;
    }

    @SubscribeEvent
    public void onMissingBlockMappings(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getMappings()) {
            String regName = mapping.key.getResourcePath();
            if (regName.startsWith(COMP_BLOCK_NAME) || regName.startsWith(SURF_ROCK_BLOCK_NAME)) {
                mapping.ignore();
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
