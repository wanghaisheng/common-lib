package com.clear.v3.bin;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.clear.v3.conf.BinInfoBean;
import com.clear.v3.conf.ConfigBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chengweisen on 2014/12/17.
 */
public class MySqlBinRecives {

    private static List<ConfigBean> configBean;

    public MySqlBinRecives(List<ConfigBean> configBean) {
        this.configBean = configBean;
        Map<String, BinInfoBean> binInfoBeanMap = getBinHost(configBean);
        BinaryLogClient client;
        for (BinInfoBean binInfoBean : binInfoBeanMap.values()) {
            String hostname = binInfoBean.getHostname();
            Integer port = binInfoBean.getPort();
            String username = binInfoBean.getUsername();
            String password = binInfoBean.getPassword();
            client = new BinaryLogClient(hostname, port, username, password);
            client.registerEventListener(new DateEventListener());
            try {
                client.connect();
            } catch (IOException e) {
                new Exception("Mysql 服务器连接异常", e);
            }
        }
    }

    public static void main(String[] args) {
        new MySqlBinRecives(new ArrayList<ConfigBean>());
    }

    class DateEventListener implements BinaryLogClient.EventListener {
        @Override
        public void onEvent(Event event) {
            executeEvents(event);
        }
    }

    /**
     * 支持多源一目标的功能
     *
     * @param configBean
     * @return
     */
    private Map<String, BinInfoBean> getBinHost(List<ConfigBean> configBean) {
        Map<String, BinInfoBean> binInfoBeanMap = new HashMap<String, BinInfoBean>();
        BinInfoBean binInfoBean;
        Pattern pattern = Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}:\\d{1,4}");
        for (ConfigBean cb : configBean) {
            Matcher matcher = pattern.matcher(cb.getUrl());
            if (matcher.find()) {
                binInfoBean = new BinInfoBean();
                String[] split = matcher.group().split(":");
                binInfoBean.setHostname(split[0]);
                binInfoBean.setPort(Integer.parseInt(split[1]));
                binInfoBean.setPassword(cb.getPassword());
                binInfoBean.setUsername(cb.getUsername());
                binInfoBeanMap.put(matcher.group(), binInfoBean);
            }
        }
        return binInfoBeanMap;
    }

    private void executeEvents(Event event) {
        EventType eventType = event.getHeader().getEventType();
        if (eventType.equals(EventType.QUERY)) {
            QueryEventData queryEventData = event.getData();
            MyddlOperate myddlOperate = new MyddlOperate(queryEventData);
            myddlOperate.execute(configBean);
        } else if (eventType.equals(EventType.XID)) {
            /* --------------------------- */
        } else if (eventType.equals(EventType.TABLE_MAP)) {
            TableMapEventData tableMapEventData = event.getData();
            long tableId = tableMapEventData.getTableId();
            DitUtils.put(tableId, tableMapEventData);
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

        }
    }

}
