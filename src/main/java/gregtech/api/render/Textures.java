package gregtech.api.render;

import codechicken.lib.texture.TextureUtils.IIconRegister;
import gregtech.api.GTValues;
import gregtech.api.util.GTLog;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gregtech.api.render.OrientedOverlayRenderer.OverlayFace.*;

public class Textures {

    public static List<IIconRegister> iconRegisters = new ArrayList<>();

    public static SimpleSidedRenderer STEAM_CASING_BRONZE = new SimpleSidedRenderer("casings/steam/bronze");
    public static SimpleSidedRenderer STEAM_CASING_STEEL = new SimpleSidedRenderer("casings/steam/steel");
    public static SimpleSidedRenderer STEAM_BRICKED_CASING_BRONZE = new SimpleSidedRenderer("casings/steam/bricked_bronze");
    public static SimpleSidedRenderer STEAM_BRICKED_CASING_STEEL = new SimpleSidedRenderer("casings/steam/bricked_steel");
    public static SimpleSidedRenderer[] VOLTAGE_CASINGS = new SimpleSidedRenderer[GTValues.V.length];

    public static OrientedOverlayRenderer COAL_BOILER_OVERLAY = new OrientedOverlayRenderer("generators/boiler/coal", FRONT);
    public static OrientedOverlayRenderer LAVA_BOILER_OVERLAY = new OrientedOverlayRenderer("generators/boiler/lava", FRONT);
    public static OrientedOverlayRenderer SOLAR_BOILER_OVERLAY = new OrientedOverlayRenderer("generators/boiler/solar", TOP);

    public static OrientedOverlayRenderer ALLOY_SMELTER_OVERLAY = new OrientedOverlayRenderer("machines/alloy_smelter", FRONT);
    public static OrientedOverlayRenderer FURNACE_OVERLAY = new OrientedOverlayRenderer("machines/furnace", FRONT);
    public static OrientedOverlayRenderer EXTRACTOR_OVERLAY = new OrientedOverlayRenderer("machines/extractor", FRONT, TOP, SIDE);
    public static OrientedOverlayRenderer COMPRESSOR_OVERLAY = new OrientedOverlayRenderer("machines/compressor", FRONT, TOP, SIDE);
    public static OrientedOverlayRenderer HAMMER_OVERLAY = new OrientedOverlayRenderer("machines/hammer", FRONT);
    public static OrientedOverlayRenderer MACERATOR_OVERLAY = new OrientedOverlayRenderer("machines/macerator", FRONT, TOP);

    public static SimpleOverlayRenderer PIPE_OUT_OVERLAY = new SimpleOverlayRenderer("machine/overlay_pipe_out");
    public static SimpleOverlayRenderer FLUID_OUTPUT_OVERLAY = new SimpleOverlayRenderer("machine/overlay_fluid_output");
    public static SimpleOverlayRenderer ITEM_OUTPUT_OVERLAY = new SimpleOverlayRenderer("machine/overlay_item_output");

    static {
        for(int i = 0; i < VOLTAGE_CASINGS.length; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            VOLTAGE_CASINGS[i] = new SimpleSidedRenderer("casings/voltage/" + voltageName);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void register(TextureMap textureMap) {
        GTLog.logger.info("Loading meta tile entity texture sprites...");
        for(IIconRegister iconRegister : iconRegisters) {
            iconRegister.registerIcons(textureMap);
        }
    }

}
