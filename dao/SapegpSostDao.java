package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapegpSostPO;
import com.lenovo.sap.api.jmodel.record.SapegpSostRecord;
import com.lenovo.sap.api.jmodel.table.TSapegpSost;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Repository
public class SapegpSostDao extends BaseDao<SapegpSostRecord, SapegpSostPO, TSapegpSost,Integer> {
    protected SapegpSostDao(Configuration conf) {
        super(conf);
    }
}
