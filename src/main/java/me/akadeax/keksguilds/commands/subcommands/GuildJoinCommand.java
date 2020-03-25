package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.KeksGuilds;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildJoinCommand implements Subcommand {

    private String guildJoinedMessage = KeksGuilds.config.getString("messages.join.success");
    private String nameNotExistError = KeksGuilds.config.getString("messages.join.nameNotExists");
    private String alreadyInGuildError = KeksGuilds.config.getString("messages.join.alreadyInGuild");
    private String maxMemberCountError = KeksGuilds.config.getString("messages.join.maxMemberCount");

    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 1) return false;

        String result = GuildController.joinGuild(args.get(0), sender.getUniqueId());
        switch(result) {
            case "success":
                String message = guildJoinedMessage.replace("{GUILD}", args.get(0));
                sender.sendMessage(message); break;
            case "notExists":
                sender.sendMessage(nameNotExistError); break;
            case "alreadyInGuild":
                sender.sendMessage(alreadyInGuildError); break;
            case "maxMemberCount":
                sender.sendMessage(maxMemberCountError); break;
        }

        return true;
    }
}
