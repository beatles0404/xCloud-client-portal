package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.AuditPO;
import com.lenovo.sap.api.jmodel.record.AuditRecord;
import com.lenovo.sap.api.jmodel.table.TAudit;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author yuhao5
 * @date 6/9/22
 **/
@Repository
public class AuditDao extends BaseDao<AuditRecord, AuditPO, TAudit,Integer> {
    protected AuditDao(Configuration conf) {
        super(conf);
    }
}
