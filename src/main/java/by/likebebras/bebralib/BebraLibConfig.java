package by.likebebras.bebralib;

import by.likebebras.bebralib.ez.EzConfig;
import by.likebebras.bebralib.ez.menu.EzMenu;
import by.likebebras.bebralib.ez.menu.ItemBuilder;
import by.likebebras.bebralib.utils.ColorUtil;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class BebraLibConfig extends EzConfig {
    private EzMenu menu;

    public BebraLibConfig(BebraLib bebraLib) {
        super(bebraLib);
    }

    @Override
    public void load(){
        load("messages.yml", "settings.yml");
        ConfigurationSection menuSection = getLoadedFile("settings.yml").getConfigurationSection("menu");

        if (menuSection != null)
            loadMenu(menuSection);

    }

    public void loadMenu(ConfigurationSection menuSection){
        int slots = menuSection.getInt("slots");
        String title = ColorUtil.hex(menuSection.getString("slots"));

        ConfigurationSection itemsSection = menuSection.getConfigurationSection("items");

        if (itemsSection != null){
            for (String item : itemsSection.getKeys(false)) {
                ConfigurationSection itemSection = itemsSection.getConfigurationSection(item);

                assert itemSection != null;

                String type = itemSection.getString("type");
                String name = itemSection.getString("name");
                int slot = itemSection.getInt("slot", 1);

                ItemBuilder builder = new ItemBuilder();
            }
        }
    }
}
