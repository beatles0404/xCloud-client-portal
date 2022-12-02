package com.lenovo.sap.api.model.request;

import com.lenovo.sap.api.jmodel.entity.RequestPO;
import com.lenovo.sap.api.model.history.HistoryType;
import com.lenovo.sap.api.model.history.HistoryVO;
import com.lenovo.xframe.util.IModel;
import lombok.Data;

/**
 * @author yuhao5
 * @date 6/15/22
 **/
@Data
public class RequestCreateForm implements IModel<RequestPO> {
    private String        requestNo;
    private HistoryType   type;
    private RequestStatus status;
    private String        requester;
    private HistoryVO     params;
}
