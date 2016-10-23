package gregtech.common.render.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public interface IIconData {

    ImmutableList<BakedQuad> getQuads(EnumFacing side);

}
