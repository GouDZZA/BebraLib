package by.likebebras.bebralib.ez;

import by.likebebras.bebralib.utils.ColorUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EzConfig{

    private final EzPlugin plugin;
    private final HashMap<String, FileConfiguration> files = new HashMap<>();

    public EzConfig(EzPlugin plugin) {
        this.plugin = plugin;
    }

    public void reload(){
        clearFiles();
        load();
    }
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

    public <T> List<T> getAsListOf(String fileName, String path, Class<T> tClass){
        List<T> list;

        try {
            list = (List<T>) getFromAs(fileName, path, List.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void saveFile(String name){
        try {
            files.get(name).save(getFile(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInTo(String fileName, String path, Object to){
        files.get(fileName).set(path, to);
    }

    public String getStringFrom(String fileName, String path, String throwElse){
        fileName = fileName.toLowerCase().contains(".yml") ? fileName : fileName + ".yml";

        return ColorUtil.hex(files.get(fileName).getString(path, throwElse));
    }

    public String getStringFrom(String fileName, String path){
        fileName = fileName.toLowerCase().contains(".yml") ? fileName : fileName + ".yml";

        return ColorUtil.hex(files.get(fileName).getString(path, "Нету сообщения: " + path));
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
