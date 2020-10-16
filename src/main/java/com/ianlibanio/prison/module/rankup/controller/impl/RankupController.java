package com.ianlibanio.prison.module.rankup.controller.impl;

import com.google.common.collect.Lists;
import com.ianlibanio.prison.PrisonCore;
import com.ianlibanio.prison.module.rankup.controller.IRankupController;
import com.ianlibanio.prison.module.rankup.data.Rank;
import com.ianlibanio.prison.module.rankup.messages.RankupMessages;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RankupController implements IRankupController {

    private String listMessage;
    private final List<Rank> ranks = Lists.newArrayList();

    @Override
    public void load() {
        val configuration = PrisonCore.getRanksConfiguration().getConfiguration();
        val section = configuration.getConfigurationSection("ranks");

        if (section == null) return;

        val keys = section.getKeys(false);
        val i = new AtomicInteger(1);

        for (String name : keys) {
            val prefix = ChatColor.translateAlternateColorCodes('&', configuration.getString("ranks." + name + ".prefix"));
            val price = configuration.getDouble("ranks." + name + ".price");

            val position = i.getAndIncrement();

            ranks.add(new Rank(name, prefix, price, position, position == 1, position == keys.size()));
        }
    }

    @Override
    public Rank get(String name) {
        return ranks.stream().filter(rank -> rank.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public Rank get(int position) {
        return ranks.stream().filter(rank -> rank.getPosition() == position).findFirst().orElse(null);
    }

    @Override
    public String ranks() {
        if (listMessage != null) return listMessage;

        val stringBuilder = new StringBuilder();

        val list = RankupMessages.getMessageList("ranks-list", null);
        val format = RankupMessages.getMessage("ranks-list-format", null);

        for (Rank rank : ranks) {
            String status = "";

            if (rank.isFirst()) status = " §a(First rank)";
            if (rank.isLast()) status = " §c(Last rank)";

            stringBuilder.append(format
                    .replace("{prefix}", rank.getPrefix())
                    .replace("{price}", rank.getPrice() + "")
                    .replace("{status}", status)
            );

            if (!rank.isLast()) stringBuilder.append(" \n");
        }

        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).replace("{total}", ranks.size() + ""));
            list.set(i, list.get(i).replace("{info}", stringBuilder.toString()));
        }

        val message = StringUtils.join(list, " \n");

        listMessage = message;
        return message;
    }

    @Override
    public List<Rank> all() {
        return this.ranks;
    }
}
