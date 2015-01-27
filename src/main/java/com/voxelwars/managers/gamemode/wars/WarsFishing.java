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

package com.voxelwars.managers.gamemode.wars;

import com.voxelwars.managers.Manager;
import com.voxelwars.utils.Lootable;
import com.voxelwars.utils.Message;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class WarsFishing extends WarsModule implements Listener {

    private final Lootable loot;

    public WarsFishing(WarsGame game) {
        super(game, WarsGame.MODULE_WARS_FISHING);

        this.loot = new Lootable();
    }

    @Override
    public void loadConfig() {
        //TODO: Lets pretend we get these values below by loading config.
        this.loot.setMaxLoot(1);
        this.loot.addLoot(15, new ItemStack(Material.WOOD_PICKAXE));
        this.loot.addLoot(10, new ItemStack(Material.STONE_PICKAXE));
        this.loot.addLoot(5, new ItemStack(Material.IRON_PICKAXE));
        this.loot.addLoot(1, new ItemStack(Material.DIAMOND_PICKAXE));
    }

    @Override
    public void saveConfig() {
        //TODO: Save any changes possibly made by in-game commands.
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (!this.getWars().isActiveWorld(event.getPlayer().getWorld().getName())) {
            return;
        }
        
        //TODO: Some other system for deciding if this event applies.

        for (ItemStack item : this.loot.generateLoot()) {
            event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), item);
            this.success(event.getPlayer(), "You stub your toe on a " + item.getItemMeta().getDisplayName() + "!");
        }
    }
}
