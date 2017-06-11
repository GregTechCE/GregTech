package gregtech.common.blocks.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialReinforced extends Material {

    public static final Material INSTANCE = new MaterialReinforced();

    public MaterialReinforced() {
        super(MapColor.STONE);
        setRequiresTool();
        setAdventureModeExempt();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
