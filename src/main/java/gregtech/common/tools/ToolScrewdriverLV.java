package gregtech.common.tools;

import gregtech.api.items.toolitem.ToolMetaItem;
import net.minecraft.item.ItemStack;

public class ToolScrewdriverLV extends ToolScrewdriver {

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 200;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return !aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadScrewdriver.mTextureIndex] : Textures.ItemIcons.HANDLE_ELECTRIC_SCREWDRIVER;
    }
}
