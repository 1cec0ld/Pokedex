package com.gmail.ak1cec0ld.plugins.Pokedex;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Pokedex extends JavaPlugin{
    private Set<String> pokedexUsers;
    private PokedexStorageManager pStorage;
    private Pokedex instance = this;
    
    public void onEnable(){
        this.pokedexUsers = new HashSet<String>();
        this.pStorage = new PokedexStorageManager(this);
        
        
        
        this.getServer().getPluginManager().registerEvents(new AsyncChatListener(this), this);
        this.getCommand("pokedex").setExecutor(new CommandManager(this));
    }
    
    public Set<String> getPokedexUsers(){
        return this.pokedexUsers;
    }
    
    public boolean isUsingPokedex(String name){
        return this.pokedexUsers.contains(name);
    }
    
    public void toggleUsingPokedex(Player p){
        String name = p.getName();
        if (this.pokedexUsers.contains(name)){
            this.pokedexUsers.remove(name);
            p.sendMessage("§c[Pokédex] §6Disconnecting from database...");
            getServer().getScheduler().runTaskLater(instance, new Runnable(){
                @Override
                public void run() {
                    p.sendMessage("§c[Pokédex] §6Successfully disconnected!");
                }}, 10L);
        } else {
            p.sendMessage("§c[Pokédex] §6Connecting to database...");
            getServer().getScheduler().runTaskLater(instance, new Runnable(){
                @Override
                public void run() {
                    p.sendMessage("§c[Pokédex] §6Loading Pokémon...");
                    getServer().getScheduler().runTaskLater(instance, new Runnable(){
                        @Override
                        public void run() {
                            p.sendMessage("§c[Pokédex] §6Loaded 721 different species!");
                            pokedexUsers.add(name);
                        }}, 21L);
                }}, 15L);
        }
    }
    
    public PokedexStorageManager getPokedexStorageManager(){
        return this.pStorage;
    }
}
