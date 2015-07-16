package me.bman7842.dailyquest.main.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.UUID;

/**
 * Created by brand_000 on 7/13/2015.
 */
public class Messages {

    private static Data data = Data.getInstance();

    private static String errorMessageFormat = "&c&lERROR> &7";
    public static void setErrorMessageFormat(String format) { errorMessageFormat = format; }

    private static String alertMessageFormat = "&a&lALERT> &7";
    public static void setAlertMessageFormat(String format) { alertMessageFormat = format; }

    public static void errorMessage(UUID player, String msg) { Bukkit.getPlayer(player).sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessageFormat + msg)); }
    public static void alertMessage(UUID player, String msg) { Bukkit.getPlayer(player).sendMessage(ChatColor.translateAlternateColorCodes('&', alertMessageFormat + msg)); }
    public static void noPermsMessage(UUID player) { errorMessage(player, "You don't have permission to run this command!"); }

    public static String questToString() {
        String type = data.getQuestType(CurrentQuest.getCurrentSelectQuest());

        if (type.toLowerCase().contains("breakmaterial")) {
            String[] args = type.split(":");

            return ("mine " + args[2] + " blocks of " + args[1] + " before the day is over!");
        }

        return null;
        //TODO: add more quest types
    }
}
