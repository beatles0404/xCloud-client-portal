package com.lenovo.sap.api.model.sap;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

/**
 * @author yuhao5
 * @date 2022/3/7
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class CreatePeriodicJobForm extends BasicJobForm{
    private List<PeriodicJobItem> jobreqitem;

    public CreatePeriodicJobForm(){
        super();
        this.jobreqitem = Collections.singletonList(new PeriodicJobItem());
    }
}
