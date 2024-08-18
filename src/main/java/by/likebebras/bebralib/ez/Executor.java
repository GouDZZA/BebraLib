package by.likebebras.bebralib.ez;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface Executor {
    void run(CommandSender cs, String label, String[] args);
    void run(Player issuer);
}
