package by.likebebras.bebralib.ez.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@Getter
public class EzDatabase {

    private HikariDataSource dataSource;

    public EzDatabase(DataBaseSettings settings) {
        load(settings);
    }

    public void load(DataBaseSettings settings){
        HikariConfig config = new HikariConfig();

        if (settings.isLocal()) {
            File databaseFile = new File(settings.getPath(), settings.getName() + ".db");
            if (!databaseFile.exists()){
                try {
                    boolean newFile = databaseFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            config.setJdbcUrl("jdbc:sqlite:" + databaseFile.getAbsolutePath());
        } else {
            config.setJdbcUrl("jdbc:mysql://" + settings.getHost() + ":" + settings.getPort() + "/" + settings.getName() + "?useSSL=false");
            config.setUsername(settings.getUsername());
            config.setPassword(settings.getPassword());
        }

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);

        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);

        this.dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}