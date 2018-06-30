package gregtech.common.items.behaviors;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ButtonWidget;
import gregtech.api.gui.widgets.DynamicLabelWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.SceneRenderWidget;
import gregtech.api.items.gui.ItemUIFactory;
import gregtech.api.items.gui.PlayerInventoryHolder;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
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
        HashMap<BlockPos, Tuple<IBlockState, TileEntity>> renderBlocks = new HashMap<>();
        for(int i = 0; i < 16; i++) {
            for(int j = 0; j < 16; j++) {
                renderBlocks.put(new BlockPos(i - 8, 0, j - 8), new Tuple<>(Blocks.DIRT.getDefaultState(), null));
            }
        }
        MetaTileEntityHolder tileEntityHolder = new MetaTileEntityHolder();
        tileEntityHolder.setMetaTileEntity(MetaTileEntities.DIESEL_GENERATOR[0]);
        renderBlocks.put(new BlockPos(0, 1, 0), new Tuple<>(MetaBlocks.MACHINE.getDefaultState(), tileEntityHolder));

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
            .widget(new SceneRenderWidget(0, 0, 400, 300, renderBlocks))
            .build(holder, entityPlayer);
    }

    private static void adjustConfiguration(PlayerInventoryHolder holder, int amount) {
        ItemStack stack = holder.getCurrentItem();
        int configuration = IntCircuitIngredient.getCircuitConfiguration(stack) + amount;
        configuration += amount;
        if(configuration < 0 || configuration > 32)
            configuration = 0;
        IntCircuitIngredient.setCircuitConfiguration(stack, configuration);
        holder.markAsDirty();
    }
}
