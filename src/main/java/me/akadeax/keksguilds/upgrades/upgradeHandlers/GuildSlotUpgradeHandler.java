package me.akadeax.keksguilds.upgrades.upgradeHandlers;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.upgrades.GuildUpgrade;

public class GuildSlotUpgradeHandler extends UpgradeHandler {
    @Override
    public void onServerUpdate(Guild g, GuildUpgrade upgrade) {
        g.additionalGuildSlots = upgrade.amount;
    }
}
