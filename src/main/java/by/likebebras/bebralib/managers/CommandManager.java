package by.likebebras.bebralib.managers;

import by.likebebras.bebralib.ez.EzPlugin;
import by.likebebras.bebralib.ez.cmd.EzCommand;
import by.likebebras.bebralib.utils.ReflectUtil;
import org.bukkit.command.CommandMap;

import java.util.HashMap;

public class CommandManager {
    private final HashMap<String, EzCommand> commands = new HashMap<>();
    private final CommandMap map = ReflectUtil.getCommandMap();
    private final EzPlugin plugin;


    public CommandManager(EzPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(EzCommand cmd) {
        commands.put(cmd.getLabel(), cmd);

        register(cmd);
    }

    public EzCommand getCommand(String name) {
        return commands.get(name);
    }

    public void unregisterCommand(EzCommand cmd) {
        commands.remove(cmd.getLabel());
        cmd.getAliases().forEach(commands::remove);

        unregister(cmd);
    }

    public void unregisterAll() {
        commands.values().forEach(this::unregister);
    }

    private void register(EzCommand cmd) {

        map.register(plugin.getName(), cmd);

        map.getKnownCommands().put(cmd.getLabel(), cmd);
        for (String alias : cmd.getAliases()) {
            map.getKnownCommands().put(alias, cmd);
        }
    }

    private void unregister(EzCommand cmd) {
        if (cmd == null) return;

        map.getKnownCommands().entrySet().removeIf(commandEntry -> commandEntry.getValue() == cmd);
        cmd.unregister(map);

    }

}
