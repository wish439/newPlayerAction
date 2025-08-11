package org.wishtoday.ps.newPlayerAction;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.wishtoday.ps.newPlayerAction.Command.MainCommand;
import org.wishtoday.ps.newPlayerAction.Config.VarConfig;
import org.wishtoday.ps.newPlayerAction.Config.VarObject;
import org.wishtoday.ps.newPlayerAction.Event.PlayerJoinEvent;

public final class NewPlayerAction extends JavaPlugin {
    private static NewPlayerAction instance;

    @Override
    public void onEnable() {
        instance = this;
        configAction();
        this.getServer().getPluginManager().registerEvents(new PlayerJoinEvent(),this);
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            Commands registrar = event.registrar();
            MainCommand.registerCommands(registrar);
        });
    }

    @Override
    public void onDisable() {
    }
    public static NewPlayerAction getInstance() {
        return instance;
    }
    public void configAction() {
        ConfigurationSerialization.registerClass(VarObject.class);
        this.saveDefaultConfig();
        VarConfig.loadVarConfig();
    }
}
