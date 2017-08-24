package gregtech.api.util;

import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Just a simple Logging Function.
 */
public class GTLog {
    
    public static Logger logger;

    public static void init(Logger modLogger, File parentDir) {
        logger = modLogger;
    }

}