package com.gmail.ak1cec0ld.plugins.Pokedex;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor{
    private Pokedex plugin;
    public CommandManager(Pokedex pokedex) {
        this.plugin = pokedex;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("pokedex")){
            if(sender instanceof Player && args.length > 0){
                Player player = (Player)sender;
                if(args.length == 1 && args[0].equalsIgnoreCase("on")){
                    if(args.length == 1 && plugin.isUsingPokedex(player.getName())){
                        player.sendMessage("§c[Pokédex] §6Already connected to database!");
                        player.sendMessage("§c[Pokédex] §6Type §d/pokedex off §6to disconnect");
                        player.sendMessage("§c[Pokédex] §6Or just type §doff§6!");
                    } else {
                        plugin.toggleUsingPokedex(player);
                    }
                } else if(args.length == 1 && args[0].equalsIgnoreCase("off")){
                    if(!plugin.isUsingPokedex(player.getName())){
                        player.sendMessage("§c[Pokédex] §6Not connected to database!");
                        player.sendMessage("§c[Pokédex] §6Type §d/pokedex on §6to connect!");
                    } else {
                        plugin.toggleUsingPokedex(player);
                    }
                } else if(args.length == 1 && args[0].equalsIgnoreCase("help")){
                    player.sendMessage("§c[Pokédex] §"+(plugin.isUsingPokedex(player.getName())?"2":"4")+"§lHelp Menu");
                    player.sendMessage("§c[Pokédex] §6Type §d/pokedex on §6to turn on Pokédex");
                    player.sendMessage("§c[Pokédex] §6Type §d/pokedex off §6to turn off Pokédex");
                    player.sendMessage("§c[Pokédex] §6Type §d/pokedex [species] §6to see information about a Pokémon");
                } else if(args.length==0){
                    player.sendMessage("§c[Pokédex] §dVersion 6.0");
                    player.sendMessage("§c[Pokédex] §6Type §d/pokedex help §6for help!");
                    player.sendMessage("§c[Pokédex] §"+((plugin.isUsingPokedex(player.getName()))?"2§lOn":"4§lOff"));
                } else {
                    String sentence = "";
                    for(String word : args){
                        sentence += word+" ";
                    }
                    if(plugin.isUsingPokedex(player.getName())){
                        player.chat(sentence);
                    } else {
                        plugin.toggleUsingPokedex(player);
                        player.chat(sentence);
                        plugin.toggleUsingPokedex(player);
                    }
                }
            }
        }
        return false;
    }

}
