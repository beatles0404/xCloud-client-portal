package com.lenovo.sap.api.model.sap;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author yuhao5
 * @date 2022/3/7
 **/
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class PeriodicJobItem extends BasicJobReqItem{
    private String sdlstrtdt;
    private String sdlstrttm;
    private String laststrtdt;
    private String laststrttm;

    public PeriodicJobItem(){
        super();
        this.sdlstrtdt = "";
        this.sdlstrttm = "";
        this.laststrtdt = "";
        this.laststrttm = "";
    }

    public void setSdlstrtdt(String sdlstrtdt) {
        this.sdlstrtdt = sdlstrtdt == null ? "" : sdlstrtdt;
    }

    public void setSdlstrttm(String sdlstrttm) {
        this.sdlstrttm = sdlstrttm == null ? "" : sdlstrttm;
    }

    public void setLaststrtdt(String laststrtdt) {
        this.laststrtdt = laststrtdt == null ? "" : laststrtdt;
    }

    public void setLaststrttm(String laststrttm) {
        this.laststrttm = laststrttm == null ? "" : laststrttm;
    }

}
