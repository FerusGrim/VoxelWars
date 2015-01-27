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

import com.voxelwars.VoxelWars;
import com.voxelwars.managers.Identifier;
import com.voxelwars.managers.Manager;

import java.util.UUID;

public class ClientManager extends Manager {

    public static final String MODULE_LISTENER = "client_listener";
    public static final String MODULE_RANKS = "client_ranks";
    public static final String MODULE_MUTES = "client_mutes";
    public static final String MODULE_BANS = "client_bans";
    public static final String MODULE_NAMES = "client_names";

    public ClientManager(VoxelWars plugin) {
        super(plugin, Identifier.CLIENT);
    }

    @Override
    public void loadConfig() {

    }

    @Override
    public void saveConfig() {

    }

    @Override
    public void registerModules() {
        this.addModule(new ClientListener(this));
        this.addModule(new ClientRanks(this));
        this.addModule(new ClientMutes(this));
        this.addModule(new ClientBans(this));
        this.addModule(new ClientNames(this));

        //TODO: Remove when done testing:
        this.getNames().update(UUID.fromString("a865e930-676e-3b77-acfb-63416a2fa2e6"), "FerusGrim"); // Offline UUID
        this.getNames().update(UUID.fromString("54fe6e4c-f45a-43ef-8ca7-d2219b5090b4"), "FerusGrim"); // Online UUID
    }

    public ClientListener getListener() {
        return (ClientListener) this.getModule(MODULE_LISTENER);
    }

    public ClientRanks getRanks() {
        return (ClientRanks) this.getModule(MODULE_RANKS);
    }

    public ClientMutes getMutes() {
        return (ClientMutes) this.getModule(MODULE_MUTES);
    }

    public ClientBans getBans() {
        return (ClientBans) this.getModule(MODULE_BANS);
    }

    public ClientNames getNames() {
        return (ClientNames) this.getModule(MODULE_NAMES);
    }
}
