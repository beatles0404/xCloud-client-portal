package com.lenovo.sap.api.model.history;

import com.lenovo.sap.api.jmodel.entity.HistoryPO;
import com.lenovo.xframe.util.IModel;
import lombok.Data;
import org.jooq.JSONB;

import java.time.Instant;

/**
 * @author yuhao5
 * @date 6/15/22
 **/
@Data
public class HistoryCreateForm implements IModel<HistoryPO> {
    private HistoryType   type;
    private String        requestNo;
    private HistoryStatus status;
    private JSONB         params;
    private JSONB         result;
}
