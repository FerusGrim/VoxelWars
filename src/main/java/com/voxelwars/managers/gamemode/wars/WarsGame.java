package com.voxelwars.managers.gamemode.wars;

import com.voxelwars.managers.Manager;
import com.voxelwars.managers.gamemode.Game;
import com.voxelwars.managers.gamemode.GameType;

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

public class WarsGame extends Game {

    public static final String MODULE_CLANS_HOOK = "module_clans_hook";
    public static final String MODULE_WARS_FISHING = "module_wars_fishing";
    public static final String MODULE_WARS_LISTENER = "module_wars_listener";
    public static final String MODULE_WARS_BORDER = "module_wars_border";

    public WarsGame(Manager manager) {
        super(manager, GameType.WARS);

        // Game#addWorlds is how the manager system identifies
        // if the player is in the right 'gamemode'. It's just
        // a simple world check. Don't over-think it.

        // We could make it so specific areas in specific worlds
        // are how you define a gamemode location (and if no
        // specific area is picked, the entire world), but I
        // figured we'd start out simple.
        this.addWorlds("Wars", "Wars_nether");
        this.addGameModule(new WarsFishing(this));
        this.addGameModule(new WarsInteractListener(this));
    }

    @Override
    public void loadConfig() {
        //TODO: Load config, obv.
    }

    @Override
    public void saveConfig() {
        //TODO: Save config, obv.
    }

    @Override
    public void loadGameModules() {
        this.addGameModule(new ClansHook(this));
    }
}
