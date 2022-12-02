package com.lenovo.sap.api.controller;

import com.lenovo.sap.api.Permissions;
import com.lenovo.sap.api.jmodel.entity.UserInfoPO;
import com.lenovo.sap.api.model.userinfo.UserCreateForm;
import com.lenovo.sap.api.model.userinfo.UserUpdateForm;
import com.lenovo.sap.api.service.UserInfoService;
import com.lenovo.xframe.model.AuthInfo;
import com.lenovo.xframe.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.lenovo.xframe.model.ResultBody.success;

@Slf4j
@RestController
@RequestMapping("/sao/api/v1/userinfo")
@RequiredArgsConstructor
@Api(tags = "UserInfo ", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserInfoController {
    final UserInfoService service;

    @PostMapping("/")
    @Permissions.basic
    @ApiOperation("Insert user info")
    public ResultBody<Object> create(@RequestBody UserCreateForm form,
                                     AuthInfo authInfo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        service.create(form,authInfo);
        return success();
    }



    @GetMapping("/{id}")
    @Permissions.basic
    public ResultBody<UserInfoPO> get(@PathVariable Integer id) throws InvalidAlgorithmParameterException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return success(service.getByID(id));
    }

    @PutMapping("/update")
    @Permissions.basic
    public ResultBody<Object> update(@RequestBody UserUpdateForm form,
                                     AuthInfo authInfo){
        service.update(form, authInfo);
        return success();
    }




}
