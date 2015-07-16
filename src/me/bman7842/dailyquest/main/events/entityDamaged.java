package me.bman7842.dailyquest.main.events;

import me.bman7842.dailyquest.main.utils.Data;
import me.bman7842.dailyquest.main.utils.Messages;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

/**
 * Created by brand_000 on 7/15/2015.
 */
public class entityDamaged implements Listener {

    Data data = Data.getInstance();

    @EventHandler
    public void entityDamaged(EntityDamageEvent e)
    {
        if (data.getNPCs().contains(e.getEntity()))
        {
            e.setCancelled(true);
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e;
            if(nEvent.getDamager() instanceof Arrow){
                return;
            }
            Random ran = new Random();
            sendRandomFunMessage(ran.nextInt(4) + 1, (Player) nEvent.getDamager());
        }
    }

    public void sendRandomFunMessage(Integer num, Player p) //Because why not
    {
        switch (num){

            case 1: Messages.alertMessage(p.getUniqueId(), "You can't hit me, I'm INVINCIBLE!!!!");
                break;

            case 2: Messages.alertMessage(p.getUniqueId(), "I feel nothing, your ninja moves only make me laugh.");
                break;

            case 3: Messages.alertMessage(p.getUniqueId(), "Just because you're mad doesn't mean you need to hit someone else!");
                break;

            case 4: Messages.alertMessage(p.getUniqueId(), "I am NPC Version: 1.3.45, I have been designed by my developer to survive attacks.");
                break;
        }
    }

}
