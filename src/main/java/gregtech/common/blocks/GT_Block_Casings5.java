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
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Cupronickel Coil Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Kanthal Coil Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Nichrome Coil Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Tungstensteel Coil Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Naquadah Alloy Coil Block");

        ItemList.Casing_Coil_Cupronickel.set(new ItemStack(this, 1, 0));
        ItemList.Casing_Coil_Kanthal.set(new ItemStack(this, 1, 1));
        ItemList.Casing_Coil_Nichrome.set(new ItemStack(this, 1, 2));
        ItemList.Casing_Coil_TungstenSteel.set(new ItemStack(this, 1, 3));
        ItemList.Casing_Coil_NaquadahAlloy.set(new ItemStack(this, 1, 4));
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 0:
                return Textures.BlockIcons.MACHINE_COIL_CUPRONICKEL.getIcon();
            case 1:
                return Textures.BlockIcons.MACHINE_COIL_KANTHAL.getIcon();
            case 2:
                return Textures.BlockIcons.MACHINE_COIL_NICHROME.getIcon();
            case 3:
                return Textures.BlockIcons.MACHINE_COIL_TUNGSTENSTEEL.getIcon();
            case 4:
                return Textures.BlockIcons.MACHINE_COIL_NAQUADAHALLOY.getIcon();
        }
        return Textures.BlockIcons.MACHINE_COIL_CUPRONICKEL.getIcon();
    }
}
