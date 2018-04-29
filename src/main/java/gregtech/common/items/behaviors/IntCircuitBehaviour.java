package gregtech.common.items.behaviors;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ButtonWidget;
import gregtech.api.gui.widgets.DynamicLabelWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.items.HandUIWrapper;
import gregtech.api.items.MetaItemUIFactory;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.items.metaitem.stats.IUIManager;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import java.util.List;

public class IntCircuitBehaviour implements IItemBehaviour, IUIManager {

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        int configuration = IntCircuitIngredient.getCircuitConfiguration(itemStack);
        lines.add(I18n.format("metaitem.int_circuit.configuration", configuration));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if(!world.isRemote) {
            MetaItemUIFactory.INSTANCE.openUI(new HandUIWrapper(hand), (EntityPlayerMP) player);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, heldItem);
    }

    @Override
    public ModularUI createUI(HandUIWrapper holder, EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND_SMALL, 176, 60)
            .label(9, 8, "metaitem.circuit.integrated.gui")
            .widget(new AdvancedTextWidget(82, 30, components -> addDisplayText(components, holder, entityPlayer), 0x404040))
            .widget(new ButtonWidget(15, 24, 20, 20, GuiTextures.VANILLA_BUTTON, () -> false, pressed -> adjustConfiguration(holder, entityPlayer, -5)))
            .widget(new ButtonWidget(50, 24, 20, 20, GuiTextures.VANILLA_BUTTON, () -> false, pressed -> adjustConfiguration(holder, entityPlayer, -1)))
            .widget(new ButtonWidget(104, 24, 20, 20, GuiTextures.VANILLA_BUTTON, () -> false, pressed -> adjustConfiguration(holder, entityPlayer, +1)))
            .widget(new ButtonWidget(141, 24, 20, 20, GuiTextures.VANILLA_BUTTON, () -> false, pressed -> adjustConfiguration(holder, entityPlayer, +5)))
            .widget(new LabelWidget(19, 30, "-5", 0xFFFFFF))
            .widget(new LabelWidget(55, 30, "-1", 0xFFFFFF))
            .widget(new LabelWidget(109, 30, "+1", 0xFFFFFF))
            .widget(new LabelWidget(145, 30, "+5", 0xFFFFFF))
            .build(holder, entityPlayer);
    }

    protected void addDisplayText(List<ITextComponent> textList, HandUIWrapper holder, EntityPlayer entityPlayer) {
        int configuration = IntCircuitIngredient.getCircuitConfiguration(entityPlayer.getHeldItem(holder.hand));
        if (configuration < 10) {
            textList.add(new TextComponentString(" " + configuration));
        } else {
            textList.add(new TextComponentString("" + configuration));
        }
    }

    private static void adjustConfiguration(HandUIWrapper holder, EntityPlayer entityPlayer, int amount) {
        ItemStack stack = entityPlayer.getHeldItem(holder.hand);

        int configuration = IntCircuitIngredient.getCircuitConfiguration(stack) + amount;
        if (configuration < 0) {
            configuration = 0;
        } else if (configuration > 16) {
            configuration = 16;
        }
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger("Configuration", configuration);
    }
}
