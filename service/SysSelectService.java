package com.lenovo.sap.api.service;

import com.lenovo.sap.api.dao.SysSelectDao;
import com.lenovo.sap.api.jmodel.entity.SysSelectPO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuhao5
 * @date 2022/3/7
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysSelectService {
    final SysSelectDao dao;

    public List<SysSelectPO> listAll(){
        return dao.listAll();
    }
}
