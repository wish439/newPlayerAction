package org.wishtoday.ps.newPlayerAction.Command;

import io.papermc.paper.command.brigadier.Commands;
import org.wishtoday.ps.newPlayerAction.Config.Config;

import static io.papermc.paper.command.brigadier.Commands.literal;


public class MainCommand {
    public static void registerCommands(Commands commands) {
        commands.register(
                literal("newPlayerAction")
                        .then(literal("reload")
                                .executes(context -> {
                                            Config.reloadConfig();
                                            context.getSource().getSender().sendMessage("已重新加载");
                                            return 1;
                                        }
                                )
                        ).build()
        );
    }
}
