package me.bman7842.dailyquest.main.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * Created by brand_000 on 7/13/2015.
 */
public class Data {

    private static Data instance = new Data();
    public static Data getInstance() { return instance; }

    private Integer questwaittime = 24;
    public void setQuestWaitTime(Integer i) { questwaittime = i; }
    public Integer getQuestWaitTime() { return questwaittime; }

    private HashMap<String, List<String>> questDialog = new HashMap<String, List<String>>();
    public void addQuestDialog(String name, List<String> dialog) { questDialog.put(name, dialog); }
    public List<String> getQuestDialog(String name) { return questDialog.get(name); }

    private HashMap<String, String> questType = new HashMap<String, String>();
    public void addQuestType(String name, String type) { questType.put(name, type); }
    public HashMap<String, String> getQuestNames() { return questType; }
    public String getQuestType(String name) { return questType.get(name); }

    private HashSet<UUID> playersWhoCompletedQuest = new HashSet<UUID>();
    public void addPlayerWhoCompletedQuest(UUID player) { playersWhoCompletedQuest.add(player); }
    public HashSet<UUID> getPlayersWhoCompletedQuest() { return playersWhoCompletedQuest; }
    public void resetPlayersWhoCompletedQuest() { playersWhoCompletedQuest = new HashSet<UUID>(); }

    private HashSet<Entity> questNPC = new HashSet<Entity>();
    public void addNPC(Entity entity) { questNPC.add(entity); }
    public HashSet<Entity> getNPCs() { return questNPC; }

    private HashMap<Player, Integer> playersInteractingQuest = new HashMap<Player, Integer>();
    public void addPlayersInteractingQuest(Player p, Integer num) { playersInteractingQuest.put(p, num); }
    public HashMap<Player, Integer> returnPlayersInteractingQuest() { return playersInteractingQuest; }
    public void removePlayerInteractingQuest(Player p) { playersInteractingQuest.remove(p); }

    private HashSet<Player> playersInQuest = new HashSet<Player>();
    public void addPlayerInQuest(Player p) { playersInQuest.add(p); }
    public void removePlayerInQuest(Player p) { playersInQuest.remove(p); }
    public HashSet<Player> getPlayersInQuest() { return playersInQuest; }
}
