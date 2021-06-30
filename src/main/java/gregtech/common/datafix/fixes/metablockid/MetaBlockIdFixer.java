package gregtech.common.datafix.fixes.metablockid;

import gregtech.api.util.Version;
import gregtech.common.datafix.GregTechDataFixers;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

public interface MetaBlockIdFixer {

    MetaBlockIdFixer NOOP = new Noop();

    static MetaBlockIdFixer create(Version prevSaveVersion, NBTTagCompound fmlTag) {
        if (prevSaveVersion.compareTo(MetaBlockIdFixHelper.V1_10_5) < 0) {
            return PreGraniteMetaBlockIdFixer.generate(fmlTag);
        } else if (prevSaveVersion.compareTo(MetaBlockIdFixHelper.V1_15_0) < 0) {
            return PostGraniteMetaBlockIdFixer.generate(fmlTag);
        } else {
            return NOOP;
        }
    }

    int getFallbackDataVersion();

    NBTTagCompound serialize();

    static MetaBlockIdFixer deserialize(NBTTagCompound tag) {
        if (tag.hasKey(MetaBlockIdFixHelper.KEY_FALLBACK_VERSION, Constants.NBT.TAG_INT)) {
            int fallbackVersion = tag.getInteger(MetaBlockIdFixHelper.KEY_FALLBACK_VERSION);
            switch (fallbackVersion) {
                case GregTechDataFixers.V_PRE_GRANITE:
                    return PreGraniteMetaBlockIdFixer.deserialize(tag);
                case GregTechDataFixers.V0_POST_GRANITE:
                    return PostGraniteMetaBlockIdFixer.deserialize(tag);
                default:
                    throw new IllegalStateException("Bad GregTech fallback data version: " + fallbackVersion);
            }
        }
        return NOOP;
    }

    class Noop implements MetaBlockIdFixer {

        private Noop() {
        }

        @Override
        public int getFallbackDataVersion() {
            return GregTechDataFixers.V1_META_BLOCK_ID_REWORK;
        }

        @Override
        public NBTTagCompound serialize() {
            return new NBTTagCompound();
        }

    }

}
