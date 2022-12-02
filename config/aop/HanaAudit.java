package com.lenovo.sap.api.config.aop;

import com.lenovo.sap.api.model.audit.Status;
import com.lenovo.sap.api.service.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.lenovo.sap.api.model.audit.Contents.HANA;

/**
 * @author yuhao5
 * @date 4/21/22
 **/
@Aspect
@Slf4j
@Component
public class HanaAudit extends BaseAudit{

    private int id;

    @Autowired
    public HanaAudit(AuditService service) {
        super(service);
    }

    @Pointcut("execution(* com.lenovo.sap.api.controller.HanaController.*(*,..))")
    public void hanaPoint(){}

    @AfterReturning(value = "hanaPoint()",returning = "result")
    public void after(JoinPoint joinPoint , Object result){
        log.debug("=====> Start After <=====");
        setLog(joinPoint, Status.SUCCESS,HANA,result);
    }

    @AfterThrowing(value = "hanaPoint()",throwing = "ex")
    public void error(JoinPoint joinPoint,Exception ex){
        setLog(joinPoint,Status.FAILED,HANA,ex);
    }



}
