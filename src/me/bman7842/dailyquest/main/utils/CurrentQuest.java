package me.bman7842.dailyquest.main.utils;

import me.bman7842.dailyquest.main.events.blockBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by brand_000 on 7/13/2015.
 */
public class CurrentQuest {

    private static Data data = Data.getInstance();
    private static Random rand = new Random();

    private static String currentSelectQuest;
    private static String currentSelectQuestType;
    public static String getCurrentSelectQuestType() { return currentSelectQuestType; }
    public static String getCurrentSelectQuest() { return currentSelectQuest; }

    private static Integer timeStartedDay = 0;
    private static Integer nextDayTimeDay = 0;
    public static void setupCurrentQuest(Plugin plugin) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar calendar = format.getCalendar();
        timeStartedDay = calendar.DAY_OF_WEEK;
        if (calendar.DAY_OF_WEEK == 7) {
            nextDayTimeDay = 1;
        } else {
            nextDayTimeDay = timeStartedDay +1;
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (timeStartedDay == nextDayTimeDay) {
                    Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "ALERT>" + ChatColor.GRAY + "As a new day comes a new quest comes, check the quest dealer for the new daily quest!");
                    if (timeStartedDay == 7) {
                        timeStartedDay = 1;
                        nextDayTimeDay = timeStartedDay +1;
                    } else {
                        if (nextDayTimeDay + 1 == 7) {
                            timeStartedDay++;
                            nextDayTimeDay = 1;
                        } else {
                            timeStartedDay++;
                            nextDayTimeDay++;
                        }
                    }

                    selectNewQuest();
                }
            }
        }, 0, 72000);
    }

    public static void selectNewQuest() {

        HashMap<String, String> namesEdited = data.getQuestNames();
        namesEdited.remove(currentSelectQuest);
        Integer rannum = rand.nextInt(namesEdited.size());

        int num = 0;
        for (String name : namesEdited.keySet()) {
            if (num == rannum) {
                currentSelectQuest = name;
                enableQuestCheck(data.getQuestType(name));
                break;
            }
            num++;
        }
        //TODO: Make method check for type etc.
        currentSelectQuestType = "breakmaterial";
        data.resetPlayersWhoCompletedQuest();
    }

    private static void enableQuestCheck(String data) {
        String[] args = data.split(":");
        Bukkit.getLogger().info("boop");
        Bukkit.getLogger().info(args[0]);
        if (args[0].equalsIgnoreCase("breakmaterial")) {
            Bukkit.getLogger().info("in it");
            blockBreakEvent.setEventEnabled(true, Material.valueOf(args[1].toUpperCase()), Integer.parseInt(args[2]));
        }
    }
}
