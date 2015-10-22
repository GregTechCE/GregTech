package gregtech.api.interfaces;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public interface IIconContainer {
    /**
     * @return A regular Icon.
     */
    public IIcon getIcon();

    /**
     * @return Icon of the Overlay (or null if there is no Icon)
     */
    public IIcon getOverlayIcon();

    /**
     * @return the Default Texture File for this Icon.
     */
    public ResourceLocation getTextureFile();
}
