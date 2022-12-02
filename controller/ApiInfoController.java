package com.lenovo.sap.api.controller;

import com.lenovo.sap.api.Permissions;
import com.lenovo.sap.api.jmodel.entity.ApiInfoPO;
import com.lenovo.sap.api.model.apiinfo.ApiInfoCreateForm;
import com.lenovo.sap.api.model.apiinfo.ApiInfoVO;
import com.lenovo.sap.api.service.ApiInfoService;
import com.lenovo.xframe.model.AuthInfo;
import com.lenovo.xframe.model.ResultBody;
import com.lenovo.xframe.web.XframeController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuhao5
 * @date 2022/3/5
 **/
@Slf4j
@RestController
@RequestMapping("/sao/api/v1/apiinfo")
@RequiredArgsConstructor
@Api(tags = "Api Info ", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiInfoController implements XframeController {
    final ApiInfoService service;

    @PostMapping("/")
    @Permissions.basic
    @ApiOperation("Create api info")
    public ResultBody<Object> create( ApiInfoCreateForm form, AuthInfo authInfo){
        service.create(form,authInfo);
        return success();
    }

}
