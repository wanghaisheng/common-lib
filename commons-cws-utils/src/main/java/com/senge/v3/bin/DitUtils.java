package com.clear.v3.bin;

import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import org.mapdb.DBMaker;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Created by chengweisen on 2014/12/30.
 */
public final class DitUtils {

    private static DitUtils ourInstance = new DitUtils();

    private static ConcurrentNavigableMap<Long, TableMapEventData> map;

    private DitUtils() {
        String configBasedir = System.getProperty("os.base");
        String tableTemps = configBasedir + "/conf/table_temp.db";
        DBMaker dbMaker = DBMaker.newFileDB(new File(tableTemps));
        dbMaker.closeOnJvmShutdown();
        dbMaker.deleteFilesAfterClose();
        map = dbMaker.make().getTreeMap("clear");
    }

    public static void put(Long tableId, TableMapEventData value) {
        if (!map.containsKey(tableId)) {
            map.put(tableId, value);
        }
    }

    public static TableMapEventData get(Long tableId) {
        return map.get(tableId);
    }
}
