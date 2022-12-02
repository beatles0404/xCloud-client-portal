package com.lenovo.sap.api.model.apiinfo;

import lombok.Data;

import java.time.Instant;

/**
 * @author yuhao5
 * @date 2022/3/5
 **/
@Data
public class ApiInfoVO {
    private Integer id;
    private String name;
    private String params;
    private String created_by;
    private Instant created_at;
    private String updated_by;
    private Instant updated_at;
}
