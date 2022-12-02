package com.lenovo.sap.api.controller;

import com.lenovo.sap.api.Permissions;
import com.lenovo.sap.api.jmodel.entity.AuditPO;
import com.lenovo.sap.api.service.AuditService;
import com.lenovo.xframe.model.ResultBody;
import com.lenovo.xframe.web.XframeController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yuhao5
 * @date 4/26/22
 **/
@Slf4j
@RestController
@RequestMapping("/sao/api/v1/audit")
@RequiredArgsConstructor
@Api(tags = "Audit ", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuditController implements XframeController {
    final AuditService service;

    @GetMapping("/")
    @Permissions.basic
    public ResultBody<List<AuditPO>> all(){
        return success(service.list());
    }

    @GetMapping("/{id}")
    @Permissions.basic
    public ResultBody<AuditPO> get(@PathVariable Integer id){
        return success(service.get(id));
    }
}
