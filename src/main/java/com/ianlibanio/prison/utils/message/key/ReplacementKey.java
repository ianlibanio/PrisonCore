package com.ianlibanio.prison.utils.message.key;

import lombok.Data;

@Data
public class ReplacementKey {

    private final String target, replacement;

    public ReplacementKey(String target, Object replacement) {
        this.target = "{" + target + "}";
        this.replacement = replacement.toString();
    }

}
