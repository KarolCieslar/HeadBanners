package pl.globoox.HeadBanners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.*;

public class OpenShop implements CommandExecutor, Listener {

    private Main main;

    public OpenShop(Main main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("banner")) {
            if (sender instanceof Player) {
                boolean noneArg = false;
                if (args.length == 0) {
                    noneArg = true;
                } else if (!args[0].equalsIgnoreCase("gui") && !args[0].equalsIgnoreCase("reload") && !args[0].equalsIgnoreCase("set") && !args[0].equalsIgnoreCase("remove")) {
                    noneArg = true;
                }
                if (noneArg == true) {
                    sender.sendMessage("");
                    sender.sendMessage(main.parseColors(" &8»&8&M-------------&f&8✧ &3HeadBanner by GlobooX &8✧&8&m------------&r&8-&8«"));
                    sender.sendMessage("");
                    sender.sendMessage(main.parseColors("  &6/banner &7- &eopen this help menu"));
                    sender.sendMessage(main.parseColors("  &6/banner gui &7- &eopen gui banners"));
                    sender.sendMessage(main.parseColors("  &6/banner set <banner> [player] &7- &eset banner"));
                    sender.sendMessage(main.parseColors("  &6/banner remove <banner> [player] &7- &eremove banner"));
                    sender.sendMessage(main.parseColors("  &6/banner reload &7- &ereload config file"));
                    sender.sendMessage("");
                } else {
                    // OPEN GUI CMD
                    if (args[0].equalsIgnoreCase("gui")) {
                        if (args.length <= 1) {
                            if (sender.hasPermission("headbanner.use")) {
                                Player player = (Player) sender;
                                boolean soundEnable = main.getConfig().getBoolean("soundEnable");
                                if (soundEnable == true) {
                                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
                                }
                                openMainGUI(player, 1);
                            } else {
                                noPermission((Player) sender);
                            }
                        } else {
                            String prefix = main.getConfig().getString("lang.prefix");
                            String toManyArgs = main.getConfig().getString("lang.toManyArgs");
                            sender.sendMessage(main.parseColors(prefix + toManyArgs));
                        }

                        // RELOAD CMD
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        if (args.length <= 1) {
                            if (sender.hasPermission("headbanner.reload")) {
                                String prefix = main.getConfig().getString("lang.prefix");
                                String configReloaded = main.getConfig().getString("lang.configReloaded");
                                sender.sendMessage(main.parseColors(prefix + configReloaded));
                                main.reloadConfig();
                                main.reloadBanners();
                            } else {
                                noPermission((Player) sender);
                            }
                        } else {
                            String prefix = main.getConfig().getString("lang.prefix");
                            String toManyArgs = main.getConfig().getString("lang.toManyArgs");
                            sender.sendMessage(main.parseColors(prefix + toManyArgs));
                        }

                        // SET HAT
                    } else if (args[0].equalsIgnoreCase("set")) {
                        if (args.length <= 3) {
                            if (sender.hasPermission("headbanner.set")) {
                                if (args.length == 2 || args.length == 3) {
                                    Player player;
                                    if (args.length == 3) {
                                        player = Bukkit.getPlayerExact(args[2]);
                                        if (player == null) {
                                            String prefix = main.getConfig().getString("lang.prefix");
                                            String notOnline = main.getConfig().getString("lang.notOnline");
                                            sender.sendMessage(main.parseColors(prefix + notOnline));
                                            return true;
                                        }
                                    } else {
                                        player = (Player) sender;
                                    }

                                    // FIND BANNER ID
                                    if (main.banery.contains(args[1])) {
                                        boolean perBannerPermission = main.getConfig().getBoolean("perBannerPermission");
                                        if (perBannerPermission == false) {
                                            main.getManager().setBannerHead(player, "changeBanner", args[1]);
                                        } else if (perBannerPermission == true) {
                                            if (player.hasPermission("headbanner." + args[1])) {
                                                main.getManager().setBannerHead(player, "changeBanner", args[1]);
                                            } else {
                                                String prefix = main.getConfig().getString("lang.prefix");
                                                String dontHavePermForBannerSet = main.getConfig().getString("lang.dontHavePermForBannerSet");
                                                sender.sendMessage(main.parseColors(prefix + dontHavePermForBannerSet));
                                            }
                                        }
                                    } else {
                                        String prefix = main.getConfig().getString("lang.prefix");
                                        String bannerNotFound = main.getConfig().getString("lang.bannerNotFound");
                                        sender.sendMessage(main.parseColors(prefix + bannerNotFound));
                                    }

                                } else {
                                    String prefix = main.getConfig().getString("lang.prefix");
                                    String missingArgs = main.getConfig().getString("lang.missingArgs");
                                    sender.sendMessage(main.parseColors(prefix + missingArgs));
                                }
                            } else {
                                noPermission((Player) sender);
                            }
                        } else {
                            String prefix = main.getConfig().getString("lang.prefix");
                            String toManyArgs = main.getConfig().getString("lang.toManyArgs");
                            sender.sendMessage(main.parseColors(prefix + toManyArgs));
                        }

                        // REMOVE ABNNER
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if (sender.hasPermission("headbanner.remove")) {
                            if (args.length <= 2) {
                                Player player;
                                if (args.length == 2) {
                                    player = Bukkit.getPlayerExact(args[1]);
                                    if (player == null) {
                                        String prefix = main.getConfig().getString("lang.prefix");
                                        String notOnline = main.getConfig().getString("lang.notOnline");
                                        sender.sendMessage(main.parseColors(prefix + notOnline));
                                    }
                                } else {
                                    player = (Player) sender;
                                }

                                main.getManager().setBannerHead(player, "removeBanner", "none");

                            } else {
                                String prefix = main.getConfig().getString("lang.prefix");
                                String missingArgs = main.getConfig().getString("lang.missingArgs");
                                sender.sendMessage(main.parseColors(prefix + missingArgs));
                            }
                        } else {
                            noPermission((Player) sender);
                        }
                    }

                }
            } else {
                String prefix = main.getConfig().getString("lang.prefix");
                String notPlayer = main.getConfig().getString("lang.notPlayer");
                sender.sendMessage(main.parseColors(prefix + notPlayer));
            }
        }


        return true;
    }


    public void noPermission(Player player) {
        String prefix = main.getConfig().getString("lang.prefix");
        String dontHavePerm = main.getConfig().getString("lang.dontHavePerm");
        player.sendMessage(main.parseColors(prefix + dontHavePerm));

    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        // OTWIERANIE PODKADEGORII
        String guiName = main.getConfig().getString("lang.guiName");
        if (e.getInventory().getTitle().contains(main.parseColors(guiName))) {
            e.setCancelled(true);
            if (player.getInventory() != e.getClickedInventory()) {
                if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR)) || (e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE))) {
                    return;
                }


                String nextPage = main.getConfig().getString("lang.nextPage");
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main.parseColors(nextPage))) {
                    boolean soundEnable = main.getConfig().getBoolean("soundEnable");
                    if (soundEnable == true) {
                        player.playSound(player.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1, 1);
                    }
                    openMainGUI(player, e.getCurrentItem().getAmount());
                    return;
                }

                String prevPage = main.getConfig().getString("lang.prevPage");
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main.parseColors(prevPage))) {
                    boolean soundEnable = main.getConfig().getBoolean("soundEnable");
                    if (soundEnable == true) {
                        player.playSound(player.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1, 1);
                    }
                    openMainGUI(player, e.getCurrentItem().getAmount());
                    return;
                }

                String removeHat = main.getConfig().getString("lang.removeHat.name");
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main.parseColors(removeHat))) {
                    main.getManager().setBannerHead(player, "removeBanner", "none");
                    return;
                }


                // BANNER CLICK
                if ((e.getCurrentItem() != null) || (e.getCurrentItem().getType().equals(Material.BANNER))) {
                    String currentBanner = String.valueOf(e.getCurrentItem().getData());
                    currentBanner = currentBanner.replaceAll("BANNER\\(", "").replaceAll("\\)", "");
                    String banner = main.banery.get(Integer.parseInt(currentBanner));
                    boolean perBannerPermission = main.getConfig().getBoolean("perBannerPermission");
                    if (perBannerPermission == false) {
                        main.getManager().setBannerHead(player, "changeBanner", banner);
                    } else if (perBannerPermission == true) {
                        if (player.hasPermission("headbanner." + banner)) {
                            main.getManager().setBannerHead(player, "changeBanner", banner);
                        } else {
                            String prefix = main.getConfig().getString("lang.prefix");
                            String dontHavePermForBanner = main.getConfig().getString("lang.dontHavePermForBanner");
                            player.sendMessage(main.parseColors(prefix + dontHavePermForBanner));
                        }
                    }
                    return;

                }

            }


        }
    }


    public void openMainGUI(Player player, Integer page) {

        // Otwieranie gui
        Float size = main.getConfig().getConfigurationSection("banners").getKeys(false).size() / 21f;
        size = (float) Math.ceil(size);
        String guiName = main.getConfig().getString("lang.guiName");
        String pageInfo;
        pageInfo = main.getConfig().getString("lang.page");
        pageInfo = pageInfo.replaceAll("%page%", String.valueOf(page)).replaceAll("%pages%", String.valueOf(size.intValue()));
        Inventory mainGui = Bukkit.getServer().createInventory(player, 6 * 9, main.parseColors(guiName + " " + pageInfo));

        // FUCNKA SETOWANIA WSZYSTKICH OKIENEK NA SZKLO
        main.fillGlassPane(6, mainGui);


        if (!String.valueOf(page).equalsIgnoreCase("1")) {
            String prevPage = main.getConfig().getString("lang.prevPage");
            main.formatSlot(mainGui, 46, Material.PAPER, page - 1, main.parseColors(prevPage), "none");
        }

        if (!String.valueOf(page).equalsIgnoreCase(String.valueOf(size.intValue()))) {
            String nextPage = main.getConfig().getString("lang.nextPage");
            main.formatSlot(mainGui, 48, Material.PAPER, page + 1, main.parseColors(nextPage), "none");
        }

        String removeHat = main.getConfig().getString("lang.removeHat.name");
        ArrayList<String> removeHatLore = new ArrayList<String>();
        for (String lore : main.getConfig().getStringList("lang.removeHat.lore")) {
            removeHatLore.add(main.parseColors(lore));
        }
        ItemStack removeItem = new ItemStack(Material.BARRIER, 1);
        ItemMeta metaItem = removeItem.getItemMeta();
        metaItem.setDisplayName(main.parseColors(removeHat));
        metaItem.setLore(removeHatLore);
        removeItem.setItemMeta(metaItem);
        mainGui.setItem(51, removeItem);


        Integer slot = 0;
        Integer loopIndex = (21 * page) - 21;
        for (int i = 0; i < 21; i++) {
            if (main.banery.size() > loopIndex) {
                slot++;
                if (slot == 8) {
                    slot = slot + 2;
                }
                if (slot == 17) {
                    slot = slot + 2;
                }


                String bannerName = main.getConfig().getString("banners." + main.banery.get(loopIndex) + ".name");
                ArrayList<String> bannerLore = new ArrayList<String>();
                for (String loreSignle : main.getConfig().getStringList("banners." + main.banery.get(loopIndex) + ".lore")) {
                    bannerLore.add(main.parseColors(loreSignle));
                }

                ItemStack item = new ItemStack(Material.BANNER, 1, Byte.valueOf(String.valueOf(loopIndex)));
                BannerMeta bannerMeta = (BannerMeta) item.getItemMeta();
                bannerMeta.setDisplayName(main.parseColors(bannerName));
                bannerMeta.setLore(bannerLore);
                String command = main.getConfig().getString("banners." + main.banery.get(loopIndex) + ".giveCommand");
                BannerMeta newMeta = main.setBannerPattern(bannerMeta, command);


                // HIDE PATTERNS?
                if (main.getConfig().getBoolean("hidePatterns")) {
                    bannerMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                }

                loopIndex++;
                item.setItemMeta(newMeta);

                mainGui.setItem(slot + 9, item);
            }

        }
        player.openInventory(mainGui);
    }


}
