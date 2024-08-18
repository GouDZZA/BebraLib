package by.likebebras.bebralib;

import by.likebebras.bebralib.ez.EzPlugin;
import by.likebebras.bebralib.ez.menu.EzMenu;
import by.likebebras.bebralib.ez.menu.MenuListener;
import by.likebebras.bebralib.managers.CommandManager;
import lombok.Getter;

@Getter
public final class BebraLib extends EzPlugin {
    private final CommandManager manager = new CommandManager(this);

    @Override
    public void onEnable() {
        this.cfg = new BebraLibConfig(this);
        cfg.load();

        registerListener(new MenuListener());

        new BebraLibCommand(this).register(manager);
    }

    @Override
    public void onDisable() {
        manager.unregisterAll();
    }
}
