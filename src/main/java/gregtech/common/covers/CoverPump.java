package gregtech.common.covers;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.common.covers.CoverConveyor.ConveyorMode;
import gregtech.common.items.MetaItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class CoverPump extends CoverBehavior implements CoverWithUI, ITickable {

    public final int tier;
    public final int maxFluidTransferRate;
    protected int transferRate;
    protected PumpMode pumpMode;
    protected boolean isFilterInstalled;
    protected final ItemStackHandler filterTypeInventory;
    protected FluidStack[] fluidFilterSlots;
    protected int fluidLeftToTransferLastSecond;

    public CoverPump(ICoverable coverHolder, EnumFacing attachedSide, int tier, int mbPerTick) {
        super(coverHolder, attachedSide);
        this.tier = tier;
        this.maxFluidTransferRate = mbPerTick;
        this.transferRate = mbPerTick;
        this.fluidLeftToTransferLastSecond = transferRate;
        this.pumpMode = PumpMode.IMPORT;
        this.isFilterInstalled = false;
        this.fluidFilterSlots = new FluidStack[9];
        this.filterTypeInventory = new FilterTypeInventory();
    }

    protected void setTransferRate(int transferRate) {
        this.transferRate = transferRate;
        coverHolder.markDirty();
    }

    protected void adjustTransferRate(int amount) {
        setTransferRate(MathHelper.clamp(transferRate + amount, 1, maxFluidTransferRate));
    }

    public void setPumpMode(PumpMode pumpMode) {
        this.pumpMode = pumpMode;
        coverHolder.markDirty();
    }

    @Override
    public void update() {
        long timer = coverHolder.getTimer();
        if(timer % 5 == 0 && fluidLeftToTransferLastSecond > 0) {
            this.fluidLeftToTransferLastSecond -= doTransferFluids(fluidLeftToTransferLastSecond);
        }
        if(timer % 20 == 0) {
            this.fluidLeftToTransferLastSecond = transferRate;
        }
    }

    protected int doTransferFluids(int transferLimit) {
        TileEntity tileEntity = coverHolder.getWorld().getTileEntity(coverHolder.getPos().offset(attachedSide));
        IFluidHandler fluidHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, attachedSide.getOpposite());
        IFluidHandler myFluidHandler = coverHolder.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, attachedSide);
        if(fluidHandler == null || myFluidHandler == null) {
            return 0;
        }
        return doTransferFluidsInternal(myFluidHandler, fluidHandler, transferLimit);
    }

    protected int doTransferFluidsInternal(IFluidHandler myFluidHandler, IFluidHandler fluidHandler, int transferLimit) {
        if(pumpMode == PumpMode.IMPORT) {
            return moveHandlerFluids(fluidHandler, myFluidHandler, transferLimit);
        } else if(pumpMode == PumpMode.EXPORT) {
            return moveHandlerFluids(myFluidHandler, fluidHandler, transferLimit);
        }
        return 0;
    }

    protected int moveHandlerFluids(IFluidHandler sourceHandler, IFluidHandler destHandler, int transferLimit) {
        int fluidLeftToTransfer = transferLimit;
        System.out.println("We are here " + fluidLeftToTransfer + " " + sourceHandler);
        for(IFluidTankProperties tankProperties : sourceHandler.getTankProperties()) {
            FluidStack currentFluid = tankProperties.getContents();
            System.out.println("We are here 2 " + currentFluid + " " + tankProperties + " " + sourceHandler.drain(Integer.MAX_VALUE, false));
            if (currentFluid == null || currentFluid.amount == 0 || !checkInputFluid(currentFluid)) continue;
            System.out.println("We are here 3 " + currentFluid);
            currentFluid.amount = fluidLeftToTransfer;
            FluidStack fluidStack = sourceHandler.drain(currentFluid, false);
            if (fluidStack == null || fluidStack.amount == 0) continue;
            int canInsertAmount = destHandler.fill(fluidStack, false);
            if (canInsertAmount > 0) {
                fluidStack = sourceHandler.drain(canInsertAmount, true);
                if(fluidStack != null && fluidStack.amount > 0) {
                    destHandler.fill(fluidStack, true);
                    fluidLeftToTransfer -= fluidStack.amount;
                    if(fluidLeftToTransfer == 0) break;
                }
            }
        }
        return transferLimit - fluidLeftToTransfer;
    }

    protected boolean checkInputFluid(FluidStack fluidStack) {
        if(!isFilterInstalled) {
            return true;
        }
        for (FluidStack filterStack : fluidFilterSlots) {
            if (filterStack != null && filterStack.isFluidEqual(fluidStack)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup primaryGroup = new WidgetGroup();
        primaryGroup.addWidget(new LabelWidget(10, 5, "cover.pump.title", GTValues.VN[tier]));
        primaryGroup.addWidget(new ClickButtonWidget(10, 20, 20, 20, "-10", data -> adjustTransferRate(data.isShiftClick ? -100 : -10)));
        primaryGroup.addWidget(new ClickButtonWidget(146, 20, 20, 20, "+10", data -> adjustTransferRate(data.isShiftClick ? +100 : +10)));
        primaryGroup.addWidget(new ClickButtonWidget(30, 20, 20, 20, "-1", data -> adjustTransferRate(data.isShiftClick ? -5 : -1)));
        primaryGroup.addWidget(new ClickButtonWidget(126, 20, 20, 20, "+1", data -> adjustTransferRate(data.isShiftClick ? +5 : +1)));
        primaryGroup.addWidget(new ImageWidget(50, 20, 76, 20, GuiTextures.DISPLAY));
        primaryGroup.addWidget(new SimpleTextWidget(88, 30, "cover.pump.transfer_rate", 0xFFFFFF, () -> Integer.toString(transferRate)));

        primaryGroup.addWidget(new CycleButtonWidget(10, 45, 75, 20,
            GTUtility.mapToString(ConveyorMode.values(), it -> it.localeName),
            () -> pumpMode.ordinal(), newMode -> setPumpMode(PumpMode.values()[newMode])));
        primaryGroup.addWidget(new LabelWidget(10, 70, "cover.pump.fluid_filter.title"));
        primaryGroup.addWidget(new SlotWidget(filterTypeInventory, 0, 10, 85)
            .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.FILTER_SLOT_OVERLAY));

        ServerWidgetGroup fluidFilterGroup = new ServerWidgetGroup(() -> isFilterInstalled);
        for(int i = 0; i < 9; i++) {
            int slotIndex = i;
            fluidFilterGroup.addWidget(new PhantomFluidWidget(10 + 18 * (i % 3), 106 + 18 * (i / 3), 18, 18,
                () -> fluidFilterSlots[slotIndex], (newFluid) -> fluidFilterSlots[slotIndex] = newFluid)
                .setBackgroundTexture(GuiTextures.SLOT));
        }

        return ModularUI.builder(GuiTextures.BACKGROUND_EXTENDED, 176, 198)
            .widget(primaryGroup)
            .widget(fluidFilterGroup)
            .bindPlayerHotbar(player.inventory, GuiTextures.SLOT, 8, 170)
            .build(this, player);
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, float hitX, float hitY, float hitZ) {
        if(!coverHolder.getWorld().isRemote) {
            openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, attachedSide) != null;
    }

    @Override
    public void onRemoved() {
        NonNullList<ItemStack> drops = NonNullList.create();
        MetaTileEntity.clearInventory(drops, filterTypeInventory);
        for(ItemStack itemStack : drops) {
            Block.spawnAsEntity(coverHolder.getWorld(), coverHolder.getPos(), itemStack);
        }
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox) {
        Textures.PUMP_OVERLAY.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("TransferRate", transferRate);
        tagCompound.setInteger("PumpMode", pumpMode.ordinal());
        tagCompound.setTag("FilterTypeInventory", filterTypeInventory.serializeNBT());
        NBTTagList filterSlots = new NBTTagList();
        for(int i = 0; i < fluidFilterSlots.length; i++) {
            FluidStack fluidStack = fluidFilterSlots[i];
            if(fluidStack != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                fluidStack.writeToNBT(stackTag);
                stackTag.setInteger("Slot", i);
                filterSlots.appendTag(stackTag);
            }
        }
        tagCompound.setTag("FluidFilter", filterSlots);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.transferRate = tagCompound.getInteger("TransferRate");
        this.pumpMode = PumpMode.values()[tagCompound.getInteger("PumpMode")];
        this.filterTypeInventory.deserializeNBT(tagCompound.getCompoundTag("FilterTypeInventory"));
        NBTTagList filterSlots = tagCompound.getTagList("FluidFilter", NBT.TAG_COMPOUND);
        for(NBTBase nbtBase : filterSlots) {
            NBTTagCompound stackTag = (NBTTagCompound) nbtBase;
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(stackTag);
            this.fluidFilterSlots[stackTag.getInteger("Slot")] = fluidStack;
        }
    }

    private class FilterTypeInventory extends ItemStackHandler {

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if(!isFilterStack(stack)) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        protected void onLoad() {
            onContentsChanged(0);
        }

        @Override
        protected void onContentsChanged(int slot) {
            ItemStack itemStack = getStackInSlot(slot);
            CoverPump.this.isFilterInstalled = isFilterStack(itemStack);
        }

        private boolean isFilterStack(ItemStack itemStack) {
            return !itemStack.isEmpty() && MetaItems.FLUID_FILTER.isItemEqual(itemStack);
        }
    }

    public enum PumpMode {
        IMPORT("cover.pump.mode.import"),
        EXPORT("cover.pump.mode.export");

        public final String localeName;

        PumpMode(String localeName) {
            this.localeName = localeName;
        }
    }
}
