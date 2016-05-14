package com.clear.v3.bin;

import com.clear.v3.conf.ConfigBean;
import org.apache.commons.lang.ArrayUtils;

import java.util.List;

/**
 * Created by chengweisen on 2014/12/30.
 */
public abstract class ExecuteDml {

    public abstract void execute(List<ConfigBean> configBean);

    /**
     * 表是否存在
     *
     * @param tableName
     * @return
     */
    public boolean tableExit(String tableName, List<ConfigBean> configBean) {
        boolean flag = false;
        for (ConfigBean conben : configBean) {
            String[] tables = conben.getTables();
            flag = ArrayUtils.contains(tables, tableName);
            if (flag) {
                break;
            }
        }
        return flag;
    }

    public String formateTableName(String tableName) {
        return String.format("%-40s", tableName);
    }

}
