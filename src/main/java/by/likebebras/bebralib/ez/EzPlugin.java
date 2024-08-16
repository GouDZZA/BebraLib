package by.likebebras.bebralib.ez;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class EzPlugin extends JavaPlugin {
    @Getter
    public EzConfig cfg;

    @Override
    public void reloadConfig(){
        cfg.reload();
    }
}
