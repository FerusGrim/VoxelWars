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

public class Frame {

    private final VoxelWars plugin;
    private final String nameCanonical;
    private final String nameLower;
    private final String nameUpper;

    public Frame(VoxelWars plugin, String name) {
        this.plugin = plugin;
        this.nameCanonical = name;
        this.nameLower = name.toLowerCase();
        this.nameUpper = name.toUpperCase();
    }

    public VoxelWars getPlugin() {
        return this.plugin;
    }

    public String getNameCanonical() {
        return nameCanonical;
    }

    public String getNameLower() {
        return this.nameLower;
    }

    public String getNameUpper() {
        return this.nameUpper;
    }
}
