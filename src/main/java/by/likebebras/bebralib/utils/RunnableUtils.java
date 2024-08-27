package by.likebebras.bebralib.utils;

import by.likebebras.bebralib.BebraLib;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class RunnableUtils {
    public void runLater(Runnable run, long howMuch){
        Bukkit.getScheduler().runTaskLater(BebraLib.getPlugin(BebraLib.class), run, howMuch);
    }
}
