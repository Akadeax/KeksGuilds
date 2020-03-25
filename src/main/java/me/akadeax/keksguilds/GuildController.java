package me.akadeax.keksguilds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * I hate making comments that have to explain a class, but here we go.
 * all the actual controller functions in here (mostly at the bottom of this!) return a string: That string is a
 * sort-of custom error log. The subcommand classes take that string and respond with their corresponding error messages
 * for those special cases, e.g. returning "notLeader" makes the subcommand class send whatever is specified in the config
 * for that. for example "Can't do that, you're not the guild leader!" or something of the sort.
 */
public class GuildController {
    private static List<Guild> loadedGuilds;

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
     * @return "exists", "alreadyInGuild", "success"
     */
    public static String createGuild(String name, UUID leader) {
        if(getGuild(name) != null) return "exists";
        if(isInGuild(leader)) return "alreadyInGuild";
        Guild newGuild = new Guild(name, leader);
        loadedGuilds.add(newGuild);
        SaveLoadHandler.saveGuilds();
        return "success";
    }

    /**
     * removes player from guild, if not guilds leader
     * @param leaver the player to remove
     * @return "notInGuild", "isLeader", "success"
     */
    public static String leaveGuild(UUID leaver) {
        Guild leaverGuild = getGuild(leaver);
        if(leaverGuild == null) return "notInGuild";

        GuildMember leaverMember = leaverGuild.getMember(leaver);
        if(leaverMember.rank == 1 || leaverGuild.members.size() == 1) return "isLeader";

        leaverGuild.removeMember(leaver);
        SaveLoadHandler.saveGuilds();
        return "success";
    }

    /**
     * invites 'receiverName' into the senders guild.
     * @return "playerNotFound", "notInGuild", "notLeader", "success"
     */
    public static String sendGuildInvite(UUID sender, String receiverName) {
        Player mention = Bukkit.getPlayer(receiverName);
        if(mention == null) return "playerNotFound";
        else if(mention.getUniqueId().equals(sender)) return "inviteSelf";
        else if(isInGuild(mention.getUniqueId())) return "targetInGuild";

        return InviteHandler.sendInvite(sender, mention.getUniqueId());
    }

    /**
     * make 'join' join guild with name 'toJoin'
     * @return "alreadyInGuild", "notInvited", "notExists", "maxMemberCount", "success"
     */
    public static String acceptGuildInvite(String toJoin, UUID user) {
        if(isInGuild(user)) return "alreadyInGuild";

        Guild guildToJoin = getGuild(toJoin);
        if(guildToJoin == null) return "notExists";

        if(!InviteHandler.hasInvite(user, toJoin)) return "notInvited";

        if(guildToJoin.members.size() >= guildToJoin.getMaxMembers()) return "maxMemberCount";

        guildToJoin.members.add(new GuildMember(user, 0));
        SaveLoadHandler.saveGuilds();
        return "success";
    }

    /**
     * disbands guild that 'leader' is in
     * @return "notInGuild", "notLeader", "success"
     */
    public static String disbandGuild(UUID leader) {
        if(!isInGuild(leader)) return "notInGuild";

        Guild leaderGuild = getGuild(leader);
        if(leaderGuild == null) return "notInGuild";
        GuildMember leaderMember = leaderGuild.getMember(leader);
        if(leaderMember.rank != 1) return "notLeader";

        loadedGuilds.remove(leaderGuild);
        SaveLoadHandler.saveGuilds();
        InviteHandler.removeInvites(leaderGuild.name);
        return "success";
    }
}
