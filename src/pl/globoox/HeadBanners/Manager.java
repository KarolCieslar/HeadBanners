package pl.globoox.HeadBanners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;

public class Manager implements Listener {

    private Main main;

    public Manager(Main main) {
        this.main = main;
    }

    public void setBannerHead(Player player, String what, String arg) {
        if (what.equalsIgnoreCase("removeBanner")) {
            String bannerOnHelmet = main.getConfig().getString("lang.bannerOnHelmet.name");
            if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getItemMeta().getDisplayName() != null && player.getInventory().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase(main.parseColors(bannerOnHelmet))) {
                player.getInventory().setHelmet(new ItemStack(Material.AIR));
                String prefix = main.getConfig().getString("lang.prefix");
                String bannerRemoved = main.getConfig().getString("lang.bannerRemoved");
                player.sendMessage(main.parseColors(prefix + bannerRemoved));
                boolean soundEnable = main.getConfig().getBoolean("soundEnable");
                if (soundEnable == true) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
                }
            } else {
                String prefix = main.getConfig().getString("lang.prefix");
                String dontHaveBanner = main.getConfig().getString("lang.dontHaveBanner");
                player.sendMessage(main.parseColors(prefix + dontHaveBanner));
                boolean soundEnable = main.getConfig().getBoolean("soundEnable");
                if (soundEnable == true) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 1);
                }
            }


        } else if (what.equalsIgnoreCase("changeBanner")) {
            boolean setHat = false;
            if (player.getInventory().getHelmet() != null) {
                String bannerOnHelmet = main.getConfig().getString("lang.bannerOnHelmet.name");
                if (player.getInventory().getHelmet().getItemMeta().getDisplayName() != null && player.getInventory().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase(main.parseColors(bannerOnHelmet))) {
                    setHat = true;
                } else {
                    String prefix = main.getConfig().getString("lang.prefix");
                    String removeHatFirst = main.getConfig().getString("lang.removeHatFirst");
                    player.sendMessage(main.parseColors(prefix + removeHatFirst));
                    boolean soundEnable = main.getConfig().getBoolean("soundEnable");
                    if (soundEnable == true) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 1);
                    }
                }
            } else {
                setHat = true;
            }

            if (setHat == true) {


                ItemStack item = new ItemStack(Material.BANNER);
                BannerMeta metaBanner = (BannerMeta) item.getItemMeta();
                String bannerName = main.getConfig().getString("lang.bannerOnHelmet.name");
                metaBanner.setDisplayName(main.parseColors(bannerName));

                // HIDE PATTERNS?
                if (main.getConfig().getBoolean("hidePatterns")) {
                    metaBanner.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                }

                // SETLORE
                ArrayList<String> bannerLore = new ArrayList<String>();
                if (main.getConfig().getBoolean("lang.bannerOnHelmet.useThisLore") == true) {
                    for (String loreSignle : main.getConfig().getStringList("lang.bannerOnHelmet.lore")) {
                        bannerLore.add(main.parseColors(loreSignle));
                    }
                } else if (main.getConfig().getBoolean("lang.bannerOnHelmet.useThisLore") == false) {
                    for (String loreSignle : main.getConfig().getStringList("banners." + arg + ".lore")) {
                        bannerLore.add(main.parseColors(loreSignle));
                    }
                }
                metaBanner.setLore(bannerLore);



                String command = main.getConfig().getString("banners." + arg + ".giveCommand");
                BannerMeta newMeta = main.setBannerPattern(metaBanner, command);


                item.setItemMeta(newMeta);
                player.getInventory().setHelmet(item);
                String prefix = main.getConfig().getString("lang.prefix");
                String bannerSet = main.getConfig().getString("lang.bannerSet");
                player.sendMessage(main.parseColors(prefix + bannerSet));
                player.updateInventory();
                player.closeInventory();
                boolean soundEnable = main.getConfig().getBoolean("soundEnable");
                if (soundEnable == true) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
                }
            }
        }
    }


    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String bannerOnHelmet = main.getConfig().getString("lang.bannerOnHelmet.name");
        if (player.getInventory() == e.getClickedInventory()) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getType().equals(Material.BANNER) && e.getCurrentItem().getItemMeta().getDisplayName().contains(main.parseColors(bannerOnHelmet))) {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    private void onInventoryClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        String bannerOnHelmet = main.getConfig().getString("lang.bannerOnHelmet.name");
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getItemMeta().getDisplayName() != null && item.getType().equals(Material.BANNER) && item.getItemMeta().getDisplayName().contains(main.parseColors(bannerOnHelmet))) {
                if (player.getInventory().getHelmet() != item) {
                    player.getInventory().remove(item);
                }
            }
        }
        player.updateInventory();
    }


}


