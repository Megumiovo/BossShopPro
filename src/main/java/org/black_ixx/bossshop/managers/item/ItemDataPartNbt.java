package org.black_ixx.bossshop.managers.item;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.core.BSBuy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class ItemDataPartNbt extends ItemDataPart {

    private BossShop plugin = BossShop.getInstance();

    @Override
    public ItemStack transform(ItemStack item, String used_name, String argument) {
        if (!plugin.getNbtManager().getItems().containsKey(argument)) return item;

        ItemStack target = plugin.getNbtManager().getItems().get(argument);

        item = mixNbt(item, target);

        return item;
    }

    private ItemStack mixNbt(ItemStack item, ItemStack target) {
        item = getBukkitItemStack(item);
        target = getBukkitItemStack(target);

        NbtCompound nbtA = NbtFactory.asCompound(NbtFactory.fromItemTag(item));
        NbtCompound nbtB = NbtFactory.asCompound(NbtFactory.fromItemTag(target));

        for (NbtBase base : nbtB) {
            if (base.getName().equals("display") || base.getName().equals("ench")) continue;
            nbtA.put(base);
        }

        NbtFactory.setItemTag(item, nbtA);

        return item;
    }

    private static ItemStack getBukkitItemStack(ItemStack item) {
        if (item == null) {
            return new ItemStack(Material.AIR);
        }
        return !item.getClass().getName().endsWith("CraftItemStack") ? MinecraftReflection.getBukkitItemStack(item) : item;
    }

    @Override
    public boolean isSimilar(ItemStack shop_item, ItemStack player_item, BSBuy buy, Player p) {
        NbtCompound nbtA = NbtFactory.asCompound(NbtFactory.fromItemTag(shop_item));
        NbtCompound nbtB = NbtFactory.asCompound(NbtFactory.fromItemTag(player_item));

        return nbtA.equals(nbtB);
    }

    @Override
    public List<String> read(ItemStack i, List<String> output) {
        output.add("nbt: " + plugin.getNbtManager().getConvertUtil().convert(i));
        return output;
    }

    @Override
    public int getPriority() {
        return PRIORITY_NORMAL;
    }

    @Override
    public boolean removeSpaces() {
        return true;
    }

    @Override
    public String[] createNames() {
        return new String[]{"nbt"};
    }
}
