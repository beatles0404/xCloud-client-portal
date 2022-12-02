package com.lenovo.sap.api.service;

import com.lenovo.sap.api.dao.SnmpDao;
import com.lenovo.sap.api.model.monitor.SnmpVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author houzh5
 * @date 2022/6/23
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MonitorScreenService {
    final SnmpDao snmpDao;
    public List<SnmpVO> getSnmpMoType(){
        return snmpDao.getSnmpMoType();
    }

}
