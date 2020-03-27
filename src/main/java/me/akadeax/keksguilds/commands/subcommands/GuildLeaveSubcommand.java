package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.GuildMember;
import me.akadeax.keksguilds.KeksGuilds;
import me.akadeax.keksguilds.commands.CommandError;
import me.akadeax.keksguilds.commands.CommandResultState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildLeaveSubcommand implements Subcommand {

    private String guildLeftMessage = KeksGuilds.config.getString("messages.leave.success");
    private String guildLeftNotification = KeksGuilds.config.getString("messages.leave.notification");

    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 0) return false;

        Guild guild = GuildController.getGuild(sender.getUniqueId());
        CommandResultState result = GuildController.leaveGuild(sender.getUniqueId());
        switch(result) {
            case Success:
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

            default:
                sender.sendMessage(CommandError.getErrorMessage(result));
        }
        return true;
    }
}
