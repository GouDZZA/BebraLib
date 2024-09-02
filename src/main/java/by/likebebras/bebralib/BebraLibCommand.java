package by.likebebras.bebralib;

import by.likebebras.bebralib.ez.cmd.EzCommand;
import by.likebebras.bebralib.utils.ParticleUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

class BebraLibCommand extends EzCommand {

    private final BebraLib daddy;
    private final BebraLibConfig cfg;

    public BebraLibCommand(BebraLib plugin) {
        super("bebralib");
        this.daddy = plugin;
        this.cfg = (BebraLibConfig) daddy.getCfg();
        addSubCommand(new ReloadCommand());
        addSubCommand(new MenuTestCommand());
        onCommand((cs, label, args) -> {
            sender.sendMessage(cfg.getStringFrom("messages.yml", "help"));
        });
        setPermission("bebralib.admin");
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

    private class MenuTestCommand extends EzCommand {
        private MenuTestCommand() {
            super("menu");
            onCommand((cs, l, args) -> {
                if (player())
                    cfg.getMenu().show((Player) cs);
                else
                    sender.sendMessage(cfg.getStringFrom("messages.yml", "players-only"));
            });
        }
    }
}

