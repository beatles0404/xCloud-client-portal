package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapSlogsPO;
import com.lenovo.sap.api.jmodel.record.SapSlogsRecord;
import com.lenovo.sap.api.jmodel.table.TSapSlogs;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Repository
public class SapSlogsDao extends BaseDao<SapSlogsRecord, SapSlogsPO, TSapSlogs,Integer> {
    protected SapSlogsDao(Configuration conf) {
        super(conf);
    }
}
