package me.akadeax.keksguilds;

import com.google.gson.annotations.Expose;

import java.util.UUID;

public class GuildMember {
    @Expose
    public UUID uuid;
    @Expose
    public int killsAmount = 0;
    @Expose
    public int rank = 0;

    public GuildMember(UUID uuid) {
        this.uuid = uuid;
    }
    public GuildMember(UUID uuid, int rank) {
        this.uuid = uuid;
        this.rank = rank;
    }
}
