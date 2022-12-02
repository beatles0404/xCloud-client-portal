package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapGetParameterValuePO;
import com.lenovo.sap.api.jmodel.record.SapGetParameterValueRecord;
import com.lenovo.sap.api.jmodel.table.TSapGetParameterValue;
import com.lenovo.xframe.orm.jooq.BaseDao;
import jdk.jfr.Registered;
import org.jooq.Configuration;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Registered
public class SapGetParameterValueDao extends BaseDao<SapGetParameterValueRecord, SapGetParameterValuePO, TSapGetParameterValue,Integer> {
    protected SapGetParameterValueDao(Configuration conf) {
        super(conf);
    }
}
