package com.ianlibanio.prison;

import com.google.common.collect.Lists;
import com.ianlibanio.prison.database.PrisonDatabase;
import com.ianlibanio.prison.helper.PluginHelper;
import com.ianlibanio.prison.module.PrisonModule;
import com.ianlibanio.prison.module.rankup.RankupModule;
import com.ianlibanio.prison.module.rankup.service.IRankupService;
import com.ianlibanio.prison.module.rankup.service.impl.RankupService;
import com.ianlibanio.prison.utils.configuration.CustomConfiguration;
import com.ianlibanio.prison.utils.inventory.InventoryManager;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.List;

public final class PrisonCore extends PluginHelper {

    @Getter private static PrisonCore instance;
    @Getter private static PrisonDatabase hikariConnection;

    @Getter private static Economy economy;
    @Getter private static List<PrisonModule> modules;

    @Getter private static InventoryManager inventoryManager;
    @Getter private static CustomConfiguration ranksConfiguration;
    @Getter private static CustomConfiguration messagesConfiguration;

    @Override
    public void load() {
        modules = Lists.newArrayList(new RankupModule());
        provideService(IRankupService.class, new RankupService());
    }

    @Override
    public void enable() {
        instance = this;

        saveDefaultConfig();
        if (!setupEconomy()) {
            Bukkit.getConsoleSender().sendMessage("§e[PrisonCore] §cVault hook failed, plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        ranksConfiguration = new CustomConfiguration(this, "ranks");
        messagesConfiguration = new CustomConfiguration(this, "messages");
        hikariConnection = new PrisonDatabase(getHikariDataSource());

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        initModules(modules);
    }

    @Override
    public void disable() {
        stopModules(modules);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }

        economy = rsp.getProvider();
        return economy != null;
    }


}
