package com.lenovo.sap.api.controller;

import com.lenovo.sap.api.Permissions;
import com.lenovo.sap.api.jmodel.entity.RequestPO;
import com.lenovo.sap.api.model.request.RequestCreateForm;
import com.lenovo.sap.api.model.request.RequestSearchForm;
import com.lenovo.sap.api.service.RequestService;
import com.lenovo.xframe.model.AuthInfo;
import com.lenovo.xframe.model.PageResult;
import com.lenovo.xframe.model.ResultBody;
import com.lenovo.xframe.web.XframeController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuhao5
 * @date 6/23/22
 **/
@Slf4j
@RestController
@RequestMapping("/sao/api/v1/request")
@RequiredArgsConstructor
@Api(tags = "Request info api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestController implements XframeController {
    final RequestService service;

    @PostMapping("/query")
    @Permissions.admin
    @ApiOperation("Query Request Info")
    public ResultBody<PageResult<RequestPO>> query(@RequestBody @ApiParam("request form") @RequestParam("requestName") RequestSearchForm form,
                                                   @ApiParam("page") @RequestParam("page") Pageable pageable){

        return success(service.search(form, pageable));
    }

    @PostMapping("/")
    @Permissions.admin
    @ApiOperation("New Request")
    public ResultBody<RequestPO> create(@RequestBody RequestCreateForm form, AuthInfo authinfo){
        return success(service.create(form, authinfo));
    }

    @GetMapping("/{id}")
    @Permissions.admin
    @ApiOperation("Get Request Info")
    public ResultBody<RequestPO> get(@PathVariable Integer id){
        return success(service.get(id));
    }



}
