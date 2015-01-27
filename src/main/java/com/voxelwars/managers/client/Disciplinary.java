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
import java.util.concurrent.TimeUnit;

public abstract class Disciplinary extends ClientModule {

    private final Map<UUID, Long> expireMap;
    private final Map<UUID, String> reasonMap;

    public Disciplinary(ClientManager clients, String name) {
        super(clients, name);

        this.expireMap = new HashMap<>();
        this.reasonMap = new HashMap<>();
    }

    public boolean isDisciplined(UUID uuid) {
        if (!this.expireMap.containsKey(uuid)) {
            return false;
        }

        if (this.expireMap.get(uuid) == 0) {
            return true;
        }

        if (this.expireMap.get(uuid) <= System.currentTimeMillis()) {
            this.removeDiscipline(uuid);
            return false;
        }

        return true;
    }

    public long getRemainingMillis(UUID uuid) {
        return this.expireMap.get(uuid) - System.currentTimeMillis();
    }

    public String getReason(UUID uuid) {
        return this.reasonMap.get(uuid);
    }

    public void setDiscipline(UUID uuid, TimeUnit measurement, long length, String reason) {

        if (length > 0) { // Don't convert if the discipline is permanent.
            length = measurement.toMillis(length); // Convert from its unit of measure to milliseconds.
            length = length + System.currentTimeMillis(); // And then set it for a time in the future.
        }

        this.expireMap.put(uuid, length);
        this.reasonMap.put(uuid, reason);
    }

    public void removeDiscipline(UUID uuid) {
        this.expireMap.remove(uuid);
        this.reasonMap.remove(uuid);
    }
}
