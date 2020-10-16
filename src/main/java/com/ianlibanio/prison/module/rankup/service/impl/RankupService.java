package com.ianlibanio.prison.module.rankup.service.impl;

import com.google.common.collect.Maps;
import com.ianlibanio.prison.PrisonCore;
import com.ianlibanio.prison.module.rankup.RankupModule;
import com.ianlibanio.prison.module.rankup.data.Rank;
import com.ianlibanio.prison.module.rankup.service.IRankupService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class RankupService implements IRankupService {

    private final HashMap<UUID, Rank> playerRanks = Maps.newHashMap();

    @Override
    public void load(UUID uuid) {
        PrisonCore.getHikariConnection().query("SELECT * FROM `ranks` WHERE uuid=?", (statement) -> {
            try {
                statement.setString(1, uuid.toString());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            PrisonCore.getHikariConnection().result(statement, resultSet -> {
                try {
                    if (!resultSet.next()) {
                        set(uuid, RankupModule.getController().get(1));
                        return;
                    }

                    playerRanks.put(uuid, RankupModule.getController().get(resultSet.getString("rank")));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @Override
    public void saveAll() {
        PrisonCore.getHikariConnection().query("INSERT INTO ranks(uuid, rank) VALUES(?, ?) ON DUPLICATE KEY UPDATE rank=?", (statement) -> {
            playerRanks.forEach(((uuid, rank) -> {
                try {
                    statement.setString(1, uuid.toString());
                    statement.setString(2, rank.getName());
                    statement.setString(3, rank.getName());
                    statement.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }));

            try {
                statement.executeBatch();
                statement.clearBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Rank get(UUID uuid) {
        return playerRanks.get(uuid);
    }

    @Override
    public void set(UUID uuid, Rank rank) {
        playerRanks.put(uuid, rank);
    }
}
