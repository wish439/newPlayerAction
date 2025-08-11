package org.wishtoday.ps.newPlayerAction.Config;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.configuration.Configuration;
import org.wishtoday.ps.newPlayerAction.NewPlayerAction;
import org.wishtoday.ps.newPlayerAction.Util.Actions;
import org.wishtoday.ps.newPlayerAction.Util.ConfigAction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Config {
    public static final Set<ConfigAction> configActions = Sets.newHashSet();
    public static final Configuration config = NewPlayerAction.getInstance().getConfig();
    private static boolean enable = config.getBoolean("enable", true);
    private static final String actionsPath = "newPlayerActions";

    public static void reloadConfig() {
        enable = config.getBoolean("enable", true);
    }

    public static boolean isEnable() {
        return enable;
    }

    public static void getAllAction() {
        config.getConfigurationSection(actionsPath)
                .getKeys(false).forEach(key ->
                        configActions.add(getActionFromPath(
                                actionsPath + "." + key)));
        System.out.println(configActions.size());
        configActions.forEach(System.out::println);
    }

    private static ConfigAction getActionFromPath(String path) {
        System.out.println(path + "." + "Type");
        List<String> list = Lists.newArrayList();
        String type = config.getString(path + "." + "Type");
        list.addAll(config.getStringList(path + "." + "Values"));
        return new ConfigAction(Actions.valueOf(type), list);
    }
}

