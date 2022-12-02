package com.lenovo.sap.api.service;

import com.lenovo.sap.api.dao.ApiInfoDao;
import com.lenovo.sap.api.jmodel.entity.ApiInfoPO;
import com.lenovo.sap.api.model.apiinfo.ApiInfoCreateForm;
import com.lenovo.sap.api.model.apiinfo.ApiInfoUpdateForm;
import com.lenovo.xframe.model.AuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yuhao5
 * @date 2022/3/5
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ApiInfoService {
    final ApiInfoDao dao;

    public void create(ApiInfoCreateForm form, AuthInfo authInfo){
        ApiInfoPO po = form.to(new ApiInfoPO());
        po.onCreate(authInfo);
        dao.insert(po);
    }

    public void update(ApiInfoUpdateForm form,AuthInfo authInfo){
        ApiInfoPO po = form.to(new ApiInfoPO());
        po.onUpdate(authInfo);
    }

    public ApiInfoPO getById(Integer id){
        return dao.findById(id);
    }
}
