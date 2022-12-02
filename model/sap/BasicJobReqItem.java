package com.lenovo.sap.api.model.sap;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * @author yuhao5
 * @date 2022/3/12
 **/
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class BasicJobReqItem extends SimpleJobReqItem{

    private String periodic; // X or null
    private String imstrtpos;
    private String prdmins;
    private String prdhours;
    private String prddays;
    private String prdweeks;
    private String prdmonths;
    private String recipient;
    private String rec_type;
    private List<JobStep> jobsteps;

    public BasicJobReqItem(){
        this.periodic = "";
        this.imstrtpos = "";
        this.prdmins = "";
        this.prdhours = "";
        this.prdweeks = "";
        this.prdmonths = "";
        this.recipient = "";
        this.rec_type = "";
        this.jobsteps = Collections.singletonList(new JobStep());
    }


    public void setPeriodic(String periodic) {
        this.periodic = periodic == null ? "" : periodic;
    }

    public void setImstrtpos(String imstrtpos) {
        this.imstrtpos = imstrtpos == null ? "" : imstrtpos;
    }

    public void setPrdmins(String prdmins) {
        this.prdmins = prdmins == null ? "" : prdmins;
    }

    public void setPrdhours(String prdhours) {
        this.prdhours = prdhours == null ? "" : prdhours;
    }

    public void setPrddays(String prddays) {
        this.prddays = prddays == null ? "" : prddays;
    }

    public void setPrdweeks(String prdweeks) {
        this.prdweeks = prdweeks == null ? "" : prdweeks;
    }

    public void setPrdmonths(String prdmonths) {
        this.prdmonths = prdmonths == null ? "" : prdmonths;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient == null ? "" : recipient;
    }

    public void setRec_type(String rec_type) {
        this.rec_type = rec_type == null ? "" : rec_type;
    }

    public void setJobsteps(List<JobStep> jobsteps) {
        this.jobsteps = jobsteps;
    }
}
