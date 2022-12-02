package com.lenovo.sap.api.model.sap;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author yuhao5
 * @date 3/17/22
 **/
@Getter
@RequiredArgsConstructor
public enum JobType {
    PERIODIC(PeriodicJobItem.class);

    private final Class<?> value;
}
