package gregtech.api.metatileentity.multiblock;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public abstract class MultiblockWithDisplayBase extends MultiblockControllerBase {

    public MultiblockWithDisplayBase(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    /**
     * Called serverside to obtain text displayed in GUI
     * each element of list is displayed on new line
     * to use translation, use TextComponentTranslation
     */
    protected void addDisplayText(List<ITextComponent> textList) {
        if(!isStructureFormed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.invalid_structure")
                .setStyle(new Style().setColor(TextFormatting.RED)));
        }
    }

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.extendedBuilder();
        builder.image(7, 4, 162, 121, GuiTextures.DISPLAY);
        builder.label(10, 7, getMetaFullName(), 0xFFFFFF);
        builder.widget(new AdvancedTextWidget(10, 17, this::addDisplayText, 0xFFFFFF)
            .setMaxWidthLimit(156));
        builder.bindPlayerInventory(entityPlayer.inventory, 134);
        return builder;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createUITemplate(entityPlayer).build(getHolder(), entityPlayer);
    }

}
