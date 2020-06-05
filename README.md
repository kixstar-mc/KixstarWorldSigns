# Kixstar Worlds
Just a quick and simple (Hackish) plugin to tp between worlds with basic permissions.
Didn't care much for the readme, nor the code. Just needed something that worked for a project.

# Dependencies

This plugin depends upon Multiverse-Core

# How To Use

Place down a sign and throw in the following

```
1: [KIX WORLDS]
2: w:<World Name Here>
3: [OPTIONAL SIGN NAME HERE]
```


# Commands

All commands are prefixed with `kixstarworlds/kw`

|  Command | Description  |
|:-:|:-:|
| add <player> <sign name>  |  Adds a player to the list of permitted people to use the specified sign  |
| remove <player> <sign name>  |  Removes a player from the list of permitted people to use the specified sign  |


# Permissions

|  Node | Description  |
|:-:|:-:|
| kixstarworlds.world.*  |  Gives you access to all signs  |
|  kixstarworlds.command | Gives you access to running the command to add/remove players from signs. |
