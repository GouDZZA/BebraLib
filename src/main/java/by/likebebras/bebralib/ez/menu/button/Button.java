package by.likebebras.bebralib.ez.menu.button;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Button {
    @Setter
    private ItemStack item;
    Set<ButtonAction> actions = new HashSet<>();

    public Button(ItemStack item){
        this.item = item;
    }

    public boolean isClickable(){
        return !actions.isEmpty();
    }

    public void onClick(Player p, ClickType type){
        actions.forEach(a -> a.run(p, type));
    }
}
