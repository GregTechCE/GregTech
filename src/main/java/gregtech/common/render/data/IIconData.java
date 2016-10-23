package gregtech.common.render.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IIconData {

    @SideOnly(Side.CLIENT)
    ImmutableList<BakedQuad> getQuads(EnumFacing side);

}
