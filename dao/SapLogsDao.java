package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapLogsPO;
import com.lenovo.sap.api.jmodel.record.SapLogsRecord;
import com.lenovo.sap.api.jmodel.table.TSapLogs;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Repository
public class SapLogsDao extends BaseDao<SapLogsRecord, SapLogsPO, TSapLogs,Integer> {
    protected SapLogsDao(Configuration conf) {
        super(conf);
    }
}
