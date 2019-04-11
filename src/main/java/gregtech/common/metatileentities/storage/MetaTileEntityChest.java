package gregtech.common.metatileentities.storage;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.gui.impl.ModularUIContainer;
import gregtech.api.gui.widgets.SortingButtonWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.recipes.ModHandler;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MetaTileEntityChest extends MetaTileEntity {

    private static final IndexedCuboid6 CHEST_COLLISION = new IndexedCuboid6(null, new Cuboid6(1 / 16.0, 0 / 16.0, 1 / 16.0, 15 / 16.0, 14 / 16.0, 15 / 16.0));

    private final SolidMaterial material;
    private final int rowSize;
    private final int amountOfRows;
    private ItemStackHandler inventory;

    private float lidAngle;
    private float prevLidAngle;
    private int numPlayersUsing;

    public MetaTileEntityChest(ResourceLocation metaTileEntityId, SolidMaterial material, int rowSize, int amountOfRows) {
        super(metaTileEntityId);
        this.material = material;
        this.rowSize = rowSize;
        this.amountOfRows = amountOfRows;
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityChest(metaTileEntityId, material, rowSize, amountOfRows);
    }

    @Override
    public void update() {
        super.update();
        BlockPos blockPos = getPos();
        this.prevLidAngle = this.lidAngle;

        if (!getWorld().isRemote && this.numPlayersUsing != 0 && getTimer() % 200 == 0) {
            int lastPlayersUsing = numPlayersUsing;
            this.numPlayersUsing = 0;
            AxisAlignedBB box = new AxisAlignedBB(getPos()).expand(10.0, 10.0, 10.0);
            List<EntityPlayerMP> entities = getWorld().getEntitiesWithinAABB(EntityPlayerMP.class, box);
            for (EntityPlayerMP player : entities) {
                if (player.openContainer instanceof ModularUIContainer) {
                    ModularUI modularUI = ((ModularUIContainer) player.openContainer).getModularUI();
                    if (modularUI.holder instanceof MetaTileEntityHolder &&
                        ((MetaTileEntityHolder) modularUI.holder).getMetaTileEntity() == this) {
                        this.numPlayersUsing++;
                    }
                }
            }
            if (lastPlayersUsing != numPlayersUsing) {
                updateNumPlayersUsing();
            }
        }

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
            double soundX = blockPos.getX() + 0.5;
            double soundZ = blockPos.getZ() + 0.5;
            double soundY = blockPos.getY() + 0.5;
            getWorld().playSound(null, soundX, soundY, soundZ, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, getWorld().rand.nextFloat() * 0.1F + 0.9F);
        }

        if ((numPlayersUsing == 0 && this.lidAngle > 0.0F) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0F)) {
            float currentValue = this.lidAngle;

            if (this.numPlayersUsing > 0) {
                this.lidAngle += 0.1F;
            } else {
                this.lidAngle -= 0.1F;
            }

            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            } else if (this.lidAngle < 0.0F) {
                this.lidAngle = 0.0F;
            }

            if (this.lidAngle < 0.5F && currentValue >= 0.5F) {
                double soundX = blockPos.getX() + 0.5;
                double soundZ = blockPos.getZ() + 0.5;
                double soundY = blockPos.getY() + 0.5;
                getWorld().playSound(null, soundX, soundY, soundZ, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, getWorld().rand.nextFloat() * 0.1F + 0.9F);
            }
        }
    }

    private void onContainerOpen(EntityPlayer player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            updateNumPlayersUsing();
        }
    }

    private void onContainerClose(EntityPlayer player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
            updateNumPlayersUsing();
        }
    }

    private void updateNumPlayersUsing() {
        writeCustomData(100, buffer -> buffer.writeVarInt(numPlayersUsing));
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeVarInt(numPlayersUsing);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.numPlayersUsing = buf.readVarInt();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == 100) {
            this.numPlayersUsing = buf.readVarInt();
        }
    }

    @Override
    public int getLightOpacity() {
        return 1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean requiresDynamicRendering() {
        return true;
    }

    @Override
    public String getHarvestTool() {
        return ModHandler.isMaterialWood(material) ? "axe" : "pickaxe";
    }

    @Override
    public void addCollisionBoundingBox(List<IndexedCuboid6> collisionList) {
        collisionList.add(CHEST_COLLISION);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.inventory = new ItemStackHandler(rowSize * amountOfRows) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                updateComparatorValue();
            }
        };
        this.itemInventory = inventory;
        updateComparatorValue();
    }

    @Override
    public int getActualComparatorValue() {
        return ItemHandlerHelper.calcRedstoneFromInventory(inventory);
    }

    @Override
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        clearInventory(itemBuffer, inventory);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleTexture() {
        return ModHandler.isMaterialWood(material) ? Textures.WOODEN_CHEST.getParticleTexture() :
            Textures.METAL_CHEST.getParticleTexture();
    }

    @Override
    public double getCoverPlateThickness() {
        return 1.0 / 16.0; //1/16th of the block size
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
    }

    @Override
    public void renderMetaTileEntityDynamic(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, float partialTicks) {
        float angle = prevLidAngle + (lidAngle - prevLidAngle) * partialTicks;
        angle = 1.0f - (1.0f - angle) * (1.0f - angle) * (1.0f - angle);
        float resultLidAngle = angle * 90.0f;
        if (ModHandler.isMaterialWood(material)) {
            ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
            Textures.WOODEN_CHEST.render(renderState, translation, new IVertexOperation[]{multiplier}, getFrontFacing(), resultLidAngle);
        } else {
            ColourMultiplier multiplier = new ColourMultiplier(ColourRGBA.multiply(
                GTUtility.convertRGBtoOpaqueRGBA_CL(material.materialRGB),
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
            Textures.METAL_CHEST.render(renderState, translation, new IVertexOperation[]{multiplier}, getFrontFacing(), resultLidAngle);
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        Builder builder = ModularUI.builder(GuiTextures.BACKGROUND,
            14 + rowSize * 18,
            18 + 18 * amountOfRows + 94)
            .label(5, 5, getMetaFullName());
        builder.widget(new SortingButtonWidget(111, 4, 60, 10, "gregtech.gui.sort",
            (info) -> sortInventorySlotContents(inventory)));

        for (int y = 0; y < amountOfRows; y++) {
            for (int x = 0; x < rowSize; x++) {
                int index = y * rowSize + x;
                builder.slot(inventory, index, 8 + x * 18, 18 + y * 18, GuiTextures.SLOT);
            }
        }
        int startX = (14 + rowSize * 18 - 162) / 2;
        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, startX, 18 + 18 * amountOfRows + 12);
        if (!getWorld().isRemote) {
            builder.bindOpenListener(() -> onContainerOpen(entityPlayer));
            builder.bindCloseListener(() -> onContainerClose(entityPlayer));
        }
        return builder.build(getHolder(), entityPlayer);
    }

    private static void sortInventorySlotContents(IItemHandlerModifiable inventory) {
        //stack item stacks with equal items and compounds
        for (int i = 0; i < inventory.getSlots(); i++) {
            for (int j = i + 1; j < inventory.getSlots(); j++) {
                ItemStack stack1 = inventory.getStackInSlot(i);
                ItemStack stack2 = inventory.getStackInSlot(j);
                if (!stack1.isEmpty() && ItemStack.areItemsEqual(stack1, stack2) &&
                    ItemStack.areItemStackTagsEqual(stack1, stack2)) {
                    int maxStackSize = Math.min(stack1.getMaxStackSize(), inventory.getSlotLimit(i));
                    int itemsCanAccept = Math.min(stack2.getCount(), maxStackSize - Math.min(stack1.getCount(), maxStackSize));
                    if (itemsCanAccept > 0) {
                        stack1.grow(itemsCanAccept);
                        stack2.shrink(itemsCanAccept);
                    }
                }
            }
        }
        //create itemstack pairs and sort them out by attributes
        ArrayList<ItemStack> inventoryContents = new ArrayList<>();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                inventory.setStackInSlot(i, ItemStack.EMPTY);
                inventoryContents.add(itemStack);
            }
        }
        inventoryContents.sort(MetaTileEntityChest::compareItemStacks);
        for (int i = 0; i < inventoryContents.size(); i++) {
            inventory.setStackInSlot(i, inventoryContents.get(i));
        }
    }

    private static int compareItemStacks(ItemStack stack1, ItemStack stack2) {
        /*String firstStackName = stack1.getItem().getRegistryName().toString();
        String secondStackName = stack2.getItem().getRegistryName().toString();
        int result = firstStackName.compareTo(secondStackName);
        if(result != 0) {
            return result;
        }*/
        //so far InventoryTweaks use sorting by raw numeric IDs, and we'll too to keep things consistent
        //TODO make this use registry names after 1.13 transition because IDs don't make sense
        int firstItemId = Item.REGISTRY.getIDForObject(stack1.getItem());
        int secondItemId = Item.REGISTRY.getIDForObject(stack2.getItem());
        int result = Integer.compare(firstItemId, secondItemId);
        if (result != 0) {
            return result;
        }
        result = Integer.compare(stack1.getItemDamage(), stack2.getItemDamage());
        if (result != 0) {
            return result;
        }
        if (stack1.hasTagCompound() != stack2.hasTagCompound()) {
            return stack1.hasTagCompound() ? 1 : -1;
        }
        if (stack1.hasTagCompound() && !stack1.getTagCompound().equals(stack2.getTagCompound())) {
            result = -Integer.compare(stack1.getTagCompound().hashCode(), stack2.getTagCompound().hashCode());
            if (result != 0) {
                return result;
            }
        }
        result = -Integer.compare(stack1.getCount(), stack2.getCount());
        return result;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("Inventory", inventory.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.inventory.deserializeNBT(data.getCompoundTag("Inventory"));
    }

    @Override
    protected boolean shouldSerializeInventories() {
        return false; //handled manually
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.universal.tooltip.item_storage_capacity", rowSize * amountOfRows));
    }
}
