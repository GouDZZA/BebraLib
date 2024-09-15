package by.likebebras.bebralib.ez;

import by.likebebras.bebralib.utils.LogUtil;
import com.google.common.collect.ImmutableList;
import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EzEnchant extends Enchantment implements Listener {

    public final String name;
    public final int maxLvl;
    private final EzPlugin daddy;

    public EzEnchant(EzPlugin daddy, String id, String name, int maxLvl) {
        super(NamespacedKey.minecraft(id));
        this.name = name;
        this.maxLvl = maxLvl;
        this.daddy = daddy;
    }
    public final void register(){
        daddy.registerListener(this);

        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(this);
        } catch (Exception e) {
            LogUtil.warn("there was an error, while registering " + name + " enchantment.");
            LogUtil.warn(e.getMessage());
        }
    }

    public int getLvL(ItemStack item){
        if (!hasMe(item)) return 0;

        else return item.getEnchantmentLevel(this);
    }

    public boolean hasMe(ItemStack item){
        return validateItemStack(item) && item.getItemMeta().hasEnchant(this);
    }

    public boolean validateItemStack(ItemStack item){
        return item != null && item.getItemMeta() != null && item.getType() != Material.AIR && item.getAmount() != 0;
    }

    public void enchantMe(ItemStack item){
        enchantMe(item, getStartLevel());
    }

    public void enchantMe(ItemStack item, int lvl){
        if (validateItemStack(item)){
            enchantMeSilent(item, lvl);

            ItemMeta meta = item.getItemMeta();

            List<Component> list = meta.lore();

            if (list != null)
                list.add(0, Component.text(name).color(TextColor.color(0x707070)));
            else
                list = new ArrayList<>(
                        ImmutableList.of(Component.text(name).color(TextColor.color(0x707070)))
                );

            meta.lore(list);

            item.setItemMeta(meta);
        }
    }

    public void enchantMeSilent(ItemStack item, int lvl){
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(this, lvl, true);

        item.setItemMeta(meta);
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLvl;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack itemStack) {
        return false;
    }

    @Override
    public @NotNull Component displayName(int i) {
        return Component.text(name);
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.COMMON;
    }

    @Override
    public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return Set.of();
    }

    @Override
    public @NotNull String translationKey() {
        return getClass().getSimpleName().toLowerCase();
    }
}
