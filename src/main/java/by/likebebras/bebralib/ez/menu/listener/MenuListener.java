package by.likebebras.bebralib.ez.menu.listener;

import by.likebebras.bebralib.ez.menu.EzMenu;
import by.likebebras.bebralib.utils.RunnableUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

public class MenuListener implements Listener {
    @EventHandler
    public void onMainClick(InventoryClickEvent e){
        if (e.getInventory().getHolder() instanceof EzMenu menu) menu.click(e);
    }

    @EventHandler
    public void onAnotherClick1(InventoryInteractEvent e){
        if (isMenu(e.getInventory())) e.setCancelled(true);
    }
    @EventHandler
    public void onAnotherClick2(InventoryDragEvent e) {
        if (isMenu(e.getInventory())) e.setCancelled(true);
    }
    @EventHandler
    public void onAnotherClick3(InventoryCreativeEvent e){
        if (isMenu(e.getInventory())) e.setCancelled(true);
    }
    @EventHandler
    public void onAnotherClick4(InventoryMoveItemEvent e){
        if (isMenu(e.getDestination()) || isMenu(e.getSource())) e.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        if (e.getInventory().getHolder() instanceof EzMenu menu){
            if (!menu.isClosable()) RunnableUtils.runLater(() -> menu.show((Player) e.getPlayer()), 1);
        }
    }


    private boolean isMenu(Inventory inv){
        return inv.getHolder() instanceof EzMenu;
    }
}
