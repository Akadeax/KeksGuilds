package me.akadeax.keksguilds.commands.subcommands;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.GuildMember;
import me.akadeax.keksguilds.KeksGuilds;
import me.akadeax.keksguilds.commands.CommandError;
import me.akadeax.keksguilds.commands.CommandResultState;
import me.akadeax.keksguilds.upgrades.GuildUpgadeTemplate;
import me.akadeax.keksguilds.upgrades.GuildUpgrade;
import me.akadeax.keksguilds.upgrades.GuildUpgradesController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildInfoSubcommand implements Subcommand {

    private String infoFormat = KeksGuilds.config.getString("messages.info.format");
    private String memberDisplay = KeksGuilds.config.getString("messages.info.memberDisplay");
    private String upgradeDisplay = KeksGuilds.config.getString("messages.info.upgradeDisplay");

    @Override
    public boolean onCommand(Player sender, ArrayList<String> args) {
        if(args.size() != 0) return false;

        Guild senderGuild = GuildController.getGuild(sender.getUniqueId());
        if(senderGuild == null) {
            sender.sendMessage(CommandError.getErrorMessage(CommandResultState.NotInGuild));
            return true;
        }

        String finalMessage = infoFormat
                .replace("{GUILD}", senderGuild.name)
                .replace("{MEMBER_COUNT}", String.valueOf(senderGuild.members.size()))
                .replace("{MAX_MEMBERS}", String.valueOf(senderGuild.getMaxMembers()));

        StringBuilder membersString = new StringBuilder();
        for(GuildMember gm : senderGuild.members) {
            String currMemberString = memberDisplay
                    .replace("{NAME}", Bukkit.getOfflinePlayer(gm.uuid).getName())
                    .replace("{KILL_COUNT}", String.valueOf(gm.killsAmount));
            membersString.append(currMemberString).append("\n");
        }

        StringBuilder upgradesString = new StringBuilder();
        for(GuildUpgrade gu : senderGuild.upgrades) {
            GuildUpgadeTemplate template = GuildUpgradesController.getTemplate(gu.id);
            if(template == null) return false;

            String currUpgradeString = upgradeDisplay
                    .replace("{UPGRADE_TITLE}", template.title)
                    .replace("{UPGRADE_AMOUNT}", String.valueOf(gu.amount));
            upgradesString.append(currUpgradeString).append("\n");
        }

        finalMessage = finalMessage.replace("{MEMBERS}", membersString);
        finalMessage = finalMessage.replace("{UPGRADES}", upgradesString);

        sender.sendMessage(finalMessage);

        return true;
    }
}
