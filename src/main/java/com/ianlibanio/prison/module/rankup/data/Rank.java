package com.ianlibanio.prison.module.rankup.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rank {

    private String name, prefix;
    private double price;
    private int position;

    private boolean first, last;

}
