package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.ApiInfoPO;
import com.lenovo.sap.api.jmodel.record.ApiInfoRecord;
import com.lenovo.sap.api.jmodel.table.TApiInfo;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author yuhao5
 * @date 6/9/22
 **/
@Repository
public class ApiInfoDao extends BaseDao<ApiInfoRecord, ApiInfoPO, TApiInfo, Integer> {

    protected ApiInfoDao(Configuration conf) {
        super(conf);
    }
}
