package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.KeksGuilds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildInviteSubcommand implements Subcommand {

    private String inviteSuccessfulMessage = KeksGuilds.config.getString("messages.invite.success");
    private String receivedInviteMessage = KeksGuilds.config.getString("messages.invite.receivedInviteMessage");
    private String playerNotFoundError = KeksGuilds.config.getString("messages.invite.playerNotFound");
    private String notInGuildError = KeksGuilds.config.getString("messages.invite.notInGuild");
    private String notLeaderError = KeksGuilds.config.getString("messages.invite.notLeader");
    private String inviteSelfError = KeksGuilds.config.getString("messages.invite.inviteSelf");
    private String targetInGuildError = KeksGuilds.config.getString("messages.invite.targetInGuild");


    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 1) return false;

        String response = GuildController.sendGuildInvite(sender.getUniqueId(), args.get(0));
        switch(response) {
            case "success":
                Guild senderGuild = GuildController.getGuild(sender.getUniqueId());
                if(senderGuild == null) return false;

                String senderMessage = inviteSuccessfulMessage;
                senderMessage = senderMessage.replace("{PLAYER}", args.get(0));
                senderMessage = senderMessage.replace("{GUILD}", senderGuild.name);
                sender.sendMessage(senderMessage);

                String receiverMessage = receivedInviteMessage.replace("{GUILD}", senderGuild.name);
                Bukkit.getPlayer(args.get(0)).sendMessage(receiverMessage);
                break;

            case "playerNotFound":
                sender.sendMessage(playerNotFoundError); break;
            case "notInGuild":
                sender.sendMessage(notInGuildError); break;
            case "notLeader":
                sender.sendMessage(notLeaderError); break;
            case "inviteSelf":
                sender.sendMessage(inviteSelfError); break;
            case "targetInGuild":
                sender.sendMessage(targetInGuildError); break;
        }

        return true;
    }
}
