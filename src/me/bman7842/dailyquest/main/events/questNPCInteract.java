package me.bman7842.dailyquest.main.events;

import com.avaje.ebeaninternal.server.cluster.mcast.Message;
import me.bman7842.dailyquest.main.utils.CurrentQuest;
import me.bman7842.dailyquest.main.utils.Data;
import me.bman7842.dailyquest.main.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * Created by brand_000 on 7/13/2015.
 */
public class questNPCInteract implements Listener {

    Data data = Data.getInstance();

    private static HashSet<UUID> playersWithQuestComplete = new HashSet<UUID>();
    public static void addPlayersWithQuestComplete(UUID player) { playersWithQuestComplete.add(player); }

    @EventHandler
    public void entityInteract(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity npc = event.getRightClicked();

        if (event.getRightClicked().getType().equals(EntityType.VILLAGER)
                && event.getRightClicked().isCustomNameVisible()
                && data.getNPCs().contains(event.getRightClicked())) {
            event.setCancelled(true);

            if (data.getPlayersWhoCompletedQuest().contains(p.getUniqueId())) {
                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "=========================================");
                p.sendMessage(ChatColor.YELLOW + npc.getCustomName() + ":");
                p.sendMessage(ChatColor.GRAY + "You have already completed today's quest, come back tomorrow for another one!");
                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "=========================================");
                return;
            }

            if (playersWithQuestComplete.contains(p.getUniqueId())) {
                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "=========================================");
                p.sendMessage(ChatColor.YELLOW + npc.getCustomName() + ":");
                p.sendMessage(ChatColor.GRAY + "Oh great you have the items I need?");
                p.sendMessage(ChatColor.GRAY + "Thank you very much!");
                p.sendMessage("");
                p.sendMessage(ChatColor.RED + "Come back tomorrow for another quest!");
                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "=========================================");

                completeQuest(p);
                return;
            }

            if (data.getPlayersInQuest().contains(p)) {
                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "=========================================");
                p.sendMessage(ChatColor.YELLOW + npc.getCustomName() + ":");
                p.sendMessage(ChatColor.GRAY + "You have already started this quest, ");
                p.sendMessage(Messages.questToString());
                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "=========================================");
            } else {
                if (data.returnPlayersInteractingQuest().keySet().contains(p)) {
                    String quest = CurrentQuest.getCurrentSelectQuest();
                    Integer numOn = data.returnPlayersInteractingQuest().get(p);

                    List<String> dialog = data.getQuestDialog(quest);
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "=========================================");
                    p.sendMessage(ChatColor.YELLOW + npc.getCustomName() + ":");

                    if ((numOn+1) == dialog.size()) {
                        p.sendMessage("Quest Started: " + Messages.questToString());
                        data.addPlayerInQuest(p);
                        data.removePlayerInteractingQuest(p);
                        p.sendMessage("");
                        p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "=========================================");
                    } else {
                        p.sendMessage(ChatColor.GRAY + dialog.get(numOn + 1));
                        data.addPlayersInteractingQuest(p, numOn +1);
                        p.sendMessage("");
                        p.sendMessage("*right click me to continue*");
                        p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "=========================================");
                    }
                } else {
                    data.addPlayersInteractingQuest(p, 0);

                    String quest = CurrentQuest.getCurrentSelectQuest();
                    List<String> dialog = data.getQuestDialog(quest);
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "=========================================");
                    p.sendMessage(ChatColor.YELLOW + npc.getCustomName() + ":");
                    p.sendMessage(ChatColor.GRAY + dialog.get(0));
                    p.sendMessage("");
                    p.sendMessage("*right click me to continue*");
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "=========================================");
                }
            }
        }
    }

    public void completeQuest(Player p) {
        if (CurrentQuest.getCurrentSelectQuestType().toLowerCase().equals("breakmaterial")) {

            //TODO: Implement this for take item method
//            Integer num = 0;
//            for (ItemStack item : p.getInventory().getContents()) {
//                if (item.getType().equals(blockBreakEvent.getMaterial())) {
//                    ItemStack newitem = new ItemStack(item.getType(), item.getAmount() - blockBreakEvent.getAmount());
//                    p.getInventory().setItem(num, newitem);
//                    break;
//                }
//                num++;
//            }
            data.addPlayerWhoCompletedQuest(p.getUniqueId());
        }
    }

}
