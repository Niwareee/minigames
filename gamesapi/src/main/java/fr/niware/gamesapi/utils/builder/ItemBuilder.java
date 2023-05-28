package fr.niware.gamesapi.utils.builder;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemBuilder implements Cloneable {

    private final Material material;
    private final int amount;
    private final List<Component> lores;
    private final Map<Enchantment, Integer> enchantments;
    private final List<ItemFlag> flags;

    private ItemStack item;
    private ItemMeta meta;
    private String name;
    private int durability;

    public ItemBuilder(Material material, int amount, String name, List<Component> lore, List<ItemFlag> flags, Map<Enchantment, Integer> enchantments) {
        this.material = material;
        this.amount = amount;
        this.name = name;
        this.lores = lore;
        this.flags = flags;
        this.enchantments = enchantments;
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack item) {
        this(item.getType(), 1);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, null);
    }

    public ItemBuilder(Material material, int amount, String name) {
        this(material, amount, name, new ArrayList<>(), new ArrayList<>(), new HashMap<>());
    }

    public ItemBuilder(Material material, String name) {
        this(material, 1, name, new ArrayList<>(), new ArrayList<>(), new HashMap<>());
    }

    public ItemBuilder(Material material, ItemFlag... flags) {
        this(material, 1, null, new ArrayList<>(), Arrays.asList(flags), new HashMap<>());
    }

    public ItemBuilder(Material material, Component... lore) {
        this(material, 1, null, Arrays.asList(lore), new ArrayList<>(), new HashMap<>());
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int value) {
        this.enchantments.put(enchantment, value);
        return this;
    }

    public ItemBuilder setUnbreakable() {
        if (this.meta == null) {
            this.build();
        }
        this.meta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder setFlags(ItemFlag... flags) {
        this.flags.addAll(Arrays.asList(flags));
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        this.flags.add(flag);
        return this;
    }

    public ItemBuilder addLoreLine(String format) {
        this.lores.add(Component.text(format));
        return this;
    }

    public ItemBuilder addLoreLine(List<String> format) {
        format.forEach(line -> this.lores.add(Component.text(line)));
        return this;
    }

    public ItemBuilder setLores(List<Component> lore) {
        this.lores.addAll(lore);
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        this.durability = durability;
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        if (this.meta == null) {
            this.build();
        }
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) this.meta;
        armorMeta.setColor(color);
        return this;
    }

    public ItemBuilder setGlowing() {
        return this.addEnchant(this.material != Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK, 1).addFlag(ItemFlag.HIDE_ENCHANTS);
    }

    public ItemBuilder setOwner(UUID uuid) {
        if (this.meta == null) {
            this.build();
        }
        var skullMeta = (SkullMeta) this.meta;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        return this;
    }

    public ItemStack build() {
        this.item = new ItemStack(this.material, this.amount);

        if (this.meta == null) {
            this.meta = this.item.getItemMeta();
        }

        if (!this.flags.isEmpty()) {
            this.flags.forEach(flag -> this.meta.addItemFlags(flag));
        }

        if (this.name != null) {
            this.meta.displayName(Component.text(this.name));
        }

        if (!this.lores.isEmpty()) {
            this.meta.lore(this.lores);
        }

        if (!this.enchantments.isEmpty()) {
            this.enchantments.forEach((e, l) -> this.meta.addEnchant(e, l, true));
        }

        if (this.durability != 0 && this.meta instanceof Damageable damageable) {
            damageable.setDamage(this.durability);
        }

        this.item.setItemMeta(this.meta);
        return this.item;
    }

    @Override
    public ItemBuilder clone() {
        try {
            return (ItemBuilder) super.clone();
        } catch (CloneNotSupportedException exception) {
            return new ItemBuilder(this.material);
        }
    }

    public ItemStack getItem() {
        return this.item;
    }

    public Material getMaterial() {
        return this.material;
    }

    public ItemMeta getMeta() {
        if (this.meta == null) {
            this.meta = new ItemStack(this.material, this.amount).getItemMeta();
        }
        return this.meta;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getName() {
        return this.name;
    }

    public List<Component> getLores() {
        return this.lores;
    }

    public List<ItemFlag> getFlags() {
        return this.flags;
    }

    public int getDurability() {
        return this.durability;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return this.enchantments;
    }
}