package com.lenovo.sap.api.model.sap;

import com.lenovo.sap.api.model.request.RequestNo;
import com.lenovo.sap.api.util.ToolBox;
import lombok.Data;

/**
 * @author yuhao5
 * @date 2022/3/12
 **/
@Data
public class BasicJobForm implements RequestNo {
    private String jobreq;
    private String scdocid;
    private String decrp;
    private String jrtype;
    private String requestor
;
    public BasicJobForm(){
        this.jobreq = "";
        this.scdocid = ToolBox.generateNumber("SAPJB",9);
        this.decrp = "";
        this.jrtype = "";
        this.requestor = "ark";
    }

    @Override
    public String requestNo() {
        return this.scdocid;
    }
}
