package com.lenovo.sap.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Nullable;
import java.util.Objects;

public interface NamedModel {

    String getName();

    @JsonIgnore
    default boolean isNameEquals(@Nullable String name) {
        return Objects.equals(getName(), name);
    }

    @JsonIgnore
    default boolean isNameEquals(@Nullable NamedModel other) {
        if (other == null) {
            return false;
        }
        return isNameEquals(other.getName());
    }
}
