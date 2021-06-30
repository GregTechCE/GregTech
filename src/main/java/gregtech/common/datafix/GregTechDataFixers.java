package gregtech.common.datafix;

import gregtech.api.GTValues;
import gregtech.common.datafix.fixes.Fix0PostGraniteMetaBlockShift;
import gregtech.common.datafix.fixes.Fix0PostGraniteMetaBlockShiftInWorld;
import gregtech.common.datafix.fixes.Fix1MetaBlockIdSystem;
import gregtech.common.datafix.fixes.Fix1MetaBlockIdSystemInWorld;
import gregtech.common.datafix.walker.WalkChunkSection;
import gregtech.common.datafix.walker.WalkItemStackLike;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraftforge.common.util.CompoundDataFixer;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GregTechDataFixers {

    /**
     * Data version corresponding to before the granite GregTech material was added.
     */
    public static final int V_PRE_GRANITE = -1;

    /**
     * Data version corresponding to after the granite GregTech material was added.
     */
    public static final int V0_POST_GRANITE = 0;

    /**
     * Data version corresponding to after the implementation of the new meta block ID system.
     */
    public static final int V1_META_BLOCK_ID_REWORK = 1;

    /**
     * The current data format version for all of GregTech. Increment this when breaking changes are made and a new
     * data fixer needs to be written. All defined version numbers are listed in {@link GregTechDataFixers}.
     */
    public static final int DATA_VERSION = V1_META_BLOCK_ID_REWORK;

    /**
     * Constants used for fixing NBT data.
     */
    public static final String COMPOUND_ID = "id";
    public static final String COMPOUND_META = "Damage";

    public static void init() {
        CompoundDataFixer fmlFixer = FMLCommonHandler.instance().getDataFixer();
        IDataWalker walkItemStackLike = new WalkItemStackLike();
        fmlFixer.registerVanillaWalker(FixTypes.BLOCK_ENTITY, walkItemStackLike);
        fmlFixer.registerVanillaWalker(FixTypes.ENTITY, walkItemStackLike);
        fmlFixer.registerVanillaWalker(FixTypes.PLAYER, walkItemStackLike);
        fmlFixer.registerVanillaWalker(FixTypes.CHUNK, new WalkChunkSection());

        ModFixs gtFixer = fmlFixer.init(GTValues.MODID, DATA_VERSION);
        gtFixer.registerFix(GregTechFixType.ITEM_STACK_LIKE, new Fix0PostGraniteMetaBlockShift());
        gtFixer.registerFix(GregTechFixType.CHUNK_SECTION, new Fix0PostGraniteMetaBlockShiftInWorld());
        gtFixer.registerFix(GregTechFixType.ITEM_STACK_LIKE, new Fix1MetaBlockIdSystem());
        gtFixer.registerFix(GregTechFixType.CHUNK_SECTION, new Fix1MetaBlockIdSystemInWorld());
    }
}
