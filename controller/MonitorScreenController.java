package com.lenovo.sap.api.controller;

import com.lenovo.sap.api.service.MonitorScreenService;
import com.lenovo.xframe.annotation.SecureApi;
import com.lenovo.xframe.model.ResultBody;
import com.lenovo.xframe.web.XframeController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author houzh5
 * @date 2022/6/23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "MonitorScreen", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/sao/api/v1/monitor")
public class MonitorScreenController implements XframeController {
    final MonitorScreenService ms;

    @GetMapping("/getmotype")
    @SecureApi(requireAuthentication = false)
    public ResultBody<Object> getMoType(){

        return success(ms.getSnmpMoType());
    }
}
