package by.likebebras.bebralib.ez;

import by.likebebras.bebralib.managers.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class EzCommand extends Command {
    private boolean onlyPlayer = false, onlyConsole = false;
    private final Set<Permission> perms = new HashSet<>();
    private final List<String> aliases = new ArrayList<>();

    protected EzCommand(@NotNull String label) {
        super(label);
    }

    public void addAlias(String... aliases){
        for (String alias : aliases) this.aliases.add(alias);
    }

    @Override
    public List<String> getAliases(){
        return aliases;
    }

    public abstract String wrongSender();

    public void checkPerms(String... perms){
        this.perms.addAll(Arrays.stream(perms).map(Permission::new).toList());
    }

    public void onlyPlayers(){
        onlyPlayer = true;
    }

    public void onlyPlayers(boolean b){
        onlyPlayer = b;
    }

    public void onlyConsole(){
        onlyConsole = true;
    }

    public void onlyConsole(boolean b){
        onlyConsole = b;
    }


    @Override
    public final boolean execute(@NotNull CommandSender cs, @NotNull String s, @NotNull String[] args) {
        if  (onlyPlayer)         if (!(cs instanceof Player))        {  cs.sendMessage(wrongSender());  return true;  }
        if (onlyConsole)  if (!(cs instanceof ConsoleCommandSender)) {  cs.sendMessage(wrongSender());  return true;  }

        onCommand(cs, s, args);

        return true;
    }

    public abstract void onCommand(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] args);


    public void register(CommandManager manager){
        manager.registerCommand(this);
    }
}
