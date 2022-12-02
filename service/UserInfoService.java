package com.lenovo.sap.api.service;

import com.lenovo.sap.api.dao.UserInfoDao;
import com.lenovo.sap.api.jmodel.entity.UserInfoPO;
import com.lenovo.sap.api.model.userinfo.UserCreateForm;
import com.lenovo.sap.api.model.userinfo.UserUpdateForm;
import com.lenovo.sap.api.util.AesUtil;
import com.lenovo.xframe.model.AuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.lenovo.sap.api.util.AesUtil.generateString;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UserInfoService {

    final UserInfoDao dao;

    public static final String key = generateString(16);
    public static final String vi = generateString(16);
    public static final String VIPARA = "1234567890abcdef";
    public static final String CODE_TYPE = "UTF-8";



    public UserInfoPO create(UserCreateForm form, AuthInfo authInfo) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        UserInfoPO po = form.to(new UserInfoPO());
        //String systemKey = System.getStr("key");
        String input = po.getPassword();

        po.setPassword(String.valueOf(AesUtil.encrypt(input,key, vi)));
        po.onCreate(authInfo);
        dao.insert(po);
        return po;
    }

    public UserInfoPO create(String ip, String username, String password, AuthInfo authInfo) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        UserCreateForm form = new UserCreateForm();
        form.setPassword(String.valueOf(AesUtil.encrypt(password,key, vi)));
        form.setIp(ip);
        form.setUsername(username);
        form.setStartTime(null);
        form.setEndTime(null);
        return create(form,authInfo);
    }

    public void update(UserUpdateForm form, AuthInfo authInfo){
        UserInfoPO po = form.to(new UserInfoPO());
        po.onUpdate(authInfo);
    }

    public UserInfoPO getByID(Integer id) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        UserInfoPO po = dao.findById(id);
        String source = po.getPassword();
        po.setPassword(String.valueOf(AesUtil.decrypt(source,key,vi)));
        return po;
    }

    public void delete(String username){ dao.deleteByUsername(username);}

}

