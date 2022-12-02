package com.lenovo.sap.api.dao;

import com.lenovo.sap.api.jmodel.entity.UserInfoPO;
import com.lenovo.sap.api.jmodel.record.UserInfoRecord;
import com.lenovo.sap.api.jmodel.table.TUserInfo;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDao extends BaseDao<UserInfoRecord, UserInfoPO, TUserInfo, Integer> {
    protected UserInfoDao(Configuration conf) {
        super(conf);
    }

    public void deleteByUsername(String username){
        deleteBy(T.USERNAME.eq(username));
    }

}
