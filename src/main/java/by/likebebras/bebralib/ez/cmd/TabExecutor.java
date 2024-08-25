package by.likebebras.bebralib.ez.cmd;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface TabExecutor {
    List<String> run(CommandSender cs, String label, String[] args);

    default List<String> filter(List<String> list, String currentArgument){
        return list
                .stream()
                .filter(s -> s.startsWith(currentArgument))
                .toList();
    }
}
