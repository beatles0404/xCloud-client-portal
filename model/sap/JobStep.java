package com.lenovo.sap.api.model.sap;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Locale;

/**
 * @author yuhao5
 * @date 2022/3/7
 **/
@Getter
@ToString
@EqualsAndHashCode
public class JobStep {
    private Integer stepcount;
    private String programs;
    private String variant;
    private String authname;
    private String language;

    public JobStep(){
        this.stepcount = 1;
        this.programs = "";
        this.variant = "";
        this.authname = "";
        this.language = "";
    }

    public void setStepcount(Integer stepcount) {
        this.stepcount = stepcount == null ? 1 : stepcount;
    }

    public void setPrograms(String programs) {
        this.programs = programs == null ? "" : programs;
    }

    public void setVariant(String variant) {
        this.variant = variant == null ? "" : variant;
    }

    public void setAuthname(String authname) {
        this.authname = authname == null ? "" : authname.toUpperCase(Locale.ROOT);
    }

    public void setLanguage(String language) {
        this.language = language == null ? "" : language;
    }
}
