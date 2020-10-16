package com.ianlibanio.prison.utils.message;

import com.google.common.collect.Lists;
import com.ianlibanio.prison.PrisonCore;
import com.ianlibanio.prison.utils.message.key.ReplacementKey;
import com.ianlibanio.prison.utils.message.module.Module;
import lombok.val;
import org.bukkit.ChatColor;

import java.util.List;

public class Messages {

    public static String getMessage(Module module, String message, ReplacementKey... keys) {
        String msg = ChatColor.translateAlternateColorCodes(
                '&',
                PrisonCore.getMessagesConfiguration().getConfiguration().getString("messages." + module.getName() + "." + message, "Message not found (" + message + ").")
        );;

        for (val key : keys) {
            msg = msg.replace(key.getTarget(), key.getReplacement());
        }

        return msg;
    }

    public static List<String> getMessageList(Module module, String message, ReplacementKey... keys) {
        List<String> messages = PrisonCore.getMessagesConfiguration().getConfiguration().getStringList("messages." + module.getName() + "." + message);

        if (messages == null) messages = Lists.newArrayList("Message not found (" + message + ").");

        for (int i = 0; i < messages.size(); i++) {
            messages.set(i, ChatColor.translateAlternateColorCodes('&', messages.get(i)));

            for (val key : keys) {
                messages.set(i, messages.get(i).replace(key.getTarget(), key.getReplacement()));
            }
        }

        return messages;
    }

    public static String getMessage(Module module, String message, List<ReplacementKey> keys) {
        String msg = ChatColor.translateAlternateColorCodes(
                '&',
                PrisonCore.getMessagesConfiguration().getConfiguration().getString("messages." + module.getName() + "." + message)
        );;

        for (val key : keys) {
            msg = msg.replace(key.getTarget(), key.getReplacement());
        }

        return msg;
    }

    public static List<String> getMessageList(Module module, String message, List<ReplacementKey> keys) {
        val messages = PrisonCore.getMessagesConfiguration().getConfiguration().getStringList("messages." + module.getName() + "." + message);

        for (int i = 0; i < messages.size(); i++) {
            messages.set(i, ChatColor.translateAlternateColorCodes('&', messages.get(i)));

            for (val key : keys) {
                messages.set(i, messages.get(i).replace(key.getTarget(), key.getReplacement()));
            }
        }

        return messages;
    }

}
