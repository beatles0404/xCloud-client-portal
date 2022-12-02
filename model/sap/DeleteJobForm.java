package com.lenovo.sap.api.model.sap;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author yuhao5
 * @date 3/16/22
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteJobForm extends BasicJobForm{
    private List<SimpleJobReqItem> jobreqitem;
}
