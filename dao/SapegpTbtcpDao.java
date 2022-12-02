package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SapegpTbtcpPO;
import com.lenovo.sap.api.jmodel.record.SapegpTbtcpRecord;
import com.lenovo.sap.api.jmodel.table.TSapegpTbtcp;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Repository
public class SapegpTbtcpDao extends BaseDao<SapegpTbtcpRecord, SapegpTbtcpPO, TSapegpTbtcp,Integer> {
    protected SapegpTbtcpDao(Configuration conf) {
        super(conf);
    }
}
