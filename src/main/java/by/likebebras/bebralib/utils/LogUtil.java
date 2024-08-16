package by.likebebras.bebralib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

@UtilityClass
public class LogUtil {

    private Logger logger = Bukkit.getLogger();

    public void warn(String msg){
        logger.warning(msg);
    }
    public void info(String msg){
        logger.info(msg);
    }
    public void severe(String msg){
        logger.severe(msg);
    }
}
