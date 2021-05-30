package gregtech.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IThrottle;
import gregtech.api.capability.IThrottleable;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.SimpleTextWidget;
import gregtech.api.render.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;

public class CoverThrottle extends CoverBehavior implements CoverWithUI, IThrottle {

    private int throttlePercentage;

    public CoverThrottle(final ICoverable coverHolder, final EnumFacing attachedSide) {
        super(coverHolder, attachedSide);
        this.throttlePercentage = 100;
    }

    @Override
    public int getThrottlePercentage() {
        return this.throttlePercentage;
    }

    public void setThrottlePercentage(final int throttlePercentage) {
        this.throttlePercentage = MathHelper.clamp(throttlePercentage, 20, 100);
        this.coverHolder.markDirty();
    }

    public void adjustThrottle(final int change) {
        setThrottlePercentage(this.throttlePercentage + change);
    }

    @Override
    public boolean canAttach() {
        return getThrottleable() != null;
    }

    @Override
    public void onAttached(final ItemStack itemStack) {
        super.onAttached(itemStack);
        addThrottle();
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        removeThrottle();
    }

    private IThrottleable getThrottleable() {
        return this.coverHolder.getCapability(GregtechCapabilities.CAPABILITY_THROTTLEABLE, null);
    }

    private void addThrottle() {
        IThrottleable throttleable = getThrottleable();
        if (throttleable != null) {
            throttleable.setThrottle(this);
        }
    }

    private void removeThrottle() {
        IThrottleable throttleable = getThrottleable();
        if (throttleable != null) {
            throttleable.setThrottle(IThrottle.FULL_THROTTLE);
        }
    }

    private long getOutputVoltage() {
        IThrottleable throttleable = getThrottleable();
        return throttleable != null ? throttleable.getRecipeOutputVoltage() : 0;
    }

    @Override
    public void renderCover(final CCRenderState renderState, final Matrix4 translation, final IVertexOperation[] pipeline, final Cuboid6 plateBox, final BlockRenderLayer layer) {
        Textures.MACHINE_CONTROLLER_OVERLAY.renderSided(this.attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public EnumActionResult onScrewdriverClick(final EntityPlayer playerIn, final EnumHand hand, final CuboidRayTraceResult hitResult) {
        if (!this.coverHolder.getWorld().isRemote) {
            openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        return ModularUI.defaultBuilder()
                .label(10, 5, "cover.machine_controller.name")
                .widget(new ClickButtonWidget(10, 20, 20, 20, "-10", data -> adjustThrottle(data.isShiftClick ? -100 : -10)))
                .widget(new ClickButtonWidget(146, 20, 20, 20, "+10", data -> adjustThrottle(data.isShiftClick ? +100 : +10)))
                .widget(new ClickButtonWidget(30, 20, 20, 20, "-1", data -> adjustThrottle(data.isShiftClick ? -5 : -1)))
                .widget(new ClickButtonWidget(126, 20, 20, 20, "+1", data -> adjustThrottle(data.isShiftClick ? +5 : +1)))
                .widget(new ImageWidget(50, 20, 76, 20, GuiTextures.DISPLAY))
                .widget(new SimpleTextWidget(88, 30, "cover.conveyor.transfer_rate", 0xFFFFFF, () -> Integer.toString(getThrottlePercentage())))
                .widget(new SimpleTextWidget(88, 60, "cover.conveyor.transfer_rate", 0xFFFFFF, () -> Long.toString(getOutputVoltage())))
                .build(this, player);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("ThrottlePercentage", this.throttlePercentage);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.throttlePercentage = tagCompound.getInteger("ThrottlePercentage");
    }
}
