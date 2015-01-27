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

package com.voxelwars.managers.economy;

import com.voxelwars.VoxelWars;
import com.voxelwars.managers.Identifier;
import com.voxelwars.managers.Manager;
import com.voxelwars.managers.economy.shops.ShopsModule;

public class EconomyManager extends Manager {

    public static final String MODULE_SHOPS = "shops_module";

    public EconomyManager(VoxelWars plugin) {
        super(plugin, Identifier.ECONOMY);
    }

    @Override
    public void registerModules() {
        this.addModule(new ShopsModule(this));
    }

    @Override
    public void loadConfig() {

    }

    @Override
    public void saveConfig() {

    }

    public ShopsModule getShops() {
        return (ShopsModule) this.getModule(MODULE_SHOPS);
    }
}
