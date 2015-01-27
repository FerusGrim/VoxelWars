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

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

public class HandyLocation {

    public static String locationToString(Location loc) {
        return loc.getWorld().getName()
                + ":" + loc.getBlockX()
                + ":" + loc.getBlockY()
                + ":" + loc.getBlockZ();
    }

    public static Location locationFromString(String loc) {
        String[] parts = loc.split(":");

        return new Location(
                Bukkit.getWorld(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3])
        );
    }

    public static String chunkToString(Chunk chunk) {
        return chunk.getWorld().getName()
                + ":" + chunk.getX()
                + ":" + chunk.getZ();
    }

    public static Chunk chunkFromString(String chunk) {
        String[] parts = chunk.split(":");

        return Bukkit.getWorld(parts[0]).getChunkAt(
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2])
        );
    }

    //TODO: No testing to prove the validity of this method bas been done.
    public static double getDistance(Location point1, Location point2) {
        return Math.abs(point1.getBlockX() - point2.getBlockX())
                + Math.abs(point1.getBlockZ() - point2.getBlockZ());
    }

    //TODO: No testing to prove the validity of this method bas been done.
    public static int getDistance(Chunk chunk1, Chunk chunk2) {
        return Math.abs(chunk1.getX() - chunk2.getX())
                + Math.abs(chunk1.getZ() - chunk2.getZ());
    }
}
