package by.likebebras.bebralib.ez;

import org.bukkit.plugin.java.JavaPlugin;

public class EzPlugin extends JavaPlugin {
    public EzConfig cfg;

    @Override
    public void reloadConfig(){
        cfg.reload();
    }
}
