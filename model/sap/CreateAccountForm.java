package com.lenovo.sap.api.model.sap;

import com.lenovo.sap.api.model.request.RequestNo;
import com.lenovo.sap.api.util.ToolBox;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yuhao5
 * @date 3/23/22
 **/
@Getter
@ToString
@EqualsAndHashCode
public class CreateAccountForm implements RequestNo {
    private String requestor;
    private String appl;
    private String sysid;
    private String client;
    private String uname;
    private String zreset;
    private String referuname;
    private String category;
    private String applyuser;
    private String acttype;
    private String status;


    public CreateAccountForm(){
        this.requestor = ToolBox.generateNumber("SAPCA",9);
        this.appl = "";
        this.sysid = "";
        this.client = "";
        this.uname = "";
        this.referuname = "";
        this.category = "";
        this.applyuser = "";
        this.acttype = "";
        this.status = "";
        this.zreset = "";
    }

    public void setRequestor(String requestor) {
        this.requestor = StringUtils.isBlank(requestor) ? "" : requestor;
    }

    public void setAppl(String appl) {
        this.appl = StringUtils.isBlank(appl) ? "" : appl;
    }

    public void setSysid(String sysid) {
        this.sysid = StringUtils.isBlank(sysid) ? "" : sysid;
    }

    public void setClient(String client) {
        this.client = StringUtils.isBlank(client) ? "" : client;
    }

    public void setUname(String uname) {
        this.uname = StringUtils.isBlank(uname) ? "" : uname;
    }

    public void setReferuname(String referuname) {
        this.referuname = StringUtils.isBlank(referuname) ? "" : referuname;
    }

    public void setCategory(String category) {
        this.category = StringUtils.isBlank(category) ? "" : category;
    }

    public void setApplyuser(String applyuser) {
        this.applyuser = StringUtils.isBlank(applyuser) ? "" : applyuser;
    }

    public void setActtype(String acttype) {
        this.acttype = StringUtils.isBlank(acttype) ? "" : acttype;
    }

    public void setStatus(String status) {
        this.status = StringUtils.isBlank(status) ? "" : status;
    }

    public void setZreset(String zreset) {
        this.zreset = StringUtils.isBlank(zreset) ? "" : zreset;
    }

    @Override
    public String requestNo() {
        return this.requestor;
    }
}
