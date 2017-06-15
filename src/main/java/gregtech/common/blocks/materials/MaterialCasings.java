package gregtech.common.blocks.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialCasings extends Material {

    public static final Material INSTANCE = new MaterialCasings();

    public MaterialCasings() {
        super(MapColor.IRON);
        setRequiresTool();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
