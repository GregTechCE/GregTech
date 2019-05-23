package gregtech.common.metatileentities.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.api.unification.OreDictUnifier;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.SlotWidget;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


public class MetaTileEntityFisher extends TieredMetaTileEntity {

    private final int inventorySize;
    private final long fishingTicks;
    private final long energyAmountPerFish;
    private static final long waterCheckSize = 25L;

    public MetaTileEntityFisher(ResourceLocation metaTileEntityId, int tier){
        super(metaTileEntityId, tier);
        this.inventorySize = (tier + 1) * (tier + 1);
        this.fishingTicks = 1000 - tier * 200;
        this.energyAmountPerFish = tier == 0 ? 1 : GTValues.V[getTier() - 1];
        initializeInventory();
    }

    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityFisher(metaTileEntityId, getTier());
    }

    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int rowSize = (int) Math.sqrt(inventorySize);

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176,
            18 + 18 * rowSize + 94)
            .label(10, 5, getMetaFullName())
            .widget(new SlotWidget(importItems, 0, 18, 18, true, true)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.IN_SLOT_OVERLAY));

        for (int y = 0; y < rowSize; y++) {
            for (int x = 0; x < rowSize; x++) {
                int index = y * rowSize + x;
                builder.widget(new SlotWidget(exportItems, index, 89 - rowSize * 9 + x * 18, 18 + y * 18, true, false)
                    .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.OUT_SLOT_OVERLAY));
            }
        }

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 18 + 18 * rowSize + 12);
        return builder.build(getHolder(), entityPlayer);
    }

    public void update() {
        super.update();
        if (!getWorld().isRemote && energyContainer.getEnergyStored() >= energyAmountPerFish && getTimer() % fishingTicks == 0L &&
            importItems.getStackInSlot(0).getCount() > 0) {
            WorldServer world = (WorldServer) this.getWorld();
            int waterCount = 0;
            int edgeSize = (int) Math.sqrt(waterCheckSize);
            for (int x = 0; x < edgeSize; x++){
                for (int z = 0; z < edgeSize; z++){
                    BlockPos waterCheckPos = getPos().down().add(x - edgeSize / 2, 0, z - edgeSize / 2);
                    if (world.getBlockState(waterCheckPos).getBlock() instanceof BlockLiquid &&
                        world.getBlockState(waterCheckPos).getMaterial() == Material.WATER) {
                        waterCount++;
                    }
                }
            }
            if (waterCount == waterCheckSize) {
                LootTable table = world.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING);
                List<ItemStack> itemStacks = table.generateLootForPools(world.rand, new LootContext.Builder(world).build());
                addToInventoryOrDropItems(itemStacks);
                importItems.getStackInSlot(0).shrink(1);
                energyContainer.removeEnergy(energyAmountPerFish);
            }
        }
    }

    private void addToInventoryOrDropItems(List<ItemStack> drops) {
        EnumFacing outputFacing = getFrontFacing();
        double itemSpawnX = getPos().getX() + 0.5 + outputFacing.getFrontOffsetX();
        double itemSpawnY = getPos().getY() + 0.5 + outputFacing.getFrontOffsetY();
        double itemSpawnZ = getPos().getZ() + 0.5 + outputFacing.getFrontOffsetZ();
        for(ItemStack itemStack : drops) {
            ItemStack remainStack = ItemHandlerHelper.insertItemStacked(exportItems, itemStack, false);
            if(!remainStack.isEmpty()) {
                EntityItem entityitem = new EntityItem(getWorld(), itemSpawnX, itemSpawnY, itemSpawnZ, remainStack);
                entityitem.setDefaultPickupDelay();
                getWorld().spawnEntity(entityitem);
            }
        }
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(1) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(OreDictUnifier.getOreDictionaryNames(stack).contains("string")) {
                    return super.insertItem(slot, stack, simulate);
                }
                return stack;
            }
        };
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(inventorySize);
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.SCREEN.renderSided(EnumFacing.UP, renderState, translation, pipeline);
        Textures.PIPE_IN_OVERLAY.renderSided(EnumFacing.DOWN, renderState, translation, pipeline);
    }

    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.machine.fisher.speed", fishingTicks));
        tooltip.add(I18n.format("gregtech.machine.fisher.requirement", waterCheckSize));
        tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_in", energyContainer.getInputVoltage(), "LV"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
    }
}
