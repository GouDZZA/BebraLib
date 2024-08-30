package by.likebebras.bebralib.ez;

import by.likebebras.bebralib.utils.ColorUtil;
import by.likebebras.bebralib.utils.LogUtil;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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

    public double getDouble(String fileName, String path, double dif){
        FileConfiguration file = getLoadedFile(fileName);

        if (file == null) return dif;
        else return file.getDouble(path, dif);
    }

    public double getDouble(String fileName, String path){
        return getDouble(fileName, path, 0);
    }

    public float getFloat(String fileName, String path, float dif){
        return (float) getDouble(fileName, path, dif);
    }

    public float getFloat(String fileName, String path){
        return (float) getDouble(fileName, path);
    }

    public int getInt(String fileName, String path, int dif){
        FileConfiguration file = getLoadedFile(fileName);

        if (file == null) return dif;
        else return file.getInt(path);
    }

    public int getInt(String fileName, String path){
        return getInt(fileName, path, 0);
    }

    public boolean getBoolean(String fileName, String path, boolean dif){
        FileConfiguration file = getLoadedFile(fileName);

        if (file == null) return dif;
        return file.getBoolean(path);
    }

    public boolean getBoolean(String fileName, String path){
        return getBoolean(fileName, path, false);
    }

    public EzSound getSound(String fileName, String path){
        FileConfiguration file = getLoadedFile(fileName);

        if (file == null) return EzSound.DEFAULT;

        ConfigurationSection cfg = file.getConfigurationSection(path);

        if (cfg == null) return EzSound.DEFAULT;

        float volume = (float) cfg.getDouble("volume", 1f),
              pitch = (float) cfg.getDouble("pitch", 1f);

        String soundString = cfg.getString("name");

        if (soundString == null) {
            LogUtil.warn("Error while loading sound for " + plugin.getClass().getSimpleName() + " no sound name provided, using default");
            return EzSound.DEFAULT;
        }

        try {
            Sound sound = Sound.valueOf(soundString.toUpperCase());
            return new EzSound(sound, volume, pitch);

        } catch (IllegalArgumentException e) {
            LogUtil.warn("loading sound for " + plugin.getClass().getSimpleName() + " thrown IAE, no such sound: " + soundString + ", using default");

            return EzSound.DEFAULT;
        }
    }

    public <T> T getFromAs(String fileName, String path, Class<T> tClass, T dif){
        try {
            Object o = getLoadedFile(fileName).get(path);

            if (o != null) return tClass.cast(o);
        } catch (Exception e) {

            if (tClass == String.class){

                return (T) ("Нету сообщения: " + path);
            }

            throw new RuntimeException(e);
        }
        return dif;
    }

    public <T> List<T> getAsListOf(String fileName, String path, Class<T> tClass){
        List<T> list;

        try {
            list = (List<T>) getFromAs(fileName, path, List.class);
        } catch (Exception e) {
            LogUtil.warn("loading list of " + tClass.getSimpleName() + " for " + plugin.getClass().getSimpleName() + " thrown " + e.getClass().getSimpleName() + ", " + e.getMessage());
            return null;
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
