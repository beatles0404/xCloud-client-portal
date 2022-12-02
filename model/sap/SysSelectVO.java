package com.lenovo.sap.api.model.sap;

import lombok.Data;

/**
 * @author yuhao5
 * @date 6/27/22
 **/
@Data
public class SysSelectVO {
    private Integer id;
    private String  name;
    private String  value;
    private String  text;
    private String  remark;
    private Short   seq;
}
