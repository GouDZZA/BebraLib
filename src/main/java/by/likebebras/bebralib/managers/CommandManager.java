package by.likebebras.bebralib.managers;

import by.likebebras.bebralib.ez.EzCommand;
import by.likebebras.bebralib.ez.EzPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.util.HashMap;

public class CommandManager {
    private final HashMap<String, EzCommand> commands = new HashMap<>();
    private final EzPlugin plugin;

    public CommandManager(EzPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(EzCommand cmd){
        commands.put(cmd.getLabel(), cmd);

        register(cmd);
    }

    public void unregisterCommand(EzCommand cmd){
        commands.remove(cmd.getLabel());
        cmd.getAliases().forEach(commands::remove);

        unregister(cmd);
    }

    private void register(EzCommand cmd){
        Bukkit.getServer().getCommandMap().register(plugin.getName(), cmd);
        Bukkit.getServer().getCommandMap().getKnownCommands().put(cmd.getLabel(), cmd);
        for (String alias : cmd.getAliases()) {
            Bukkit.getServer().getCommandMap().getKnownCommands().put(alias, cmd);
        }
    }
    private void unregister(EzCommand cmd){
        if (cmd == null) return;

        CommandMap map = Bukkit.getServer().getCommandMap();
        map.getKnownCommands().entrySet().removeIf(commandEntry -> commandEntry.getValue() == cmd);
        cmd.unregister(map);
    }

    public void unregisterAll(){
        commands.values().forEach(this::unregister);
    }
}
