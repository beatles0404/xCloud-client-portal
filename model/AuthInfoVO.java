package com.lenovo.sap.api.model;

import com.lenovo.xframe.model.AuthInfo;
import com.lenovo.xframe.util.Models;
import lombok.Data;

@Data
public class AuthInfoVO {

    private int orgId;
    private int uid;
    private String clientIp;
    private String userCode;

    public static AuthInfoVO of(AuthInfo source) {
        return Models.map(source, AuthInfoVO::new);
    }
}
