package by.likebebras.bebralib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.Map;

@UtilityClass
public class CommandHelper {

    public void register(Command cmd){
        CommandMap map = getCommandMap();
        map.register(cmd.getLabel(), cmd);
    }

    public void unregister(Command cmd){
        unregisterCommand(cmd.getLabel(), true);
    }

    public static void unregisterCommand(final String label, final boolean removeAliases) {
        try {
            final PluginCommand command = Bukkit.getPluginCommand(label);

            if (command != null) {
                final Field commandField = Command.class.getDeclaredField("commandMap");
                commandField.setAccessible(true);

                if (command.isRegistered())
                    command.unregister((CommandMap) commandField.get(command));
            }

            final Field f = SimpleCommandMap.class.getDeclaredField("knownCommands");
            f.setAccessible(true);

            final Map<String, Command> cmdMap = (Map<String, Command>) f.get(getCommandMap());

            cmdMap.remove(label);

            if (command != null && removeAliases)
                for (final String alias : command.getAliases())
                    cmdMap.remove(alias);

        } catch (final ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
    }

    public SimpleCommandMap getCommandMap() {
        final Class<?> craftServer = ReflectUtil.getCBClass("CraftServer");

        try {
            return (SimpleCommandMap) craftServer.getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());

        } catch (final ReflectiveOperationException ex) {

            try {
                return ReflectUtil.getFieldContent(Bukkit.getServer().getClass(), "commandMap", Bukkit.getServer());

            } catch (final Throwable ex2) {
                ex2.printStackTrace();
                return null;
            }
        }
    }
}
