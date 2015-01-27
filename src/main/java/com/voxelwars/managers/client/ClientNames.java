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

package com.voxelwars.managers.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientNames extends ClientModule {

    private final Map<UUID, String> nameMap;

    public ClientNames(ClientManager clients) {
        super(clients, ClientManager.MODULE_NAMES);

        this.nameMap = new HashMap<>();
    }

    @Override
    public void loadConfig() {

    }

    @Override
    public void saveConfig() {

    }

    public String getName(UUID uuid) {
        String name = this.nameMap.get(uuid);

        if (name != null && !"".equals(name)) {
            return name;
        }
                        // Note, that things which check against ClientNames#getName must check if the result is null.
        return null;    // If the result IS null, they need to call the lookup method. But, to do this, they'll need
    }                   // to run the the lookup Asynchronously, and then Synchronously run the rest of the code.

    public UUID getUuid(String name) {
        for (UUID uuid : this.nameMap.keySet()) {
            if (this.nameMap.get(uuid).equalsIgnoreCase(name)) {
                return uuid;
            }
        }
                        // Note, that things which check against ClientNames#getUuid must check if the result is null.
        return null;    // If the result IS null, they need to call the lookup method. But, to do this, they'll need
    }                   // to run the the lookup Asynchronously, and then Synchronously run the rest of the code.

    public Map<UUID, String> lookup(UUID uuid) {
        return null; //TODO: Implement real lookup method. Should never return null, if the server is online.
    }

    public Map<UUID, String> lookup(String name) {
        return null; //TODO: Implement real lookup method. Should never return null, if the server is online.
    }

    public void update(UUID uuid, String name) {
        this.nameMap.put(uuid, name);
    }
}
