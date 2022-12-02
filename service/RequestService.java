package com.lenovo.sap.api.service;

import com.lenovo.sap.api.dao.RequestDao;
import com.lenovo.sap.api.jmodel.entity.RequestPO;
import com.lenovo.sap.api.jmodel.record.HistoryRecord;
import com.lenovo.sap.api.model.history.HistoryStatus;
import com.lenovo.sap.api.model.history.HistoryType;
import com.lenovo.sap.api.model.history.HistoryVO;
import com.lenovo.sap.api.model.request.RequestCreateForm;
import com.lenovo.sap.api.model.request.RequestSearchForm;
import com.lenovo.sap.api.model.request.RequestStatus;
import com.lenovo.xframe.model.AuthInfo;
import com.lenovo.xframe.model.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.JSONB;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuhao5
 * @date 6/15/22
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class RequestService {
    private final RequestDao dao;
    private final HistoryService historyService;

    public RequestPO create(RequestCreateForm form, AuthInfo authInfo){
        RequestPO po = form.to(RequestPO::new);
        po.onCreate(authInfo);
        return dao.insertR(po);
    }

    public RequestPO create(String requestNo, HistoryType type, RequestStatus status, HistoryVO params,
                            AuthInfo authInfo){
        RequestCreateForm form = new RequestCreateForm();
        form.setRequestNo(requestNo);
        form.setType(type);
        form.setStatus(status);
        form.setRequester(authInfo.getUserCode());
        form.setParams(params);
        return create(form,authInfo);
    }

    public RequestPO create(String requestNo, HistoryType type, JSONB historyParams,@Nullable JSONB historyResult, AuthInfo authInfo){
        HistoryRecord historyRecord = historyService.create(type, requestNo, HistoryStatus.PROCESS, historyParams,
                historyResult, authInfo);
        HistoryVO historyVO = new HistoryVO();
        BeanUtils.copyProperties(historyRecord,historyVO);
        return create(requestNo,type,RequestStatus.PROCESSING,historyVO,authInfo);
    }

    public RequestPO create(String requestNo, HistoryType type, JSONB historyParams, AuthInfo authInfo){
        return create(requestNo, type, historyParams, null, authInfo);
    }

    public void update(RequestPO po,HistoryStatus status,JSONB result,AuthInfo authInfo){
        List<Integer> historyIds = po.getParams().stream().peek(historyVO -> {
            historyVO.setStatus(status);
        }).map(HistoryVO::getId).collect(Collectors.toList());
        historyService.update(historyIds,status,result,authInfo);
        switch (status){
            case FAILED:
                po.setStatus(RequestStatus.ERROR_HANDLING);
                break;
            case SUCCESS:
                po.setStatus(RequestStatus.CLOSED);
                break;
            default:
                return;
        }
        dao.update(po);
    }



    public PageResult<RequestPO> search(RequestSearchForm form, Pageable pageable){
        return dao.fetchPage(pageable,form);
    }

    @Nullable
    public RequestPO get(Integer id){
        return dao.findById(id);
    }


}
