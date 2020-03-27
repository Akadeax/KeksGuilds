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

public class GuildDisbandSubcommand implements Subcommand {

    private String guildDisbandedMessage = KeksGuilds.config.getString("messages.disband.success");
    private String guildDisbandedNotification = KeksGuilds.config.getString("messages.disband.notification");

    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 0) return false;


        Guild guild = GuildController.getGuild(sender.getUniqueId());
        if(guild != null) guild = new Guild(guild);
        CommandResultState result = GuildController.disbandGuild(sender.getUniqueId());
        switch(result) {
            case Success:
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
            default:
                sender.sendMessage(CommandError.getErrorMessage(result));
        }

        return true;
    }
}
