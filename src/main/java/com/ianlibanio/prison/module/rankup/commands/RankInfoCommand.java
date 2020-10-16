package com.ianlibanio.prison.module.rankup.commands;

import com.ianlibanio.prison.PrisonCore;
import com.ianlibanio.prison.module.rankup.RankupModule;
import com.ianlibanio.prison.module.rankup.inventory.RankInfoInventory;
import com.ianlibanio.prison.module.rankup.messages.RankupMessages;
import com.ianlibanio.voidcommand.VoidCommand;
import com.ianlibanio.voidcommand.annotation.command.Command;
import com.ianlibanio.voidcommand.context.Context;
import com.ianlibanio.voidcommand.settings.Executor;
import lombok.val;

public class RankInfoCommand extends VoidCommand {

    @Command(name = "rankinfo", executor = Executor.PLAYER_ONLY)
    public void command(Context context) {
        val player = context.player();
        val args = context.args();

        if (args.length < 1) {
            player.sendMessage(RankupMessages.getMessage("rankinfo-insufficient-args", null));
            return;
        }

        val rank = RankupModule.getController().get(args[0]);
        if (rank == null) {
            player.sendMessage(RankupMessages.getMessage("rankinfo-invalid-rank", null));
            return;
        }

        val gui = PrisonCore.getInstance().getConfig().getBoolean("modules.rankup.rankinfo-command-gui", true);

        if (gui) RankInfoInventory.getInventory(rank).open(player);
        else RankupMessages.getMessageList("rankinfo-infos", rank).forEach(player::sendMessage);
    }
}
