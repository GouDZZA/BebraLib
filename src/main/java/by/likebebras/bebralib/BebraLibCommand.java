package by.likebebras.bebralib;

import by.likebebras.bebralib.ez.cmd.EzCommand;

public class BebraLibCommand extends EzCommand {

    private final BebraLib daddy;
    private final BebraLibConfig cfg;

    public BebraLibCommand(BebraLib plugin) {
        super("bebralib");
        this.daddy = plugin;
        this.cfg = (BebraLibConfig) daddy.getCfg();
        addSubCommand(new ReloadCommand());
        onCommand((cs, label, args) -> {
            sender.sendMessage(cfg.getStringFrom("messages.yml", "help"));
        });
    }

    private class ReloadCommand extends EzCommand {
        private ReloadCommand() {
            super("reload");
            onCommand((cs, l, args) -> {
                daddy.reloadConfig();
                sender.sendMessage(cfg.getStringFrom("messages.yml", "reloaded"));
            });
        }
    }
}

