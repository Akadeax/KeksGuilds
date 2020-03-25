package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.KeksGuilds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class GuildCreateSubcommand implements Subcommand {

    private String guildCreatedMessage = KeksGuilds.config.getString("messages.create.success");
    private String guildNameExistsError = KeksGuilds.config.getString("messages.create.guildNameExists");
    private String alreadyInGuildError = KeksGuilds.config.getString("messages.create.alreadyInGuild");


    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 1) return false;

        String name = args.get(0);
        UUID leader = sender.getUniqueId();

        String result = GuildController.createGuild(name, leader);
        switch(result) {
            case "success":
                String message = guildCreatedMessage.replace("{GUILD}", args.get(0));
                sender.sendMessage(message); break;
            case "exists":
                sender.sendMessage(guildNameExistsError); break;
            case "alreadyInGuild":
                sender.sendMessage(alreadyInGuildError); break;
        }

        return true;
    }
}
