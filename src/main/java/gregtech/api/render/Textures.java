package gregtech.api.render;

import gregtech.api.GTValues;
import gregtech.api.util.GTLog;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

public class Textures {

    public static SimpleSidedRenderer STEAM_CASING_BRONZE = new SimpleSidedRenderer("casings/steam/bronze");
    public static SimpleSidedRenderer STEAM_CASING_STEEL = new SimpleSidedRenderer("casings/steam/steel");
    public static SimpleSidedRenderer[] VOLTAGE_CASINGS = new SimpleSidedRenderer[GTValues.V.length];

    static {
        for(int i = 0; i < VOLTAGE_CASINGS.length; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            VOLTAGE_CASINGS[i] = new SimpleSidedRenderer("casings/voltage/" + voltageName);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void register(TextureMap textureMap) {
        GTLog.logger.info("Loading meta tile entity texture sprites...");
        STEAM_CASING_BRONZE.registerSprites(textureMap);
        STEAM_CASING_STEEL.registerSprites(textureMap);
        Arrays.stream(VOLTAGE_CASINGS).forEach(c -> c.registerSprites(textureMap));
    }

}
