package by.likebebras.bebralib.ez;

import by.likebebras.bebralib.utils.ColorUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public abstract class EzConfig {

    private final EzPlugin plugin;
    private final HashMap<String, FileConfiguration> files = new HashMap<>();

    public EzConfig(EzPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void reload();
    public void load(){
        load("messages.yml", "settings.yml");
    }
    public void load(String... names){
        for (String name : names)
            files.put(name, getFileConfig(name));

    }

    public FileConfiguration getLoadedFile(String name){
        return files.get(name);
    }

    public void clearFiles(){
        this.files.clear();
    }


    public <T> T getFromAs(String fileName, String path, Class<T> tClass){
        try {
            Object o = files.get(fileName).get(path);

            if (o != null) return tClass.cast(o);
        } catch (Exception e) {

            if (tClass == String.class){
                return (T) ("Нету сообщения: " + path);
            }

            throw new RuntimeException(e);
        }
        return null;
    }

    public String getStringFrom(String fileName, String path){
        return fileName.toLowerCase().contains(".yml") ?
                ColorUtil.hex(files.get(fileName).getString(path, "Нету сообщения: " + path))
                :
                ColorUtil.hex(files.get(fileName + ".yml").getString(path, "Нету сообщения: " + path));
    }

    private File getFile(String name){
        File file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + name);

        if (!file.exists()){
            plugin.saveResource(name, false);
        }

        return file;
    }

    private FileConfiguration getFileConfig(String name){
        return YamlConfiguration.loadConfiguration(getFile(name));
    }
}
