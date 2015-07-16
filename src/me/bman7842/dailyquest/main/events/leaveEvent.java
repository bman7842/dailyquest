package me.bman7842.dailyquest.main.events;

import me.bman7842.dailyquest.main.utils.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by brand_000 on 7/13/2015.
 */
public class leaveEvent implements Listener {

    private Data data = Data.getInstance();

    //TODO: remove player from all places needed
    @EventHandler
    public void playerLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        if (data.returnPlayersInteractingQuest().keySet().contains(p)) {
            data.removePlayerInteractingQuest(p);
        }
    }

}
