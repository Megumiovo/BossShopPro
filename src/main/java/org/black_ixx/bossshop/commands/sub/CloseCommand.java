package org.black_ixx.bossshop.commands.sub;

import org.black_ixx.bossshop.commands.MegumiCommand;
import org.black_ixx.bossshop.managers.ClassManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CloseCommand extends MegumiCommand {
    @Override
    public void perform(CommandSender sender, String[] Strings) {
        Player p = null;
        String name = sender instanceof Player ? sender.getName() : "CONSOLE";

        if (sender instanceof Player) {
            p = (Player) sender;
        }
        if (Strings.length >= 2) {
            name = Strings[1];
            p = Bukkit.getPlayer(name);
        }

        if (p == null) {
            ClassManager.manager.getMessageHandler().sendMessage("Main.PlayerNotFound", sender, name);
            return;
        }

        p.closeInventory();
        if (p != sender) {
            ClassManager.manager.getMessageHandler().sendMessage("Main.CloseShopOtherPlayer", sender, p);
        }
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "BossShop.close";
    }
}
