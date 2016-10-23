package gregtech.common.render.data;

import com.google.common.collect.ImmutableList;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IIconContainer;
import gregtech.common.render.RenderUtil;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import java.util.EnumMap;

public class DefaultQuadData implements IIconData, Runnable {

    private EnumMap<EnumFacing, ImmutableList<BakedQuad>> quads = new EnumMap<>(EnumFacing.class);
    private Object[] iconData;


    public DefaultQuadData(Object... iconData) {
        this.iconData = iconData;
        GregTech_API.sAfterGTIconload.add(this);
    }

    @Override
    public ImmutableList<BakedQuad> getQuads(EnumFacing side) {
        return quads.get(side);
    }

    @Override
    public void run() {
        for (EnumFacing enumFacing : EnumFacing.VALUES) {
            ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
            float offset = 0f;
            for (int i = 0; i < iconData.length; i++) {
                if (iconData[i] instanceof TextureAtlasSprite || iconData[i] instanceof IIconContainer) {
                    TextureAtlasSprite sprite = iconData[i] instanceof TextureAtlasSprite ?
                            (TextureAtlasSprite) iconData[i] :
                            ((IIconContainer) iconData[i]).getIcon();
                    int color = 0x0ffffff;
                    if(iconData.length > i + 1 && iconData[i + 1] instanceof Integer) {
                        color = (Integer) iconData[i + 1]; i++;
                    }
                    builder.add(RenderUtil.renderSide(sprite, enumFacing, offset, color));
                    offset += 0.001f;
                } else {
                    String typeTag = iconData[i] == null ? "null" : iconData[i].getClass().getName();
                    throw new IllegalArgumentException("Illegal type at index " + i + ": " + typeTag);
                }
            }
            quads.put(enumFacing, builder.build());
        }
    }

}
