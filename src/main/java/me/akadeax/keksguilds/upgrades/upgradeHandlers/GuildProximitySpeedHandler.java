package me.akadeax.keksguilds.upgrades.upgradeHandlers;

import me.akadeax.keksguilds.Guild;
import me.akadeax.keksguilds.GuildMember;
import me.akadeax.keksguilds.upgrades.GuildUpgrade;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class GuildProximitySpeedHandler extends UpgradeHandler {
    @Override
    public void onServerUpdate(Guild g, GuildUpgrade upgrade) {
        ArrayList<Player> members = new ArrayList<>();
        for(GuildMember gm : g.members) {
            Player gmPlayer = Bukkit.getPlayer(gm.uuid);
            if(gmPlayer != null) members.add(gmPlayer);
        }

        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 10, 1, true, false);
        for(Player p1 : members) {
            for(Player p2 : members) {
                if(p1.getUniqueId().equals(p2.getUniqueId())) continue;
                if(p1.getLocation().distance(p2.getLocation()) < 10) {
                    p1.addPotionEffect(speed);
                }
            }
        }
    }
}
