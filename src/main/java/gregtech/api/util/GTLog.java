package gregtech.api.util;

import gregtech.api.GTValues;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.AbstractLogger;

/**
 * GregTech logger
 */
@SuppressWarnings("serial")
public class GTLog extends AbstractLogger { //TODO Add static logger methods and delegate to internal logger?
    public static GTLog logger;
    private Logger internal;

    public GTLog(Logger logger) {
        this.internal = logger;
    }

    public static void init(Logger modLogger) {
        logger = new GTLog(modLogger);
    }

    @Override
    public void logMessage(String fqcn, Level level, Marker marker, Message message, Throwable t) {
        if (!GTValues.DEBUG && level.isLessSpecificThan(Level.DEBUG)) {
            return;
        }
        if (GTValues.useLoggerPrefix) {
            message = getMessageFactory().newMessage("[GregTech] " + message.getFormattedMessage());
        }
        internal.log(level, marker, message, t);
    }

    @Override
    public Level getLevel() {
        return GTValues.DEBUG ? Level.ALL : Level.INFO;
    }

    //Internal methods
    // ==============
    public boolean isEnabled(Level level, Marker marker) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Message message, Throwable t) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, CharSequence message, Throwable t) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Object message, Throwable t) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Throwable t) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object... params) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return internal.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return internal.isEnabled(level, marker);
    }
}