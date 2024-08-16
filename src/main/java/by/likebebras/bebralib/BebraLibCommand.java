package by.likebebras.bebralib;

import by.likebebras.bebralib.ez.EzCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class BebraLibCommand extends EzCommand {

    private final BebraLib daddy;

    public BebraLibCommand(BebraLib plugin) {
        super("bebralib");
        this.daddy = plugin;
    }

    @Override
    public void onCommand() {
        daddy.reloadConfig();
        sender.sendMessage(daddy.getCfg().getStringFrom("messages.yml", "reloaded"));
    }
}
