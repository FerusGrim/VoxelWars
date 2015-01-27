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

package com.voxelwars.managers.economy.shops;

import com.voxelwars.utils.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Shop {

    private final int id;
    private String name;
    private LivingEntity owner;
    private Map<Integer, ItemStack> itemMap = new HashMap<>();
    private int slots;

    public Shop(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LivingEntity getOwner() {
        return this.owner;
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
    }

    public int getSlots() {
        return this.slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public ItemStack getSlot(int slot) {
        return this.itemMap.get(slot);
    }

    public boolean setSlot(CommandSender sender, int slot, ItemStack item) {
        if (slot > this.slots) {
            Message.failure(sender, "Shops", "Maximum slots: " + this.slots);
            return false;
        }

        this.itemMap.put(slot, item);
        return true;
    }

    public void generateShop(Player player) {
        Inventory inventory = player.getServer().createInventory(null, this.slots, this.name);

        for (int slot : this.itemMap.keySet()) {
            inventory.setItem(slot, this.itemMap.get(slot));
        }

        player.openInventory(inventory);
    }

    public static Shop create(int id, int slots, String name) {
        Shop shop = new Shop(id);

        shop.setSlots(slots);
        shop.setName(name);

        return shop;
    }

    public static Shop load(JSONObject json) {
        // TODO: Decide proper loading mechanism.
        return null;
    }
}
