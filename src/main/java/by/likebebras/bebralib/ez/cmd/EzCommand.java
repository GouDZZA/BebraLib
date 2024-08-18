package by.likebebras.bebralib.ez.cmd;

import by.likebebras.bebralib.ez.Executor;
import by.likebebras.bebralib.managers.CommandManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EzCommand extends Command {
    private boolean onlyPlayer = false, onlyConsole = false;
    private final List<String> aliases = new ArrayList<>();
    private final Map<String, EzCommand> subCommands = new HashMap<>();

    protected Executor executor;
    protected CommandSender sender;
    protected String[] args;
    protected String label;

    public EzCommand(@NotNull String label) {
        super(label);
    }

    public EzCommand addAlias(String... aliases){
        Collections.addAll(this.aliases, aliases);

        return this;
    }

    @Override
    public @NotNull List<String> getAliases(){
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

    public EzCommand addSubCommand(@NotNull EzCommand subCommand) {
        subCommands.put(subCommand.getLabel().toLowerCase(), subCommand);
        for (String alias : subCommand.getAliases()) {
            subCommands.put(alias.toLowerCase(), subCommand);
        }
        return this;
    }

    @Override
    public final boolean execute(@NotNull CommandSender cs, @NotNull String s, @NotNull String[] args) {
        if  (onlyPlayer)         if (!(cs instanceof Player))        {  cs.sendMessage(wrongSender());  return true;  }
        if (onlyConsole)  if (!(cs instanceof ConsoleCommandSender)) {  cs.sendMessage(wrongSender());  return true;  }

        this.sender = cs;
        this.label = s;
        this.args = args;

        if (args.length > 0 && !subCommands.isEmpty()) {
            EzCommand subCommand = subCommands.get(args[0].toLowerCase());
            if (subCommand != null) {
                String[] subArgs = new String[args.length - 1];
                System.arraycopy(args, 1, subArgs, 0, subArgs.length);
                subCommand.execute(cs, args[0], subArgs);
                return true;
            }
        }

        if (executor != null) executor.run(sender, label, args);
        else onCommand();

        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender cs, @NotNull String alias, @NotNull String[] args, @Nullable Location location) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            for (String subLabel : subCommands.keySet()) {
                if (subLabel.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(subLabel);
                }
            }
            return completions;
        } else if (args.length > 1) {
            EzCommand subCommand = subCommands.get(args[0].toLowerCase());
            if (subCommand != null) {
                String[] subArgs = new String[args.length - 1];
                System.arraycopy(args, 1, subArgs, 0, subArgs.length);
                return subCommand.tabComplete(cs, args[0], subArgs, location);
            }
        }

        return onTabComplete();
    }

    @NotNull
    public List<String> onTabComplete() {
        return Collections.emptyList();
    }

    public void onCommand(){
    }

    public void onCommand(Executor executor){
        this.executor = executor;

    }

    public final void register(CommandManager manager){
        manager.registerCommand(this);
    }

    public final void unregister(CommandManager manager) {
        manager.unregisterCommand(this);
    }
}
