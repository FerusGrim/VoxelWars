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

package com.voxelwars.managers;

import com.voxelwars.VoxelWars;

import java.util.HashMap;
import java.util.Map;

public abstract class Manager extends ExtendedFrame implements IManager {

    private final Identifier identifier;
    private final Map<String, Module> moduleMap;

    public Manager(VoxelWars plugin, Identifier identifier) {
        super(plugin, identifier.toString());

        this.identifier = identifier;
        this.moduleMap = new HashMap<>();
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public void addModule(Module module) {
        this.moduleMap.put(module.getNameLower(), module);
    }

    public void removeModule(String name) {
        this.moduleMap.remove(name);
    }

    public Module getModule(String name) {
        return this.moduleMap.get(name);
    }

    private void initializeModules() {
        this.moduleMap.values().forEach(Module::initialize);
    }

    private void shutdownModules() {
        this.moduleMap.values().forEach(Module::shutdown);
    }

    @Override
    public void initialize() {
        this.loadConfig();
        this.registerModules();
        this.initializeModules();
    }

    @Override
    public void shutdown() {
        this.saveConfig();
        this.shutdownModules();
    }
}
