package eu.maxpi.fiverr.warzone.events;

import eu.maxpi.fiverr.warzone.Warzone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class onEntityDamage implements Listener {

    @EventHandler
    public void fallDamage(EntityDamageEvent event){
        if(event.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        if(!(event.getEntity() instanceof Player p)) return;
        event.setCancelled(Warzone.inWarzone.contains(p));
    }

}
