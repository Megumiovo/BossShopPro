package org.black_ixx.bossshop.commands;

import org.black_ixx.bossshop.managers.ClassManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class MegumiCommand {
    private boolean isPlayer;
    private Player player;

    public final void execute(CommandSender sender, String[] strings) {
        this.isPlayer = sender instanceof Player;
        if (isPlayer) player = (Player) sender;

        if (playerOnly() && !isPlayer) return;

        if (getPermission() != null && !sender.hasPermission(getPermission())) {
            ClassManager.manager.getMessageHandler().sendMessage("Main.NoPermission", sender);
            return;
        }
        perform(sender, strings);
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void perform(CommandSender CommandSender, String[] Strings);

    public abstract boolean playerOnly();

    public abstract String getPermission();
}
