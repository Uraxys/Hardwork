package no.minecraft.Minecraftno.conf;

import no.minecraft.Minecraftno.Minecraftno;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ConfigurationServer {

    private Minecraftno plugin;
    public ArrayList<Location> locations;
    private Map<String, ConfigurationWorld> worlds;
    private HashMap<String, Object> configDefaults;
    private YamlConfiguration config;
    private File configFile;

    public ConfigurationServer(Minecraftno plugin) {
        this.plugin = plugin;
        this.worlds = new HashMap<String, ConfigurationWorld>();
        this.configDefaults = new HashMap<String, Object>();
        this.locations = new ArrayList<Location>();
    }

    // Database
    public String dbuser;
    public String dbpass;
    public String dbname;
    public String dbhost;
    public String Maindbname;
    public int dbport;

    // Chars for caps
    public double percentage;

    // Server
    public boolean showloginonguest = true;
    public boolean maintaince;
    public boolean stop;
    public boolean backup;
    public String backupFolder;
    private final Set<Material> defaultGiftItems = new HashSet<Material>() {{
        add(Material.STONE);
        add(Material.OAK_PLANKS);
        add(Material.SAND);
        add(Material.BRICKS);
        add(Material.MOSSY_COBBLESTONE);
        add(Material.OBSIDIAN);
        add(Material.PUMPKIN);
        add(Material.JACK_O_LANTERN);
        add(Material.CLAY);
        add(Material.NETHER_WART);
        add(Material.COAL);
        add(Material.DIAMOND);
        add(Material.IRON_INGOT);
        add(Material.GOLD_INGOT);
        add(Material.SNOWBALL);
        add(Material.LEATHER);
        add(Material.BRICK);
        add(Material.CLAY_BALL);
        add(Material.SLIME_BALL);
        add(Material.EGG);
        add(Material.GLOWSTONE_DUST);
        add(Material.BLACK_DYE);
        add(Material.SUGAR);
        add(Material.COOKIE);
        add(Material.SHEARS);
        add(Material.MELON_SLICE);
        add(Material.BLAZE_ROD);
        add(Material.GHAST_TEAR);
        add(Material.GOLD_NUGGET);
        add(Material.SPIDER_EYE);
        add(Material.FERMENTED_SPIDER_EYE);
        add(Material.BLAZE_POWDER);
        add(Material.MAGMA_CREAM);
        add(Material.GLISTERING_MELON_SLICE);
    }};

    // irc
    public boolean irc;
    public String irchostname;
    public String ircname;
    public int ircport;

    // Antal spillere
    public int maxplayers;
    public boolean showWaringsJoin;
    public boolean warpInfoJoin;
    public boolean showGroupInvitesJoin;

    // Fill
    public List<Material> illegalItems = new ArrayList<>();
    private final Set<Material> defaultIllegalItems = new HashSet<Material>() {{
        add(Material.BEDROCK);
        add(Material.SPONGE);
        add(Material.TNT);
        add(Material.FIRE);
        add(Material.SPAWNER);
        add(Material.ICE);
        add(Material.NETHER_PORTAL);
        add(Material.CHAINMAIL_HELMET);
        add(Material.CHAINMAIL_CHESTPLATE);
        add(Material.CHAINMAIL_LEGGINGS);
        add(Material.CHAINMAIL_BOOTS);
    }};

    public List<Material> throughBlock = new ArrayList<>();
    private final Set<Material> defaultThroughBlock = new HashSet<Material>() {{
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_SAPLING") && !material.name().contains("POTTED") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_TULIP") && !material.name().contains("POTTED"))).collect(Collectors.toSet()) );
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_SIGN") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_PRESSURE_PLATE") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_BUTTON") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        add(Material.AIR);
        add(Material.WATER);
        add(Material.LAVA);
        add(Material.POWERED_RAIL);
        add(Material.DETECTOR_RAIL);
        add(Material.COBWEB);
        add(Material.DEAD_BUSH);
        add(Material.GRASS);
        add(Material.FERN);
        add(Material.DANDELION);
        add(Material.POPPY);
        add(Material.BLUE_ORCHID);
        add(Material.ALLIUM);
        add(Material.AZURE_BLUET);
        add(Material.OXEYE_DAISY);
        add(Material.BROWN_MUSHROOM);
        add(Material.RED_MUSHROOM);
        add(Material.TORCH);
        add(Material.WALL_TORCH);
        add(Material.REDSTONE_WIRE);
        add(Material.WHEAT);
        add(Material.LADDER);
        add(Material.RAIL);
        add(Material.LEVER);
        add(Material.REDSTONE_TORCH);
        add(Material.REDSTONE_WALL_TORCH);
        add(Material.SNOW);
        add(Material.SUGAR_CANE);
    }};

    public List<Material> noCraft = new ArrayList<>();
    private final Set<Material> defaultNoCraft = new HashSet<Material>() {{
       add(Material.TNT);
       add(Material.FLINT_AND_STEEL);
    }};

    public List<Material> noRemoveFromWaterLava = new ArrayList<>();
    private final Set<Material> defaultNoRemoveFromWaterLava = new HashSet<Material>() {{
        add(Material.POWERED_RAIL);
        add(Material.DETECTOR_RAIL);
        add(Material.TORCH);
        add(Material.WALL_TORCH);
        add(Material.REDSTONE_WIRE);
        add(Material.RAIL);
        add(Material.LEVER);
        add(Material.REDSTONE_TORCH);
        add(Material.REDSTONE_WALL_TORCH);
        add(Material.STONE_BUTTON);
        add(Material.REPEATER);
    }};

    public List<Material> noDamageTools = new ArrayList<>();
    private final Set<Material> defaultNoDamageTools = new HashSet<Material>() {{
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_SHOVEL") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_PICKAXE") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_AXE") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_SWORD") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_HOE") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        add(Material.BOW);
        add(Material.FLINT_AND_STEEL);
        add(Material.FISHING_ROD);
        add(Material.SHEARS);
    }};

    public List<Material> nonProtectBlocks = new ArrayList<>();
    private final Set<Material> defaultNonProtectBlocks = new HashSet<Material>() {{
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_SAPLING") && !material.name().contains("POTTED") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_BED") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        add(Material.GRASS_BLOCK);
        add(Material.DIRT);
        add(Material.BEDROCK);
        add(Material.WATER);
        add(Material.LAVA);
        add(Material.SAND);
        add(Material.RED_SAND);
        add(Material.GRAVEL);
        add(Material.BROWN_MUSHROOM);
        add(Material.RED_MUSHROOM);
        add(Material.FIRE);
        add(Material.WHEAT);
        add(Material.FARMLAND);
        add(Material.SUGAR_CANE);
        add(Material.PUMPKIN_STEM);
        add(Material.MELON_STEM);
        add(Material.VINE);
        add(Material.NETHER_WART);
        add(Material.CARROTS);
        add(Material.POTATOES);
    }};

    public List<Material> protectBlockUp = new ArrayList<>();
    private final Set<Material> defaultProtectBlockUp = new HashSet<Material>() {{
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_SIGN") && !material.name().contains("WALL_SIGN") && !material.name().contains("LEGACY"))).collect(Collectors.toSet()) );
        add(Material.RED_MUSHROOM);
        add(Material.BROWN_MUSHROOM);
        add(Material.REDSTONE_WIRE);
        add(Material.RAIL);
        add(Material.REPEATER);
        // All the flowers...
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_TULIP") && !material.name().contains("POTTED"))).collect(Collectors.toSet()) );
        add(Material.DANDELION);
        add(Material.POPPY);
        add(Material.BLUE_ORCHID);
        add(Material.ALLIUM);
        add(Material.AZURE_BLUET);
        add(Material.OXEYE_DAISY);

    }};

    public List<Material> protectUnprotected = new ArrayList<>();
    private final Set<Material> defaultProtectUnprotected = new HashSet<Material>() {{
        addAll( Arrays.stream(Material.values()).filter(material -> (material.name().endsWith("_WOOL") && !material.name().equals("LEGACY_WOOL"))).collect(Collectors.toSet()) );
        add(Material.SPONGE);
        add(Material.GLASS);
        add(Material.GOLD_BLOCK);
        add(Material.IRON_BLOCK);
        add(Material.BRICKS);
        add(Material.BOOKSHELF);
        add(Material.CHEST);
        add(Material.DIAMOND_BLOCK);
        add(Material.GLOWSTONE);
    }};

    // Login og reg
    public List<String> srvReg = new ArrayList<String>();
    public List<String> srvLogin = new ArrayList<String>();

    // BadWords
    public List<String> advertise = new ArrayList<String>();

    public void load() throws Exception {

        // Location
        locations.add(new Location(this.plugin.getServer().getWorlds().get(0), 5449, 63, 1860));
        locations.add(new Location(this.plugin.getServer().getWorlds().get(0), 5476, 63, 1860));
        locations.add(new Location(this.plugin.getServer().getWorlds().get(0), 5503, 63, 1860));
        locations.add(new Location(this.plugin.getServer().getWorlds().get(0), 5530, 63, 1860));

        this.config = new YamlConfiguration();
        this.configFile = new File(plugin.getDataFolder(), "Minecraftno.yml");

        // DB
        this.configDefaults.put("SQL.db-user", "username");
        this.configDefaults.put("SQL.db-pass", "password");
        this.configDefaults.put("SQL.db-name", "database");
        this.configDefaults.put("SQL.db-main-name", "database");
        this.configDefaults.put("SQL.db-host", "localhost");
        this.configDefaults.put("SQL.db-port", 3306);

        // Caps
        this.configDefaults.put("Caps.prosent", 40);

        // Server
        this.configDefaults.put("Server.vedlikehold", false);
        this.configDefaults.put("Server.stop", false);
        this.configDefaults.put("Server.backup", true);
        this.configDefaults.put("Server.backupFolder", "/home/minecraft/backups/");

        // IRC
        this.configDefaults.put("IRC.irc-on", true);
        this.configDefaults.put("IRC.server", "irc.hostname.com");
        this.configDefaults.put("IRC.port", 6697);
        this.configDefaults.put("IRC.nick", "H");

        // Player
        this.configDefaults.put("Player.max-players", 200);
        this.configDefaults.put("Player.ShowWaringsOnJoin", true);
        this.configDefaults.put("Player.WarpToInfoOnJoinGuest", true);
        this.configDefaults.put("Player.ShowGroupInvitesOnJoin", true);

        // Fill
        this.configDefaults.put("Fill.blokker-kompasset-ignorer", defaultThroughBlock.stream().map(Material::name).collect(Collectors.joining(",")));
        this.configDefaults.put("Fill.blokker-som-ikke-blir-beskyttet", defaultNonProtectBlocks.stream().map(Material::name).collect(Collectors.joining(",")));
        this.configDefaults.put("Fill.blokker-som-beskytter-blokken-under", defaultProtectBlockUp.stream().map(Material::name).collect(Collectors.joining(",")));
        this.configDefaults.put("Fill.blokker-som-blir-sett-som-verdifulle", defaultProtectUnprotected.stream().map(Material::name).collect(Collectors.joining(",")));
        this.configDefaults.put("Fill.blokker-stengt-ute-for-normale-brukere", defaultIllegalItems.stream().map(Material::name).collect(Collectors.joining(",")));
        this.configDefaults.put("Fill.blokker-du-ikke-kan-crafte", defaultNoCraft.stream().map(Material::name).collect(Collectors.joining(",")));
        this.configDefaults.put("Fill.blokker-som-er-imune-mot-vann", defaultNoRemoveFromWaterLava.stream().map(Material::name).collect(Collectors.joining(",")));

        this.configDefaults.put("Fill.verktoy-som-ikke-tar-skade", defaultNoDamageTools.stream().map(Material::name).collect(Collectors.joining(",")));

        // Login
        this.configDefaults.put("Reg.Melding", "1. G/a/ til www.minecraft.no /::/ 2. Trykk p/a/ byggetillatelse i menyen. /::/ 3. Trykk p/a/ tr/a/den som heter LES HER F/o/R DU SØKER /::/ 4. Opprett ny tr/a/d på samme plass og f/o/lg punktene som st/a/r i tr/a/den. /::/ 5. N/a/r s/o/knaden din er godkjent vil du bli lagt til på serveren.");
        this.configDefaults.put("Login.Melding", "&6========[ &9 www.minecraft.no - Hardwork buildserver &6 ]======= /::/ For informasjon bes/o/k www.minecraft.no");

        // WordFilter:
        String[] advertiseshort = {"closeddoors", "closeddoors.org", "mc.closeddoors.org"};
        this.configDefaults.put("BadWords.advertise", advertiseshort);

        // Gifts

        this.configDefaults.put("Server.gifts.item", defaultGiftItems.stream().map(Material::name).collect(Collectors.joining(",")));

        if (!this.configFile.exists()) {
            for (String key : this.configDefaults.keySet()) {
                this.config.set(key, this.configDefaults.get(key));
            }
            try {
                this.config.save(this.configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.config.load(this.configFile);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                for (String key : this.configDefaults.keySet()) {
                    if (!this.config.contains(key)) {
                        this.config.set(key, this.configDefaults.get(key));
                    }
                }
            }
        }

        this.dbuser = config.getString("SQL.db-user");
        this.dbpass = config.getString("SQL.db-pass");
        this.dbname = config.getString("SQL.db-name");
        this.Maindbname = config.getString("SQL.db-main-name");
        this.dbhost = config.getString("SQL.db-host");
        this.dbport = config.getInt("SQL.db-port");

        this.percentage = config.getDouble("Caps.prosent");

        this.maintaince = config.getBoolean("Server.vedlikehold");
        this.stop = config.getBoolean("Server.stop");
        this.backup = config.getBoolean("Server.backup");
        this.backupFolder = config.getString("Server.backupFolder");

        this.irc = config.getBoolean("IRC.irc-on");
        this.irchostname = config.getString("IRC.server");
        this.ircname = config.getString("IRC.nick");
        this.ircport = config.getInt("IRC.port");

        this.maxplayers = config.getInt("Player.max-players");
        this.showWaringsJoin = config.getBoolean("Player.ShowWaringsOnJoin");
        this.warpInfoJoin = config.getBoolean("Players.WarpToInfoOnJoinGuest");
        this.showGroupInvitesJoin = config.getBoolean("Player.ShowGroupInvitesOnJoin");

        String[] tempillegalItems = config.getString("Fill.blokker-stengt-ute-for-normale-brukere").split(",");
        for (String item : tempillegalItems) {
            Material mat = Material.getMaterial(item);
            if (mat == null) {
                plugin.getLogger().warning("Couldn't parse material "+item+" from config! (Fill.blokker-stengt-ute-for-normale-brukere)");
                continue;
            }
            illegalItems.add(mat);
        }

        String[] tempdefaultThroughBlock = config.getString("Fill.blokker-kompasset-ignorer").split(",");
        for (String item : tempdefaultThroughBlock) {
            Material mat = Material.getMaterial(item);
            if (mat == null) {
                plugin.getLogger().warning("Couldn't parse material "+item+" from config! (Fill.blokker-kompasset-ignorer)");
                continue;
            }
            throughBlock.add(mat);
        }

        String[] tempNoCraft = config.getString("Fill.blokker-du-ikke-kan-crafte").split(",");
        for (String item : tempNoCraft) {
            Material mat = Material.getMaterial(item);
            if (mat == null) {
                plugin.getLogger().warning("Couldn't parse material "+item+" from config! (Fill.blokker-du-ikke-kan-crafte)");
                continue;
            }
            noCraft.add(mat);
        }

        String[] tempnoRemoveFromWaterLava = config.getString("Fill.blokker-som-er-imune-mot-vann").split(",");
        for (String item : tempnoRemoveFromWaterLava) {
            Material mat = Material.getMaterial(item);
            if (mat == null) {
                plugin.getLogger().warning("Couldn't parse material "+item+" from config! (Fill.blokker-som-er-imune-mot-vann)");
                continue;
            }
            noRemoveFromWaterLava.add(mat);
        }

        String[] tempnoDamageTools = config.getString("Fill.verktoy-som-ikke-tar-skade").split(",");
        for (String item : tempnoDamageTools) {
            Material mat = Material.getMaterial(item);
            if (mat == null) {
                plugin.getLogger().warning("Couldn't parse material "+item+" from config! (Fill.verktoy-som-ikke-tar-skade)");
                continue;
            }
            noDamageTools.add(mat);
        }

        String[] tempnonProtectBlocks = config.getString("Fill.blokker-som-ikke-blir-beskyttet").split(",");
        for (String item : tempnonProtectBlocks) {
            Material mat = Material.getMaterial(item);
            if (mat == null) {
                plugin.getLogger().warning("Couldn't parse material "+item+" from config! (Fill.blokker-som-ikke-blir-beskyttet)");
                continue;
            }
            nonProtectBlocks.add(mat);
        }

        String[] tempprotectBlockUp = config.getString("Fill.blokker-som-beskytter-blokken-under").split(",");
        for (String item : tempprotectBlockUp) {
            Material mat = Material.getMaterial(item);
            if (mat == null) {
                plugin.getLogger().warning("Couldn't parse material "+item+" from config! (Fill.blokker-som-beskytter-blokken-under)");
                continue;
            }
            protectBlockUp.add(mat);
        }

        String[] tempprotectUnprotected = config.getString("Fill.blokker-som-blir-sett-som-verdifulle").split(",");
        for (String item : tempprotectUnprotected) {
            Material mat = Material.getMaterial(item);
            if (mat == null) {
                plugin.getLogger().warning("Couldn't parse material "+item+" from config! (Fill.blokker-som-blir-sett-som-verdifulle)");
                continue;
            }
            protectUnprotected.add(mat);
        }

        String[] tempsrvReg = config.getString("Reg.Melding").split("/::/");
        for (String item : tempsrvReg) {
            srvReg.add(colorTxt(item.trim().replace("/a/", "å").replace("/o/", "ø")));
        }

        String[] tempsrvLogin = config.getString("Login.Melding").split("/::/");
        for (String item : tempsrvLogin) {
            srvLogin.add(colorTxt(item.trim().replace("/a/", "å").replace("/o/", "ø")));
        }

        this.advertise = this.config.getStringList("BadWords.advertise");
        Collections.sort(advertise, new StringLengthComparator());

        for (World world : this.plugin.getServer().getWorlds()) {
            get(world);
        }
    }

    public void cleanup() throws Exception {
        this.configDefaults.clear();

        for (World world : this.plugin.getServer().getWorlds()) {
            get(world).cleanup();
        }

        this.worlds.clear();
        try {
            this.config.save(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConfigurationWorld get(World world) {
        String worldName = world.getName();
        ConfigurationWorld config = this.worlds.get(worldName);

        if (config == null) {
            config = new ConfigurationWorld(this.plugin, worldName);
            this.worlds.put(worldName, config);
        }

        return config;
    }

    public String colorTxt(String string) {
        string = string.replaceAll("&0", ChatColor.BLACK + "");
        string = string.replaceAll("&1", ChatColor.DARK_BLUE + "");
        string = string.replaceAll("&2", ChatColor.DARK_GREEN + "");
        string = string.replaceAll("&3", ChatColor.DARK_AQUA + "");
        string = string.replaceAll("&4", ChatColor.DARK_RED + "");
        string = string.replaceAll("&5", ChatColor.DARK_PURPLE + "");
        string = string.replaceAll("&6", ChatColor.GOLD + "");
        string = string.replaceAll("&7", ChatColor.GRAY + "");
        string = string.replaceAll("&8", ChatColor.DARK_GRAY + "");
        string = string.replaceAll("&9", ChatColor.BLUE + "");
        string = string.replaceAll("&a", ChatColor.GREEN + "");
        string = string.replaceAll("&b", ChatColor.AQUA + "");
        string = string.replaceAll("&c", ChatColor.RED + "");
        string = string.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
        string = string.replaceAll("&e", ChatColor.YELLOW + "");
        string = string.replaceAll("&f", ChatColor.WHITE + "");
        return string;
    }

    public boolean addAdvertiseWord(String badWord) {
        if (!advertise.contains(badWord)) {
            if (advertise.add(badWord)) {
                Collections.sort(advertise, new StringLengthComparator());
                config.set("BadWords.advertise", advertise);
                return true;
            }
        }

        return false;
    }

    public boolean delAdvertiseWord(String badWord) {
        if (advertise.contains(badWord)) {
            if (advertise.remove(badWord)) {
                config.set("BadWords.advertise", advertise);
                return true;
            }
        }

        return false;
    }

    public class StringLengthComparator implements Comparator<String> {
        public int compare(String o1, String o2) {
            if (o1.length() < o2.length()) {
                return -1;
            } else if (o1.length() > o2.length()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
