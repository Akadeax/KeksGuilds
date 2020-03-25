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
     * @param name the name of the guild
     * @param leader uuid of the leader
     * @return whether the creation was successful
     */
    public static String createGuild(String name, UUID leader) {
        if(getGuild(name) != null) return "exists";
        if(isInGuild(leader)) return "alreadyMember";
        Guild newGuild = new Guild(name, leader);
        loadedGuilds.add(newGuild);
        SaveLoadHandler.saveGuilds();
        return "success";
    }


}
