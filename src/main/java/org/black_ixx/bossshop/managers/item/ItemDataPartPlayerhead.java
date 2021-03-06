package org.black_ixx.bossshop.managers.item;

import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.ClassManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemDataPartPlayerhead extends ItemDataPart {

    @Override
    public ItemStack transform(ItemStack item, String used_name, String argument) {
        if (!(item.getItemMeta() instanceof SkullMeta)) {
            ClassManager.manager.getBugFinder().warn("Mistake in Config: Itemdata of type '" + used_name + "' with value '" + argument + "' can not be added to an item with material '" + item.getType().name() + "'. Don't worry I'll automatically transform the material into '" + Material.SKULL_ITEM + "' with durability '3'.");
            item.setType(Material.SKULL_ITEM);
            item.setDurability((short) 3);
        }

        SkullMeta meta = (SkullMeta) item.getItemMeta();

		/*if(argument.contains("%")){
			//just a placeholder! Mark the placeholder! TODO
		}else{

			OfflinePlayer p;
			try{
				UUID uuid = UUID.fromString(argument);
				p = Bukkit.getOfflinePlayer(uuid);
			}catch(IllegalArgumentException e){
				p = Bukkit.getOfflinePlayer(argument);
			}
			meta.setOwningPlayer(p);
		}*/
        //meta.setOwner(null); //might fix paperspigot issue when setting the owner to a placeholder
        meta.setOwner(argument);

        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getPriority() {
        return PRIORITY_EARLY;
    }

    @Override
    public boolean removeSpaces() {
        return true;
    }

    @Override
    public String[] createNames() {
        return new String[]{"playerhead", "head", "owner"};
    }


    @Override
    public List<String> read(ItemStack i, List<String> output) {
        if (i.getItemMeta() instanceof SkullMeta) {
            SkullMeta meta = (SkullMeta) i.getItemMeta();
            if (i.getDurability() == 3) {
                if (meta.hasOwner()) {
                    output.add("playerhead:" + meta.getOwner());
                }
            }
        }
        return output;
    }


    @Override
    public boolean isSimilar(ItemStack shop_item, ItemStack player_item, BSBuy buy, Player p) {
        if (shop_item.getType() == Material.SKULL_ITEM) {
            if (player_item.getType() != Material.SKULL_ITEM) {
                return false;
            }

            SkullMeta ms = (SkullMeta) shop_item.getItemMeta();
            SkullMeta mp = (SkullMeta) player_item.getItemMeta();

            if (ms.hasOwner()) {

                if (!mp.hasOwner()) {
                    return false;
                }

                return ms.getOwner().equalsIgnoreCase(mp.getOwner());

            }
        }
        return true;
    }


}
