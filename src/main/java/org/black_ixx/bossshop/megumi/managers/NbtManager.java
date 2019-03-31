package org.black_ixx.bossshop.megumi.managers;

import lombok.Getter;
import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.megumi.util.ItemConvertUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;

public class NbtManager {
    private BossShop plugin;
    @Getter
    private ItemConvertUtil convertUtil;

    @Getter
    private HashMap<String, ItemStack> items;

    @Getter
    YamlConfiguration nbt;

    public NbtManager(BossShop plugin) {
        this.plugin = plugin;
        this.convertUtil = new ItemConvertUtil(plugin.getVersion());
        this.items = new HashMap<>();
    }

    public void init() {
        nbt = initFile();
        ConfigurationSection section = nbt.getConfigurationSection("Nbts");
        if (section == null) return;
        for (String s : section.getKeys(false)) {
            String convert = section.getString(s);
            items.put(s, convertUtil.convert(convert));
        }
    }

    private void saveNbt() {
        File file = new File(plugin.getDataFolder(), "nbt.yml");
        try {
            nbt.save(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private YamlConfiguration initFile() {
        File file = new File(plugin.getDataFolder(), "nbt.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            copyFile(plugin.getResource("nbt.yml"), file);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    private void copyFile(InputStream inputStream, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] arrayOfByte = new byte['?'];
            int i;
            while ((i = inputStream.read(arrayOfByte)) > 0) {
                fileOutputStream.write(arrayOfByte, 0, i);
            }
            fileOutputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(String name, ItemStack item) {
        String convert = convertUtil.convert(item);
        nbt.set(String.format("Nbts.%s", name), convert);
        items.put(name, item);
        saveNbt();
    }
}
