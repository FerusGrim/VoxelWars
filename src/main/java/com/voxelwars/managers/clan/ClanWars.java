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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClanWars extends ClanModule {

    private final Map<UUID, Long> expireMap;

    public ClanWars(ClanManager manager) {
        super(manager, ClanManager.MODULE_WARS);

        this.expireMap = new HashMap<>();
    }

    public boolean isDeserter(UUID uuid) {
        return this.expireMap.containsKey(uuid);
    }

    public void addDeserter(UUID uuid) {
        this.expireMap.put(uuid, System.currentTimeMillis() + 1800);
    }

    public void removeDeserter(UUID uuid) {
        this.expireMap.remove(uuid);
    }

    public Long getDuration(UUID uuid) { return this.expireMap.get(uuid); }

}
