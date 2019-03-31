package org.black_ixx.bossshop.commands.sub;

import org.black_ixx.bossshop.commands.MegumiCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand extends MegumiCommand {
    @Override
    public void perform(CommandSender sender, String[] Strings) {
        sender.sendMessage(ChatColor.RED + "/BossShop - Opens  main shop");
        sender.sendMessage(ChatColor.RED + "/BossShop <shop> [input] - Opens named shop");
        sender.sendMessage(ChatColor.RED + "/BossShop open <Shop> <Player> [input] - Opens named shop for the named player");
        sender.sendMessage(ChatColor.RED + "/BossShop close [Player] - Closes inventory of the named player");
        sender.sendMessage(ChatColor.RED + "/BossShop simulate <player> <shop> <shopitem> - Simulates click");
        sender.sendMessage(ChatColor.RED + "/BossShop nbt <name> - Save the nbt of this item");
        sender.sendMessage(ChatColor.RED + "/BossShop reload - Reloads the Plugin");
        if (sender instanceof Player) {
            sender.sendMessage(ChatColor.RED + "/BossShop read - Prints out itemdata of item in main hand");
        }
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "BossShop.admin";
    }
}
