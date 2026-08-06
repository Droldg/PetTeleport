# PetTeleport Improved

A lightweight Spigot plugin that brings a player's tamed pets along when the player teleports.

## Features

- Supports long-distance teleports in the same world
- Supports teleports between worlds
- Moves only pets owned by the teleporting player
- Leaves tamed Nautilus entities in place
- Leaves sitting pets in place
- Provides `/callpet` for manually calling loaded pets
- Contains no telemetry or bStats integration

PetTeleport can only interact with entities in loaded chunks. A pet in an unloaded chunk cannot be teleported until that chunk is loaded again.

## Requirements

- Minecraft/Spigot 26.2
- Java 25 or newer
- Maven 3.9+ for building from source

## Installation

1. Build the project or download `PetTeleport.jar` from a release.
2. Place the JAR in the server's `plugins` directory.
3. Restart the server.
4. Edit `plugins/PetTeleport_Improved/config.yml` if needed, then restart the server.

## Configuration

```yaml
teleport-same-world: true
teleport-different-world: true
callpet: true
```

| Setting | Description |
| --- | --- |
| `teleport-same-world` | Bring pets along on teleports within the same world. |
| `teleport-different-world` | Bring pets along on teleports between worlds. |
| `callpet` | Enable the `/callpet` command. |

## Commands and permissions

| Command | Permission | Description |
| --- | --- | --- |
| `/callpet` | `petteleport_improved.callpet` | Teleport your loaded, non-sitting pets to your location. |

The command permission is granted to all players by default.

## Building

```powershell
mvn clean package
```

The server-ready plugin is generated at `target/PetTeleport.jar`.
