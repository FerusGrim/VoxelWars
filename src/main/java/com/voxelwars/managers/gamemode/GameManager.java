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

import com.voxelwars.VoxelWars;
import com.voxelwars.managers.Identifier;
import com.voxelwars.managers.Manager;
import com.voxelwars.managers.Module;
import com.voxelwars.managers.gamemode.wars.WarsGame;

import java.util.HashMap;
import java.util.Map;

public class GameManager extends Manager {

    private final Map<GameType, Game> gameMap;

    public GameManager(VoxelWars plugin) {
        super(plugin, Identifier.GAME);

        this.gameMap = new HashMap<>();
    }

    @Override
    public void registerModules() {
        this.addGame(new WarsGame(this));
    }

    @Override
    public void loadConfig() {

    }

    @Override
    public void saveConfig() {

    }

    public void addGame(Game game) {
        this.gameMap.put(game.getType(), game);
    }

    public void removeGame(GameType type) {
        this.gameMap.remove(type);
    }

    public Game getGame(GameType type) {
        return this.gameMap.get(type);
    }

    /**
     * @deprecated GameManager#addGame contains no ClassCastException possibilities.
     */
    @Deprecated
    @Override
    public void addModule(Module module) {
        if (!(module instanceof Game)) {
            this.getPlugin().getLogger().severe("GameManager: Invalid module type.");
            this.getPlugin().getLogger().severe("Is a not GameModule: " + module);
            return;
        }

        this.addGame((Game) module);
    }

    /**
     * @deprecated GameManager#removeModule contains no Invalid GameType possibilities.
     */
    @Deprecated
    @Override
    public void removeModule(String name) {
        GameType type = GameType.fromString(name);

        if (type == null) {
            this.getPlugin().getLogger().severe("GameManager: Invalid GameType: " + name);
            return;
        }

        this.removeGame(type);
    }

    /**
     * @deprecated GameManager#getGame returns a casted Game and contains no Invalid GameType possibilities.
     */
    @Deprecated
    @Override
    public Module getModule(String name) {
        GameType type = GameType.fromString(name);

        if (type == null) {
            this.getPlugin().getLogger().severe("GameManager: Invalid GameType: " + name);
            return null;
        }

        return this.getGame(type);
    }
}
