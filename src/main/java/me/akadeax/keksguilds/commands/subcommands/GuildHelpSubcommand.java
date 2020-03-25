package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.KeksGuilds;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildHelpSubcommand implements Subcommand {

    private String helpMessage = KeksGuilds.config.getString("messages.help.help");

    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        sender.sendMessage(helpMessage);
        return true;
    }
}
