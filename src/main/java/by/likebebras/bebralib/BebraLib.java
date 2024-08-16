package by.likebebras.bebralib;

import by.likebebras.bebralib.ez.EzPlugin;
import by.likebebras.bebralib.managers.CommandManager;

public final class BebraLib extends EzPlugin {
    private final CommandManager manager = new CommandManager(this);

    @Override
    public void onEnable() {
        new BebraLibCommand(this).register(manager);
        this.cfg = new BebraLibConfig(this);

        cfg.load();
    }

    @Override
    public void onDisable() {
        manager.unregisterAll();
    }
}
