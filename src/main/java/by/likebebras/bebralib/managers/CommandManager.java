package by.likebebras.bebralib.managers;

import by.likebebras.bebralib.ez.EzPlugin;
import by.likebebras.bebralib.ez.cmd.EzCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.util.HashMap;

public class CommandManager {
    private final HashMap<String, EzCommand> commands = new HashMap<>();
    private final EzPlugin plugin;

    public CommandManager(EzPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(EzCommand cmd, boolean hidden){
        commands.put(cmd.getLabel(), cmd);

        register(cmd, hidden);
    }

    public void registerCommand(EzCommand cmd){
        commands.put(cmd.getLabel(), cmd);

        register(cmd, false);
    }

    public void unregisterCommand(EzCommand cmd){
        commands.remove(cmd.getLabel());
        cmd.getAliases().forEach(commands::remove);

        unregister(cmd);
    }

    private void register(EzCommand cmd, boolean hidden){
        CommandMap map = Bukkit.getServer().getCommandMap();

        map.register(plugin.getName(), cmd);

        if (!hidden) {
            map.getKnownCommands().put(cmd.getLabel(), cmd);
            for (String alias : cmd.getAliases()) {
                map.getKnownCommands().put(alias, cmd);
            }
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
