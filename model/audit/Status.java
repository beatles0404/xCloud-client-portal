package com.lenovo.sap.api.model.audit;

import com.lenovo.xframe.model.Valued;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author yuhao5
 * @date 4/25/22
 **/
@Getter
@RequiredArgsConstructor
public enum Status implements Valued<Integer> {
    SUCCESS(0),
    FAILED(1);

    private final Integer value;

}
