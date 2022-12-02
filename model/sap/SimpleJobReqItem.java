package com.lenovo.sap.api.model.sap;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Locale;

/**
 * @author yuhao5
 * @date 3/16/22
 **/
@Getter
@ToString
@EqualsAndHashCode
public class SimpleJobReqItem {
    private String itemno;
    private String sysname;
    private String jobname;

    public SimpleJobReqItem(){
        this.itemno = "";
        this.sysname = "";
        this.jobname = "";
    }

    public void setItemno(String itemno) {
        this.itemno = itemno == null ? "" : itemno;
    }

    public void setSysname(String sysname) {
        this.sysname = sysname == null ? "" : sysname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname == null ? "" : jobname.toUpperCase(Locale.ROOT);
    }
}
