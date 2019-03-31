package org.black_ixx.bossshop.commands.sub;

import org.black_ixx.bossshop.commands.MegumiCommand;
import org.black_ixx.bossshop.managers.ClassManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends MegumiCommand {

    @Override
    public void perform(CommandSender sender, String[] Strings) {
        sender.sendMessage(ChatColor.YELLOW + "Starting BossShop reload...");
        ClassManager.manager.getPlugin().reloadPlugin(sender);
        sender.sendMessage(ChatColor.YELLOW + "Done!");
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "BossShop.reload";
    }
}
