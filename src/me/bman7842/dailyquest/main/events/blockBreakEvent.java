package me.bman7842.dailyquest.main.events;

import me.bman7842.dailyquest.main.utils.CurrentQuest;
import me.bman7842.dailyquest.main.utils.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by brand_000 on 7/14/2015.
 */
public class blockBreakEvent implements Listener {

    Data data = Data.getInstance();

    private static boolean enabled = false;
    private static Material type;
    private static Integer maxamt;
    public static Material getMaterial() { return type; }
    public static Integer getAmount() { return maxamt; }
    public static void setEnabled(boolean value, Material item, Integer maxamount) {
        enabled = false;
        type = item;
        maxamt = maxamount;
    }

    private static HashMap<UUID, Integer> playersProgress = new HashMap<UUID, Integer>();

    public void blockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();

        if (enabled) {
            if (data.getPlayersInQuest().contains(p)) {
                if (!event.getBlock().getType().equals(type)) { return; }

                if (playersProgress.containsKey(p.getUniqueId())) {
                    if (playersProgress.get(p.getUniqueId()) == maxamt) {
                        questNPCInteract.addPlayersWithQuestComplete(p.getUniqueId());
                    }
                } else {
                    playersProgress.put(p.getUniqueId(), 1);
                }
            }
        }
    }

}
