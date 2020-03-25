package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.KeksGuilds;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildLeaveSubcommand implements Subcommand {

    private String guildLeftMessage = KeksGuilds.config.getString("messages.leave.success");
    private String notInGuildError = KeksGuilds.config.getString("messages.leave.notInGuild");
    private String isLeaderError = KeksGuilds.config.getString("messages.leave.isLeader");

    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 0) return false;

        Guild guild = GuildController.getGuild(sender.getUniqueId());
        String result = GuildController.leaveGuild(sender.getUniqueId());
        switch(result) {
            case "success":
                if(guild == null) return false;
                String message = guildLeftMessage.replace("{GUILD}", guild.name);
                sender.sendMessage(message); break;
            case "notInGuild":
                sender.sendMessage(notInGuildError); break;
            case "isLeader":
                sender.sendMessage(isLeaderError); break;
        }
        return true;
    }
}
