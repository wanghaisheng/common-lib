package com.clear.v2.bin;

import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Created by chengweisen on 2014/12/30.
 */
public class DitMapdb {

    private static DitMapdb ourInstance = new DitMapdb();

    private ConcurrentNavigableMap<Long, TableMapEventData> map = null;

    public static DitMapdb getInstance() {
        return ourInstance;
    }

    private DitMapdb() {
        DB db = DBMaker.newFileDB(new File("d:/testdb.db"))
                .closeOnJvmShutdown()
                .deleteFilesAfterClose()
                .encryptionEnable("password")
                .make();
        map = db.getTreeMap("clear");
    }

    public void put(Long tableId, TableMapEventData value) {
        if (!map.containsKey(tableId)) {
            map.put(tableId, value);
        }
    }

    public TableMapEventData get(Long tableId) {
        return map.get(tableId);
    }
}
