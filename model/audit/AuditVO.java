package com.lenovo.sap.api.model.audit;

import lombok.Data;
import org.jooq.JSONB;

import java.time.Instant;

/**
 * @author yuhao5
 * @date 6/27/22
 **/
@Data
public class AuditVO {
    private Integer id;
    private Option  optionType;
    private Status  status;
    private String requestParams;
    private String  createdBy;
    private Instant createdAt;
    private String  updatedBy;
    private Instant updatedAt;
    private String   resultParams;
}
