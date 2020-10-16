package com.ianlibanio.prison.module.rankup.service;

import com.ianlibanio.prison.module.rankup.data.Rank;

import java.util.UUID;

public interface IRankupService {

    void load(UUID uuid);
    void saveAll();

    Rank get(UUID uuid);
    void set(UUID uuid, Rank rank);

}
