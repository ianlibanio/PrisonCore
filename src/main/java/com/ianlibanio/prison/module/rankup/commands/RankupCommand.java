package com.ianlibanio.prison.module.rankup.commands;

import com.ianlibanio.prison.PrisonCore;
import com.ianlibanio.prison.module.rankup.RankupModule;
import com.ianlibanio.prison.module.rankup.controller.IRankupController;
import com.ianlibanio.prison.module.rankup.inventory.RankupInventory;
import com.ianlibanio.prison.module.rankup.messages.RankupMessages;
import com.ianlibanio.prison.module.rankup.service.IRankupService;
import com.ianlibanio.voidcommand.VoidCommand;
import com.ianlibanio.voidcommand.annotation.command.Command;
import com.ianlibanio.voidcommand.context.Context;
import com.ianlibanio.voidcommand.settings.Executor;
import lombok.val;

public class RankupCommand extends VoidCommand {

    private final IRankupService service = RankupModule.getService();
    private final IRankupController controller = RankupModule.getController();

    @Command(name = "rankup", executor = Executor.PLAYER_ONLY)
    public void command(Context context) {
        val player = context.player();
        val gui = PrisonCore.getInstance().getConfig().getBoolean("modules.rankup.rankup-command-gui", true);

        if (gui) {
            RankupInventory.getInventory(player).open(player);
        } else {
            val actual = service.get(player.getUniqueId());

            if (actual.isLast()) {
                player.sendMessage(RankupMessages.getMessage("rankup-command-last-rank", actual));
                return;
            }

            val economy = PrisonCore.getEconomy();
            val next = controller.get(actual.getPosition() + 1);

            if (!economy.has(player, next.getPrice())) {
                player.closeInventory();
                player.sendMessage(RankupMessages.getMessage("rankup-command-nomoney", next).replace("{need}", "" + (next.getPrice() - economy.getBalance(player))));
                return;
            }

            economy.withdrawPlayer(player, next.getPrice());
            service.set(player.getUniqueId(), next);

            player.sendMessage(RankupMessages.getMessage("rankup-inventory-confirm-success", next));
        }
    }
}
