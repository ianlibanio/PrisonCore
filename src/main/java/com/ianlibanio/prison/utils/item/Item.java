package com.ianlibanio.prison.utils.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Item {

    private final ItemStack stack;
    private final ItemMeta meta;

    public Item(ItemStack stack) {
        this.stack = stack;
        this.meta = stack.getItemMeta();
    }

    public Item(Material material) {
        this.stack = new ItemStack(material);
        this.meta = stack.getItemMeta();
    }

    public Item(Material material, int amount) {
        this.stack = new ItemStack(material, amount);
        this.meta = stack.getItemMeta();
    }

    public Item(Material material, int amount, int data) {
        this.stack = new ItemStack(material, amount, (short) data);
        this.meta = stack.getItemMeta();
    }

    public Item name(String name) {
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        return this;
    }

    public Item lore(String... lines) {
        String[] linesArray = lines.clone();

        for (int i = 0; i < linesArray.length; i++) {
            linesArray[i] = ChatColor.translateAlternateColorCodes('&', linesArray[i]);
        }
        meta.setLore(Arrays.stream(linesArray).collect(Collectors.toList()));

        return this;
    }

    public Item lore(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, ChatColor.translateAlternateColorCodes('&', lines.get(i)));
        }

        meta.setLore(lines);
        return this;
    }

    public Item enchant(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);

        return this;
    }

    public Item flags(ItemFlag... flags) {
        meta.addItemFlags(flags);

        return this;
    }

    public Item meta(Consumer<ItemMeta> metaConsumer) {
        metaConsumer.accept(meta);

        return this;
    }

    public ItemStack build() {
        stack.setItemMeta(meta);

        return stack;
    }

}
