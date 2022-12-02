package com.lenovo.sap.api.model.hana;

import com.lenovo.sap.api.model.request.RequestNo;
import com.lenovo.sap.api.util.ToolBox;
import lombok.Data;

import java.util.List;

/**
 * @author yuhao5
 * @date 4/12/22
 **/
@Data
public class CreateAccountForm implements RequestNo {
    private String requestId;
    private String userName;
    private String sidName;
    private String envName;
    private List<String> role;
    private String expireDate;

    public CreateAccountForm(){
        this.requestId = ToolBox.generateNumber("HANACA",8);
    }

    @Override
    public String requestNo() {
        return requestId;
    }
}
