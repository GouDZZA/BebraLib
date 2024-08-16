package by.likebebras.bebralib.managers;

import by.likebebras.bebralib.ez.EzCommand;
import by.likebebras.bebralib.ez.EzPlugin;
import by.likebebras.bebralib.utils.CommandHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class CommandManager {
    private final HashMap<String, EzCommand> commands = new HashMap<>();
    private final EzPlugin plugin;

    public CommandManager(EzPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(EzCommand cmd){
        commands.put(cmd.getLabel(), cmd);
        cmd.getAliases().forEach(l -> commands.put(l, cmd));

        CommandHelper.register(cmd);
    }

    public void unregisterCommand(String cmd){
        EzCommand ezCommand = commands.remove(cmd);

        if (ezCommand == null) return;

        ezCommand.getAliases().forEach(commands::remove);

        CommandHelper.unregister(ezCommand);
    }

    public void unregisterAll(){
        Collection<String> cmds = commands.keySet();

        cmds.forEach(this::unregisterCommand);
    }
}
