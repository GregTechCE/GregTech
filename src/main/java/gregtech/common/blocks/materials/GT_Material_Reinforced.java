package gregtech.common.blocks.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class GT_Material_Reinforced extends Material {

    public static final Material INSTANCE = new GT_Material_Reinforced();

    public GT_Material_Reinforced() {
        super(MapColor.STONE);
        setRequiresTool();
        setAdventureModeExempt();
    }

    public boolean isOpaque() {
        return false;
    }
}
