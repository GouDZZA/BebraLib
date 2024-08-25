package by.likebebras.bebralib.ez.menu.button;

import by.likebebras.bebralib.utils.ColorUtil;
import by.likebebras.bebralib.utils.LogUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {
    private ItemStack itemStack = new ItemStack(Material.DIRT);
    private ItemMeta meta = itemStack.getItemMeta();


    public ItemBuilder name(String name){
        meta.setDisplayName(ColorUtil.hex(name));

        return this;
    }

    public ItemBuilder lore(List<String> lore){
        meta.setLore(ColorUtil.hex(lore));

        return this;
    }

    public ItemBuilder type(String type){
        Material material = Material.getMaterial(type);

        if (material != null)
            itemStack.setType(material);
        else
            LogUtil.warn("wrong material: " + type);

        return this;
    }

    public ItemBuilder amount(int amount){
        if (amount < 64 && amount > 0) amount = 1;

        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder glow(boolean glow){
        if (glow){
            meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        else {
            meta.removeEnchant(Enchantment.ARROW_DAMAGE);
        }

        return this;
    }


    public ItemStack build(){
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
