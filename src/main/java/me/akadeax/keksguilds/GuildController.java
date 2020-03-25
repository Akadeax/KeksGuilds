package me.akadeax.keksguilds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
     * make 'join' join guild with name 'toJoin'
     * @return "alreadyInGuild", "notExists", "maxMemberCount", "success"
     */
    public static String joinGuild(String toJoin, UUID user) {
        if(isInGuild(user)) return "alreadyInGuild";

        Guild guildToJoin = getGuild(toJoin);
        if(guildToJoin == null) return "notExists";

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
        return "success";
    }
}
