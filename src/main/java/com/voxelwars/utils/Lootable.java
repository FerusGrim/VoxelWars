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

package com.voxelwars.utils;

import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class Lootable {

    public static final Random rand = new Random();

    private final Map<ItemStack, Integer> lootMap;
    private int maxLoot = 1;

    public Lootable() {
        this.lootMap = new HashMap<>();
        this.maxLoot = 1;
    }

    public Lootable(int maxLoot) {
        this.maxLoot = maxLoot;
        this.lootMap = new HashMap<>();
    }

    public Lootable(Map<ItemStack, Integer> lootMap) {
        this.lootMap = lootMap;
        this.maxLoot = 1;
    }

    public Lootable(Map<ItemStack, Integer> lootMap, int maxLoot) {
        this.lootMap = lootMap;
        this.maxLoot = maxLoot;
    }

    public void addLoot(int percentage, ItemStack item) {
        Integer cur = this.lootMap.get(item);

        if (cur != null) {
            percentage = percentage + cur;
            if (percentage > 100) {
                percentage = 100; // Note, 100 percentage means that the item will ALWAYS be dropped.
            }
        }

        this.lootMap.put(item, percentage);
    }

    public void removeLoot(ItemStack item) {
        this.lootMap.remove(item);
    }

    public int getPercentage(ItemStack item) {
        return this.lootMap.get(item);
    }

    public int getMaxLoot() {
        return this.maxLoot;
    }

    public void setMaxLoot(int maxLoot) {
        this.maxLoot = maxLoot;
    }

    public List<ItemStack> generateLoot() {
        List<ItemStack> items = new ArrayList<>();

        int hit = rand.nextInt(100) - 99; // If 'hit' is below or equal to the percentage in the map, it's a 'hit'.

        items.addAll(this.lootMap.keySet().stream().filter(item -> hit <= this.lootMap.get(item))
                .collect(Collectors.toList()));

        return items;
    }
}
