package com.lenovo.sap.api.model.sap;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author yuhao5
 * @date 3/18/22
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ChangeStatusForm extends BasicJobForm{
    private String jroper;
    private List<SimpleJobReqItem> jobReqItem;
}
