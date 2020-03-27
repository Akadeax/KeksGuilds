package me.akadeax.keksguilds;

import me.akadeax.keksguilds.commands.CommandResultState;

import java.util.*;

public class InviteHandler {

    private static HashMap<String, ArrayList<UUID>> pendingInvites = new HashMap<>();

    public static CommandResultState sendInvite(UUID sender, UUID toInvite) {
        Guild senderGuild = GuildController.getGuild(sender);
        if(senderGuild == null) return CommandResultState.NotInGuild;

        GuildMember senderMember = senderGuild.getMember(sender);
        if(senderMember.rank != 1) return CommandResultState.NotLeader;

        if(!pendingInvites.containsKey(senderGuild.name)) {
            pendingInvites.put(senderGuild.name, new ArrayList<>(Collections.singleton(toInvite)));
        } else {
            pendingInvites.get(senderGuild.name).add(toInvite);
        }
        return CommandResultState.Success;
    }

    public static boolean hasInvite(UUID user, String guild) {
        List<UUID> invites = pendingInvites.get(guild);
        if(invites == null) return false;
        return invites.contains(user);
    }

    public static void removeInvites(String guild) {
        pendingInvites.remove(guild);
    }
}
