package org.wishtoday.ps.newPlayerAction.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.wishtoday.ps.newPlayerAction.Config.Config;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        if (!Config.isEnable()) return;
        Player player = event.getPlayer();
        Config.getAllAction();
        Config.configActions.forEach(action -> {
           action.runAction(player);
        });
    }
}
