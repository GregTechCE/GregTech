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
import gregtech.api.metatileentity.ITieredMetaTileEntity;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityQuantumChest extends MetaTileEntity implements ITieredMetaTileEntity {

    private static final double[] rotations = new double[]{180.0, 0.0, -90.0, 90.0};

    private final int tier;
    private final long maxStoredItems;
    private ItemStack itemStack = ItemStack.EMPTY;
    private long itemsStoredInside = 0L;

    private static final String NBT_ITEMSTACK = "ItemStack";
    private static final String NBT_PARTIALSTACK = "PartialStack";
    private static final String NBT_ITEMCOUNT = "ItemAmount";

    public MetaTileEntityQuantumChest(ResourceLocation metaTileEntityId, int tier, long maxStoredItems) {
        super(metaTileEntityId);
        this.tier = tier;
        this.maxStoredItems = maxStoredItems;
    }

    @Override
    public int getTier() {
        return tier;
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
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        return Pair.of(Textures.VOLTAGE_CASINGS[tier].getParticleSprite(), getPaintingColor());
    }

    @Override
    public int getActualComparatorValue() {
        float f = itemsStoredInside / (maxStoredItems * 1.0f);
        return MathHelper.floor(f * 14.0f) + (itemsStoredInside > 0 ? 1 : 0);
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            if (itemsStoredInside < maxStoredItems) {
                ItemStack inputStack = importItems.getStackInSlot(0);
                ItemStack outputStack = exportItems.getStackInSlot(0);
                if (outputStack.isEmpty() || outputStack.isItemEqual(inputStack) && ItemStack.areItemStackTagsEqual(inputStack, outputStack)) {
                    if (!inputStack.isEmpty() && (itemStack.isEmpty() || areItemStackIdentical(itemStack, inputStack))) {
                        int amountOfItemsToInsert = (int) Math.min(inputStack.getCount(), maxStoredItems - itemsStoredInside);
                        if (this.itemsStoredInside == 0L || itemStack.isEmpty()) {
                            this.itemStack = GTUtility.copyAmount(1, inputStack);
                        }
                        inputStack.shrink(amountOfItemsToInsert);
                        importItems.setStackInSlot(0, inputStack);
                        this.itemsStoredInside += amountOfItemsToInsert;
                        markDirty();
                    }
                }
            }
            if (itemsStoredInside > 0 && !itemStack.isEmpty()) {
                ItemStack outputStack = exportItems.getStackInSlot(0);
                int maxStackSize = itemStack.getMaxStackSize();
                if (outputStack.isEmpty() || (areItemStackIdentical(itemStack, outputStack) && outputStack.getCount() < maxStackSize)) {
                    int amountOfItemsToRemove = (int) Math.min(maxStackSize - outputStack.getCount(), itemsStoredInside);
                    if (outputStack.isEmpty()) {
                        outputStack = GTUtility.copyAmount(amountOfItemsToRemove, itemStack);
                    } else outputStack.grow(amountOfItemsToRemove);
                    exportItems.setStackInSlot(0, outputStack);
                    this.itemsStoredInside -= amountOfItemsToRemove;
                    if (this.itemsStoredInside == 0) {
                        this.itemStack = ItemStack.EMPTY;
                    }
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
        tooltip.add(I18n.format("gregtech.machine.quantum_chest.capacity", maxStoredItems));

        NBTTagCompound compound = stack.getTagCompound();
        if (compound != null) {
            String translationKey = null;
            long count = 0;
            if (compound.hasKey(NBT_ITEMSTACK)) {
                translationKey = new ItemStack(compound.getCompoundTag(NBT_ITEMSTACK)).getDisplayName();
                count = compound.getLong(NBT_ITEMCOUNT);
            } else if (compound.hasKey(NBT_PARTIALSTACK)) {
                ItemStack tempStack = new ItemStack(compound.getCompoundTag(NBT_PARTIALSTACK));
                translationKey = tempStack.getDisplayName();
                count = tempStack.getCount();
            }
            if (translationKey != null) {
                tooltip.add(I18n.format("gregtech.machine.quantum_chest.tooltip.item",
                        I18n.format(translationKey)));
                tooltip.add(I18n.format("gregtech.machine.quantum_chest.tooltip.count", count));
            }
        }
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.itemInventory = new QuantumChestItemHandler();
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
        if (!itemStack.isEmpty() && itemsStoredInside > 0L) {
            tagCompound.setTag(NBT_ITEMSTACK, itemStack.writeToNBT(new NBTTagCompound()));
            tagCompound.setLong(NBT_ITEMCOUNT, itemsStoredInside);
        }
        return tagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if (data.hasKey(NBT_ITEMSTACK, NBT.TAG_COMPOUND)) {
            this.itemStack = new ItemStack(data.getCompoundTag(NBT_ITEMSTACK));
            if (!itemStack.isEmpty()) {
                this.itemsStoredInside = data.getLong(NBT_ITEMCOUNT);
            }
        }
    }

    @Override
    public void initFromItemStackData(NBTTagCompound itemStack) {
        super.initFromItemStackData(itemStack);
        if (itemStack.hasKey(NBT_ITEMSTACK, NBT.TAG_COMPOUND)) {
            this.itemStack = new ItemStack(itemStack.getCompoundTag(NBT_ITEMSTACK));
            if (!this.itemStack.isEmpty()) {
                this.itemsStoredInside = itemStack.getLong(NBT_ITEMCOUNT);
            }
        } else if (itemStack.hasKey(NBT_PARTIALSTACK, NBT.TAG_COMPOUND)) {
            exportItems.setStackInSlot(0, new ItemStack(itemStack.getCompoundTag(NBT_PARTIALSTACK)));
        }
    }

    @Override
    public void writeItemStackData(NBTTagCompound itemStack) {
        super.writeItemStackData(itemStack);
        if (!this.itemStack.isEmpty()) {
            itemStack.setTag(NBT_ITEMSTACK, this.itemStack.writeToNBT(new NBTTagCompound()));
            itemStack.setLong(NBT_ITEMCOUNT, itemsStoredInside + this.itemStack.getMaxStackSize());
        } else {
            ItemStack partialStack = exportItems.extractItem(0, 64, false);
            if (!partialStack.isEmpty()) {
                itemStack.setTag(NBT_PARTIALSTACK, partialStack.writeToNBT(new NBTTagCompound()));
            }
        }
        this.itemStack = ItemStack.EMPTY;
        this.itemsStoredInside = 0;
        exportItems.setStackInSlot(0, ItemStack.EMPTY);
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

    @Override
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        clearInventory(itemBuffer, importItems);
    }

    private class QuantumChestItemHandler implements IItemHandler {

        @Override
        public int getSlots() {
            return 1;
        }

        @Nonnull
        @Override
        public ItemStack getStackInSlot(int slot) {
            ItemStack itemStack = MetaTileEntityQuantumChest.this.itemStack;
            long itemsStored = MetaTileEntityQuantumChest.this.itemsStoredInside;
            if (itemStack.isEmpty() || itemsStored == 0L) {
                return ItemStack.EMPTY;
            }
            ItemStack resultStack = itemStack.copy();
            resultStack.setCount((int) itemsStored);
            return resultStack;
        }

        @Override
        public int getSlotLimit(int slot) {
            return (int) MetaTileEntityQuantumChest.this.maxStoredItems;
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            int extractedAmount = (int) Math.min(amount, itemsStoredInside);
            if (itemStack.isEmpty() || extractedAmount == 0) {
                return ItemStack.EMPTY;
            }
            ItemStack extractedStack = itemStack.copy();
            extractedStack.setCount(extractedAmount);
            if (!simulate) {
                MetaTileEntityQuantumChest.this.itemsStoredInside -= extractedAmount;
                if (itemsStoredInside == 0L) {
                    MetaTileEntityQuantumChest.this.itemStack = ItemStack.EMPTY;
                }
            }
            return extractedStack;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if(stack.isEmpty()) {
                return ItemStack.EMPTY;
            }
            if (itemsStoredInside > 0L &&
                !itemStack.isEmpty() &&
                !areItemStackIdentical(itemStack, stack)) {
                return stack;
            }
            long amountLeftInChest = itemStack.isEmpty() ? maxStoredItems : maxStoredItems - itemsStoredInside;
            int insertedAmount = (int) Math.min(stack.getCount(), amountLeftInChest);

            if (insertedAmount == 0) {
                return stack;
            }
            ItemStack remainingStack = ItemStack.EMPTY;
            if(stack.getCount() > insertedAmount) {
                remainingStack = stack.copy();
                remainingStack.setCount(stack.getCount() - insertedAmount);
                return ItemStack.EMPTY;
            }
            if (!simulate) {
                if (itemStack.isEmpty()) {
                    MetaTileEntityQuantumChest.this.itemStack = stack.copy();
                    MetaTileEntityQuantumChest.this.itemsStoredInside = insertedAmount;
                } else {
                    MetaTileEntityQuantumChest.this.itemsStoredInside += insertedAmount;
                }
            }
            return remainingStack;
        }
    }

}
