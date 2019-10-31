package gregtech.common.metatileentities.storage;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.gui.widgets.TabGroup.TabLocation;
import gregtech.api.gui.widgets.tab.ItemTabInfo;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.api.util.Position;
import gregtech.api.util.Size;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

public class MetaTileEntityWorkbench extends MetaTileEntity {

    public MetaTileEntityWorkbench(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityWorkbench(metaTileEntityId);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        TextureAtlasSprite craftingTable = TextureUtils.getBlockTexture("crafting_table_top");
        pipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColor())));
        for (EnumFacing side : EnumFacing.VALUES) {
            Textures.renderFace(renderState, translation, pipeline, side, Cuboid6.full, craftingTable);
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BORDERED_BACKGROUND, 176, 198)
            .bindPlayerInventory(entityPlayer.inventory, 117);

        ItemStackHandler craftingGridHandler = new ItemStackHandler(9);
        ItemStackHandler resultSlotInventory = new ItemStackHandler(1);
        ItemStackHandler internalInventory = new ItemStackHandler(18);

        builder.label(5, 5, getMetaFullName());

        WidgetGroup containerGroup = new WidgetGroup();
        ScrollableListWidget scrollableListWidget = new ScrollableListWidget(11, 15, 154, 90);
        containerGroup.addWidget(scrollableListWidget);
        for (int j = 0; j < 20; j++) {
            WidgetGroup slotGroup = new WidgetGroup(new Position(0, 0), new Size(144, 18));
            for (int i = 0; i < 8; i++) {
                slotGroup.addWidget(new SlotWidget(internalInventory, (j + i) % 18, i * 18, 0)
                    .setBackgroundTexture(GuiTextures.SLOT));
            }
            scrollableListWidget.addWidget(slotGroup);
        }

        TabGroup tabGroup = new TabGroup(TabLocation.HORIZONTAL_TOP_LEFT);
        WidgetGroup craftingGroup = new WidgetGroup();
        tabGroup.addTab(new ItemTabInfo("gregtech.machine.workbench.tab.crafting", new ItemStack(Blocks.CRAFTING_TABLE)), craftingGroup);
        tabGroup.addTab(new ItemTabInfo("gregtech.machine.workbench.tab.container", new ItemStack(Blocks.CHEST)), containerGroup);

        craftingGroup.addWidget(new ImageWidget(124 + 8 - 13, 35 + 8 - 13, 26, 26, GuiTextures.SLOT_BIG));
        craftingGroup.addWidget(new SlotWidget(resultSlotInventory, 0, 124, 35));

        //crafting grid
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                craftingGroup.addWidget(new PhantomSlotWidget(craftingGridHandler, j + i * 3, 30 + j * 18, 17 + i * 18).setBackgroundTexture(GuiTextures.SLOT));
            }
        }
        //internal inventory
        for(int i = 0; i < 2; ++i) {
            for(int j = 0; j < 9; ++j) {
                craftingGroup.addWidget(new SlotWidget(internalInventory, j + i * 9, 8 + j * 18, 76 + i * 18).setBackgroundTexture(GuiTextures.SLOT));
            }
        }
        return builder.widget(tabGroup).build(getHolder(), entityPlayer);
    }
}
