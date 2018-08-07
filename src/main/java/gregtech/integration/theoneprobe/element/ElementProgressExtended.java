package gregtech.integration.theoneprobe.element;

import gregtech.integration.theoneprobe.TheOneProbeCompatibility;
import io.netty.buffer.ByteBuf;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IProgressStyle;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import mcjty.theoneprobe.network.NetworkTools;

import java.math.BigInteger;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ElementProgressExtended implements IElement {

    private enum NumberType {
        Long(ByteBuf::readLong, (buf, value) -> buf.writeLong((Long) value)),
        Double(ByteBuf::readDouble, (buf, value) -> buf.writeDouble((Double) value)),
        BigInteger(buf -> new BigInteger(NetworkTools.readStringUTF8(buf)), (buf, value) -> NetworkTools.writeStringUTF8(buf, value.toString()));

        final Function<ByteBuf, Number> reader;
        final BiConsumer<ByteBuf, Number> writer;

        NumberType(Function<ByteBuf, Number> reader, BiConsumer<ByteBuf, Number> writer) {
            this.reader = reader;
            this.writer = writer;
        }

        void writeValue(ByteBuf buf, Number value) {
            writer.accept(buf, value);
        }

        Number readValue(ByteBuf buf) {
            return reader.apply(buf);
        }
    }

    public static class Builder {
        private NumberType typeCurrent, typeMax;
        private Number current, max;
        private IProgressStyle style;
        private String format1, format2, infixWithFormat;
        public Builder setCurrent(long current) {
            this.current = current;
            typeCurrent = NumberType.Long;
            return this;
        }
        public Builder setCurrent(double current) {
            this.current = current;
            typeCurrent = NumberType.Double;
            return this;
        }
        public Builder setCurrent(BigInteger current) {
            this.current = current;
            typeCurrent = NumberType.BigInteger;
            return this;
        }
        public Builder setMax(long max) {
            this.max = max;
            typeMax = NumberType.Long;
            return this;
        }
        public Builder setMax(double max) {
            this.max = max;
            typeMax = NumberType.Double;
            return this;
        }
        public Builder setMax(BigInteger max) {
            this.max = max;
            typeMax = NumberType.BigInteger;
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
        public ElementProgressExtended build() {
            Objects.requireNonNull(current);
            Objects.requireNonNull(max);
            Objects.requireNonNull(style);
            if (format1 == null) format1 = "";
            if (format2 == null) format2 = "";
            if (infixWithFormat == null) infixWithFormat = "%s / %s";
            return new ElementProgressExtended(current, typeCurrent, max, typeMax, style, format1, format2, infixWithFormat);
        }
    }

    public static Builder start() {
        return new Builder();
    }

    private final NumberType typeCurrent;
    private final NumberType typeMax;
    private final Number current;
    private final Number max;
    private final IProgressStyle style;
    private final String format1;
    private final String format2;
    private final String infixWithFormat;

    private ElementProgressExtended(Number current, NumberType typeCurrent, Number max, NumberType typeMax, IProgressStyle style, String format1, String format2, String infixWithFormat) {
        this.current = current;
        this.typeCurrent = typeCurrent;
        this.max = max;
        this.typeMax = typeMax;
        this.style = style;
        this.format1 = format1;
        this.format2 = format2;
        this.infixWithFormat = infixWithFormat;
    }

    public ElementProgressExtended(ByteBuf buf) {
        typeCurrent = NumberType.values()[buf.readInt()];
        current = typeCurrent.readValue(buf);
        typeMax = NumberType.values()[buf.readInt()];
        max = typeMax.readValue(buf);
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
        buf.writeInt(typeCurrent.ordinal());
        typeCurrent.writeValue(buf, current);
        buf.writeInt(typeMax.ordinal());
        typeMax.writeValue(buf, max);
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
        return TheOneProbeCompatibility.ELEMENT_PROGRESS_EXTENDED;
    }
}
