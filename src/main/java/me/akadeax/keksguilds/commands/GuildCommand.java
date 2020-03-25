package me.akadeax.keksguilds.commands;

import me.akadeax.keksguilds.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GuildCommand implements CommandExecutor, TabCompleter {

    private HashMap<String, Subcommand> subcommands = new HashMap<String, Subcommand>() {
        {
            put("help", new GuildHelpSubcommand());
            put("create", new GuildCreateSubcommand());
            put("leave", new GuildLeaveSubcommand());
            put("disband", new GuildDisbandSubcommand());
            put("accept", new GuildAcceptSubcommand());
            put("invite", new GuildInviteSubcommand());
            put("info", new GuildInfoSubcommand());
        }
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player p = (Player)sender;

        if(args.length == 0 || !subcommands.containsKey(args[0])) {
            help(p);
            return true;
        }

        // args[0] is subcommand identifier (/guild <create, leave, delete,...>)
        Subcommand sub = subcommands.get(args[0]);

        // pass all args except identifier (aka. ignore first element)
        ArrayList<String> newArgs = new ArrayList<>(Arrays.asList(args));
        newArgs.remove(0);

        if(!sub.onCommand(p, newArgs)) {
            help(p);
        }
        return true;
    }

    void help(Player sender) {
        subcommands.get("help").onCommand(sender, null);
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length != 1) return null;
        return new ArrayList<>(subcommands.keySet());
    }
}
