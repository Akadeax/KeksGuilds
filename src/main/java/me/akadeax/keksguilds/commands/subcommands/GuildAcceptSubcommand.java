package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.GuildMember;
import me.akadeax.keksguilds.KeksGuilds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildAcceptSubcommand implements Subcommand {

    private String guildJoinedMessage = KeksGuilds.config.getString("messages.join.success");
    private String guildJoinedNotification = KeksGuilds.config.getString("messages.join.notification");
    private String nameNotExistError = KeksGuilds.config.getString("messages.join.nameNotExists");
    private String noInviteError = KeksGuilds.config.getString("messages.join.noInvite");
    private String alreadyInGuildError = KeksGuilds.config.getString("messages.join.alreadyInGuild");
    private String maxMemberCountError = KeksGuilds.config.getString("messages.join.maxMemberCount");

    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 1) return false;

        String result = GuildController.acceptGuildInvite(args.get(0), sender.getUniqueId());
        switch(result) {
            case "success":
                Guild guild = GuildController.getGuild(sender.getUniqueId());
                if(guild == null) return false;

                String senderMessage = guildJoinedMessage.replace("{GUILD}", args.get(0));
                sender.sendMessage(senderMessage);

                // sent to all other online members
                String notificationMessage = guildJoinedNotification.replace("{PLAYER}", sender.getName());
                for(GuildMember gm : guild.members) {
                    // if player just joined or isn't online, continue
                    if(gm.uuid.equals(sender.getUniqueId())) continue;
                    Player gmPlayer = Bukkit.getPlayer(gm.uuid);
                    if(gmPlayer == null) continue;
                    gmPlayer.sendMessage(notificationMessage);
                }
                break;
            case "notExists":
                sender.sendMessage(nameNotExistError); break;
            case "notInvited":
                sender.sendMessage(noInviteError); break;
            case "alreadyInGuild":
                sender.sendMessage(alreadyInGuildError); break;
            case "maxMemberCount":
                sender.sendMessage(maxMemberCountError); break;
        }

        return true;
    }
}
