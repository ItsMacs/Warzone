package eu.maxpi.fiverr.warzone.utils;

import eu.maxpi.fiverr.warzone.Warzone;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PluginLoader {

    public static String warzoneRegion;

    public static void load(){
        Warzone.getInstance().saveResource("config.yml", false);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(Warzone.getInstance().getDataFolder() + "/config.yml"));
        warzoneRegion = config.getString("region");
    }

}
