package by.likebebras.bebralib.ez.cmd;

import org.bukkit.command.CommandSender;

public interface Executor {
    void run(CommandSender cs, String label, String[] args);
}
