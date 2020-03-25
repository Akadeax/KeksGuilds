package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.KeksGuilds;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildDisbandSubcommand implements Subcommand {

    private String guildDisbandedMessage = KeksGuilds.config.getString("messages.disband.success");
    private String notInGuildErorr = KeksGuilds.config.getString("messages.disband.notInGuild");
    private String notLeaderError = KeksGuilds.config.getString("messages.disband.notLeader");

    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 0) return false;

        Guild guild = GuildController.getGuild(sender.getUniqueId());
        String result = GuildController.disbandGuild(sender.getUniqueId());
        switch(result) {
            case "success":
                if(guild == null) return false;
                String message = guildDisbandedMessage.replace("{GUILD}", guild.name);
                sender.sendMessage(message); break;
            case "notInGuild":
                sender.sendMessage(notInGuildErorr); break;
            case "notLeader":
                sender.sendMessage(notLeaderError); break;
        }

        return true;
    }
}
