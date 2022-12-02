package com.lenovo.sap.api.model.sap;

import lombok.Data;

/**
 * @author yuhao5
 * @date 3/17/22
 **/
@Data
public class SearchJobForm {
    private String sysname;
    private String jobname;
    private String status;
    private String jrtype;
    private String requestor;
}
