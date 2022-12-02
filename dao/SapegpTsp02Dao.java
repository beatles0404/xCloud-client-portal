package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapegpTsp02PO;
import com.lenovo.sap.api.jmodel.record.SapegpTsp02Record;
import com.lenovo.sap.api.jmodel.table.TSapegpTsp02;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Repository
public class SapegpTsp02Dao extends BaseDao<SapegpTsp02Record, SapegpTsp02PO, TSapegpTsp02,Integer> {
    protected SapegpTsp02Dao(Configuration conf) {
        super(conf);
    }
}
