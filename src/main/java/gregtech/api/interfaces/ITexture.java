package gregtech.api.interfaces;

import net.minecraft.block.Block;

public interface ITexture {
    public void renderXPos(Block aBlock, int aX, int aY, int aZ);

    public void renderXNeg(Block aBlock, int aX, int aY, int aZ);

    public void renderYPos(Block aBlock, int aX, int aY, int aZ);

    public void renderYNeg(Block aBlock, int aX, int aY, int aZ);

    public void renderZPos(Block aBlock, int aX, int aY, int aZ);

    public void renderZNeg(Block aBlock, int aX, int aY, int aZ);

    public boolean isValidTexture();
}