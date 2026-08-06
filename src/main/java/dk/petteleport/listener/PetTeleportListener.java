package dk.petteleport.listener;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public final class PetTeleportListener implements Listener {
    private final Plugin plugin;
    private final boolean sameWorldEnabled;
    private final boolean differentWorldEnabled;

    public PetTeleportListener(Plugin plugin, boolean sameWorldEnabled, boolean differentWorldEnabled) {
        this.plugin = plugin;
        this.sameWorldEnabled = sameWorldEnabled;
        this.differentWorldEnabled = differentWorldEnabled;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Location destination = event.getTo();
        boolean changesWorld = !event.getFrom().getWorld().equals(destination.getWorld());

        if ((changesWorld && !differentWorldEnabled) || (!changesWorld && !sameWorldEnabled)) {
            return;
        }

        UUID playerId = event.getPlayer().getUniqueId();
        List<Tameable> pets = event.getFrom().getWorld().getEntitiesByClass(Tameable.class).stream()
                .filter(Tameable::isTamed)
                .filter(pet -> pet.getOwner() != null && pet.getOwner().getUniqueId().equals(playerId))
                .filter(pet -> pet.getType() != EntityType.NAUTILUS)
                .filter(pet -> !(pet instanceof Sittable sittable) || !sittable.isSitting())
                .toList();

        if (pets.isEmpty()) {
            return;
        }

        Location target = destination.clone();
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            for (Tameable pet : pets) {
                if (pet.isValid()) {
                    pet.teleport(target);
                }
            }
        });
    }
}
