package me.akadeax.keksguilds.upgrades;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.GuildController;
import me.akadeax.keksguilds.SaveLoadHandler;
import me.akadeax.keksguilds.upgrades.upgradeHandlers.GuildSlotUpgradeHandler;
import me.akadeax.keksguilds.upgrades.upgradeHandlers.UpgradeHandler;

import java.util.HashMap;

public class GuildUpgradesController {
    private static HashMap<Integer, UpgradeHandler> handlers = new HashMap<Integer, UpgradeHandler>() {
        {
            put(0, new GuildSlotUpgradeHandler());
        }
    };

    public static void sendServerStart() {
        for(Guild g : GuildController.getLoadedGuilds()) {
            if(g.upgrades.size() == 0) continue;
            for(GuildUpgrade upgrade : g.upgrades) {
                getHandler(upgrade.id).onServerStart(g, upgrade);
            }
        }
    }

    public static void sendServerUpdate() {
        for(Guild g : GuildController.getLoadedGuilds()) {
            if(g.upgrades.size() == 0) continue;
            for(GuildUpgrade upgrade : g.upgrades) {
                getHandler(upgrade.id).onServerUpdate(g, upgrade);
            }
        }
    }

    private static UpgradeHandler getHandler(int id) {
        return handlers.get(id);
    }
}
