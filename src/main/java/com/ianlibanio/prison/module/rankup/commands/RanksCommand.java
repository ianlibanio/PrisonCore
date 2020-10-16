package com.ianlibanio.prison.module.rankup.commands;

import com.ianlibanio.prison.module.rankup.RankupModule;
import com.ianlibanio.voidcommand.VoidCommand;
import com.ianlibanio.voidcommand.annotation.command.Command;
import com.ianlibanio.voidcommand.context.Context;
import com.ianlibanio.voidcommand.settings.Executor;

public class RanksCommand extends VoidCommand {

    @Command(name = "ranks", executor = Executor.PLAYER_ONLY)
    public void command(Context context) {
        context.player().sendMessage(RankupModule.getController().ranks());
    }
}
