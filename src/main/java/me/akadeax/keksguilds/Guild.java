package me.akadeax.keksguilds;

import com.google.gson.annotations.Expose;
import me.akadeax.keksguilds.upgrades.GuildUpgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Guild {
    @Expose
    public String name;

    public int baseMaxMembers = KeksGuilds.config.getInt("guildDefault.maxMembers");
    public int getMaxMembers() {
        return baseMaxMembers + (guildLvl - 1);
    }

    @Expose
    public List<GuildMember> members = new ArrayList<>();

    @Expose
    public int guildLvl = 1;
    @Expose
    public int guildExp = 0;

    @Expose
    public List<GuildUpgrade> upgrades = new ArrayList<>();

    public Guild(String name) {
        this.name = name;
    }
    public Guild(String name, UUID leader) {
        this.name = name;
        members.add(new GuildMember(leader, 1));
    }

    public boolean hasUpgrade(int id) {
        for(GuildUpgrade gu : upgrades) {
            if(gu.id == id) return true;
        }
        return false;
    }
}
