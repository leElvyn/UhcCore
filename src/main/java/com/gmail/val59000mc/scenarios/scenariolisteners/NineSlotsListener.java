package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.events.UhcStartedEvent;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class NineSlotsListener extends ScenarioListener{

    private ItemStack fillItem;

    @Override
    public void onEnable() {
        fillItem = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = fillItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "BLOCKED");
        fillItem.setItemMeta(meta);
    }

    @EventHandler
    public void onGameStarted(UhcStartedEvent e){
        e.getPlayersManager().getOnlinePlayingPlayers().forEach(uhcPlayer -> {
            try{
                fillInventory(uhcPlayer.getPlayer());
            }catch (UhcPlayerNotOnlineException ex){
                ex.printStackTrace();
            }
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        ItemStack item = e.getCurrentItem();

        // Only handle clicked items.
        if (item == null){
            return;
        }

        if (item.equals(fillItem)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        List<ItemStack> drops = e.getDrops();

        // Remove all fill items.
        while (drops.remove(fillItem)){}
    }

    private void fillInventory(Player player){
        for (int i = 9; i <= 35; i++) {
            player.getInventory().setItem(i, fillItem);
        }
    }

}