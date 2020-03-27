package me.akadeax.keksguilds.commands;

import me.akadeax.keksguilds.KeksGuilds;

import java.util.HashMap;

public class CommandError {
    private static HashMap<CommandResultState, String> errorMessages = new HashMap<>();

    public static void loadErrorMessages() {
        for(CommandResultState crs : CommandResultState.values()) {
            errorMessages.put(crs, load("error." + crs.name()));
        }
    }

    private static String load(String path) {
        return KeksGuilds.config.getString(path);
    }

    public static String getErrorMessage(CommandResultState crs) {
        return errorMessages.get(crs);
    }
}
