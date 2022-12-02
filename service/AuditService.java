package com.lenovo.sap.api.service;

import com.lenovo.sap.api.dao.AuditDao;
import com.lenovo.sap.api.jmodel.entity.AuditPO;
import com.lenovo.sap.api.model.audit.AuditCreateForm;
import com.lenovo.xframe.model.AuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuhao5
 * @date 4/22/22
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AuditService {
    final AuditDao dao;

    public void create(AuditCreateForm form, AuthInfo authInfo){
        AuditPO po = form.to(AuditPO::new);
        po.onCreate(authInfo);
        dao.insert(po);
    }

    public List<AuditPO> list(){
        return dao.listAll();
    }

    public AuditPO get(Integer id){
        return dao.findById(id);
    }


}
