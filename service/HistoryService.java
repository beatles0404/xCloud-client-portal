package com.lenovo.sap.api.service;

import com.lenovo.sap.api.dao.HistoryDao;
import com.lenovo.sap.api.jmodel.entity.HistoryPO;
import com.lenovo.sap.api.jmodel.record.HistoryRecord;
import com.lenovo.sap.api.model.history.HistoryCreateForm;
import com.lenovo.sap.api.model.history.HistorySearchForm;
import com.lenovo.sap.api.model.history.HistoryStatus;
import com.lenovo.sap.api.model.history.HistoryType;
import com.lenovo.xframe.model.AuthInfo;
import com.lenovo.xframe.model.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.JSONB;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuhao5
 * @date 2022/3/11
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class HistoryService {
    final HistoryDao dao;

    public Integer insert(String reqNo, HistoryType type){
        return dao.insert(reqNo,type);
    }

    public HistoryRecord create(HistoryCreateForm form, AuthInfo authInfo){
        HistoryPO po = form.to(HistoryPO::new);
        po.onCreate(authInfo);
        return dao.insertR(po);
    }

    public HistoryRecord create(HistoryType type, String requestNo, HistoryStatus status, JSONB params, @Nullable JSONB result, AuthInfo authInfo){
        HistoryCreateForm form = new HistoryCreateForm();
        form.setType(type);
        form.setRequestNo(requestNo);
        form.setStatus(status);
        form.setParams(params);
        form.setResult(result);
        return create(form,authInfo);
    }

    public void update(List<Integer> ids, HistoryStatus status,JSONB result, AuthInfo authInfo){
        List<HistoryPO> pos = dao.listByIds(ids);
        pos.forEach(po -> {
            po.setStatus(status);
            po.setResult(result);
            po.onUpdate(authInfo);
        });
        dao.update(pos);
    }

    public HistoryPO get(Integer id){
        return dao.findById(id);
    }

    public PageResult<HistoryPO> search(HistorySearchForm form, Pageable pageable){
        return dao.fetchPage(pageable,form);
    }


}
