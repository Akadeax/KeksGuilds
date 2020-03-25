package me.akadeax.keksguilds;

import me.akadeax.keksguilds.upgrades.GuildUpgrade;
import me.akadeax.keksguilds.upgrades.GuildUpgradesController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GuildCommand implements CommandExecutor {

    String guildCreatedMessage = KeksGuilds.config.getString("messages.guildCreated");
    String guildNameExistsError = KeksGuilds.config.getString("error.guildNameExists");
    String alreadyInGuildError = KeksGuilds.config.getString("error.alreadyInGuild");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player p = (Player)sender;

        if(args.length == 0) return false;

        switch (args[0]) {
            case "load":
                SaveLoadHandler.initFiles();
                SaveLoadHandler.loadGuilds();
                SaveLoadHandler.loadUpgradeTemplates();
                break;

            case "create":
                if(args.length != 2) return false;
                String name = args[1];
                UUID leader = p.getUniqueId();

                String result = GuildController.createGuild(name, leader);
                switch(result) {
                    case "success":
                        p.sendMessage(guildCreatedMessage); break;
                    case "exists":
                        p.sendMessage(guildNameExistsError); break;
                }

            case "start":
                GuildUpgradesController.sendServerStart();
                break;
        }

        return true;
    }


}
