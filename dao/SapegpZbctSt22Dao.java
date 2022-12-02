package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapegpZbctSt22PO;
import com.lenovo.sap.api.jmodel.record.SapegpZbctSt22Record;
import com.lenovo.sap.api.jmodel.table.TSapegpZbctSt22;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Repository
public class SapegpZbctSt22Dao extends BaseDao<SapegpZbctSt22Record, SapegpZbctSt22PO, TSapegpZbctSt22,Integer> {
    protected SapegpZbctSt22Dao(Configuration conf) {
        super(conf);
    }
}
