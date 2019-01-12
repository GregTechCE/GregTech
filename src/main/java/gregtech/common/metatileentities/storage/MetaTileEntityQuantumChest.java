package gregtech.common.metatileentities.storage;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Vector3;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityQuantumChest extends MetaTileEntity {

    private static final double[] rotations = new double[] {180.0, 0.0, -90.0, 90.0};

    private final int tier;
    private final long maxStoredItems;
    private ItemStack itemStack = ItemStack.EMPTY;
    private long itemsStoredInside = 0L;

    public MetaTileEntityQuantumChest(ResourceLocation metaTileEntityId, int tier, long maxStoredItems) {
        super(metaTileEntityId);
        this.tier = tier;
        this.maxStoredItems = maxStoredItems;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityQuantumChest(metaTileEntityId, tier, maxStoredItems);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        Textures.VOLTAGE_CASINGS[tier].render(renderState, translation, ArrayUtils.add(pipeline,
            new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()))));
        translation.translate(0.5, 0.001, 0.5);
        translation.rotate(Math.toRadians(rotations[getFrontFacing().getIndex() - 2]), new Vector3(0.0, 1.0, 0.0));
        translation.translate(-0.5, 0.0, -0.5);
        Textures.SCREEN.renderSided(EnumFacing.UP, renderState, translation, pipeline);
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return Textures.VOLTAGE_CASINGS[tier].getParticleSprite();
    }

    @Override
    public int getComparatorValue() {
        float f = itemsStoredInside / (maxStoredItems * 1.0f);
        return MathHelper.floor(f * 14.0f) + (itemsStoredInside > 0 ? 1 : 0);
    }

    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote) {
            if(itemsStoredInside < maxStoredItems) {
                ItemStack inputStack = importItems.getStackInSlot(0);
                if(!inputStack.isEmpty() && (itemStack.isEmpty() || areItemStackIdentical(itemStack, inputStack))) {
                    int amountOfItemsToInsert = (int) Math.min(inputStack.getCount(), maxStoredItems - itemsStoredInside);
                    if(this.itemsStoredInside == 0L || itemStack.isEmpty()) {
                        this.itemStack = GTUtility.copyAmount(1, inputStack);
                    }
                    inputStack.shrink(amountOfItemsToInsert);
                    importItems.setStackInSlot(0, inputStack);
                    this.itemsStoredInside += amountOfItemsToInsert;
                    updateComparatorValue(true);
                    markDirty();
                }
            }
            if(itemsStoredInside > 0 && !itemStack.isEmpty()) {
                ItemStack outputStack = exportItems.getStackInSlot(0);
                int maxStackSize = itemStack.getMaxStackSize();
                if(outputStack.isEmpty() || (areItemStackIdentical(itemStack, outputStack) && outputStack.getCount() < maxStackSize)) {
                    int amountOfItemsToRemove = (int) Math.min(maxStackSize - outputStack.getCount(), itemsStoredInside);
                    if(outputStack.isEmpty()) {
                        outputStack = GTUtility.copyAmount(amountOfItemsToRemove, itemStack);
                    } else outputStack.grow(amountOfItemsToRemove);
                    exportItems.setStackInSlot(0, outputStack);
                    this.itemsStoredInside -= amountOfItemsToRemove;
                    if(this.itemsStoredInside == 0) {
                        this.itemStack = ItemStack.EMPTY;
                    }
                    updateComparatorValue(true);
                    markDirty();
                }
            }
        }
    }

    private static boolean areItemStackIdentical(ItemStack first, ItemStack second) {
        return ItemStack.areItemsEqual(first, second) &&
            ItemStack.areItemStackTagsEqual(first, second);
    }

    protected void addDisplayInformation(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("gregtech.machine.quantum_chest.items_stored"));
        textList.add(new TextComponentString(String.format("%,d", itemsStoredInside)));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format(getTierlessTooltipKey()));
        tooltip.add(I18n.format("gregtech.machine.quantum_chest.capacity", maxStoredItems));
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public boolean hasFrontFacing() {
        return false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        NBTTagCompound tagCompound = super.writeToNBT(data);
        if(!itemStack.isEmpty() && itemsStoredInside > 0L) {
            tagCompound.setTag("ItemStack", itemStack.writeToNBT(new NBTTagCompound()));
            tagCompound.setLong("ItemAmount", itemsStoredInside);
        }
        return tagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if(data.hasKey("ItemStack", NBT.TAG_COMPOUND)) {
            this.itemStack = new ItemStack(data.getCompoundTag("ItemStack"));
            if(!itemStack.isEmpty()) {
                this.itemsStoredInside = data.getLong("ItemAmount");
            }
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        Builder builder = ModularUI.defaultBuilder();
        builder.image(7, 16, 81, 55, GuiTextures.DISPLAY);
        builder.widget(new AdvancedTextWidget(11, 20, this::addDisplayInformation, 0xFFFFFF));
        return builder.label(6, 6, getMetaFullName())
            .widget(new SlotWidget(importItems, 0, 90, 17, true, true)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.IN_SLOT_OVERLAY))
            .widget(new SlotWidget(exportItems, 0, 90, 54, true, false)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.OUT_SLOT_OVERLAY))
            .bindPlayerInventory(entityPlayer.inventory)
            .build(getHolder(), entityPlayer);
    }

    @Nonnull
    public final String getTierlessTooltipKey() {
        String metaName = getMetaName();
        int lastIndexOfDot = metaName.lastIndexOf('.');
        String subName = lastIndexOfDot == -1 ? metaName : metaName.substring(0, lastIndexOfDot);
        return subName + ".tooltip";
    }

}
