package com.ianlibanio.prison.module.rankup.controller;

import com.ianlibanio.prison.module.rankup.data.Rank;

import java.util.List;

public interface IRankupController {

    void load();

    Rank get(String name);
    Rank get(int position);
    String ranks();

    List<Rank> all();

}
