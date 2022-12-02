package com.lenovo.sap.api.controller;

import com.lenovo.xframe.annotation.SecureApi;
import com.lenovo.xframe.model.ResultBody;
import com.lenovo.xframe.web.XframeController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuhao5
 * @date 2022/3/9
 **/
@Slf4j
@RestController
@RequestMapping("/sao/api/v1/heath")
public class HeathController implements XframeController {

    @GetMapping("/check")
    @SecureApi(requireAuthentication = false)
    public ResultBody<Object> check(){
        return success();
    }
}
