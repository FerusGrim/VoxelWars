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

public enum  Rank {

    BANNED("Banned", 0),
    STANDARD("Standard", 1),
    DONOR("Donor", 2),
    MODERATOR("Moderator", 3),
    ADMINISTRATOR("Administrator", 4),
    OWNER("Owner", 5),
    OPERATOR("Operator", 6),
    CONSOLE("Console", 7);

    private final String string;
    private final int level;

    private Rank(String string, int level) {
        this.string = string;
        this.level = level;
    }

    public String toString() {
        return this.string;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean isAtLeast(Rank rank) {
        return this.level >= rank.level;
    }

    public boolean isAbove(Rank rank) {
        return this.level > rank.level;
    }

    public static Rank fromString(String string) {
        for (Rank rank : values()) {
            if (rank.toString().equalsIgnoreCase(string)) {
                return rank;
            }
        }
        return null;
    }

    public static Rank fromLevel(int level) {
        for (Rank rank : values()) {
            if (rank.getLevel() == level) {
                return rank;
            }
        }
        return null;
    }
}
