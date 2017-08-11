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
public class GTLog {
    
    public static Logger logger;

    public static void init(Logger modLogger, File parentDir) {
        logger = modLogger;

        /*ore.println("******************************************************************************");
        ore.println("* This is the complete log of the GT5-Unofficial OreDictionary Handler. It   *");
        ore.println("* processes all OreDictionary entries and can sometimes cause errors. All    *");
        ore.println("* entries and errors are being logged. If you see an error please raise an   *");
        ore.println("* issue at https://github.com/Blood-Asp/GT5-Unofficial.                      *");
        ore.println("******************************************************************************");*/

    }

}