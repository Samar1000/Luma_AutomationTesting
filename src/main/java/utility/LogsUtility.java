package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogsUtility {
    public static String LOGS_PATH = "test-outputs/Logs";

    private static Logger getLogger() {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        return LogManager.getLogger(className);
    }
    public static void trace(String message) {
        getLogger().trace(message);
    }
    public static void info(String message) {
        getLogger().info(message);
    }
    public static void warn(String message) {
        getLogger().warn(message);
    }
    public static void error(String message) {
        getLogger().error(message);
    }
    public static void debug(String message) {
        getLogger().debug(message);
    }
    public static void fatal(String message) {
        getLogger().fatal(message);
    }
}
