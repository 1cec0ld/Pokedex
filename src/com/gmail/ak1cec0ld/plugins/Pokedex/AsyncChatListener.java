package com.gmail.ak1cec0ld.plugins.Pokedex;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener{
    private Pokedex plugin;

    public AsyncChatListener(Pokedex pokedex) {
        this.plugin = pokedex;
    }

    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent event){
        if (plugin.isUsingPokedex(event.getPlayer().getName())){
            event.setCancelled(true);
            if(event.getMessage().matches("^[Oo].{0,1}[Ff]$") || event.getMessage().contains("pokedex off")){
                plugin.toggleUsingPokedex(event.getPlayer());
            } else if (event.getMessage().matches("^[Hh].{0,2}[Pp]$")){
                event.getPlayer().sendMessage("§c[Pokédex] §dHelp Menu");
                event.getPlayer().sendMessage("§c[Pokédex] §6Type §d/pokedex off §6to turn off Pokédex");
                event.getPlayer().sendMessage("§c[Pokédex] §6Or just type §doff§6!");
                event.getPlayer().sendMessage("§c[Pokédex] §6Or just type a Pokémon's name!");
            } else {
                String pokemonName = checkMessage(event.getMessage());
                messagePlayerDelayed(pokemonName, plugin.getPokedexStorageManager().getMessages(pokemonName.toLowerCase()),event.getPlayer());
            }
        } else {
            if (event.getMessage().contains("pokedex on")){
                plugin.toggleUsingPokedex(event.getPlayer());
                event.setCancelled(true);
            }
        }
        if (event.getMessage().equals("dex reload")){
            plugin.getPokedexStorageManager().reload();
        }
    }
    
    private String checkMessage(String message){
        String[] splitMessage = message.split(" ");
        for(String word : splitMessage){
            if(plugin.getPokedexStorageManager().getKeys().contains(word.toLowerCase())){
                return word;
            }
        }
        return "";
    }

    private void messagePlayerDelayed(String pokemon, List<String> list, Player player){
        if(!pokemon.equals("")){
            player.sendMessage("§c[Pokédex] §6Searching for species named §d"+pokemon+"§6...");
        }
        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
            @Override
            public void run() {
                if(list.size() > 0){
                    player.sendMessage("§c[Pokédex] §6Match found! Displaying data...");
                } else {
                    player.sendMessage("§c[Pokédex] §6Species not found in database!");
                    player.sendMessage("§c[Pokédex] §6Did you spell the name correctly?");
                    player.sendMessage("§c[Pokédex] §6If you believe this is an error, report it to Bill");
                }
            }
        }, 20L);
        if(list.size() > 0){
            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
                @Override
                public void run() {
                    for(String line : list){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                    }
                }
            }, 30L);
        }
    }
}
