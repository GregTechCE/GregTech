package gregtech.common.blocks.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class GT_Material_Machines extends Material {

    public static final Material INSTANCE = new GT_Material_Machines();

    public GT_Material_Machines() {
        super(MapColor.IRON);
        setRequiresTool();
        setImmovableMobility();
        setAdventureModeExempt();
    }

    public boolean isOpaque() {
        return false;
    }
}
