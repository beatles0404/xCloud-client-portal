package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapegpZbctSto3ndPO;
import com.lenovo.sap.api.jmodel.record.SapegpZbctSto3ndRecord;
import com.lenovo.sap.api.jmodel.table.TSapegpZbctSto3nd;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Repository
public class SapegpZbctSto3ndDao extends BaseDao<SapegpZbctSto3ndRecord, SapegpZbctSto3ndPO, TSapegpZbctSto3nd,Integer> {
    protected SapegpZbctSto3ndDao(Configuration conf) {
        super(conf);
    }
}
