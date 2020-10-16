package com.ianlibanio.prison.module.rankup.inventory;

import com.ianlibanio.prison.module.rankup.RankupModule;
import com.ianlibanio.prison.module.rankup.controller.IRankupController;
import com.ianlibanio.prison.module.rankup.data.Rank;
import com.ianlibanio.prison.module.rankup.messages.RankupMessages;
import com.ianlibanio.prison.module.rankup.service.IRankupService;
import com.ianlibanio.prison.utils.inventory.ClickableItem;
import com.ianlibanio.prison.utils.inventory.SmartInventory;
import com.ianlibanio.prison.utils.inventory.content.InventoryContents;
import com.ianlibanio.prison.utils.inventory.content.InventoryProvider;
import com.ianlibanio.prison.utils.item.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RankInfoInventory implements InventoryProvider {

    private static Rank savedRank;

    private static final IRankupService service = RankupModule.getService();
    private static final IRankupController controller = RankupModule.getController();

    public static SmartInventory getInventory(Rank rank) {
        savedRank = rank;

        return SmartInventory.builder()
                .provider(new RankInfoInventory())
                .size(5, 9)
                .title(RankupMessages.getMessage("rankinfo-inventory-title", rank))
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(0, 4, ClickableItem.empty(
                new Item(Material.ENDER_CHEST)
                        .name(RankupMessages.getMessage("rankinfo-chest-title", savedRank))
                        .lore(RankupMessages.getMessageList("rankinfo-chest-lore", savedRank))
                        .build()
        ));

        contents.set(2, 1, ClickableItem.empty(
                new Item(Material.PAPER)
                        .name(RankupMessages.getMessage("rankinfo-prefix-paper-title", savedRank))
                        .lore(RankupMessages.getMessageList("rankinfo-prefix-paper-lore", savedRank))
                        .build()
        ));

        contents.set(3, 1, ClickableItem.empty(
                new Item(Material.NAME_TAG)
                        .name(RankupMessages.getMessage("rankinfo-nametag-title", savedRank))
                        .lore(RankupMessages.getMessageList("rankinfo-nametag-lore", savedRank))
                        .build()
        ));

        contents.set(2, 3, ClickableItem.empty(
                new Item(Material.PAPER)
                        .name(RankupMessages.getMessage("rankinfo-price-paper-title", savedRank))
                        .lore(RankupMessages.getMessageList("rankinfo-price-paper-lore", savedRank))
                        .build()
        ));

        contents.set(3, 3, ClickableItem.empty(
                new Item(Material.DIAMOND)
                        .name(RankupMessages.getMessage("rankinfo-diamond-title", savedRank))
                        .lore(RankupMessages.getMessageList("rankinfo-diamond-lore", savedRank))
                        .build()
        ));

        contents.set(2, 5, ClickableItem.empty(
                new Item(Material.PAPER)
                        .name(RankupMessages.getMessage("rankinfo-first-paper-title", savedRank))
                        .lore(RankupMessages.getMessageList("rankinfo-first-paper-lore", savedRank))
                        .build()
        ));

        contents.set(3, 5, ClickableItem.empty(
                new Item(Material.WOOL, 1, savedRank.isFirst() ? 5 : 14)
                        .name(RankupMessages.getMessage("rankinfo-first-wool-title", savedRank))
                        .lore(RankupMessages.getMessageList("rankinfo-first-wool-title", savedRank))
                        .build()
        ));

        contents.set(2, 7, ClickableItem.empty(
                new Item(Material.PAPER)
                        .name(RankupMessages.getMessage("rankinfo-last-paper-title", savedRank))
                        .lore(RankupMessages.getMessageList("rankinfo-last-paper-lore", savedRank))
                        .build()
        ));

        contents.set(3, 7, ClickableItem.empty(
                new Item(Material.WOOL, 1, savedRank.isLast() ? 5 : 14)
                        .name(RankupMessages.getMessage("rankinfo-last-wool-title", savedRank))
                        .lore(RankupMessages.getMessageList("rankinfo-last-wool-title", savedRank))
                        .build()
        ));
    }

}
