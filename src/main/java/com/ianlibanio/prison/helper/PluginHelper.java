package com.ianlibanio.prison.helper;

import com.ianlibanio.prison.module.PrisonModule;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class PluginHelper extends JavaPlugin {

    public abstract void load();
    public abstract void enable();
    public abstract void disable();

    @Override
    public void onLoad() {
        load();
    }

    @Override
    public void onEnable() {
        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }

    public <T> T getService(Class<T> service) {
        Objects.requireNonNull(service, "clazz");
        return Optional.ofNullable(Bukkit.getServicesManager().getRegistration(service)).map(RegisteredServiceProvider::getProvider).orElseThrow(() -> new IllegalStateException("No registration present for service '" + service.getName() + "'"));
    }

    public <T> void provideService(Class<T> clazz, T instance, ServicePriority priority) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(instance, "instance");
        Objects.requireNonNull(priority, "priority");
        Bukkit.getServicesManager().register(clazz, instance, this, priority);
    }

    public <T> void provideService(Class<T> clazz, T instance) {
        this.provideService(clazz, instance, ServicePriority.Normal);
    }

    public void listener(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    public void initModules(List<PrisonModule> modules) {
        modules.forEach(PrisonModule::init);
    }

    public void stopModules(List<PrisonModule> modules) {
        modules.forEach(PrisonModule::stop);
    }

    @SneakyThrows
    public HikariDataSource getHikariDataSource() {
        val configuration = getConfig();
        val dataSource = new HikariDataSource();

        dataSource.setMaximumPoolSize(20);
        dataSource.setJdbcUrl("jdbc:mysql://" + configuration.getString("MySQL.host", "localhost") + ":" + configuration.getString("MySQL.port", "3306") + "/" + configuration.getString("MySQL.database", "prisoncore"));
        dataSource.setUsername(configuration.getString("MySQL.user", "root"));
        dataSource.setPassword(configuration.getString("MySQL.password", ""));
        dataSource.addDataSourceProperty("autoReconnect", "true");

        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource.addDataSourceProperty("useServerPrepStmts", "true");

        return dataSource;
    }

}
