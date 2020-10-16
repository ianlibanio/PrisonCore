package com.ianlibanio.prison.module.rankup.listener;

import com.ianlibanio.prison.module.rankup.RankupModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        RankupModule.getService().load(event.getPlayer().getUniqueId());
    }

}
