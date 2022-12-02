package com.lenovo.sap.api.model.history;

import lombok.Data;
import org.jooq.JSONB;

/**
 * @author yuhao5
 * @date 6/15/22
 **/
@Data
public class HistoryVO {
    private Integer       id;
    private HistoryType   type;
    private String        requestNo;
    private HistoryStatus status;
    private JSONB params;
}
