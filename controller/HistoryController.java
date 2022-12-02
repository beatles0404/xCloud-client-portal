package com.lenovo.sap.api.controller;

import com.lenovo.sap.api.Permissions;
import com.lenovo.sap.api.jmodel.entity.HistoryPO;
import com.lenovo.sap.api.model.history.HistorySearchForm;
import com.lenovo.sap.api.service.HistoryService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuhao5
 * @date 6/23/22
 **/
@Slf4j
@RestController
@RequestMapping("/sao/api/v1/history")
@RequiredArgsConstructor
@Api(tags = "History info api", produces = MediaType.APPLICATION_JSON_VALUE)
public class HistoryController implements XframeController {
    final HistoryService service;

    @PostMapping("/query")
    @Permissions.basic
    @ApiOperation("Query History Info")
    public ResultBody<PageResult<HistoryPO>> query(@RequestBody HistorySearchForm form, Pageable pageable){
        return success(service.search(form, pageable));
    }

    @GetMapping("/{id}")
    @Permissions.basic
    @ApiOperation("Get History Info By id")
    public ResultBody<HistoryPO> get(@PathVariable Integer id){
        return success(service.get(id));
    }
}
