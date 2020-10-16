package com.ianlibanio.prison.module.rankup.messages;

import com.google.common.collect.Lists;
import com.ianlibanio.prison.module.rankup.data.Rank;
import com.ianlibanio.prison.utils.message.Messages;
import com.ianlibanio.prison.utils.message.key.ReplacementKey;
import com.ianlibanio.prison.utils.message.module.Module;

import java.util.List;

public class RankupMessages {

    public static String getMessage(String message, Rank rank) {
        return rank != null ?  Messages.getMessage(Module.RANKUP, message, getKeys(rank)) : Messages.getMessage(Module.RANKUP, message);
    }

    public static List<String> getMessageList(String message, Rank rank) {
        return rank != null ? Messages.getMessageList(Module.RANKUP, message, getKeys(rank)) : Messages.getMessageList(Module.RANKUP, message);
    }

    private static List<ReplacementKey> getKeys(Rank rank) {
        return Lists.newArrayList(new ReplacementKey("name", rank.getName()),
                new ReplacementKey("prefix", rank.getPrefix()),
                new ReplacementKey("price", rank.getPrice()),
                new ReplacementKey("first", rank.isFirst() ? "§a✔" : "§c✘"),
                new ReplacementKey("last", rank.isLast() ? "§a✔" : "§c✘"));
    }
}
