package me.akadeax.keksguilds;

import me.akadeax.keksguilds.commands.GuildCommand;
import me.akadeax.keksguilds.upgrades.GuildUpgradesController;
import me.akadeax.keksguilds.util.TimeUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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

    @SuppressWarnings("ConstantConditions")
    private void register() {
        GuildCommand guildCommand = new GuildCommand();
        getCommand("guild").setExecutor(guildCommand);
        getCommand("guild").setTabCompleter(guildCommand);
    }

    private void initConfig() {
        getConfig().addDefault("DBFolder", "./DB");
        getConfig().addDefault("guildListFile", "./DB/GuildList.json");
        getConfig().addDefault("guildUpgradesTemplatesFile", "./DB/GuildUpgradeTemplates.json");

        getConfig().addDefault("guildDefault.maxMembers", 5);

        getConfig().addDefault("messages.create.success", "§a{GUILD} has successfully been created.");
        getConfig().addDefault("messages.create.guildNameExists", "§cThat Name already exists!");
        getConfig().addDefault("messages.create.alreadyInGuild", "§cYou are already in a guild!");

        getConfig().addDefault("messages.leave.success", "§aYou successfully left {GUILD}!");
        getConfig().addDefault("messages.leave.notInGuild", "§cYou aren't part of any guild!");
        getConfig().addDefault("messages.leave.isLeader", "§cYou are the guilds leader, you cannot leave the guild!");

        getConfig().addDefault("messages.join.success", "§aYou successfully joined {GUILD}!");
        getConfig().addDefault("messages.join.nameNotExists", "§cThat isn't a guild name!");
        getConfig().addDefault("messages.join.alreadyInGuild", "§cYou are already in a guild!");
        getConfig().addDefault("messages.join.maxMemberCount", "§cThat guild is full!");

        getConfig().addDefault("messages.disband.success", "§aSuccessfully disbanded {GUILD}.");
        getConfig().addDefault("messages.disband.notInGuild", "§cYou aren't part of any guild!");
        getConfig().addDefault("messages.disband.notLeader", "§cYou are not the leader of that guild.");

        getConfig().addDefault("messages.help.help", "§4Go get some help.\nboop.");

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}
