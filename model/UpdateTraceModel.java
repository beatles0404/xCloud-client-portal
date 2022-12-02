package com.lenovo.sap.api.model;

import com.lenovo.xframe.model.AuthInfo;
import lombok.val;

import java.time.Instant;

public interface UpdateTraceModel {

    void setCreatedAt(Instant createdAt);

    void setUpdatedAt(Instant updatedAt);

    void setCreatedBy(String createdBy);

    void setUpdatedBy(String updatedBy);

    default void onCreate(AuthInfo auth) {
        val now = Instant.now();
        val code = auth.getUserCode();
        setCreatedAt(now);
        setUpdatedAt(now);
        setCreatedBy(code);
        setUpdatedBy(code);
    }

    default void onUpdate(AuthInfo auth) {
        setUpdatedAt(Instant.now());
        setUpdatedBy(auth.getUserCode());
    }
}
