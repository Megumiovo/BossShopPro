package org.black_ixx.bossshop.megumi.managers;

import lombok.Data;

@Data
public class NbtContent {
    private String type;
    private String name;
    private String value;

    public NbtContent(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }
}
