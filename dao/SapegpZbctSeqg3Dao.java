package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapegpZbctSeqg3PO;
import com.lenovo.sap.api.jmodel.record.SapegpZbctSeqg3Record;
import com.lenovo.sap.api.jmodel.table.TSapegpZbctSeqg3;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;

/**
 * @author houzh5
 * @date 2022/6/21
 */
public class SapegpZbctSeqg3Dao extends BaseDao<SapegpZbctSeqg3Record, SapegpZbctSeqg3PO, TSapegpZbctSeqg3,Integer> {
    protected SapegpZbctSeqg3Dao(Configuration conf) {
        super(conf);
    }
}
