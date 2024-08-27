package by.likebebras.bebralib.ez.cmd;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface TabExecutor {
    List<String> run(CommandSender cs, String label, String[] args);
}
