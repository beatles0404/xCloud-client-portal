package com.lenovo.sap.api.model.audit;

import com.lenovo.sap.api.jmodel.entity.AuditPO;
import com.lenovo.xframe.util.IModel;
import lombok.Data;

/**
 * @author yuhao5
 * @date 4/25/22
 **/
@Data
public class AuditCreateForm implements IModel<AuditPO> {
    private Option optionType;
    private Status status;
    private String requestParams;
    private String resultParams;
}
