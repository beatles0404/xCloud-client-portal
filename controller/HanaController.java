package com.lenovo.sap.api.controller;

import com.lenovo.sap.api.Permissions;
import com.lenovo.sap.api.model.hana.CreateAccountForm;
import com.lenovo.sap.api.model.hana.UserCheckForm;
import com.lenovo.sap.api.service.HanaService;
import com.lenovo.xframe.model.AuthInfo;
import com.lenovo.xframe.model.ResultBody;
import com.lenovo.xframe.web.XframeController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author yuhao5
 * @date 4/12/22
 **/
@Slf4j
@RestController
@RequestMapping("/sao/api/v1/hana")
@RequiredArgsConstructor
@Api(tags = "Hana Service ", produces = MediaType.APPLICATION_JSON_VALUE)
public class HanaController implements XframeController {
    final HanaService service;

    @GetMapping("/")
    @Permissions.basic
    @ApiOperation("List All landscape and SYSID")
    public ResultBody<Map> listSysId(){
        return success(service.listAllSysId());
    }

    @GetMapping("/{appName}")
    @Permissions.basic
    public ResultBody<Map> listLandscape(@PathVariable String appName){
        return success(service.listLandscape(appName));
    }

    @PostMapping("/check")
    @Permissions.basic
    public ResultBody<Map> checkUser(@RequestBody UserCheckForm form, AuthInfo authInfo){
        return success(service.checkUser(form));
    }

    @PostMapping("/account")
    @Permissions.basic
    public ResultBody<Map> createAccount(@RequestBody CreateAccountForm form,AuthInfo authInfo){
        return success(service.createAccount(form,authInfo));
    }

    @PutMapping("/account")
    @Permissions.basic
    public ResultBody<Map> addPrivilege(@RequestBody CreateAccountForm form,AuthInfo authInfo){
        return success(service.addPrivilege(form,authInfo));
    }

    @PutMapping("/account/reset")
    @Permissions.basic
    public ResultBody<Map> resetPassword(@RequestBody CreateAccountForm form,AuthInfo authInfo){
        return success(service.resetPassword(form,authInfo));
    }

    @PutMapping("/account/active")
    @Permissions.basic
    public ResultBody<Map> activateAccount(@RequestBody CreateAccountForm form,AuthInfo authInfo){
        return success(service.activateAccount(form,authInfo));
    }

    @PostMapping("/testaop")
    @Permissions.basic
    public ResultBody<CreateAccountForm> testAop(@RequestBody CreateAccountForm form,AuthInfo authInfo){
        return success(form);
    }

}
