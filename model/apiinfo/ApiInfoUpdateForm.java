package com.lenovo.sap.api.model.apiinfo;

import com.lenovo.sap.api.jmodel.entity.ApiInfoPO;
import com.lenovo.xframe.util.IModel;
import lombok.Data;

/**
 * @author yuhao5
 * @date 2022/3/5
 **/
@Data
public class ApiInfoUpdateForm implements IModel<ApiInfoPO> {
    private Integer id;
    private String name;
    private String params;
}
