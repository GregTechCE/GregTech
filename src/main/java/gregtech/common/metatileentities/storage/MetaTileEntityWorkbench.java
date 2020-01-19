package gregtech.common.metatileentities.storage;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import com.google.common.base.Preconditions;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.PhantomSlotWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.common.gui.widget.CraftingSlotWidget;
import gregtech.common.inventory.itemsource.sources.InventoryItemSource;
import gregtech.common.inventory.itemsource.ItemSourceList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;

public class MetaTileEntityWorkbench extends MetaTileEntity {

    private ItemStackHandler internalInventory = new ItemStackHandler(18);

    private ItemStackHandler craftingGrid = new ItemStackHandler(9) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    private ItemStackHandler toolInventory = new ItemStackHandler(9) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!(stack.getItem() instanceof ToolMetaItem) &&
                !(stack.getItem() instanceof ItemTool) &&
                !(stack.isItemStackDamageable())) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }
    };

    private CraftingRecipeResolver recipeResolver = null;
    private int itemsCrafted = 0;

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
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("CraftingGridInventory", craftingGrid.serializeNBT());
        data.setTag("ToolInventory", toolInventory.serializeNBT());
        data.setTag("InternalInventory", internalInventory.serializeNBT());
        data.setInteger("ItemsCrafted", recipeResolver == null ? itemsCrafted : recipeResolver.getItemsCrafted());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.craftingGrid.deserializeNBT(data.getCompoundTag("CraftingGridInventory"));
        this.toolInventory.deserializeNBT(data.getCompoundTag("ToolInventory"));
        this.internalInventory.deserializeNBT(data.getCompoundTag("InternalInventory"));
        this.itemsCrafted = data.getInteger("ItemsCrafted");
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            getRecipeResolver().update();
        }
    }

    private CraftingRecipeResolver getRecipeResolver() {
        Preconditions.checkState(getWorld() != null, "getRecipeResolver called without world context");
        if (getWorld().isRemote) {
            //recipe resolver only exists on server side
            return null;
        }
        if (recipeResolver == null) {
            this.recipeResolver = new CraftingRecipeResolver(getWorld(), craftingGrid);
            this.recipeResolver.setItemsCrafted(itemsCrafted);
            ItemSourceList itemSourceList = this.recipeResolver.getItemSourceList();
            itemSourceList.addItemHandler(InventoryItemSource.direct(getWorld(), toolInventory, 100));
            itemSourceList.addItemHandler(InventoryItemSource.direct(getWorld(), internalInventory, 99));
        }
        return recipeResolver;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BORDERED_BACKGROUND, 176, 221)
            .bindPlayerInventory(entityPlayer.inventory, 140);

        builder.label(5, 5, getMetaFullName());
        //crafting grid
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                builder.widget(new PhantomSlotWidget(craftingGrid, j + i * 3, 8 + j * 18, 17 + i * 18).setBackgroundTexture(GuiTextures.SLOT));
            }
        }
        CraftingRecipeResolver recipeResolver = getRecipeResolver();
        builder.image(88-13, 44-13, 26, 26, GuiTextures.SLOT);
        builder.widget(new CraftingSlotWidget(recipeResolver, 0, 88-9, 44-9));

        builder.image(168-18*3, 44-18*3/2, 18*3, 18*3, TextureArea.fullImage("textures/gui/base/darkened_slot.png"));
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                //TODO JEI recipe transfer support
                //TODO recipe memory
                //TODO clear grid button
                //TODO external inventory support
                //TODO display amount of items crafted
                //builder.widget(new SlotWidget(craftingGridHandler, j + i * 3, 168-18*3/2-27 + j * 18, 44-27 + i * 18));
            }
        }

        //tool inventory
        for (int i = 0; i < 9; i++) {
            builder.widget(new SlotWidget(toolInventory, i, 8 + i * 18, 76).setBackgroundTexture(GuiTextures.SLOT, GuiTextures.TOOL_SLOT_OVERLAY));
        }
        //internal inventory
        for(int i = 0; i < 2; ++i) {
            for(int j = 0; j < 9; ++j) {
                builder.widget(new SlotWidget(internalInventory, j + i * 9, 8 + j * 18, 99 + i * 18).setBackgroundTexture(GuiTextures.SLOT));
            }
        }
        return builder.build(getHolder(), entityPlayer);
    }
}
