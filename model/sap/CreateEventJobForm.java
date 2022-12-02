package com.lenovo.sap.api.model.sap;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author yuhao5
 * @date 2022/3/12
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateEventJobForm extends BasicJobForm{
    private List<EventJobItem> jobreqitem;
}
