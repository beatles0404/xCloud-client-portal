package com.lenovo.sap.api.controller;

import com.lenovo.sap.api.Permissions;
import com.lenovo.sap.api.jmodel.entity.SysSelectPO;
import com.lenovo.sap.api.model.sap.*;
import com.lenovo.sap.api.service.SapService;
import com.lenovo.sap.api.util.SapException;
import com.lenovo.xframe.model.AuthInfo;
import com.lenovo.xframe.model.ResultBody;
import com.lenovo.xframe.web.XframeController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author yuhao5
 * @date 2022/3/7
 **/
@Slf4j
@RestController
@RequestMapping("/sao/api/v1/sap")
@RequiredArgsConstructor
@Api(tags = "Call SAP api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SapController implements XframeController {
    final SapService service;

    @GetMapping("/list/sysid")
    @Permissions.basic
    @ApiOperation("Get sap landscape list")
    public ResultBody<Map> listSysId(){
        return success(service.listSysId());
    }

    @GetMapping("/list/nameper")
    @Permissions.basic
    @ApiOperation("Get name per list")
    public ResultBody<List<SysSelectPO>> listNamePer(){
        return success(service.getNamePer());
    }

    @GetMapping("/check/event")
    @Permissions.basic
    @ApiOperation(("Check Event ID"))
    public ResultBody<Map> checkEvent(@RequestParam String sysName, @RequestParam Integer eventId){
        return success(service.checkEvent(sysName,eventId));
    }

    @PostMapping("/")
    @Permissions.basic
    @ApiOperation("Create an Sap job")
    public ResultBody<Object> createJob(@RequestBody CreatePeriodicJobForm form, AuthInfo authInfo){
        return success(service.createJob(form,authInfo));
    }

    @PostMapping("/del")
    @Permissions.basic
    @ApiOperation("Delete an Sap job")
    public ResultBody<Map> deleteJob(@RequestBody DeleteJobForm form, AuthInfo authInfo){
        return success(service.deleteJob(form,authInfo));
    }

    @PostMapping("/search")
    @Permissions.basic
    @ApiOperation("Search Job From sap")
    public ResultBody<Map> searchJob(@RequestBody SearchJobForm form,AuthInfo authInfo){
        try {
           return success(service.searchJob(form));
        }catch (SapException e){
            return failed(null, e.getLocalizedMessage());
        }
    }

    @PostMapping("/change")
    @Permissions.basic
    @ApiOperation("Change sap job")
    public ResultBody<Map> changeJob(@RequestBody ChangeJobForm form,AuthInfo authInfo){
        return success(service.changeJob(form,authInfo));
    }

    @PostMapping("/status")
    @Permissions.basic
    @ApiOperation("Change job status")
    public ResultBody<Map> changeStatus(@RequestBody ChangeStatusForm form,AuthInfo authInfo){
        return success(service.changeStatus(form,authInfo));
    }

    @GetMapping("/rolecategory/{sys}")
    @Permissions.basic
    @ApiOperation("Get sys role category")
    public ResultBody<Map> roleCategory(@PathVariable String sys){
        return success(service.roleCategory(sys));
    }

    @PostMapping("/account")
    @Permissions.basic
    public ResultBody<Map> account(@RequestBody CreateAccountForm form,AuthInfo authInfo){
        return success(service.account(form,authInfo));
    }

    @PostMapping("/transport")
    @Permissions.basic
    public ResultBody<Map> transport(@RequestBody TransportForm form,AuthInfo authInfo){
        return success(service.transport(form,authInfo));
    }

    @PostMapping("/testaop")
    @Permissions.basic
    public ResultBody<CreatePeriodicJobForm> testAop(@RequestBody CreatePeriodicJobForm form,AuthInfo authInfo){
        return success(form);
    }
}
