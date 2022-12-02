package com.lenovo.sap.api.dao;


import com.lenovo.sap.api.jmodel.entity.SapegpSdfMonHeaderPO;
import com.lenovo.sap.api.jmodel.record.SapegpSdfMonHeaderRecord;
import com.lenovo.sap.api.jmodel.table.TSapegpSdfMonHeader;
import com.lenovo.xframe.orm.jooq.BaseDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author houzh5
 * @date 2022/6/15
 */
@Repository
public class SapegpSdfMonHeaderDao extends BaseDao<SapegpSdfMonHeaderRecord, SapegpSdfMonHeaderPO, TSapegpSdfMonHeader,Integer> {

    protected SapegpSdfMonHeaderDao(Configuration conf) {
        super(conf);
    }

}
