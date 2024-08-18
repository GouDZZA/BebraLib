package by.likebebras.bebralib.ez;

import org.bukkit.command.CommandSender;

public interface Executor {
    void run(CommandSender cs, String label, String[] args);
}
