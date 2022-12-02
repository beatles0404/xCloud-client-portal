package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapegpTbtcoPO;
import com.lenovo.sap.api.jmodel.record.SapegpTbtcoRecord;
import com.lenovo.sap.api.jmodel.table.TSapegpTbtco;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Repository
public class SapegpTbtcoDao extends BaseDao<SapegpTbtcoRecord, SapegpTbtcoPO, TSapegpTbtco,Integer> {
    protected SapegpTbtcoDao(Configuration conf) {
        super(conf);
    }
}
