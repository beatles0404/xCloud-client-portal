package com.lenovo.sap.api.model.apiinfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author yuhao5
 * @date 2022/3/7
 **/
@Getter
@RequiredArgsConstructor
public enum ApiNameEnum {
    CREATE_JOB(1),
    DELETE_JOB(2),
    CHANGE_JOB(3),
    SEARCH_JOB(4),
    SCHEDULE_JOB(5),
    RELEASE_JOB(6),
    CANCEL_JOB(7),
    ROLE_CATEGORY(8),
    SAP_TOKEN(9),
    CREATE_ACCOUNT(10),
    TRANSPORT(11),
    HANA_CREATE_ACCOUNT(12),
    HANA_RESET_PASSWORD(13);

    private final Integer value;
}
