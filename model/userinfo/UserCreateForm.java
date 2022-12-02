package com.lenovo.sap.api.model.userinfo;

import com.lenovo.sap.api.jmodel.entity.UserInfoPO;
import com.lenovo.xframe.util.IModel;
import lombok.Data;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Data
public class UserCreateForm implements IModel<UserInfoPO>, Map<String, Object> {

    private String  ip;
    private String  username;
    private String  password;
    private Instant startTime;
    private Instant endTime;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public Object put(String key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return null;
    }
}
