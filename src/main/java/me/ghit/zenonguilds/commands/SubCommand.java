package me.ghit.zenonguilds.commands;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    //name of the subcommand e.g /prank <subcommand>
    public abstract String getName();

    //e.g "This adds a mine"
    public abstract String getDescription();

    //How to use command e.g /mine add <label>
    public abstract String getSyntax();

    //code for the subcommand
    public abstract void perform(Player player, String args[]);

    public abstract List<String> getSubcommandArguments(Player player, String args[]);

}
