package org.wishtoday.ps.newPlayerAction.Config;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.wishtoday.ps.newPlayerAction.NewPlayerAction;
import org.wishtoday.ps.newPlayerAction.Util.Actions;
import org.wishtoday.ps.newPlayerAction.Util.ConfigAction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Config {
    public static final Set<ConfigAction> configActions = Sets.newHashSet();
    public static final FileConfiguration config = NewPlayerAction.getInstance().getConfig();
    private static boolean enable = config.getBoolean("enable", true);
    private static final String actionsPath = "newPlayerActions";

    public static void reloadConfig() {
        File file = new File(NewPlayerAction.getInstance().getDataFolder(), "config.yml");
        configActions.clear();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            NewPlayerAction.getInstance().getLogger().warning("Failed to reload\n" + e.getMessage());
        }
        VarConfig.loadVarConfig();
        enable = config.getBoolean("enable", true);
    }

    public static boolean isEnable() {
        return enable;
    }

    public static void getAllAction() {
        ConfigurationSection section = config.getConfigurationSection(actionsPath);
        if (section == null) {
            NewPlayerAction.getInstance().getLogger().warning("config.yml doesn't contain actions!");
            return;
        }
        section.getKeys(false).forEach(key ->
                configActions.add(getActionFromPath(
                        actionsPath + "." + key)));
    }

    private static ConfigAction getActionFromPath(String path) {
        List<String> list = Lists.newArrayList();
        String type = config.getString(path + "." + "Type");
        list.addAll(config.getStringList(path + "." + "Values"));
        return new ConfigAction(Actions.valueOf(type), list);
    }
}

