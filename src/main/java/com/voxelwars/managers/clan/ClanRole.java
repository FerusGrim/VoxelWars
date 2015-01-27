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

package com.voxelwars.managers.clan;

public enum ClanRole {

    NOT_JOINED("Not", 0),
    RECRUIT("Recruit", 1),
    MEMBER("Member", 2),
    ELDER("Elder", 3),
    LEADER("Leader", 4);

    private final String string;
    private final int level;

    private ClanRole(String string, int level) {
        this.string = string;
        this.level = level;
    }

    public String toString() {
        return this.string;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean isAtLeast(ClanRole role) {
        return this.level >= role.level;
    }

    public boolean isAbove(ClanRole role) {
        return this.level > role.level;
    }

    public static ClanRole fromString(String string) {
        for (ClanRole role : values()) {
            if (role.toString().equalsIgnoreCase(string)) {
                return role;
            }
        }
        return null;
    }

    public static ClanRole fromLevel(int level) {
        for (ClanRole role : values()) {
            if (role.getLevel() == level) {
                return role;
            }
        }
        return null;
    }
}
