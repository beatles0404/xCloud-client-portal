package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapegpZbctActiveProcPO;
import com.lenovo.sap.api.jmodel.record.SapegpZbctActiveProcRecord;
import com.lenovo.sap.api.jmodel.table.TSapegpZbctActiveProc;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Repository
public class SapegpZbctActiveProcDao extends BaseDao<SapegpZbctActiveProcRecord, SapegpZbctActiveProcPO, TSapegpZbctActiveProc,Integer> {
    protected SapegpZbctActiveProcDao(Configuration conf) {
        super(conf);
    }
}
