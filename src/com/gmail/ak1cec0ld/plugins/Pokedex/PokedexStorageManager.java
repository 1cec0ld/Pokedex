package com.gmail.ak1cec0ld.plugins.Pokedex;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;


public class PokedexStorageManager {
    
    private CustomYMLStorage yml;
    private YamlConfiguration storage;
    
    
    public PokedexStorageManager(Pokedex plugin){
        yml = new CustomYMLStorage(plugin,"Pokedex"+File.separator+"Default.yml");
        storage = yml.getYamlConfiguration();
        yml.save();
    }
    
    public Set<String> getKeys(){
        return storage.getKeys(false);
    }
    
    public List<String> getMessages(String pokemonName){
        return storage.getStringList(pokemonName);
    }
    
    public void reload(){
        yml.reload();
        storage = yml.getYamlConfiguration();
    }
}
