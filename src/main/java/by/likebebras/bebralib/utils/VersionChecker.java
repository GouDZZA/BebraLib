package by.likebebras.bebralib.utils;

import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class VersionChecker {
    private final String version;

    public VersionChecker() {
        this.version = getServerVersion();
    }

    public String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public boolean isVersionAtLeast(String targetVersion) {
        String[] currentParts = version.split("_");
        String[] targetParts = targetVersion.split("_");

        for (int i = 0; i < Math.min(currentParts.length, targetParts.length); i++) {
            int current = Integer.parseInt(currentParts[i].replaceAll("[^0-9]", ""));
            int target = Integer.parseInt(targetParts[i].replaceAll("[^0-9]", ""));

            if (current < target)
                return false;
             else if (current > target)
                return true;
        }
        return currentParts.length >= targetParts.length;
    }

}