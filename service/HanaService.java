package com.lenovo.sap.api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lenovo.sap.api.jmodel.entity.ApiInfoPO;
import com.lenovo.sap.api.jmodel.entity.RequestPO;
import com.lenovo.sap.api.model.hana.CreateAccountForm;
import com.lenovo.sap.api.model.hana.UserCheckForm;
import com.lenovo.sap.api.model.history.HistoryStatus;
import com.lenovo.sap.api.model.history.HistoryType;
import com.lenovo.sap.api.model.request.RequestNo;
import com.lenovo.sap.api.util.JacksonUtil;
import com.lenovo.sap.api.util.RemoteRestClient;
import com.lenovo.xframe.model.AuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.JSONB;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.lenovo.sap.api.model.apiinfo.ApiNameEnum.HANA_CREATE_ACCOUNT;
import static com.lenovo.sap.api.model.apiinfo.ApiNameEnum.HANA_RESET_PASSWORD;

/**
 * @author yuhao5
 * @date 4/12/22
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class HanaService {
    final ApiInfoService apiInfoService;
    final RemoteRestClient remoteRestClient;
    final RequestService requestService;

    public Map listAllSysId(){
        ApiInfoPO apiInfo = apiInfoService.getById(HANA_CREATE_ACCOUNT.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("hana_user_auth").toString());
        return remoteRestClient.getRequest(null,map.get("hana_app_list_url").toString(), headers);
    }

    public Map listLandscape(String application){
        ApiInfoPO apiInfo = apiInfoService.getById(HANA_CREATE_ACCOUNT.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("hana_user_auth").toString());
        return remoteRestClient.getRequest(null,map.get("hana_app_info_url").toString() + "/" + application, headers);
    }

    public Map checkUser(UserCheckForm form){
        ApiInfoPO apiInfo = apiInfoService.getById(HANA_CREATE_ACCOUNT.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("hana_user_auth").toString());
        return remoteRestClient.getRequest(null,
                map.get("hana_user_action_url").toString() + "/" + form.getSidName() + "/" + form.getEnvName() + "/" + form.getUserName(),
                headers);
    }

    public Map createAccount(CreateAccountForm form,AuthInfo authInfo){
        RequestPO record = record(form, HistoryType.HANA_CREATE_ACCOUNT, authInfo);
        ApiInfoPO apiInfo = apiInfoService.getById(HANA_CREATE_ACCOUNT.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("hana_user_auth").toString());
        Map<String, Object> params = new HashMap<>();
        params.put("request_id",form.getRequestId());
        params.put("user_name",form.getUserName());
        params.put("system_name",form.getSidName());
        params.put("system_role",form.getEnvName());
        params.put("role_name",form.getRole());
        params.put("expire_date",form.getExpireDate());
        Map response = remoteRestClient.postRequest(JacksonUtil.toJSon(params), map.get("hana_user_action_url").toString(), headers);
        record(record,response,authInfo);
        return response;
    }

    public Map addPrivilege(CreateAccountForm form,AuthInfo authInfo){
        RequestPO record = record(form, HistoryType.HANA_ADD_PRIVILEGE, authInfo);
        ApiInfoPO apiInfo = apiInfoService.getById(HANA_CREATE_ACCOUNT.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("hana_user_auth").toString());
        Map<String, Object> params = new HashMap<>();
        params.put("request_id",form.getRequestId());
        params.put("user_name",form.getUserName());
        params.put("system_name",form.getSidName());
        params.put("system_role",form.getEnvName());
        params.put("role_name",form.getRole());
        Map response = remoteRestClient.putRequest(JacksonUtil.toJSon(params), map.get("hana_user_role_url").toString(), headers);
        record(record,response,authInfo);
        return response;
    }

    public Map resetPassword(CreateAccountForm form,AuthInfo authInfo){
        RequestPO record = record(form, HistoryType.HANA_RESET_PASSWORD, authInfo);
        ApiInfoPO apiInfo = apiInfoService.getById(HANA_RESET_PASSWORD.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("hana_user_auth").toString());
        Map<String, Object> params = new HashMap<>();
        params.put("request_id",form.getRequestId());
        params.put("system_name",form.getSidName());
        params.put("system_role",form.getEnvName());
        params.put("operation_name","reset");
        Map response = remoteRestClient.putRequest(JacksonUtil.toJSon(params), map.get("hana_user_action_url").toString() + "/" + form.getUserName(), headers);
        record(record,response,authInfo);
        return response;
    }

    public Map activateAccount(CreateAccountForm form,AuthInfo authInfo){
        RequestPO record = record(form, HistoryType.HANA_ACTIVE_ACCOUNT, authInfo);
        ApiInfoPO apiInfo = apiInfoService.getById(HANA_RESET_PASSWORD.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("hana_user_auth").toString());
        Map<String, Object> params = new HashMap<>();
        params.put("request_id",form.getRequestId());
        params.put("system_name",form.getSidName());
        params.put("system_role",form.getEnvName());
        params.put("operation_name","activate");
        Map response = remoteRestClient.putRequest(JacksonUtil.toJSon(params), map.get("hana_user_action_url").toString() + "/" + form.getUserName(), headers);
        record(record,response,authInfo);
        return response;
    }


    private RequestPO record(RequestNo requestNo, HistoryType type, AuthInfo authInfo){
        return requestService.create(requestNo.requestNo(),type, JSONB.valueOf(JacksonUtil.toJSon(requestNo)),authInfo);
    }

    private void record(RequestPO po, Map response,AuthInfo authInfo){
        Boolean success =(Boolean) response.get("success");
        if (success){
            requestService.update(po, HistoryStatus.SUCCESS,JSONB.valueOf(JacksonUtil.toJSon(response)),authInfo);
        }else {
            requestService.update(po,HistoryStatus.FAILED,JSONB.valueOf(JacksonUtil.toJSon(response)),authInfo);
        }
    }
}
