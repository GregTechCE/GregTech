package gregtech.common.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class GT_Material_Casings
        extends Material {
    public static final Material INSTANCE = new GT_Material_Casings();

    private GT_Material_Casings() {
        super(MapColor.ironColor);
        setRequiresTool();
    }

    public boolean isOpaque() {
        return false;
    }
}
