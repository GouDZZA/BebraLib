package by.likebebras.bebralib;

import by.likebebras.bebralib.ez.EzCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BebraLibCommand extends EzCommand {

    private final BebraLib daddy;

    public BebraLibCommand(BebraLib plugin) {
        super("bebralib");
        this.daddy = plugin;
    }

    @Override
    public String wrongSender() {
        return "";
    }

    @Override
    public void onCommand(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] args) {
    }
}
