package me.akadeax.keksguilds;

import me.akadeax.keksguilds.upgrades.GuildUpgadeTemplate;
import me.akadeax.keksguilds.util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SaveLoadHandler {

    private static File dbFolder;
    private static File guildListFile;
    private static File guildUpgradesFile;

    public static void initFiles() {
        dbFolder = new File(KeksGuilds.config.getString("DBFolder"));
        guildListFile = new File(KeksGuilds.config.getString("guildListFile"));
        guildUpgradesFile = new File(KeksGuilds.config.getString("guildUpgradesTemplatesFile"));

        dbFolder.mkdir();

        try {
            if(guildListFile.createNewFile()) {
                JsonUtil.writeJson(guildListFile, new Guild[] {
                        new Guild("new")
                });
            }
            if(guildUpgradesFile.createNewFile()) {
                JsonUtil.writeJson(guildUpgradesFile, new GuildUpgadeTemplate[] {
                        new GuildUpgadeTemplate()
                });
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGuilds() {
        Guild[] guilds = JsonUtil.readJson(guildListFile, Guild[].class);
        GuildController.setLoadedGuilds(guilds);
    }
    public static void saveGuilds() {
        JsonUtil.writeJson(guildListFile, GuildController.getLoadedGuilds().toArray());
    }

    public static List<GuildUpgadeTemplate> guildUpgadeTemplates;

    public static void loadUpgradeTemplates() {
        GuildUpgadeTemplate[] templates = JsonUtil.readJson(guildUpgradesFile, GuildUpgadeTemplate[].class);
        guildUpgadeTemplates = Arrays.asList(templates);
    }
}
