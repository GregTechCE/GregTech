package gregtech.common.blocks.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class GT_Material_Casings extends Material {

    public static final Material INSTANCE = new GT_Material_Casings();

    public GT_Material_Casings() {
        super(MapColor.IRON);
        setRequiresTool();
    }

    public boolean isOpaque() {
        return false;
    }
}
