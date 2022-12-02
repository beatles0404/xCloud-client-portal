package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SysSelectPO;
import com.lenovo.sap.api.jmodel.record.SysSelectRecord;
import com.lenovo.sap.api.jmodel.table.TSysSelect;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author yuhao5
 * @date 6/9/22
 **/
@Repository
public class SysSelectDao extends BaseDao<SysSelectRecord, SysSelectPO, TSysSelect,Integer> {
    protected SysSelectDao(Configuration conf) {
        super(conf);
    }
}
