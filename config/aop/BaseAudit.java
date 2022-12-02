package com.lenovo.sap.api.config.aop;

import com.lenovo.sap.api.model.audit.AuditCreateForm;
import com.lenovo.sap.api.model.audit.Option;
import com.lenovo.sap.api.model.audit.Status;
import com.lenovo.sap.api.service.AuditService;
import com.lenovo.sap.api.util.JacksonUtil;
import com.lenovo.xframe.model.AuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * @author yuhao5
 * @date 4/28/22
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class BaseAudit {
    private final AuditService service;

    public void setLog(JoinPoint joinPoint, Status status, String type, Object result){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        if (!verifyAnnotation(signature)){
            return;
        }
        Option option = OptionType(signature,type);
        if (option != null){
            Object[] args = joinPoint.getArgs();
            AuditCreateForm form = new AuditCreateForm();
            form.setRequestParams(JacksonUtil.toJSon(args[0]));
            form.setResultParams(JacksonUtil.toJSon(result));
            form.setStatus(status);
            form.setOptionType(option);
            AuthInfo authInfo = (AuthInfo)args[1];
            service.create(form,authInfo);
        }
    }

    public boolean verifyAnnotation(MethodSignature signature){
        if (signature.getMethod().getDeclaredAnnotations().length == 0){
            return false;
        }
        for (Annotation annotation : signature.getMethod().getDeclaredAnnotations()) {
            if (PostMapping.class.getName().equals(annotation.annotationType().getName())){
                return true;
            }
            if (PutMapping.class.getName().equals(annotation.annotationType().getName())){
                return true;
            }
//            if (GetMapping.class.getName().equals(annotation.annotationType().getName())){
//                return true;
//            }
        }
        return false;
    }

    public Option OptionType(MethodSignature signature,String type){
        for (Option value : Option.values()) {
            if (Objects.equals(value.getType(),type) && Objects.equals(value.getMethodName(),signature.getName())){
                return value;
            }
        }
        return null;
    }
}
