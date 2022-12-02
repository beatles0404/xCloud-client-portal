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

import static com.lenovo.sap.api.model.audit.Contents.SAP;


/**
 * @author yuhao5
 * @date 4/21/22
 **/
@Aspect
@Slf4j
@Component
public class SapAudit extends BaseAudit{
    private int id;

    @Autowired
    public SapAudit(AuditService service) {
        super(service);
    }

    @Pointcut("execution(* com.lenovo.sap.api.controller.SapController.*(*,..))")
    public void sapPoint(){}

    @AfterReturning(value = "sapPoint()",returning = "result")
    public void after(JoinPoint joinPoint, Object result){
        log.debug("=====> Start After <=====");
        setLog(joinPoint, Status.SUCCESS,SAP,result);
    }

    @AfterThrowing(value = "sapPoint()",throwing = "ex")
    public void error(JoinPoint joinPoint,Exception ex){
        setLog(joinPoint,Status.FAILED,SAP,ex);
    }

    @Override
    public void setLog(JoinPoint joinPoint, Status status, String type, Object result) {
        super.setLog(joinPoint, status, type, result);
    }
}
