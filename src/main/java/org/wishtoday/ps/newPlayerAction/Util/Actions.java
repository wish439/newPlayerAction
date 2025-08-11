package org.wishtoday.ps.newPlayerAction.Util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.wishtoday.ps.newPlayerAction.Config.VarConfig;

import java.util.HashMap;
import java.util.Map;

public enum Actions {
    RUN_COMMAND("run_command",0) {
        @Override
        public void run(Player sender, String command) {
            command = command.replace("%player_uuid%", sender.getUniqueId().toString());
            command = command.replace("%player%", sender.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    },
    SEND_MESSAGE("send_message",1) {
        @Override
        public void run(Player player, String message) {
            message = message.replace("%player_uuid%", player.getUniqueId().toString());
            message = message.replace("%player%", player.getName());
            TextComponent text = Component.text(message);
            Component component = VarConfig.tryParsePlaceholders(text);
            player.sendMessage(component);
        }
    };
    public abstract void run(Player player, String message);
    private String name;
    private int index;
    private static Map<Integer, Actions> map = new HashMap<>();
    Actions(String name, int index) {
        this.name = name;
        this.index = index;
    }
    static {
        for (Actions value : values()) {
            map.put(value.index, value);
        }
    }

    public String getName() {
        return name;
    }
    public int getIndex() {
        return index;
    }
}
