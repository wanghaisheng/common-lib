package com.clear.v2.bin;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.clear.v2.conf.ConfigBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengweisen on 2014/12/17.
 */
public class MySqlSync {

    private static List<ConfigBean> configBean;

    public MySqlSync(List<ConfigBean> configBean) {
        this.configBean = configBean;
        BinaryLogClient client = new BinaryLogClient("192.168.158.94", 3358, "aos2", "aos2");
        client.registerEventListener(new BinaryLogClient.EventListener() {
            @Override
            public void onEvent(Event event) {
                executeEvents(event);
            }
        });
        try {
            client.connect();
        } catch (IOException e) {
            new Exception("Mysql 服务器连接异常", e);
        }
    }

    public static void main(String[] args) {
        new MySqlSync(new ArrayList<ConfigBean>());
    }

    public static void executeEvents(Event event) {
        EventType eventType = event.getHeader().getEventType();
        if (eventType.equals(EventType.QUERY)) {
            /* dml操作 */
        } else if (eventType.equals(EventType.XID)) {
            /* 事务操作 */
        } else if (eventType.equals(EventType.TABLE_MAP)) {
            TableMapEventData tableMapEventData = event.getData();
            long tableId = tableMapEventData.getTableId();
            DitMapdb.getInstance().put(tableId, tableMapEventData);
        } else if (EventType.isDelete(eventType)) {
            DeleteRowsEventData deleteRowsEventData = event.getData();
            MyDeleteRows myDeleteRows = new MyDeleteRows(deleteRowsEventData);
            myDeleteRows.execute(configBean);
        } else if (EventType.isWrite(eventType)) {
            WriteRowsEventData writeRowsEventData = event.getData();
            MyWriteRows myWriteRows = new MyWriteRows(writeRowsEventData);
            myWriteRows.execute(configBean);
        } else if (EventType.isUpdate(eventType)) {
            UpdateRowsEventData updateRowsEventData = event.getData();
            MyUpdateRows myUpdateRows = new MyUpdateRows(updateRowsEventData);
            myUpdateRows.execute(configBean);
        } else {
            /* 其它操作 */
        }
    }

}
