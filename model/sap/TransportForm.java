package com.lenovo.sap.api.model.sap;

import com.lenovo.sap.api.model.request.RequestNo;
import com.lenovo.sap.api.util.ToolBox;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuhao5
 * @date 3/30/22
 **/
@Data
public class TransportForm implements RequestNo {
    private String scdocid;
    private String appl;
    private String landscape;
    private String sysname;
    private List<String> trkorrs;
    private String category;

    public TransportForm(){
        this.scdocid = ToolBox.generateNumber("SAPTR",9);
        this.appl = "";
        this.landscape = "";
        this.sysname = "";
        this.trkorrs = new ArrayList<>();
        this.category = "";
    }

    @Override
    public String requestNo() {
        return this.scdocid;
    }
}
