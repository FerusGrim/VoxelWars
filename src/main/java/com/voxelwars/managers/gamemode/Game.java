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

package com.voxelwars.managers.gamemode;

import com.voxelwars.managers.Manager;
import com.voxelwars.managers.Module;
import org.bukkit.event.Listener;

import java.util.*;

public abstract class Game extends Module implements IGame {

    private final GameManager manager;
    private final GameType type;
    private final Set<String> worldSet;
    private final Map<String, GameModule> moduleMap;

    public Game(Manager manager, GameType type) {
        super(manager, type.toString());

        this.manager = (GameManager) manager;
        this.type = type;
        this.worldSet = new HashSet<>();
        this.moduleMap = new HashMap<>();
    }

    @Override
    public GameManager getManager() {
        return this.manager;
    }

    public GameType getType() {
        return this.type;
    }

    public void addWorlds(String... worlds) {
        Collections.addAll(this.worldSet, worlds);
    }

    public void addWorld(String world) {
        this.addWorlds(world);
    }

    public void removeWorlds(String... worlds) {
        Iterator<String> setIter = this.worldSet.iterator();
        while (setIter.hasNext()) {
            String cur = setIter.next();
            for (String world : worlds) {
                if (cur.equalsIgnoreCase(world)) {
                    setIter.remove();
                }
            }
        }
    }

    public void removeWorld(String world) {
        this.worldSet.remove(world);
    }

    public boolean isActiveWorld(String world) {
        return this.worldSet.contains(world);
    }

    public void addGameModule(GameModule module) {
        this.moduleMap.put(module.getNameLower(), module);
    }

    public void removeGameModule(String name) {
        this.moduleMap.remove(name);
    }

    public GameModule getGameModule(String name) {
        return this.moduleMap.get(name);
    }

    @Override
    public void initialize() {
        this.loadConfig();
        this.loadGameModules();
        this.moduleMap.values().forEach(GameModule::initialize);

        if (this instanceof Listener) {
            this.getPlugin().getServer().getPluginManager().registerEvents((Listener) this, this.getPlugin());
        }
    }

    @Override
    public void shutdown() {
        this.saveConfig();
        this.moduleMap.values().forEach(GameModule::shutdown);
    }
}
