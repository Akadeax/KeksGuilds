package me.akadeax.keksguilds.util;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeUtil {
    public static void runDelayed(Plugin p, int delay, Runnable toRun) {
        new BukkitRunnable() {

            @Override
            public void run() {
                toRun.run();
            }
        }.runTaskLater(p, delay);
    }

    public static void repeat(Plugin p, int delay, int period, Runnable toRun) {
        new BukkitRunnable() {

            @Override
            public void run() {
                toRun.run();
            }
        }.runTaskTimer(p, delay, period);
    }
}
