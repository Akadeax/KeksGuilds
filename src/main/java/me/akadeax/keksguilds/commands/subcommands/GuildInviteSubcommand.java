package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.KeksGuilds;
import me.akadeax.keksguilds.commands.CommandError;
import me.akadeax.keksguilds.commands.CommandResultState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildInviteSubcommand implements Subcommand {

    private String inviteSuccessfulMessage = KeksGuilds.config.getString("messages.invite.success");
    private String receivedInviteMessage = KeksGuilds.config.getString("messages.invite.receivedInviteMessage");

    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 1) return false;

        CommandResultState result = GuildController.sendGuildInvite(sender.getUniqueId(), args.get(0));
        switch(result) {
            case Success:
                Guild senderGuild = GuildController.getGuild(sender.getUniqueId());
                if(senderGuild == null) return false;

                String senderMessage = inviteSuccessfulMessage;
                senderMessage = senderMessage.replace("{PLAYER}", args.get(0));
                senderMessage = senderMessage.replace("{GUILD}", senderGuild.name);
                sender.sendMessage(senderMessage);

                System.out.println(receivedInviteMessage);
                String receiverMessage = receivedInviteMessage.replace("{GUILD}", senderGuild.name);
                Bukkit.getPlayer(args.get(0)).sendMessage(receiverMessage);

            default:
                sender.sendMessage(CommandError.getErrorMessage(result));
        }

        return true;
    }
}
