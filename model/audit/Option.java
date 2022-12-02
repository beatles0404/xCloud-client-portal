package com.lenovo.sap.api.model.audit;

import com.lenovo.xframe.model.Valued;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.lenovo.sap.api.model.audit.Contents.HANA;
import static com.lenovo.sap.api.model.audit.Contents.SAP;


/**
 * @author yuhao5
 * @date 4/21/22
 **/
@Getter
@RequiredArgsConstructor
public enum Option implements Valued<Integer> {
    SAP_CREATE_JOB(1,SAP,"createJob","com.lenovo.sapapi.model.sap.CreatePeriodicJobForm"),
    SAP_DELETE_JOB(2,SAP,"deleteJob","com.lenovo.sapapi.model.sap.DeleteJobForm"),
    SAP_CHANGE_JOB(3,SAP,"changeJob","com.lenovo.sapapi.model.sap.ChangeJobForm"),
    SAP_CHANGE_JOB_STATUS(4,SAP,"changeStatus","com.lenovo.sapapi.model.sap.ChangeStatusForm"),
    SAP_CREATE_ACCOUNT(5,SAP,"account","com.lenovo.sapapi.model.sap.CreateAccountForm"),
    SAP_TRANSPORT(6,SAP,"transport","com.lenovo.sapapi.model.sap.TransportForm"),
    HANA_CHECK_USER(7,HANA,"checkUser","com.lenovo.sapapi.model.hana.UserCheckForm"),
    HANA_CREATE_ACCOUNT(8,HANA,"createAccount","com.lenovo.sapapi.model.hana.CreateAccountForm"),
    HANA_ADD_PRIVILEGE(9,HANA,"addPrivilege","com.lenovo.sapapi.model.hana.CreateAccountForm"),
    HANA_RESET_PASSWORD(10,HANA,"resetPassword","com.lenovo.sapapi.model.hana.CreateAccountForm"),
    HANA_ACTIVATE_ACCOUNT(11,HANA,"activateAccount","com.lenovo.sapapi.model.hana.CreateAccountForm"),
    SAP_TEST_AUDIT(12,SAP,"testAop","com.lenovo.sapapi.model.sap.CreatePeriodicJobForm"),
    HANA_TEST_AUDIT(13,HANA,"testAop","com.lenovo.sapapi.model.hana.CreateAccountForm");


    private final Integer value;
    private final String type;
    private final String methodName;
    private final String requestType;

}
