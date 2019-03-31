package org.black_ixx.bossshop.commands.sub;

import org.black_ixx.bossshop.commands.MegumiCommand;
import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.core.BSShop;
import org.black_ixx.bossshop.managers.ClassManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimulateCommand extends MegumiCommand {
    @Override
    public void perform(CommandSender sender, String[] Strings) {
        if (Strings.length != 4) {
            sender.sendMessage(ChatColor.RED + "Format: /BossShop simulate <player> <shop> <shopitem> - Simulates click");
            return;
        }
        Player p = Bukkit.getPlayer(Strings[1]);
        if (p == null) {
            ClassManager.manager.getMessageHandler().sendMessage("Main.PlayerNotFound", sender, Strings[1]);
            return;
        }

        BSShop shop = ClassManager.manager.getShops().getShop(Strings[2]);
        if (shop == null) {
            ClassManager.manager.getMessageHandler().sendMessage("Main.ShopNotExisting", sender, null, p, null, null, null);
            return;
        }

        BSBuy buy = shop.getItem(Strings[3]);
        if (buy == null) {
            ClassManager.manager.getMessageHandler().sendMessage("Main.ShopItemNotExisting", sender, null, p, shop, null, null);
            return;
        }

        buy.click(p, shop, null, null, null, ClassManager.manager.getPlugin());
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "BossShop.simulate";
    }
}
