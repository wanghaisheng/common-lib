package com.clear.v3.conf;

/**
 * Created by chengweisen on 2015/1/22.
 */
public enum SyncType {

    Mysql(0),

    Redis(1);

    private int types;

    public int getTypes() {
        return types;
    }

    private SyncType(int types) {
        this.types = types;
    }
}
