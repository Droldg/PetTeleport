package dk.petteleport.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;

public final class CallPetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by a player.");
            return true;
        }

        int teleportedPets = 0;
        for (Tameable pet : player.getWorld().getEntitiesByClass(Tameable.class)) {
            if (pet.isTamed()
                    && pet.getOwner() != null
                    && pet.getOwner().getUniqueId().equals(player.getUniqueId())
                    && (!(pet instanceof Sittable sittable) || !sittable.isSitting())
                    && pet.teleport(player.getLocation())) {
                teleportedPets++;
            }
        }

        player.sendMessage(teleportedPets == 1
                ? "Your pet has been teleported to you."
                : teleportedPets + " pets have been teleported to you.");
        return true;
    }
}
