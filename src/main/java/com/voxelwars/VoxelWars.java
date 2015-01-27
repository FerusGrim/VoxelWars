/*
Copyright (c) 2014-2015 Nicholas Badger / VoxelWars

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package com.voxelwars;

import com.voxelwars.commands.CommandManager;
import com.voxelwars.managers.Identifier;
import com.voxelwars.managers.Manager;
import com.voxelwars.managers.chat.ChatManager;
import com.voxelwars.managers.clan.ClanManager;
import com.voxelwars.managers.client.ClientManager;
import com.voxelwars.managers.economy.EconomyManager;
import com.voxelwars.managers.gamemode.GameManager;
import com.voxelwars.managers.json.JSONManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class VoxelWars extends JavaPlugin {

    private final Map<Identifier, Manager> managerMap = new HashMap<>();

    @Override
    public void onEnable() {
        //saveDefaultConfig();

        registerManagers();
        this.managerMap.values().forEach(Manager::initialize);
    }

    @Override
    public void onDisable() {
        this.managerMap.values().forEach(Manager::shutdown);
    }

    private void registerManagers() {
        this.managerMap.put(Identifier.JSON, new JSONManager(this));
        this.managerMap.put(Identifier.CLIENT, new ClientManager(this));
        this.managerMap.put(Identifier.COMMAND, new CommandManager(this));
        this.managerMap.put(Identifier.ECONOMY, new EconomyManager(this));
        this.managerMap.put(Identifier.GAME, new GameManager(this));
        this.managerMap.put(Identifier.CLANS, new ClanManager(this));
        this.managerMap.put(Identifier.CHAT, new ChatManager(this));
    }

    private Manager getManager(Identifier identifier) {
        return this.managerMap.get(identifier);
    }

    public JSONManager getJsonManager() {
        return (JSONManager) this.getManager(Identifier.JSON);
    }

    public ClientManager getClientManager() {
        return (ClientManager) this.getManager(Identifier.CLIENT);
    }

    public CommandManager getCommandManager() {
        return (CommandManager) this.getManager(Identifier.COMMAND);
    }

    public EconomyManager getEconomyManager() {
        return (EconomyManager) this.getManager(Identifier.ECONOMY);
    }

    public GameManager getGameManager() {
        return (GameManager) this.getManager(Identifier.GAME);
    }

    public ClanManager getClanManager() { return (ClanManager) this.getManager(Identifier.CLANS); }

    public ChatManager getChatManager() { return (ChatManager) this.getManager(Identifier.CHAT); }
}
