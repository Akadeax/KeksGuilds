package me.akadeax.keksguilds.commands.subcommands;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface Subcommand {
    boolean onCommand(Player sender, ArrayList<String> args);
}
