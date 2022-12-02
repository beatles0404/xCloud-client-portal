package com.lenovo.sap.api.model.history;

import com.lenovo.xframe.model.Valued;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author yuhao5
 * @date 6/14/22
 **/
@Getter
@RequiredArgsConstructor
public enum HistoryType implements Valued<Integer> {
    SAP_CHANGE_JOB(1, "com.lenovo.sap.api.model.sap.ChangeJobForm"),
    SAP_CHANGE_STATUS(2,"com.lenovo.sap.api.model.sap.ChangeStatusForm"),
    SAP_CREATE_EVENT_JOB(3,"com.lenovo.sap.api.model.sap.CreateEventJobForm"),
    SAP_CREATE_PERIODIC_JOB(4,"com.lenovo.sap.api.model.sap.CreatePeriodicJobForm"),
    SAP_CANCEL_JOB(5,"com.lenovo.sap.api.model.sap.ChangeJobForm"),
    SAP_DELETE_JOB(6,"com.lenovo.sap.api.model.sap.DeleteJobForm"),
    SAP_ONE_TIME_JOB(7,"com.lenovo.sap.api.model.sap.CreatePeriodicJobForm"),
    SAP_NO_PROD_TRANSPORT(8,"com.lenovo.sap.api.model.sap.TransportForm"),
    SAP_CREATE_ACCOUNT(9,"com.lenovo.sap.api.model.sap.CreateAccountForm"),
    SAP_ADD_PRIVILEGE(10,"com.lenovo.sap.api.model.sap.CreateAccountForm"),
    SAP_ACTIVE_ACCOUNT(11,"com.lenovo.sap.api.model.sap.CreateAccountForm"),
    SAP_RESET_UNLOCK_PASSWORD(12,"com.lenovo.sap.api.model.sap.CreateAccountForm"),
    HANA_CREATE_ACCOUNT(13,"com.lenovo.sap.api.model.hana.CreateAccountForm"),
    HANA_ADD_PRIVILEGE(14,"com.lenovo.sap.api.model.hana.CreateAccountForm"),
    HANA_RESET_PASSWORD(15,"com.lenovo.sap.api.model.hana.CreateAccountForm"),
    HANA_ACTIVE_ACCOUNT(16,"com.lenovo.sap.api.model.hana.CreateAccountForm");

    private final Integer value;
    private final String className;
}
