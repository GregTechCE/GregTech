package gregtech.common.pipelike.cables;

import gregtech.api.GTValues;
import gregtech.api.pipelike.IPipeLikeTileProperty;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;
import java.util.Objects;

public class WireProperties implements IPipeLikeTileProperty {

    private int voltage;
    private int amperage;
    private int lossPerBlock;

    /**
     * Create an empty property for nbt deserialization
     */
    protected WireProperties() {}

    public WireProperties(int voltage, int baseAmperage, int lossPerBlock) {
        this.voltage = voltage;
        this.amperage = baseAmperage;
        this.lossPerBlock = lossPerBlock;
    }

    public int getVoltage() {
        return voltage;
    }

    public int getAmperage() {
        return amperage;
    }

    public int getLossPerBlock() {
        return lossPerBlock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WireProperties)) return false;
        WireProperties that = (WireProperties) o;
        return voltage == that.voltage &&
            amperage == that.amperage &&
            lossPerBlock == that.lossPerBlock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voltage, amperage, lossPerBlock);
    }

    @Override
    public void addInformation(List<String> tooltip) {
        String voltageName = GTValues.VN[GTUtility.getTierByVoltage(voltage)];
        tooltip.add(I18n.format("gregtech.cable.voltage", voltage, voltageName));
        tooltip.add(I18n.format("gregtech.cable.amperage", amperage));
        tooltip.add(I18n.format("gregtech.cable.loss_per_block", lossPerBlock));
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setIntArray("WireProperties", new int[]{voltage, amperage, lossPerBlock});
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        int[] data = nbt.getIntArray("WireProperties");
        voltage = data[0];
        amperage = data[1];
        lossPerBlock = data[2];
    }
}
