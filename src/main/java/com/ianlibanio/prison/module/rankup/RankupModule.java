package com.ianlibanio.prison.module.rankup;

import com.ianlibanio.prison.PrisonCore;
import com.ianlibanio.prison.module.PrisonModule;
import com.ianlibanio.prison.module.rankup.commands.RankInfoCommand;
import com.ianlibanio.prison.module.rankup.commands.RanksCommand;
import com.ianlibanio.prison.module.rankup.commands.RankupCommand;
import com.ianlibanio.prison.module.rankup.controller.IRankupController;
import com.ianlibanio.prison.module.rankup.controller.impl.RankupController;
import com.ianlibanio.prison.module.rankup.listener.PlayerEvents;
import com.ianlibanio.prison.module.rankup.service.IRankupService;
import com.ianlibanio.voidcommand.registration.VoidRegister;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.Bukkit;

import java.sql.SQLException;

public class RankupModule implements PrisonModule {

    @Getter private static IRankupController controller;
    @Getter private static IRankupService service;

    @Override
    @SneakyThrows
    public void init() {
        controller = new RankupController();
        service = PrisonCore.getInstance().getService(IRankupService.class);

        PrisonCore.getHikariConnection().query("CREATE TABLE IF NOT EXISTS `ranks` (`uuid` VARCHAR(36) primary key, `rank` VARCHAR(100));", statement -> {
            try {
                statement.execute();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
        controller.load();

        val register = new VoidRegister(PrisonCore.getInstance());
        register.add(new RankupCommand(), new RanksCommand(), new RankInfoCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerEvents(), PrisonCore.getInstance());
        Bukkit.getOnlinePlayers().forEach(player -> service.load(player.getUniqueId()));
    }

    @Override
    public void stop() {
        service.saveAll();
    }
}
