package gregtech.common.metatileentities.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.BitSet;
import java.util.List;

public class MetaTileEntityCharger extends TieredMetaTileEntity implements IEnergyContainer {

    private final int inventorySize;
    private boolean isChargingPlayers = false;
    private BitSet batterySlotsUsedThisTick = new BitSet();

    public MetaTileEntityCharger(ResourceLocation metaTileEntityId, int tier, int inventorySize) {
        super(metaTileEntityId, tier);
        this.inventorySize = inventorySize;
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityCharger(metaTileEntityId, getTier(), inventorySize);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        //TODO, doesn't currently change active texture
        Textures.CHARGER_OVERLAY.render(renderState, translation, pipeline, getFrontFacing(), isChargingPlayers);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(inventorySize) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                IElectricItem electricItem = stack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
                if(electricItem != null && getTier() >= electricItem.getTier()) {
                    return super.insertItem(slot, stack, simulate);
                }
                return stack;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(0);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.itemInventory = importItems;
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            this.batterySlotsUsedThisTick.clear();
            if(getEnergyStored() > 0) {
                chargePlayerInRegion();

                //Charge the internal batteries
                for (int i = 0; i < importItems.getSlots(); i++) {
                    ItemStack batteryStack = importItems.getStackInSlot(i);
                    IElectricItem electricItem = getBatteryContainer(batteryStack);

                    if (electricItem != null) {
                        long inputVoltage = Math.min(energyContainer.getInputVoltage(), energyContainer.getEnergyStored());
                        long energyUsed = electricItem.charge(inputVoltage, getTier(), false, false);
                        if(energyUsed > 0L) {
                            energyContainer.removeEnergy(energyUsed);
                            importItems.setStackInSlot(i, batteryStack);
                        }
                    }
                }
            }
        }
    }

    /**
     * Finds players within a 2 block region of the front face of the machine and charges items in their inventory
     */
    private void chargePlayerInRegion() {

        BlockPos endPos;
        //If not facing up or down, add an additional translation, to translate the charging region to be centered on a block,
        //rather than centered between blocks
        if (!(frontFacing == EnumFacing.UP || frontFacing == EnumFacing.DOWN)) {
            endPos = this.getPos().offset(frontFacing, 2).add(0, 1, 0).add(0, 0, 1);
        } else {
            endPos = this.getPos().offset(frontFacing, 2);
        }
        //Create a list of all players within the AABB created by the charge position and the end position of the charging region
        List<EntityPlayer> playersAbove = this.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.getPos(), endPos));
        //Update the charging boolean, so that the Active texture can be switched
        if(!playersAbove.isEmpty()) {
            isChargingPlayers = true;
        }
        //Charge all items in the player inventories
        for (EntityPlayer player : playersAbove) {

            for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
                chargeInventoryItems(player.inventory.mainInventory, i);
            }

            for (int i = 0; i < player.inventory.armorInventory.size(); i++) {
                chargeInventoryItems(player.inventory.armorInventory, i);
            }

            //Off hand is only 1 slot
            chargeInventoryItems(player.inventory.offHandInventory, 0);

        }
    }

    /**
     * Charges items in the inventory of the player, pulling first from any batteries in the Charger and then the
     * Energy Net
     */
    private void chargeInventoryItems(List<ItemStack> inventory, int index) {

        //The possible item to be charged
        ItemStack slotItem = inventory.get(index);
        boolean hasPossibleBattery = false;
        IElectricItem inventoryElectricItem = slotItem.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if (inventoryElectricItem == null || inventoryElectricItem.getCharge() == inventoryElectricItem.getMaxCharge()) {
            return;
        }


        IItemHandlerModifiable internalInventory = getImportItems();
        //Check if there are any possible batteries in the charger, or if the charger is empty
        for(int i = 0; i < internalInventory.getSlots(); i++) {
            if(!internalInventory.getStackInSlot(i).isEmpty()) {
                hasPossibleBattery = true;
                break;
            }
        }

        //The voltage at which the charging packets will be sent
        long voltage = getInputVoltage();
        long maxAmperage = 0L;
        TIntList slotsList = new TIntArrayList();

        //If the charger has possible battery items, check if they can be used to discharge energy
        //If there are no batteries in the Charger, or if all the Electric Items failed the discharge test,
        //items will be charged at 1A of the tier of voltage
        if(hasPossibleBattery) {
            for (int i = 0; i < internalInventory.getSlots(); i++) {
                ItemStack batteryStack = internalInventory.getStackInSlot(i);
                IElectricItem electricItem = getBatteryContainer(batteryStack);
                if (electricItem == null) continue;
                if (electricItem.discharge(voltage, getTier(), true, true, true) == voltage) {
                    slotsList.add(i);
                    maxAmperage++;
                }
            }
        }

        //If there are batteries in the Charger that can be discharged
        if(!slotsList.isEmpty()) {
            for (int i : slotsList.toArray()) {
                ItemStack batteryStack = internalInventory.getStackInSlot(i);
                IElectricItem electricItem = getBatteryContainer(batteryStack);
                if (electricItem == null) {
                    continue;
                }
                //Discharge the battery
                electricItem.discharge(voltage, getTier(), true, true, false);
                internalInventory.setStackInSlot(i, batteryStack);
                //Charge the item in the player's inventory
                inventoryElectricItem.charge(voltage, getTier(), true, false);
                inventory.set(index, slotItem);
                //Decrement from the maximum amperage available for charging items
                if (--maxAmperage == 0) break;
            }

        }
        else {
            //Charge from the energy net
            inventoryElectricItem.charge(voltage, getTier(), false, false);
            inventory.set(index, slotItem);
        }

        notifyEnergyListener(false);
    }

    public boolean getChargingStatus() {
        return isChargingPlayers;
    }

    /**
     * Used to charge the internal inventory of batteries from the energy net
     */
    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        long initialAmperage = amperage;
        if (side == null || inputsEnergy(side)) {
            if (voltage > getInputVoltage()) {
                GTUtility.doOvervoltageExplosion(this, voltage);
                return Math.min(amperage, getInputAmperage());
            }
            IItemHandlerModifiable inventory = getImportItems();
            for (int i = 0; i < inventory.getSlots(); i++) {
                if (batterySlotsUsedThisTick.get(i)) continue;
                ItemStack batteryStack = inventory.getStackInSlot(i);
                IElectricItem electricItem = getBatteryContainer(batteryStack);
                if (electricItem == null) continue;
                if (chargeItemWithVoltage(electricItem, voltage, getTier(), true)) {
                    chargeItemWithVoltage(electricItem, voltage, getTier(), false);
                    inventory.setStackInSlot(i, batteryStack);
                    this.batterySlotsUsedThisTick.set(i);
                    if (--amperage == 0) break;
                }
            }
        }
        long amperageUsed = initialAmperage - amperage;
        if (amperageUsed > 0L) {
            notifyEnergyListener(false);
        }
        return amperageUsed;
    }

    private static boolean chargeItemWithVoltage(IElectricItem electricItem, long voltage, int tier, boolean simulate) {
        long charged = electricItem.charge(voltage, tier, false, simulate);
        return charged > 0;
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return side != getFrontFacing();
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        energyToAdd = Math.abs(energyToAdd);
        long initialEnergyToAdd = energyToAdd;
        IItemHandlerModifiable inventory = getImportItems();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack batteryStack = inventory.getStackInSlot(i);
            IElectricItem electricItem = getBatteryContainer(batteryStack);
            if (electricItem == null) continue;
            long charged = electricItem.charge(energyToAdd, getTier(), false, false);
            energyToAdd -= charged;
            if (energyToAdd == 0L) break;
        }
        long energyAdded = initialEnergyToAdd - energyToAdd;
        if (energyAdded > 0L) {
            notifyEnergyListener(false);
        }
        return energyAdded;
    }

    @Override
    public long getEnergyStored() {
        long energyStored = 0L;
        IItemHandlerModifiable inventory = getImportItems();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack batteryStack = inventory.getStackInSlot(i);
            IElectricItem electricItem = getBatteryContainer(batteryStack);
            if (electricItem == null) continue;
            energyStored += electricItem.getCharge();
        }
        return energyStored;
    }

    @Override
    public long getEnergyCapacity() {
        long energyCapacity = 0L;
        IItemHandlerModifiable inventory = getImportItems();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack batteryStack = inventory.getStackInSlot(i);
            IElectricItem electricItem = getBatteryContainer(batteryStack);
            if (electricItem == null) continue;
            energyCapacity += electricItem.getMaxCharge();
        }
        return energyCapacity;
    }

    @Override
    public long getInputAmperage() {
        long inputAmperage = 0L;
        IItemHandlerModifiable inventory = getImportItems();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack batteryStack = inventory.getStackInSlot(i);
            IElectricItem electricItem = getBatteryContainer(batteryStack);
            if (electricItem == null) continue;
            inputAmperage++;
        }
        return inputAmperage;
    }

    @Override
    public long getInputVoltage() {
        return GTValues.V[getTier()];
    }

    public IElectricItem getBatteryContainer(ItemStack itemStack) {
        IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if (electricItem != null && getTier() >= electricItem.getTier() &&
                electricItem.canProvideChargeExternally())
            return electricItem;
        return null;
    }

    public void notifyEnergyListener(boolean isInitialChange) {
        ((EnergyContainerHandler.IEnergyChangeListener) this).onEnergyChanged(this.energyContainer, isInitialChange);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int rowSize = (int) Math.sqrt(inventorySize);
        Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176,
                18 + 18 * rowSize + 94)
                .label(10, 5, getMetaFullName());

        for (int y = 0; y < rowSize; y++) {
            for (int x = 0; x < rowSize; x++) {
                int index = y * rowSize + x;
                builder.widget(new SlotWidget(importItems, index, 89 - rowSize * 9 + x * 18, 18 + y * 18, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.CHARGER_OVERLAY));
            }
        }
        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 18 + 18 * rowSize + 12);
        return builder.build(getHolder(), entityPlayer);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.universal.tooltip.item_storage_capacity", inventorySize));
        tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_in", energyContainer.getInputVoltage(), GTValues.VN[getTier()]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
    }
}
