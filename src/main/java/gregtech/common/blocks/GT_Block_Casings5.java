package gregtech.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GT_Block_Casings5
        extends GT_Block_Casings_Abstract {
    public GT_Block_Casings5() {
        super(GT_Item_Casings5.class, "gt.blockcasings5", GT_Material_Casings.INSTANCE);
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            Textures.BlockIcons.CASING_BLOCKS[(i + 64)] = new GT_CopiedBlockTexture(this, 6, i);
        }
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Tungstensteel Coil Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Naquadah Alloy Coil Block");

        ItemList.Casing_Coil_TungstenSteel.set(new ItemStack(this, 1, 4));
        ItemList.Casing_Coil_NaquadahAlloy.set(new ItemStack(this, 1, 5));
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 4:
                return Textures.BlockIcons.MACHINE_COIL_TUNGSTENSTEEL.getIcon();
            case 5:
                return Textures.BlockIcons.MACHINE_COIL_NAQUADAHALLOY.getIcon();
        }
        return Textures.BlockIcons.MACHINE_COIL_CUPRONICKEL.getIcon();
    }
}
