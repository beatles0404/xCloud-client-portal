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
public enum HistoryStatus implements Valued<Integer> {
    SUCCESS(1),
    FAILED(2),
    PROCESS(3);

    private final Integer value;
}
