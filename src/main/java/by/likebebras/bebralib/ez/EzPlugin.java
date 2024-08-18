package by.likebebras.bebralib.ez;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class EzPlugin extends JavaPlugin {
    public EzConfig cfg;

    @Override
    public void reloadConfig(){
        cfg.reload();
    }

    public void registerListener(Listener listener){
        Bukkit.getPluginManager().registerEvents(listener, this);
    }
}
