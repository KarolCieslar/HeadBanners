package pl.globoox.HeadBanners;

import org.bukkit.*;
import org.bukkit.block.banner.PatternType;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by GlobooX on 28.02.2018.
 */
public class Main extends JavaPlugin implements Listener {


    private OpenShop openShop;
    private Manager manager;
    public ArrayList<String> banery = new ArrayList<>();

    @Override
    public void onEnable() {

        Bukkit.getServer().getPluginManager().registerEvents(new OpenShop(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Manager(this), this);
        this.openShop = new OpenShop(this);
        this.manager = new Manager(this);
        this.getCommand("banner").setExecutor(new OpenShop(this));
        getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        for (String banner : getConfig().getConfigurationSection("banners").getKeys(false)) {
            banery.add(banner);
        }


    }


    public void reloadBanners() {
        banery.clear();
        for (String banner : getConfig().getConfigurationSection("banners").getKeys(false)) {
            banery.add(banner);
        }
    }

    // PARSE COLOR FUNKCJA
    public String parseColors(String msg) {
        return msg.replace("&", "\u00a7");
    }


    // DYE COLOR GET
    public DyeColor getDyeColor(String color) {
        if (color.equalsIgnoreCase("0")) {
            return DyeColor.BLACK;
        } else if (color.equalsIgnoreCase("1")) {
            return DyeColor.RED;
        } else if (color.equalsIgnoreCase("2")) {
            return DyeColor.GREEN;
        } else if (color.equalsIgnoreCase("3")) {
            return DyeColor.BROWN;
        } else if (color.equalsIgnoreCase("4")) {
            return DyeColor.BLUE;
        } else if (color.equalsIgnoreCase("5")) {
            return DyeColor.PURPLE;
        } else if (color.equalsIgnoreCase("6")) {
            return DyeColor.CYAN;
        } else if (color.equalsIgnoreCase("7")) {
            return DyeColor.SILVER;
        } else if (color.equalsIgnoreCase("8")) {
            return DyeColor.GRAY;
        } else if (color.equalsIgnoreCase("9")) {
            return DyeColor.PINK;
        } else if (color.equalsIgnoreCase("10")) {
            return DyeColor.LIME;
        } else if (color.equalsIgnoreCase("11")) {
            return DyeColor.YELLOW;
        } else if (color.equalsIgnoreCase("12")) {
            return DyeColor.LIGHT_BLUE;
        } else if (color.equalsIgnoreCase("13")) {
            return DyeColor.MAGENTA;
        } else if (color.equalsIgnoreCase("14")) {
            return DyeColor.ORANGE;
        } else if (color.equalsIgnoreCase("15")) {
            return DyeColor.WHITE;
        } else {
            return DyeColor.WHITE;
        }
    }

    // GET PATTERN
    public PatternType getPattern(String color) {
        if (color.equalsIgnoreCase("bo")) {
            return PatternType.BORDER;
        } else if (color.equalsIgnoreCase("bri")) {
            return PatternType.BRICKS;
        } else if (color.equalsIgnoreCase("mc")) {
            return PatternType.CIRCLE_MIDDLE;
        } else if (color.equalsIgnoreCase("cre")) {
            return PatternType.CREEPER;
        } else if (color.equalsIgnoreCase("cr")) {
            return PatternType.CROSS;
        } else if (color.equalsIgnoreCase("cbo")) {
            return PatternType.CURLY_BORDER;
        } else if (color.equalsIgnoreCase("ld")) {
            return PatternType.DIAGONAL_LEFT;
        } else if (color.equalsIgnoreCase("lud")) {
            return PatternType.DIAGONAL_LEFT_MIRROR;
        } else if (color.equalsIgnoreCase("rd")) {
            return PatternType.DIAGONAL_RIGHT;
        } else if (color.equalsIgnoreCase("rud")) {
            return PatternType.DIAGONAL_RIGHT_MIRROR;
        } else if (color.equalsIgnoreCase("flo")) {
            return PatternType.FLOWER;
        } else if (color.equalsIgnoreCase("gra")) {
            return PatternType.GRADIENT;
        } else if (color.equalsIgnoreCase("gru")) {
            return PatternType.GRADIENT_UP;
        } else if (color.equalsIgnoreCase("hh")) {
            return PatternType.HALF_HORIZONTAL;
        } else if (color.equalsIgnoreCase("hhb")) {
            return PatternType.HALF_HORIZONTAL_MIRROR;
        } else if (color.equalsIgnoreCase("vg")) {
            return PatternType.HALF_VERTICAL;
        } else if (color.equalsIgnoreCase("vhr")) {
            return PatternType.HALF_VERTICAL_MIRROR;
        } else if (color.equalsIgnoreCase("moj")) {
            return PatternType.MOJANG;
        } else if (color.equalsIgnoreCase("mr")) {
            return PatternType.RHOMBUS_MIDDLE;
        } else if (color.equalsIgnoreCase("sku")) {
            return PatternType.SKULL;
        } else if (color.equalsIgnoreCase("bl")) {
            return PatternType.SQUARE_BOTTOM_LEFT;
        } else if (color.equalsIgnoreCase("br")) {
            return PatternType.SQUARE_BOTTOM_RIGHT;
        } else if (color.equalsIgnoreCase("tl")) {
            return PatternType.SQUARE_TOP_LEFT;
        } else if (color.equalsIgnoreCase("tr")) {
            return PatternType.SQUARE_TOP_RIGHT;
        } else if (color.equalsIgnoreCase("sc")) {
            return PatternType.STRAIGHT_CROSS;
        } else if (color.equalsIgnoreCase("bs")) {
            return PatternType.STRIPE_BOTTOM;
        } else if (color.equalsIgnoreCase("cs")) {
            return PatternType.STRIPE_CENTER;
        } else if (color.equalsIgnoreCase("dls")) {
            return PatternType.STRIPE_DOWNLEFT;
        } else if (color.equalsIgnoreCase("drs")) {
            return PatternType.STRIPE_DOWNRIGHT;
        } else if (color.equalsIgnoreCase("ls")) {
            return PatternType.STRIPE_LEFT;
        } else if (color.equalsIgnoreCase("ms")) {
            return PatternType.STRIPE_MIDDLE;
        } else if (color.equalsIgnoreCase("rs")) {
            return PatternType.STRIPE_RIGHT;
        } else if (color.equalsIgnoreCase("ss")) {
            return PatternType.STRIPE_SMALL;
        } else if (color.equalsIgnoreCase("ts")) {
            return PatternType.STRIPE_TOP;
        } else if (color.equalsIgnoreCase("bt")) {
            return PatternType.TRIANGLE_BOTTOM;
        } else if (color.equalsIgnoreCase("tt")) {
            return PatternType.TRIANGLE_TOP;
        } else if (color.equalsIgnoreCase("bts")) {
            return PatternType.TRIANGLES_BOTTOM;
        } else {
            return PatternType.TRIANGLES_TOP;
        }
    }


    // FILL GLASS
    public void fillGlassPane(Integer rows, Inventory gui) {
        for (int i = 0; i < rows * 9; i++) {
            ItemStack GlassBlank = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
            ItemMeta glassMeta = GlassBlank.getItemMeta();
            glassMeta.setDisplayName(" ");
            GlassBlank.setItemMeta(glassMeta);
            gui.setItem(i, GlassBlank);
        }
    }


    public void formatSlot(Inventory GUI, Integer slot, Material material, Integer amount, String name, String lore) {

        ItemStack item = new ItemStack(material, amount);
        ItemMeta metaItem = item.getItemMeta();
        metaItem.setDisplayName(parseColors(name));
        if (!lore.equalsIgnoreCase("none")) {
            String[] lores = lore.split("%nl%");
            ArrayList<String> caleLore = new ArrayList<String>();
            for (String loreSingle : lores) {
                caleLore.add(parseColors(loreSingle));
            }
            metaItem.setLore(caleLore);
        }
        item.setItemMeta(metaItem);
        GUI.setItem(slot, item);
    }


    public BannerMeta setBannerPattern(BannerMeta meta, String command) {
        command = command
                .replaceAll("\\{BlockEntityTag:", "")
                .replaceAll("Pattern:", "\"Pattern\":")
                .replaceAll("Patterns", "\"Patterns\"")
                .replaceAll("Base", "\"Base\"")
                .replaceAll("Color", "\"Color\"")
                .replaceAll(":bo,", ":\"bo\",")
                .replaceAll(":bri,", ":\"bri\",")
                .replaceAll(":mc,", ":\"mc\",")
                .replaceAll(":cre,", ":\"cre\",")
                .replaceAll(":cr,", ":\"cr\",")
                .replaceAll(":cbo,", ":\"cbo\",")
                .replaceAll(":ld,", ":\"ld\",")
                .replaceAll(":lud,", ":\"lud\",")
                .replaceAll(":rd,", ":\"rd\",")
                .replaceAll(":rud,", ":\"rud\",")
                .replaceAll(":flo,", ":\"flo\",")
                .replaceAll(":gra,", ":\"gra\",")
                .replaceAll(":gru,", ":\"gru\",")
                .replaceAll(":hh,", ":\"hh\",")
                .replaceAll(":hhb,", ":\"hhb\",")
                .replaceAll(":vh,", ":\"vh\",")
                .replaceAll(":vhr,", ":\"vhr\",")
                .replaceAll(":moj,", ":\"moj\",")
                .replaceAll(":mr,", ":\"mr\",")
                .replaceAll(":sku,", ":\"sku\",")
                .replaceAll(":bl,", ":\"bl\",")
                .replaceAll(":br,", ":\"br\",")
                .replaceAll(":tl,", ":\"tl\",")
                .replaceAll(":tr,", ":\"tr\",")
                .replaceAll(":sc,", ":\"sc\",")
                .replaceAll(":bs,", ":\"bs\",")
                .replaceAll(":cs,", ":\"cs\",")
                .replaceAll(":dls,", ":\"dls\",")
                .replaceAll(":drs,", ":\"drs\",")
                .replaceAll(":ls,", ":\"ls\",")
                .replaceAll(":ms,", ":\"ms\",")
                .replaceAll(":rs,", ":\"rs\",")
                .replaceAll(":ss,", ":\"ss\",")
                .replaceAll(":ts,", ":\"ts\",")
                .replaceAll(":bt,", ":\"bt\",")
                .replaceAll(":tt,", ":\"tt\",")
                .replaceAll(":bts,", ":\"bts\",")
                .replaceAll(":tts,", ":\"tts\",")
                .replaceAll("10", "\"10\"")
                .replaceAll("11", "\"11\"")
                .replaceAll("12", "\"12\"")
                .replaceAll("13", "\"13\"")
                .replaceAll("14", "\"14\"")
                .replaceAll("15", "\"15\"");
        command = command.replaceAll(":1", ":\"1\"")
                .replaceAll(":2", ":\"2\"")
                .replaceAll(":3", ":\"3\"")
                .replaceAll(":4", ":\"4\"")
                .replaceAll(":5", ":\"5\"")
                .replaceAll(":6", ":\"6\"")
                .replaceAll(":7", ":\"7\"")
                .replaceAll(":8", ":\"8\"")
                .replaceAll(":9", ":\"9\"")
                .replaceAll(":0", ":\"0\"");

        List<org.bukkit.block.banner.Pattern> patternsList = new ArrayList<org.bukkit.block.banner.Pattern>();

        DyeColor base = DyeColor.RED;
        try {
            final JSONObject obj = new JSONObject(command);
            base = getDyeColor(obj.getString("Base"));
            final JSONArray patterns = obj.getJSONArray("Patterns");
            final int n = patterns.length();
            for (int x = 0; x < n; ++x) {
                final JSONObject person = patterns.getJSONObject(x);
                PatternType pattern = PatternType.valueOf(String.valueOf(getPattern(String.valueOf(person.getString("Pattern")))));
                DyeColor color = getDyeColor(String.valueOf(person.getString("Color")));
                patternsList.add(new org.bukkit.block.banner.Pattern(color, pattern));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        meta.setBaseColor(base);
        meta.setPatterns(patternsList);
        return meta;
    }

    public Manager getManager() {
        return this.manager;
    }


}

