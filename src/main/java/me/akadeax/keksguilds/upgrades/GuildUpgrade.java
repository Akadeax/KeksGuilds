package me.akadeax.keksguilds.upgrades;

import com.google.gson.annotations.Expose;

public class GuildUpgrade {
    @Expose
    public int id;
    @Expose
    public int amount = 0;

    public GuildUpgrade(int id) {
        this.id = id;
    }

    public GuildUpgrade(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }
}
