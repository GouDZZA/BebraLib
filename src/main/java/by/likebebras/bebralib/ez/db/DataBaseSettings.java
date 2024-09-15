package by.likebebras.bebralib.ez.db;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class DataBaseSettings {
    private String host, username, password, path, name;
    private int port;

    private boolean local = false;

    private DataBaseSettings(){}

    public static @NotNull DataBaseSettings newLocal(@NotNull String path,@NotNull String name){
        DataBaseSettings settings = new DataBaseSettings();

        settings.path = path;
        settings.name = name;
        settings.local = true;

        return  settings;
    }

    public static @NotNull DataBaseSettings newMySQL(@NotNull String host, @NotNull String username, @NotNull String password, @NotNull String name, int port){
        DataBaseSettings settings = new DataBaseSettings();

        settings.host = host;
        settings.username = username;
        settings.password = password;
        settings.name = name;
        settings.port = port;

        return settings;
    }
}
