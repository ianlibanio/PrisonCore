package com.ianlibanio.prison.utils.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Head {

    private final ItemStack stack;
    private final SkullMeta meta;

    public Head() {
        this.stack = new Item(Material.SKULL_ITEM, 1, 3).build();
        this.meta = (SkullMeta) stack.getItemMeta();
    }

    public Head owner(String owner) {
        meta.setOwner(owner);
        return this;
    }

    public Head fromURL(String url) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());

        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    public Head name(String name) {
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        return this;
    }

    public Head lore(String... lines) {
        for (int i = 0; i < lines.length; i++) {
            lines[i] = ChatColor.translateAlternateColorCodes('&', lines[i]);
        }

        meta.setLore(Arrays.stream(lines).collect(Collectors.toList()));

        return this;
    }

    public Head lore(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, ChatColor.translateAlternateColorCodes('&', lines.get(i)));
        }

        meta.setLore(lines);
        return this;
    }

    public ItemStack create() {
        stack.setItemMeta(meta);
        return stack;
    }

}
