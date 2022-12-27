package eu.maxpi.fiverr.warzone;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import eu.maxpi.fiverr.warzone.events.onEntityDamage;
import eu.maxpi.fiverr.warzone.utils.PluginLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Warzone extends JavaPlugin {

    private static Warzone instance = null;

    public static Warzone getInstance() { return Warzone.instance; }

    private static void setInstance(Warzone in) { Warzone.instance = in; }

    public static List<Player> inWarzone = new ArrayList<>();

    @Override
    public void onEnable() {
        setInstance(this);

        PluginLoader.load();

        loadEvents();
        loadTasks();

        Bukkit.getLogger().info("Warzone by fiverr.com/macslolz was enabled successfully!");
    }

    private void loadEvents(){
        Bukkit.getPluginManager().registerEvents(new onEntityDamage(), this);
    }

    private void loadTasks(){
        new BukkitRunnable() {
            @Override
            public void run() {
                RegionManager container = WorldGuard.getInstance().getPlatform().getRegionContainer().get(WorldGuard.getInstance().getPlatform().getMatcher().getWorldByName("world"));

                if(container == null){
                    Bukkit.getLogger().warning("Mondo specificato non trovato! CoreAPI.java, manageRegions()");
                    return;
                }

                inWarzone.removeIf(Objects::isNull);

                inWarzone.clear();

                inWarzone.addAll(Bukkit.getOnlinePlayers().stream().filter(p -> container.getApplicableRegions(BlockVector3.at(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ())).getRegions().stream().anyMatch(r -> r.getId().equals(PluginLoader.warzoneRegion))).collect(Collectors.toList()));

                inWarzone.forEach(p -> {
                    if(p.hasPermission("warzone.exclude")) return;

                    p.setFlying(false);
                    p.setAllowFlight(false);
                });
            }
        }.runTaskTimerAsynchronously(this, 0L, 3L);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Warzone by fiverr.com/macslolz was enabled successfully!");
    }
}
