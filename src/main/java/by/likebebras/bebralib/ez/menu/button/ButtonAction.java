package by.likebebras.bebralib.ez.menu.button;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class ButtonAction {
    private final ClickType click;
    private final List<String> commands = new ArrayList<>();

    public ButtonAction(ClickType click, List<String> commands) {
        this.click = click;
        this.commands.addAll(commands);
    }

    public void run(Player p, ClickType type){
        if (click == null)
            commands.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{player}", p.getName())));

        else if (this.click == type)
            commands.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{player}", p.getName())));
    }
}
