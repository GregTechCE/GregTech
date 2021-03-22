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
     * The current data format version for all of GregTech. Increment this when breaking changes are made and a new
     * data fixer needs to be written. All defined version numbers are listed below:
     * <ul>
     *     <li>-1: before addition of granite GregTech material</li>
     *     <li>0: added granite material</li>
     *     <li>1: new meta block ID system</li>
     * </ul>
     */
    public static final int DATA_VERSION = 1;

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
