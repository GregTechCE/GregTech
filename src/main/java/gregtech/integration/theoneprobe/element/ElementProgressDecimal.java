package gregtech.integration.theoneprobe.element;

import gregtech.integration.theoneprobe.TheOneProbeCompatibility;
import io.netty.buffer.ByteBuf;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IProgressStyle;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import mcjty.theoneprobe.network.NetworkTools;

import java.util.Objects;

public class ElementProgressDecimal implements IElement {

    public static class Builder {
        private Number current, max;
        private IProgressStyle style;
        private String format1, format2, infixWithFormat;
        public Builder setCurrent(long current) {
            this.current = current;
            return this;
        }
        public Builder setCurrent(double current) {
            this.current = current;
            return this;
        }
        public Builder setMax(long max) {
            this.max = max;
            return this;
        }
        public Builder setMax(double max) {
            this.max = max;
            return this;
        }
        public Builder setStyle(IProgressStyle style) {
            this.style = style;
            return this;
        }
        public Builder setFormatForCurrent(String format) {
            format1 = format;
            return this;
        }
        public Builder setFormatForMax(String format) {
            format2 = format;
            return this;
        }
        public Builder setInfixWithFormat(String infixWithFormat) {
            this.infixWithFormat = infixWithFormat;
            return this;
        }
        public ElementProgressDecimal build() {
            Objects.requireNonNull(current);
            Objects.requireNonNull(max);
            Objects.requireNonNull(style);
            if (format1 == null) format1 = "";
            if (format2 == null) format2 = "";
            if (infixWithFormat == null) infixWithFormat = "%s / %s";
            return new ElementProgressDecimal(current, max, style, format1, format2, infixWithFormat);
        }
    }

    public static Builder start() {
        return new Builder();
    }

    private final Number current;
    private final Number max;
    private final IProgressStyle style;
    private final String format1;
    private final String format2;
    private final String infixWithFormat;

    public ElementProgressDecimal(Number current, Number max, IProgressStyle style, String format1, String format2, String infixWithFormat) {
        this.current = current;
        this.max = max;
        this.style = style;
        this.format1 = format1;
        this.format2 = format2;
        this.infixWithFormat = infixWithFormat;
    }

    public ElementProgressDecimal(ByteBuf buf) {
        current = buf.readBoolean() ? buf.readDouble() : buf.readLong();
        max = buf.readBoolean() ? buf.readDouble() : buf.readLong();
        format1 = NetworkTools.readStringUTF8(buf);
        format2 = NetworkTools.readStringUTF8(buf);
        infixWithFormat = NetworkTools.readStringUTF8(buf);
        style = new ProgressStyle()
            .width(buf.readInt())
            .height(buf.readInt())
            .prefix(NetworkTools.readStringUTF8(buf))
            .suffix(NetworkTools.readStringUTF8(buf))
            .borderColor(buf.readInt())
            .filledColor(buf.readInt())
            .alternateFilledColor(buf.readInt())
            .backgroundColor(buf.readInt())
            .showText(buf.readBoolean())
            .numberFormat(NumberFormat.values()[buf.readByte()])
            .lifeBar(buf.readBoolean())
            .armorBar(buf.readBoolean());
    }

    @Override
    public void render(int x, int y) {
        ElementRenders.render(style, current, max, format1, format2, infixWithFormat, x, y, getWidth(), getHeight());
    }

    @Override
    public int getWidth() {
        return style.getWidth();
    }

    @Override
    public int getHeight() {
        return style.getHeight();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        boolean isDouble = current instanceof Double;
        buf.writeBoolean(isDouble);
        if (isDouble) buf.writeDouble((Double) current);
        else buf.writeLong((Long) current);
        isDouble = max instanceof Double;
        buf.writeBoolean(isDouble);
        if (isDouble) buf.writeDouble((Double) max);
        else buf.writeLong((Long) max);
        NetworkTools.writeStringUTF8(buf, format1);
        NetworkTools.writeStringUTF8(buf, format2);
        NetworkTools.writeStringUTF8(buf, infixWithFormat);
        buf.writeInt(style.getWidth());
        buf.writeInt(style.getHeight());
        NetworkTools.writeStringUTF8(buf, style.getPrefix());
        NetworkTools.writeStringUTF8(buf, style.getSuffix());
        buf.writeInt(style.getBorderColor());
        buf.writeInt(style.getFilledColor());
        buf.writeInt(style.getAlternatefilledColor());
        buf.writeInt(style.getBackgroundColor());
        buf.writeBoolean(style.isShowText());
        buf.writeByte(style.getNumberFormat().ordinal());
        buf.writeBoolean(style.isLifeBar());
        buf.writeBoolean(style.isArmorBar());
    }

    @Override
    public int getID() {
        return TheOneProbeCompatibility.ELEMENT_PROGRESS_DECIMAL;
    }
}
