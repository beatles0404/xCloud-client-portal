package com.lenovo.sap.api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lenovo.sap.api.jmodel.entity.ApiInfoPO;
import com.lenovo.sap.api.jmodel.entity.RequestPO;
import com.lenovo.sap.api.jmodel.entity.SysSelectPO;
import com.lenovo.sap.api.model.history.HistoryStatus;
import com.lenovo.sap.api.model.history.HistoryType;
import com.lenovo.sap.api.model.request.RequestNo;
import com.lenovo.sap.api.model.sap.*;
import com.lenovo.sap.api.util.JacksonUtil;
import com.lenovo.sap.api.util.RemoteRestClient;
import com.lenovo.sap.api.util.SapException;
import com.lenovo.xframe.model.AuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.JSONB;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lenovo.sap.api.model.apiinfo.ApiNameEnum.*;
import static com.lenovo.sap.api.model.sap.Content.SAP_TOKEN_NAME;
import static com.lenovo.sap.api.model.sap.JobType.PERIODIC;

/**
 * @author yuhao5
 * @date 2022/3/7
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
// TODO 优化代码， 提取公共方法
public class SapService{
    final ApiInfoService apiInfoService;
    final RemoteRestClient remoteRestClient;
    final SysSelectService sysSelectService;
    final HistoryService historyService;
    final JobInfoService jobInfoService;
    final RequestService requestService;

    public Map createJob(CreatePeriodicJobForm form, AuthInfo authInfo){
        // create request
        RequestPO record = record(form, HistoryType.SAP_CREATE_PERIODIC_JOB, authInfo);
        List<PeriodicJobItem> jobReqItems = form.getJobreqitem();
        for (int i = 0; i < jobReqItems.size(); i++) {
            jobReqItems.get(i).setItemno(String.format("%09d0",i+1));
        }
        form.getJobreqitem().forEach(item -> jobInfoService.create(item.getJobname(), JacksonUtil.toJSon(item),PERIODIC.name(),authInfo));
        ApiInfoPO apiInfo = apiInfoService.getById(CREATE_JOB.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("solman_auth").toString());
        Map response = remoteRestClient.postRequest(JacksonUtil.toJSon(form), map.get("solman_job_request").toString(), headers);
        record(record,response,authInfo);
        return response;
    }

    public Map listSysId(){
        ApiInfoPO apiInfo = apiInfoService.getById(CREATE_JOB.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("solman_auth").toString());
        return remoteRestClient.getRequest(null, map.get("solman_landscape_url").toString(), headers);
    }

    public List<SysSelectPO> getNamePer(){
        return sysSelectService.listAll();
    }

    public Map checkEvent(String sysName, Integer eventId){
        ApiInfoPO apiInfo = apiInfoService.getById(CREATE_JOB.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("solman_auth").toString());
        return remoteRestClient.getRequest(null,String.format(map.get("event_check_api").toString(),sysName,eventId),headers);
    }

    public Map deleteJob(DeleteJobForm form,AuthInfo authInfo){
        RequestPO record = record(form, HistoryType.SAP_DELETE_JOB, authInfo);
        List<SimpleJobReqItem> jobReqItems = form.getJobreqitem();
        for (int i = 0; i < jobReqItems.size(); i++) {
            jobReqItems.get(i).setItemno(String.format("%09d0",i+1));
            jobInfoService.delete(jobReqItems.get(i).getJobname());
        }
        ApiInfoPO apiInfo = apiInfoService.getById(DELETE_JOB.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("solman_auth").toString());
        Map response = remoteRestClient.deleteRequest(JacksonUtil.toJSon(form), map.get("solman_job_request").toString(), headers);
        record(record,response,authInfo);
        return response;
    }

    public Map searchJob(SearchJobForm form){
        ApiInfoPO apiInfo = apiInfoService.getById(SEARCH_JOB.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("solman_auth").toString());
        Map response = remoteRestClient.putRequest(JacksonUtil.toJSon(form), map.get("solman_job_read").toString(), headers);
        Object message = JacksonUtil.objectMapper.convertValue(response.get("data"), Map.class).get("message");
        if (message != null && message.toString().contains("not exist")){
            throw new SapException("SAP Error","Job not exist");
        }
        return response;
    }

    public Map changeJob(ChangeJobForm form,AuthInfo authInfo){
        RequestPO record = record(form, HistoryType.SAP_CHANGE_JOB, authInfo);
        ApiInfoPO apiInfo = apiInfoService.getById(CHANGE_JOB.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("solman_auth").toString());
        Map response = remoteRestClient.putRequest(JacksonUtil.toJSon(form), map.get("solman_job_request").toString(), headers);
        record(record,response,authInfo);
        return response;
    }

    public Map changeStatus(ChangeStatusForm form, AuthInfo authInfo){
        RequestPO record = record(form, HistoryType.SAP_CHANGE_STATUS, authInfo);
        ApiInfoPO apiInfo = apiInfoService.getById(SCHEDULE_JOB.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("solman_auth").toString());
        Map reponse = remoteRestClient.putRequest(JacksonUtil.toJSon(form), map.get("solman_job_request").toString(), headers);
        record(record,reponse,authInfo);
        return reponse;
    }

    public Map roleCategory(String sys){
        ApiInfoPO apiInfo = apiInfoService.getById(ROLE_CATEGORY.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("solman_auth").toString());
        return remoteRestClient.getRequest(null, map.get("solman_role_category").toString() + "'" + sys + "'", headers);
    }

    public Map account(CreateAccountForm form,AuthInfo authInfo){
        RequestPO record = record(form, HistoryType.SAP_CREATE_ACCOUNT, authInfo);
        Map<String, Object> tokenMap = getToken(SAP_TOKEN_NAME);
        ApiInfoPO apiInfo = apiInfoService.getById(CREATE_ACCOUNT.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("solman_auth").toString());
        headers.set(map.get("api_token_key").toString(),tokenMap.get("token").toString());
        headers.put("cookie",(List<String>) tokenMap.get("cookie"));
        Map<String, Object> params = new HashMap<>();
        params.put("Requestor", form.getRequestor());   //SC单号
        params.put("Appl", form.getAppl());             //系统名称
        params.put("Sysid", form.getSysid());           //系统SID
        params.put("Client", form.getClient());         //客户端
        params.put("Uname", form.getUname());           //需要创建的用户名
        params.put("Referuname", form.getReferuname()); //复制的用户
        params.put("Category", form.getCategory());     //角色分类
        params.put("Applyuser", form.getApplyuser());   //申请用户的操作人
        params.put("Acttype", form.getActtype());                              //操作用户类型
        params.put("Status", form.getStatus());
        params.put("Zreset",form.getZreset());
        Map response = remoteRestClient.postRequest(JacksonUtil.toJSon(params), map.get("sap_acc_url").toString(), headers);
        record(record,response,authInfo);
        return response;
    }

    public Map transport(TransportForm form,AuthInfo authInfo){
        RequestPO record = record(form, HistoryType.SAP_NO_PROD_TRANSPORT, authInfo);
        ApiInfoPO apiInfo = apiInfoService.getById(TRANSPORT.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("solman_auth").toString());
        remoteRestClient.postRequest(JacksonUtil.toJSon(form), map.get("trtp_url").toString(), headers);
        Map<String,String> params = new HashMap<>(1);
        params.put("scdocid",form.getScdocid());
        Map response = remoteRestClient.putRequest(JacksonUtil.toJSon(params), map.get("trtp_url").toString(), headers);
        record(record,response,authInfo);
        return response;
    }

    private Map<String,Object> getToken(String tokenName){
        log.debug("start get token");
        ApiInfoPO apiInfo = apiInfoService.getById(SAP_TOKEN.getValue());
        Map<String, Object> map = JacksonUtil.readValue(apiInfo.getParams(), new TypeReference<Map<String, Object>>() {
        });
        String apiTokenKey = map.get("api_token_key").toString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization","Basic " + map.get("solman_auth").toString());
        headers.add(apiTokenKey,"fetch");
        HttpHeaders responseHeader = remoteRestClient.getRequestHeader(null, map.get("sap_acc_url").toString(), headers);
        if(responseHeader == null || !responseHeader.containsKey(apiTokenKey)){
            log.error("Get SAP token failed");
            throw new SapException("Error","Get SAP Token Failed");
        }
        Map<String,Object> tokenMap = new HashMap<>(2);
        tokenMap.put("token",responseHeader.get(apiTokenKey).get(0));
        List<String> cookie = responseHeader.get("set-cookie");
        tokenMap.put("cookie",cookie);
        return tokenMap;
    }

    private RequestPO record(RequestNo requestNo, HistoryType type, AuthInfo authInfo){
        return requestService.create(requestNo.requestNo(),type,JSONB.valueOf(JacksonUtil.toJSon(requestNo)),authInfo);
    }

    private void record(RequestPO po, Map response,AuthInfo authInfo){
        Boolean success =(Boolean) response.get("success");
        if (success){
            requestService.update(po,HistoryStatus.SUCCESS,JSONB.valueOf(JacksonUtil.toJSon(response)),authInfo);
        }else {
            requestService.update(po,HistoryStatus.FAILED,JSONB.valueOf(JacksonUtil.toJSon(response)),authInfo);
        }
    }

}
