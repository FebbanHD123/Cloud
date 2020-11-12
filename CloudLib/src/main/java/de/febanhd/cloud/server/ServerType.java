package de.febanhd.cloud.server;

import lombok.Getter;

public enum ServerType {

    PROXY(0), MINECRAFT(1);

    @Getter
    private int id;

    ServerType(int id) {
        this.id = id;
    }

    public static ServerType fromID(int id) {
        for (ServerType value : values()) {
            if(value.getId() == id) return value;
        }
        return null;
    }

}
