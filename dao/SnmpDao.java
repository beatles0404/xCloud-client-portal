package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.SnmpPO;
import com.lenovo.sap.api.jmodel.record.SnmpRecord;
import com.lenovo.sap.api.jmodel.table.TSnmp;
import com.lenovo.sap.api.model.monitor.SnmpVO;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author houzh5
 * @date 2022/6/21
 */
@Repository
public class SnmpDao extends BaseDao<SnmpRecord, SnmpPO, TSnmp,Integer> {
    protected SnmpDao(Configuration conf) {
        super(conf);
    }

    public List<SnmpVO> getSnmpMoType(){
        return dsl().select(T.R3MAIALERTMOTYPE.as("MoTypeName"),DSL.count(T.R3MAIALERTMOTYPE).as("sum")).from(T).groupBy(T.R3MAIALERTMOTYPE).fetchInto(SnmpVO.class);
    }
}
