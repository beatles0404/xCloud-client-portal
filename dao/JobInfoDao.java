package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.JobInfoPO;
import com.lenovo.sap.api.jmodel.record.JobInfoRecord;
import com.lenovo.sap.api.jmodel.table.TJobInfo;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author yuhao5
 * @date 6/9/22
 **/
@Repository
public class JobInfoDao extends BaseDao<JobInfoRecord, JobInfoPO, TJobInfo,Integer> {
    protected JobInfoDao(Configuration conf) {
        super(conf);
    }

    public void deleteByName(String name){
        deleteBy(T.NAME.eq(name));
    }
}
