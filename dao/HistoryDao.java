package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.HistoryPO;
import com.lenovo.sap.api.jmodel.record.HistoryRecord;
import com.lenovo.sap.api.jmodel.table.THistory;
import com.lenovo.sap.api.model.history.HistoryStatus;
import com.lenovo.sap.api.model.history.HistoryType;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yuhao5
 * @date 6/9/22
 **/
@Repository
public class HistoryDao extends BaseDao<HistoryRecord, HistoryPO, THistory,Integer> {
    protected HistoryDao(Configuration conf) {
        super(conf);
    }

    public Integer insert(String requestNo, HistoryType type){
        HistoryRecord record = dsl().newRecord(T);
        record.setRequestNo(requestNo);
        record.setType(type);
        record.insert();
        return record.getId();
    }

    public HistoryRecord insertR(HistoryPO po){
        HistoryRecord record = po.toRecord();
        record.attach(getConfiguration());
        record.insert();
        return record;
    }

}
