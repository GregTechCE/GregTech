package gregtech.loaders.misc;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.common.covers.GT_Cover_Vent;
import ic2.core.ref.ItemName;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class GT_CoverLoader
        implements Runnable {
    public void run() {
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            GregTech_API.registerCover(new ItemStack(Blocks.CARPET, 1, i), new GT_CopiedBlockTexture(Blocks.WOOL, 0, i), null);
        }
        GregTech_API.registerCover(GT_ModHandler.getIC2Item(ItemName.heat_vent, 1), new GT_RenderedTexture(Textures.BlockIcons.VENT_NORMAL), new GT_Cover_Vent(4));
        GregTech_API.registerCover(GT_ModHandler.getIC2Item(ItemName.reactor_heat_vent, 1), new GT_RenderedTexture(Textures.BlockIcons.VENT_NORMAL), new GT_Cover_Vent(8));
        GregTech_API.registerCover(GT_ModHandler.getIC2Item(ItemName.advanced_heat_vent, 1), new GT_RenderedTexture(Textures.BlockIcons.VENT_ADVANCED), new GT_Cover_Vent(16));
        GregTech_API.registerCover(GT_ModHandler.getIC2Item(ItemName.component_heat_vent, 1), new GT_RenderedTexture(Textures.BlockIcons.VENT_NORMAL), new GT_Cover_Vent(6));
        GregTech_API.registerCover(GT_ModHandler.getIC2Item(ItemName.overclocked_heat_vent, 1), new GT_RenderedTexture(Textures.BlockIcons.VENT_ADVANCED), new GT_Cover_Vent(24));
    }
}
