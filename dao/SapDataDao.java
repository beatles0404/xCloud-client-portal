package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapDataPO;
import com.lenovo.sap.api.jmodel.record.SapDataRecord;
import com.lenovo.sap.api.jmodel.table.TSapData;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Repository
public class SapDataDao extends BaseDao<SapDataRecord, SapDataPO, TSapData,Integer> {

    protected SapDataDao(Configuration conf) {
        super(conf);
    }
}
