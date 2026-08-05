package dk.petteleport;

import dk.petteleport.command.CallPetCommand;
import dk.petteleport.listener.PetTeleportListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PetTeleportPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        FileConfiguration config = getConfig();
        config.addDefault("teleport-same-world", true);
        config.addDefault("teleport-different-world", true);
        config.addDefault("callpet", true);
        config.options().copyDefaults(true);
        saveConfig();

        boolean sameWorld = config.getBoolean("teleport-same-world");
        boolean differentWorld = config.getBoolean("teleport-different-world");
        if (sameWorld || differentWorld) {
            getServer().getPluginManager().registerEvents(
                    new PetTeleportListener(this, sameWorld, differentWorld),
                    this
            );
        }

        if (config.getBoolean("callpet")) {
            PluginCommand callPetCommand = getCommand("callpet");
            if (callPetCommand == null) {
                throw new IllegalStateException("Command 'callpet' is missing from plugin.yml");
            }
            callPetCommand.setExecutor(new CallPetCommand());
        }
    }
}
