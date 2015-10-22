package gregtech.api.interfaces;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

public interface ITexture {
    public void renderXPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ);

    public void renderXNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ);

    public void renderYPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ);

    public void renderYNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ);

    public void renderZPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ);

    public void renderZNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ);

    public boolean isValidTexture();
}