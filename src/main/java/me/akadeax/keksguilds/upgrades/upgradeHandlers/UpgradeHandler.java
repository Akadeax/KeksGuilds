package me.akadeax.keksguilds.upgrades.upgradeHandlers;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.upgrades.GuildUpgrade;

public abstract class UpgradeHandler {
    public void onServerStart(Guild g, GuildUpgrade upgrade) { }
    public void onServerUpdate(Guild g, GuildUpgrade upgrade) { }
}
