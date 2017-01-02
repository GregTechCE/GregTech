package gregtech.api.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.FileAppender;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * Just a simple Logging Function.
 */
public class GT_Log {
    
    public static Logger logger;
    
    public static PrintStream out;
    public static PrintStream err;
    public static PrintStream ore;
    public static PrintStream pal = null;

    public static File mLogFile;
    public static File mOreDictLogFile;
    public static File mPlayerActivityLogFile;

    public static void init(Logger modLogger, File parentDir) {
        logger = modLogger;

        mLogFile = new File(parentDir, "logs/GregTech.log");
        if (!mLogFile.exists()) {
            try {
                mLogFile.createNewFile();
            } catch (Throwable e) {
            }
        }
        try {
            out = new LogPrintStream(mLogFile, logger, Level.INFO);
            err = new LogPrintStream(mLogFile, logger, Level.ERROR);
        } catch (Throwable e) {
        }

        mOreDictLogFile = new File(parentDir, "logs/OreDict.log");
        if (!mOreDictLogFile.exists()) {
            try {
                mOreDictLogFile.createNewFile();
            } catch (Throwable e) {
            }
        }
        try {
            ore = new PrintStream(mOreDictLogFile);
        } catch (Throwable e) {
        }

        mPlayerActivityLogFile = new File(parentDir, "logs/PlayerActivity.log");
        if (!mPlayerActivityLogFile.exists()) {
            try {
                mPlayerActivityLogFile.createNewFile();
            } catch (Throwable e) {
            }
        }

        ore.println("******************************************************************************");
        ore.println("* This is the complete log of the GT5-Unofficial OreDictionary Handler. It   *");
        ore.println("* processes all OreDictionary entries and can sometimes cause errors. All    *");
        ore.println("* entries and errors are being logged. If you see an error please raise an   *");
        ore.println("* issue at https://github.com/Blood-Asp/GT5-Unofficial.                      *");
        ore.println("******************************************************************************");

    }

    public static void enablePlayerActivityLogger() {
        try {
            pal = new PrintStream(mPlayerActivityLogFile);
        } catch (Throwable e) {
        }
    }

    public static class LogPrintStream extends PrintStream {

        private Logger logger;
        private Level level;

        public LogPrintStream(File file, Logger logger, Level level) throws FileNotFoundException {
            super(file);
            this.logger = logger;
            this.level = level;
        }

        @Override
        public void println() {
            super.println();
            logger.log(level, "");
        }

        @Override
        public void println(boolean x) {
            super.println(x);
            logger.log(level, x);
        }

        @Override
        public void println(char x) {
            super.println(x);
            logger.log(level, x);
        }

        @Override
        public void println(long x) {
            super.println(x);
            logger.log(level, x);
        }

        @Override
        public void println(int x) {
            super.println(x);
            logger.log(level, x);
        }

        @Override
        public void println(float x) {
            super.println(x);
            logger.log(level, x);
        }

        @Override
        public void println(double x) {
            super.println(x);
            logger.log(level, x);
        }

        @Override
        public void println(char[] x) {
            super.println(x);
            logger.log(level, x);
        }

        @Override
        public void println(Object x) {
            super.println(x);
            logger.log(level, x);
        }

        @Override
        public void println(String x) {
            super.println(x);
            logger.log(level, x);
        }

    }


}