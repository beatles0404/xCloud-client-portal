package com.lenovo.sap.api.model.request;

import com.lenovo.xframe.model.Valued;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author yuhao5
 * @date 6/14/22
 **/
@Getter
@RequiredArgsConstructor
public enum RequestStatus implements Valued<Integer> {
    WAIT_APPROVE(1),
    PROCESSING(2),
    EDIT(3),
    CLOSED(4),
    ERROR_HANDLING(5),
    CANCEL(6);

    private final Integer value;
}
