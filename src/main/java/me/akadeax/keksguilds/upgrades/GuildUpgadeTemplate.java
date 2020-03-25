package me.akadeax.keksguilds.upgrades;

import com.google.gson.annotations.Expose;

public class GuildUpgadeTemplate {
    @Expose
    public int uid = 0;
    @Expose
    public int maxBuyAmount = 1;

    @Expose
    public String title = "";
    @Expose
    public String description = "";

    @Expose
    public double cost = 1.0;
    @Expose
    public double costIncrementFactor = 0.1;
}
