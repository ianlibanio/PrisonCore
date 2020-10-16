package com.ianlibanio.prison.module.rankup.inventory;

import com.ianlibanio.prison.PrisonCore;
import com.ianlibanio.prison.module.rankup.RankupModule;
import com.ianlibanio.prison.module.rankup.controller.IRankupController;
import com.ianlibanio.prison.module.rankup.messages.RankupMessages;
import com.ianlibanio.prison.module.rankup.service.IRankupService;
import com.ianlibanio.prison.utils.item.Head;
import com.ianlibanio.prison.utils.item.Item;
import com.ianlibanio.prison.utils.inventory.ClickableItem;
import com.ianlibanio.prison.utils.inventory.SmartInventory;
import com.ianlibanio.prison.utils.inventory.content.InventoryContents;
import com.ianlibanio.prison.utils.inventory.content.InventoryProvider;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RankupInventory implements InventoryProvider {

    private static final IRankupService service = RankupModule.getService();
    private static final IRankupController controller = RankupModule.getController();

    public static SmartInventory getInventory(Player player) {
        val actual = service.get(player.getUniqueId());

        String title;
        if (actual.isLast()) {
            title = RankupMessages.getMessage("rankup-inventory-lastrank-title", actual);
        } else {
            val next = controller.get(actual.getPosition() + 1);
            title = RankupMessages.getMessage("rankup-inventory-firstrank-title", next);
        }

        return SmartInventory.builder()
                .provider(new RankupInventory())
                .size(actual.isLast() ? 3 : 5, 9)
                .title(title)
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        val actual = service.get(player.getUniqueId());

        if (actual.isLast()) {
            contents.set(1, 4, ClickableItem.empty(new Item(Material.BARRIER)
                    .name(RankupMessages.getMessage("rankup-inventory-lastrank-itemname", actual))
                    .lore(RankupMessages.getMessageList("rankup-inventory-lastrank-itemlore", actual))
                    .build()));
            return;
        }

        val next = controller.get(actual.getPosition() + 1);

        contents.set(1, 4, ClickableItem.empty(new Head()
                .owner(player.getDisplayName())
                .name(RankupMessages.getMessage("rankup-inventory-head-itemname", next))
                .lore(RankupMessages.getMessageList("rankup-inventory-head-itemlore", next))
                .create())
        );

        contents.set(3, 3, ClickableItem.of(new Item(Material.WOOL, 1, 13)
                .name(RankupMessages.getMessage("rankup-inventory-confirm-itemname", next))
                .lore(RankupMessages.getMessageList("rankup-inventory-confirm-itemlore", next))
                .build(),
                event -> {
            val economy = PrisonCore.getEconomy();

            if (!economy.has(player, next.getPrice())) {
                player.closeInventory();
                player.sendMessage(RankupMessages.getMessage("rankup-inventory-confirm-nomoney", next).replace("{need}", "" + (next.getPrice() - economy.getBalance(player))));
                return;
            }

            economy.withdrawPlayer(player, next.getPrice());
            service.set(player.getUniqueId(), next);

            player.closeInventory();
            player.sendMessage(RankupMessages.getMessage("rankup-inventory-confirm-success", next));
        }));

        contents.set(3, 5, ClickableItem.of(new Item(Material.WOOL, 1, 14)
                .name(RankupMessages.getMessage("rankup-inventory-cancel-itemname", next))
                .lore(RankupMessages.getMessageList("rankup-inventory-cancel-itemlore", next))
                .build(), event -> {
            player.closeInventory();
            player.sendMessage(RankupMessages.getMessage("rankup-inventory-cancel-success", next));
        }));
    }

}
