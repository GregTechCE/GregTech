package gregtech.common.datafix.fixes;

import gregtech.common.datafix.fixes.metablockid.MetaBlockIdFixHelper;
import gregtech.common.datafix.fixes.metablockid.PostGraniteMetaBlockIdFixer;
import gregtech.common.datafix.fixes.metablockid.WorldDataHooks;
import gregtech.common.datafix.util.RemappedBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Fix1MetaBlockIdSystem implements IFixableData {

    public Fix1MetaBlockIdSystem() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getFixVersion() {
        return 1;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        if (!WorldDataHooks.isFixerAvailable()) {
            return compound;
        }

        String blockResLoc = compound.getString("id");
        int index = MetaBlockIdFixHelper.getCompressedIndexFromResLoc(blockResLoc);
        if (index != -1) {
            RemappedBlock remapped = ((PostGraniteMetaBlockIdFixer) WorldDataHooks.getMetaBlockIdFixer())
                    .remapCompressedPostGraniteToNew(index, compound.getShort("Damage"));
            compound.setString("id", MetaBlockIdFixHelper.COMP_RESLOC_PREF_NEW + remapped.id);
            compound.setShort("Damage", remapped.data);
            return compound;
        }

        index = MetaBlockIdFixHelper.getSurfRockIndexFromResLoc(blockResLoc);
        if (index != -1) {
            RemappedBlock remapped = ((PostGraniteMetaBlockIdFixer) WorldDataHooks.getMetaBlockIdFixer())
                    .remapSurfRockToNew(index, compound.getShort("Damage"));
            compound.setString("id", MetaBlockIdFixHelper.SURF_ROCK_RESLOC_PREF_NEW + remapped.id);
            compound.setShort("Damage", remapped.data);
        }
        return compound;
    }

    @SubscribeEvent
    public void onMissingBlockMappings(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getMappings()) {
            String regName = mapping.key.getPath();
            if (regName.startsWith(MetaBlockIdFixHelper.COMP_NAME_PREF)
                    || regName.startsWith(MetaBlockIdFixHelper.SURF_ROCK_NAME_PREF)) {
                mapping.ignore();
            }
        }
    }

    @SubscribeEvent
    public void onMissingItemMappings(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getMappings()) {
            String regName = mapping.key.getPath();
            if (regName.startsWith(MetaBlockIdFixHelper.COMP_NAME_PREF)
                    || regName.startsWith(MetaBlockIdFixHelper.SURF_ROCK_NAME_PREF)) {
                mapping.ignore();
            }
        }
    }

}
