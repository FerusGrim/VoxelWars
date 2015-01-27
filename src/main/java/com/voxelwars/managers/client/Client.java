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

import java.util.UUID;

public class Client {

    /*
            NOTE: WE DO NOT NEED TO WORRY ABOUT CONCURRENCY!
            ANY VALUES RETURNED BY THIS OBJECT WILL
            ALWAYS BE ACCURATE AS OF THE TIME OF THE RETURN!

            THIS OBJECT STORES NOTHING MORE THAN THE CLIENTMANAGER
            OBJECT, AS WELL AS THE UUID OF THE CLIENT.

            ANY VALUES RETURNED BY THIS OBJECT ARE BEING
            DONE SO BY PROXY THROUGH THE CLIENTMANAGER.
     */

    private final ClientManager manager;
    private final UUID uuid;

    public Client(ClientManager manager, UUID uuid) {
        this.manager = manager;
        this.uuid = uuid;
    }

    public ClientManager getManager() {
        return this.manager;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public boolean isBanned() {
        return this.manager.getBans().isBanned(this.uuid);
    }

    public long getBanLengthMillis() {
        return this.manager.getBans().getRemainingMillis(this.uuid);
    }

    public long getBanLengthDays() {
        return this.manager.getBans().getRemainingDays(this.uuid);
    }

    public String getBanReason() {
        return this.manager.getBans().getReason(this.uuid);
    }
}
