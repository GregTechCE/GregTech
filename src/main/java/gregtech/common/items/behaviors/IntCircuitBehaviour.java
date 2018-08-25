package gregtech.common.items.behaviors;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ButtonWidget;
import gregtech.api.gui.widgets.DynamicLabelWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.items.gui.ItemUIFactory;
import gregtech.api.items.gui.PlayerInventoryHolder;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class IntCircuitBehaviour implements IItemBehaviour, ItemUIFactory {

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        int configuration = IntCircuitIngredient.getCircuitConfiguration(itemStack);
        lines.add(I18n.format("metaitem.int_circuit.configuration", configuration));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if(!world.isRemote) {
            PlayerInventoryHolder holder = new PlayerInventoryHolder(player, hand);
            holder.openUI();
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, heldItem);
    }

    @Override
    public ModularUI createUI(PlayerInventoryHolder holder, EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND_SMALL, 176, 60)
            .label(9, 8, "metaitem.circuit.integrated.gui")
            .widget(new DynamicLabelWidget(82, 30, () -> Integer.toString(IntCircuitIngredient.getCircuitConfiguration(holder.getCurrentItem())), 0x404040))
            .widget(new ButtonWidget(15, 24, 20, 20, GuiTextures.VANILLA_BUTTON, () -> false, pressed -> adjustConfiguration(holder, -5)))
            .widget(new ButtonWidget(50, 24, 20, 20, GuiTextures.VANILLA_BUTTON, () -> false, pressed -> adjustConfiguration(holder, -1)))
            .widget(new ButtonWidget(104, 24, 20, 20, GuiTextures.VANILLA_BUTTON, () -> false, pressed -> adjustConfiguration(holder, +1)))
            .widget(new ButtonWidget(141, 24, 20, 20, GuiTextures.VANILLA_BUTTON, () -> false, pressed -> adjustConfiguration(holder, +5)))
            .widget(new LabelWidget(19, 30, "-5", 0xFFFFFF))
            .widget(new LabelWidget(55, 30, "-1", 0xFFFFFF))
            .widget(new LabelWidget(109, 30, "+1", 0xFFFFFF))
            .widget(new LabelWidget(145, 30, "+5", 0xFFFFFF))
            .build(holder, entityPlayer);
    }

    private static void adjustConfiguration(PlayerInventoryHolder holder, int amount) {
        ItemStack stack = holder.getCurrentItem();
        int configuration = IntCircuitIngredient.getCircuitConfiguration(stack);
        configuration += amount;
        configuration = MathHelper.clamp(configuration, 0, 32);
        IntCircuitIngredient.setCircuitConfiguration(stack, configuration);
        holder.markAsDirty();
    }
}
