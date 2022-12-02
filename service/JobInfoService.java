package com.lenovo.sap.api.service;

import com.lenovo.sap.api.dao.JobInfoDao;
import com.lenovo.sap.api.jmodel.entity.JobInfoPO;
import com.lenovo.xframe.model.AuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

/**
 * @author yuhao5
 * @date 3/17/22
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class JobInfoService {
    final JobInfoDao dao;

    public void create(String jobName, String params, String type, AuthInfo authInfo){
        JobInfoPO po = new JobInfoPO();
        po.setName(jobName);
        po.setParams(params);
        po.setType(type);
        po.onCreate(authInfo);
        dao.insert(po);
    }

    public void delete(String name){
        dao.deleteByName(name);
    }
}
