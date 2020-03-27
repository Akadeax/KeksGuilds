package me.akadeax.keksguilds;

import me.akadeax.keksguilds.commands.CommandError;
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
        initConfig();
        CommandError.loadErrorMessages();

        register();

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

        getConfig().addDefault("messages.leave.success", "§aYou successfully left {GUILD}!");
        getConfig().addDefault("messages.leave.notification", "§a{PLAYER} has left the guild!");

        getConfig().addDefault("messages.invite.success", "§aSuccessfully invited {PLAYER} to {GUILD}.");
        getConfig().addDefault("messages.invite.receivedInviteMessage", "§aYou have been invited to {GUILD}! use /g accept {GUILD} to accept the invitation.");

        getConfig().addDefault("messages.join.success", "§aYou successfully joined {GUILD}!");
        getConfig().addDefault("messages.join.notification", "§a{PLAYER} has joined the guild!");

        getConfig().addDefault("messages.disband.success", "§aSuccessfully disbanded {GUILD}.");
        getConfig().addDefault("messages.disband.notification", "§aYour guild has been disbanded!");

        getConfig().addDefault("messages.help.help", "§4Go get some help.");

        getConfig().addDefault("messages.info.format", "{GUILD}:\n§6Members ({MEMBER_COUNT}/{MAX_MEMBERS}):\n{MEMBERS}Upgrades:\n{UPGRADES}");
        getConfig().addDefault("messages.info.memberDisplay", "§6- §b{NAME}, Kills: {KILL_COUNT}");
        getConfig().addDefault("messages.info.upgradeDisplay", "§6{UPGRADE_TITLE} x{UPGRADE_AMOUNT}");

        getConfig().addDefault("error.Success", "§cSomething went wrong!");
        getConfig().addDefault("error.GuildAlreadyExists", "§cThat Guild already exists!");
        getConfig().addDefault("error.GuildNotExists", "§cThat Guild doesn't exist!");
        getConfig().addDefault("error.AlreadyInGuild", "§cYou are already part of a Guild!");
        getConfig().addDefault("error.NotInGuild", "§cYou aren't part of any Guild!");
        getConfig().addDefault("error.TargetInGuild", "§cThat player is already part of a Guild!");
        getConfig().addDefault("error.PlayerNotFound", "§cCouldn't find that player!");
        getConfig().addDefault("error.IsLeader", "§cYou are the leader of the Guild!");
        getConfig().addDefault("error.NotLeader", "§cYou aren't the leader of the Guild!");
        getConfig().addDefault("error.NotInvited", "§cYou aren't invited to that Guild!");
        getConfig().addDefault("error.InvitedSelf", "§cYou can't invite yourself!");
        getConfig().addDefault("error.MaxMembersReached", "§cThat Guild has reached their member cap!");

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}
