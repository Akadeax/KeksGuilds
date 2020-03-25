package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.GuildMember;
import me.akadeax.keksguilds.KeksGuilds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GuildDisbandSubcommand implements Subcommand {

    private String guildDisbandedMessage = KeksGuilds.config.getString("messages.disband.success");
    private String guildDisbandedNotification = KeksGuilds.config.getString("messages.disband.notification");
    private String notInGuildErorr = KeksGuilds.config.getString("messages.disband.notInGuild");
    private String notLeaderError = KeksGuilds.config.getString("messages.disband.notLeader");

    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 0) return false;


        Guild guild = GuildController.getGuild(sender.getUniqueId());
        if(guild != null) guild = new Guild(guild);
        String result = GuildController.disbandGuild(sender.getUniqueId());
        switch(result) {
            case "success":
                if(guild == null) return false;
                String message = guildDisbandedMessage.replace("{GUILD}", guild.name);
                sender.sendMessage(message);

                // sent to all other online members
                String notificationMessage = guildDisbandedNotification.replace("{PLAYER}", sender.getName());
                for(GuildMember gm : guild.members) {
                    // if player isn't online or is disbander, continue
                    if(gm.uuid.equals(sender.getUniqueId())) continue;
                    Player gmPlayer = Bukkit.getPlayer(gm.uuid);
                    if(gmPlayer == null) continue;
                    gmPlayer.sendMessage(notificationMessage);
                }
                break;
            case "notInGuild":
                sender.sendMessage(notInGuildErorr); break;
            case "notLeader":
                sender.sendMessage(notLeaderError); break;
        }

        return true;
    }
}
