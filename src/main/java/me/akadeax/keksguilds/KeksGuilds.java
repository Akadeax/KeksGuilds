package me.akadeax.keksguilds;

import me.akadeax.keksguilds.upgrades.GuildUpgradesController;
import me.akadeax.keksguilds.util.TimeUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class KeksGuilds extends JavaPlugin {

    public static FileConfiguration config;

    @Override
    public void onEnable() {
        config = getConfig();

        register();
        initConfig();

        SaveLoadHandler.initFiles();
        SaveLoadHandler.loadGuilds();
        SaveLoadHandler.loadUpgradeTemplates();

        // send serverStart once, and serverUpdate every 5 ticks to all UpgradeHandlers
        TimeUtil.runDelayed(this, 5, GuildUpgradesController::sendServerStart);
        TimeUtil.repeat(this, 5, 5, GuildUpgradesController::sendServerUpdate);
    }

    private void register() {
        getCommand("guild").setExecutor(new GuildCommand());
    }

    private void initConfig() {
        getConfig().addDefault("DBFolder", "./DB");
        getConfig().addDefault("guildListFile", "./DB/GuildList.json");
        getConfig().addDefault("guildUpgradesTemplatesFile", "./DB/GuildUpgradeTemplates.json");

        getConfig().addDefault("guildDefault.maxMembers", 5);

        getConfig().addDefault("messages.guildCreated", "§aYour Guild has successfully been created.");
        getConfig().addDefault("error.guildNameExists", "§4That Name already exists!");
        getConfig().addDefault("error.alreadyInGuild", "§4You are already in a guild!");

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}
