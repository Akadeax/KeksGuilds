package me.akadeax.keksguilds;

import me.akadeax.keksguilds.commands.CommandResultState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GuildController {
    private static List<Guild> loadedGuilds;

    /**
     * sets loaded guilds
     * @return whether loadedGuilds has been filled
     */
    public static boolean setLoadedGuilds(Guild[] guilds) {
        loadedGuilds = new ArrayList<>(Arrays.asList(guilds));
        return loadedGuilds.size() > 0;
    }
    public static List<Guild> getLoadedGuilds() {
        return loadedGuilds;
    }

    /**
     * gets loaded guild by name
     */
    public static Guild getGuild(String name) {
        for(Guild g : loadedGuilds) {
            if(g.name.equals(name)) return g;
        }
        return null;
    }

    /**
     * gets guild of a member
     */
    public static Guild getGuild(UUID member) {
        for(Guild g : loadedGuilds) {
            for(GuildMember gm : g.members) {
                if(gm.uuid.equals(member)) return g;
            }
        }
        return null;
    }

    public static GuildMember getGuildMember(UUID member) {
        for(Guild g : loadedGuilds) {
            for(GuildMember gm : g.members) {
                if(gm.uuid.equals(member)) return gm;
            }
        }
        return null;
    }

    public static boolean isInGuild(UUID uuid) {
        for(Guild g : loadedGuilds) {
            for(GuildMember gm : g.members) {
                if(gm.uuid.equals(uuid)) return true;
            }
        }
        return false;
    }

    /**
     * creates a guild and adds it to loaded guilds
     * @param name the name of the guild to create
     * @param leader uuid of the leader
     * @return NameAlreadyExists, AlreadyInGuild, Success
     */
    public static CommandResultState createGuild(String name, UUID leader) {
        if(getGuild(name) != null) return CommandResultState.GuildAlreadyExists;
        if(isInGuild(leader)) return CommandResultState.AlreadyInGuild;
        Guild newGuild = new Guild(name, leader);
        loadedGuilds.add(newGuild);
        SaveLoadHandler.saveGuilds();
        return CommandResultState.Success;
    }

    /**
     * removes player from guild, if not guilds leader
     * @param leaver the player to remove
     * @return "notInGuild", "isLeader", "success"
     */
    public static CommandResultState leaveGuild(UUID leaver) {
        Guild leaverGuild = getGuild(leaver);
        if(leaverGuild == null) return CommandResultState.NotInGuild;

        GuildMember leaverMember = leaverGuild.getMember(leaver);
        if(leaverMember.rank == 1 || leaverGuild.members.size() == 1) return CommandResultState.IsLeader;

        leaverGuild.removeMember(leaver);
        SaveLoadHandler.saveGuilds();
        return CommandResultState.Success;
    }

    /**
     * invites 'receiverName' into the senders guild.
     * @return "playerNotFound", "notInGuild", "notLeader", "success"
     */
    public static CommandResultState sendGuildInvite(UUID sender, String receiverName) {
        Player mention = Bukkit.getPlayer(receiverName);
        if(mention == null) return CommandResultState.PlayerNotFound;
        else if(mention.getUniqueId().equals(sender)) return CommandResultState.InvitedSelf;
        else if(isInGuild(mention.getUniqueId())) return CommandResultState.TargetInGuild;

        return InviteHandler.sendInvite(sender, mention.getUniqueId());
    }

    /**
     * make 'join' join guild with name 'toJoin'
     * @return "alreadyInGuild", "notInvited", "notExists", "maxMemberCount", "success"
     */
    public static CommandResultState acceptGuildInvite(String toJoin, UUID user) {
        if(isInGuild(user)) return CommandResultState.AlreadyInGuild;

        Guild guildToJoin = getGuild(toJoin);
        if(guildToJoin == null) return CommandResultState.GuildNotExists;

        if(!InviteHandler.hasInvite(user, toJoin)) return CommandResultState.NotInvited;

        if(guildToJoin.members.size() >= guildToJoin.getMaxMembers()) return CommandResultState.MaxMembersReached;

        guildToJoin.members.add(new GuildMember(user, 0));
        SaveLoadHandler.saveGuilds();
        return CommandResultState.Success;
    }

    /**
     * disbands guild that 'leader' is in
     * @return "notInGuild", "notLeader", "success"
     */
    public static CommandResultState disbandGuild(UUID leader) {
        Guild leaderGuild = getGuild(leader);
        if(leaderGuild == null) return CommandResultState.NotInGuild;
        GuildMember leaderMember = leaderGuild.getMember(leader);
        if(leaderMember.rank != 1) return CommandResultState.NotLeader;

        loadedGuilds.remove(leaderGuild);
        SaveLoadHandler.saveGuilds();
        InviteHandler.removeInvites(leaderGuild.name);
        return CommandResultState.IsLeader;
    }
}
