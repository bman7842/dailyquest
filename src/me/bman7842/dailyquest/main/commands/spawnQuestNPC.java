package me.bman7842.dailyquest.main.commands;

import me.bman7842.dailyquest.main.utils.Data;
import me.bman7842.dailyquest.main.utils.FreezeEntity;
import me.bman7842.dailyquest.main.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

/**
 * Created by brand_000 on 7/13/2015.
 */
public class spawnQuestNPC implements CommandExecutor {

    Data data = Data.getInstance();

    @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only for in-game players!");
        }

        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("spawnquestnpc")) {
            if (!p.hasPermission("quest.create")) {
                Messages.noPermsMessage(p.getUniqueId());
                return false;
            }
            if (args.length == 0) {
                Messages.errorMessage(p.getUniqueId(), "Invalid arguments, try /spawnquestnpc (name)");
                return false;
            }

            String name = args[0];

            Entity villager = p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
            villager.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
            villager.setCustomNameVisible(true);
            FreezeEntity.freeze(villager);

            data.addNPC(villager);

            Messages.alertMessage(p.getUniqueId(), "You have successfully spawned a quest NPC at your location!");
        }

        return false;
    }

}
