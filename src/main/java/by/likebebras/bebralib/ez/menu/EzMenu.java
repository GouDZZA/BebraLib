package by.likebebras.bebralib.ez.menu;

import by.likebebras.bebralib.ez.menu.button.Button;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class EzMenu implements InventoryHolder {

    public static final String DEFAULT_NAME = "Default Title";

    private final HashMap<Integer, Button> buttons = new HashMap<>();
    private Inventory inv;

    private int slots;
    private String title;
    @Getter
    private boolean closable = true;

    public EzMenu(Inventory inv) {
        this.inv = inv;
    }

    public void closable(boolean value){
        closable = value;
    }

    public EzMenu(){
        inv = Bukkit.createInventory(this, 9);
    }

    public EzMenu(int slots, String title){
        this.slots = slots;
        this.title = title;
    }

    public void addButton(int slot, Button button){
        this.buttons.put(slot, button);
    }

    public void show(Player p){
        prepareInv();
        prepareButtons();

        if (inv != null)
            p.openInventory(inv);
    }

    public void updateButtons(int... slots){
        for (int slot : slots) {
            inv.setItem(slot, buttons.get(slot).getItem());
        }
    }

    public void prepareInv(){
        final InventoryType type = switch (slots){
            case 5 -> InventoryType.HOPPER;
            case 9, 18, 27, 36, 45, 54 -> InventoryType.CHEST;

            default -> null;
        };

        if (type == null) {
            inv = Bukkit.createInventory(this, InventoryType.CHEST, DEFAULT_NAME);
            return;
        }

        if (buttons.isEmpty()) {
            inv =  Bukkit.createInventory(this, slots, title);
            return;
        }

        if (type == InventoryType.CHEST)
            inv = Bukkit.createInventory(this, slots, title);
        else
            inv = Bukkit.createInventory(this, type, title);
    }

    public void prepareButtons(){
        buttons.forEach((slot,b) -> inv.setItem(slot -1, b.getItem()));
    }

    public void click(InventoryClickEvent e){
        e.setCancelled(true);
        int slot = e.getSlot();

        if (slot > inv.getSize()) return;

        Button button = getButton(slot);
        if (button == null) return;

        if (button.isClickable())
            button.onClick((Player) e.getWhoClicked(), e.getClick());
    }

    public Button getButton(int slot){
        return buttons.get(slot);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
