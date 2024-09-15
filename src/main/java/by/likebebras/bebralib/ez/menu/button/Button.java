package by.likebebras.bebralib.ez.menu.button;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@Getter
public class Button {
    @Setter
    private ItemStack item;

    public Button(ItemStack item){
        this.item = item;
    }

    public void onClick(Player p, ClickType type){
    }
}
