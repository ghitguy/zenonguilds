package me.ghit.zenonguilds.commands;

import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.commands.subcommands.*;
import me.ghit.zenonguilds.menusystem.menu.GuildControlPanel;
import me.ghit.zenonguilds.menusystem.menu.GuildMenu;
import me.ghit.zenonguilds.menusystem.menu.GuildSelectMenu;
import me.ghit.zenonguilds.utils.GuildHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabExecutor {

    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandHandler(){
        subcommands.add(new SetGuild());
        subcommands.add(new SetLevel());
        subcommands.add(new SetLeader());
        subcommands.add(new SetGuildLevel());
        subcommands.add(new UserInfo());
        subcommands.add(new GuildInfo());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubCommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                        getSubCommands().get(i).perform(p, args);
                    }
                }
            }else if (args.length == 0) {

                if (GuildHandler.isLeader(p)) {
                    new GuildControlPanel(ZenonGuilds.getPlayerMenuUtility(p)).open();
                    return true;
                }

                if (!GuildHandler.getGuild(p).equals("")) {
                    new GuildMenu(ZenonGuilds.getPlayerMenuUtility(p)).open();
                    return true;
                }

                new GuildSelectMenu(ZenonGuilds.getPlayerMenuUtility(p)).open();
            }
        }


        return true;
    }

    public ArrayList<SubCommand> getSubCommands(){
        return subcommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1){ //prank <subcommand> <args>
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++){
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }

            return subcommandsArguments;
        }else if(args.length >= 2){
            for (int i = 0; i < getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }
        }

        return null;
    }

}
