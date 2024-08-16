package by.likebebras.bebralib.ez;

import by.likebebras.bebralib.managers.CommandManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class EzCommand extends Command {
    private boolean onlyPlayer = false, onlyConsole = false;
    private final List<String> aliases = new ArrayList<>();

    protected CommandSender sender;
    protected String[] args;
    protected String label;

    protected EzCommand(@NotNull String label) {
        super(label);
    }

    public EzCommand addAlias(String... aliases){
        Collections.addAll(this.aliases, aliases);

        return this;
    }

    @Override
    public List<String> getAliases(){
        return aliases;
    }

    public boolean player(){
        return sender instanceof Player;
    }
    public boolean console(){
        return sender instanceof ConsoleCommandSender;
    }

    public EzCommand setOnlyPlayers(){
        onlyPlayer = true;
        onlyConsole = false;

        return this;
    }

    public String wrongSender(){
        return "current sender is not supported!";
    }

    public EzCommand setOnlyPlayers(boolean b){
        onlyPlayer = b;
        onlyConsole = !b;

        return this;
    }

    public EzCommand setOnlyConsole(){
        onlyConsole = true;
        onlyPlayer = false;

        return this;
    }

    public EzCommand setOnlyConsole(boolean b){
        onlyConsole = b;
        onlyPlayer = !b;

        return this;
    }

    @Override
    public final boolean execute(@NotNull CommandSender cs, @NotNull String s, @NotNull String[] args) {
        if  (onlyPlayer)         if (!(cs instanceof Player))        {  cs.sendMessage(wrongSender());  return true;  }
        if (onlyConsole)  if (!(cs instanceof ConsoleCommandSender)) {  cs.sendMessage(wrongSender());  return true;  }

        this.sender = cs;
        this.label = s;
        this.args = args;

        onCommand();

        return true;
    }

    public abstract void onCommand();

    public void register(CommandManager manager){
        manager.registerCommand(this);
    }

    public void unregister(CommandManager manager) {
        manager.unregisterCommand(this);
    }
}
