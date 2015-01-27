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
import org.bukkit.scheduler.BukkitRunnable;

public abstract class GameRunnable extends BukkitRunnable {

    private final VoxelWars plugin;
    private final Game game;
    private final String name;

    public GameRunnable(Game game, String name) {
        this.plugin = game.getPlugin();
        this.game = game;
        this.name = name;
    }

    public VoxelWars getPlugin() {
        return this.plugin;
    }

    public Game getGame() {
        return this.game;
    }

    public String getName() {
        return this.name;
    }
}
