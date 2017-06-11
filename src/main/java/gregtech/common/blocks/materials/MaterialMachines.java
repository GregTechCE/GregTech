package gregtech.common.blocks.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialMachines extends Material {

    public static final Material INSTANCE = new MaterialMachines();

    public MaterialMachines() {
        super(MapColor.IRON);
        setRequiresTool();
        setImmovableMobility();
        setAdventureModeExempt();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
