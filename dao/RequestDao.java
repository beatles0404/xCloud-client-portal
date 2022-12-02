package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.RequestPO;
import com.lenovo.sap.api.jmodel.record.RequestRecord;
import com.lenovo.sap.api.jmodel.table.TRequest;
import com.lenovo.sap.api.model.request.RequestStatus;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

/**
 * @author yuhao5
 * @date 6/15/22
 **/
@Repository
public class RequestDao extends BaseDao<RequestRecord, RequestPO, TRequest,Integer> {

    protected RequestDao(Configuration conf) {
        super(conf);
    }

    public RequestPO insertR(RequestPO po){
        insert(po);
        return po;
    }

    public void update(Integer id, RequestStatus status){
        updateFieldBy(T.STATUS,status,T.ID.eq(id));
    }

    public void update(String requestNo,RequestStatus status){
        updateFieldBy(T.STATUS,status,T.REQUEST_NO.eq(requestNo));
    }
}
