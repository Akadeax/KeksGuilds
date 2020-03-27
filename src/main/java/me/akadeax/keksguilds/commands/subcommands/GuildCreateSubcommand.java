package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.KeksGuilds;
import me.akadeax.keksguilds.commands.CommandError;
import me.akadeax.keksguilds.commands.CommandResultState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class GuildCreateSubcommand implements Subcommand {

    private String guildCreatedMessage = KeksGuilds.config.getString("messages.create.success");

    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 1) return false;

        String name = args.get(0);
        UUID leader = sender.getUniqueId();

        CommandResultState result = GuildController.createGuild(name, leader);
        switch(result) {
            case Success:
                String message = guildCreatedMessage.replace("{GUILD}", args.get(0));
                sender.sendMessage(message); break;
            default:
                sender.sendMessage(CommandError.getErrorMessage(result));
        }

        return true;
    }
}
