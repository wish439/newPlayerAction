package org.wishtoday.ps.newPlayerAction.Config;

import com.google.common.collect.Sets;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.wishtoday.ps.newPlayerAction.NewPlayerAction;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class VarConfig {
    public static final Logger LOGGER = NewPlayerAction.getInstance().getLogger();
    public static final Set<VarObject> vars = Sets.newHashSet(
            VarObject.of("example", "Hello", ClickEvent.runCommand("say hello"), "this is a hoverevent text")
    );
    public static final Map<String,Component> components = new HashMap<>();
    public static final File varFile = new File(NewPlayerAction.getInstance().getDataFolder(), "vars.yml");

    public static void saveDefaultVarConfig() {
        YamlConfiguration config = new YamlConfiguration();
        for (VarObject var : vars) {
            String name = "vars." + var.getVarName();
            config.set(name + "." + "Text", var.getVarText());
            config.set(name + "." + "ClickEvent.Action", var.getClickEvent().action().name());
            config.set(name + "." + "ClickEvent.Value", var.getClickEvent().value());
            config.set(name + "." + "HoverEventText", var.getHoverText());
        }
        try {
            config.save(varFile);
        } catch (IOException e) {
            NewPlayerAction.getInstance().getLogger().warning("Failed to save vars.yml\n" + e.getMessage());
        }
    }
    public static Component tryParsePlaceholders(Component text) {
        for (Map.Entry<String, Component> entry : components.entrySet()) {
            String k = entry.getKey();
            Component v = entry.getValue();
            text = text.replaceText(
                    builder -> builder.match("%" + k + "%").replacement(v)
            );
        }
        return text;
    }
    public static void loadVarConfig() {
        if (!varFile.exists()) {
            saveDefaultVarConfig();
            return;
        }
        YamlConfiguration loaded = YamlConfiguration.loadConfiguration(varFile);
        ConfigurationSection section = loaded.getConfigurationSection("vars");
        if (section == null) return;
        section.getKeys(false).forEach(key -> components.put(key,getComponentFromPath(key, loaded)));
    }
    private static Component getComponentFromPath(String path
            , YamlConfiguration config) {
        String text = config.getString("vars." + path + "." + "Text");
        String hoverText = config.getString("vars." + path + "." + "HoverEventText");
        String clickAction = config.getString("vars." + path + "." + "ClickEvent.Action");
        String clickValue = config.getString("vars." + path + "." + "ClickEvent.Value");
        if (text == null || hoverText == null || clickAction == null || clickValue == null) {
            LOGGER.warning("vars.yml on " + path +" has null value");
            return null;
        }
        return Component.text(text)
                .clickEvent(ClickEvent.clickEvent(
                        ClickEvent.Action.valueOf(clickAction)
                        , clickValue))
                .hoverEvent(HoverEvent.showText(Component.text(hoverText)));
    }
}
