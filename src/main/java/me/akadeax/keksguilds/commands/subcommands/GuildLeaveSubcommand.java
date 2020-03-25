package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.GuildMember;
import me.akadeax.keksguilds.KeksGuilds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildLeaveSubcommand implements Subcommand {

    private String guildLeftMessage = KeksGuilds.config.getString("messages.leave.success");
    private String guildLeftNotification = KeksGuilds.config.getString("messages.leave.notification");
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
                sender.sendMessage(message);

                // sent to all other online members
                String notificationMessage = guildLeftNotification.replace("{PLAYER}", sender.getName());
                for(GuildMember gm : guild.members) {
                    // if player isn't online, continue
                    Player gmPlayer = Bukkit.getPlayer(gm.uuid);
                    if(gmPlayer == null) continue;
                    gmPlayer.sendMessage(notificationMessage);
                }
                break;
            case "notInGuild":
                sender.sendMessage(notInGuildError); break;
            case "isLeader":
                sender.sendMessage(isLeaderError); break;
        }
        return true;
    }
}
