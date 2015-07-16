package me.bman7842.dailyquest.main;

import me.bman7842.dailyquest.main.commands.spawnQuestNPC;
import me.bman7842.dailyquest.main.events.blockBreakEvent;
import me.bman7842.dailyquest.main.events.leaveEvent;
import me.bman7842.dailyquest.main.events.questNPCInteract;
import me.bman7842.dailyquest.main.utils.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by brand_000 on 7/13/2015.
 */
public class Main extends JavaPlugin {

    Data data;

    //TODO: LOADING AD SAVING OF NPCS

    @Override
    public void onEnable() {
        data = Data.getInstance();

        loadData();
        loadQuests();
        loadNPC();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new blockBreakEvent(), this);
        pm.registerEvents(new leaveEvent(), this);
        pm.registerEvents(new questNPCInteract(), this);

        getCommand("spawnquestnpc").setExecutor(new spawnQuestNPC());

        CurrentQuest.setupCurrentQuest(this);
    }

    @Override
    public void onDisable() {
        saveNPC();
    }

    public void loadData() {
        ConfigManager.load(this, "config.yml");

        FileConfiguration config = ConfigManager.get("config.yml");

        try {
            data.setQuestWaitTime(config.getInt("quest_update_time_hours"));
            Messages.setErrorMessageFormat(config.getString("error_message_format"));
            Messages.setAlertMessageFormat(config.getString("alert_message_format"));
        } catch (Exception e) {
            Bukkit.getLogger().info("Error in config, quest_update_time_hours must be an integer!");
        }
    }

    public void loadQuests() {
        ConfigManager.load(this, "quests.yml");

        FileConfiguration config = ConfigManager.get("quests.yml");

        try {
            for (String name : config.getKeys(false)) {
                data.addQuestDialog(name, config.getStringList(name + ".dialog"));
                data.addQuestType(name, config.getString(name + ".type"));
            }
        } catch (Exception e) {
            Bukkit.getLogger().info("Error with quests.yml, this is essiential for the plugin to function, disabling plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void loadNPC() {
        FileConfiguration config = ConfigManager.get("config.yml");

        if (config.getConfigurationSection("npcs") != null) {
            for (String name : config.getConfigurationSection("npcs").getKeys(false)) {
                String[] arg = (config.getString("npcs." + name + ".location")).split(":");
                Location location = new Location(Bukkit.getWorld(arg[5]), Double.parseDouble(arg[0]), Double.parseDouble(arg[1]), Double.parseDouble(arg[2]));
                location.setPitch(Float.parseFloat(arg[3]));
                location.setYaw(Float.parseFloat(arg[4]));

                Entity entity = location.getWorld().spawnEntity(location, EntityType.VILLAGER);
                entity.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
                entity.setCustomNameVisible(true);
                FreezeEntity.freeze(entity);
            }
        }
    }

    public void saveNPC() {
        FileConfiguration config = ConfigManager.get("config.yml");

        for (Entity entity : data.getNPCs()) {
            String sep = ":";
            Location l = entity.getLocation();
            String location = (l.getX() + sep + l.getY() + sep + l.getZ() + sep + l.getPitch() + sep + l.getYaw() + sep + l.getWorld().getName());
            config.set("npcs." + entity.getCustomName() + ".location", location);

            if (!(entity.isDead())) {
                entity.remove();
            }
        }
    }
}
