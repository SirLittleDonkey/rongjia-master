package com.wyait.manage.service.basic;

import com.wyait.manage.entity.basic.ProcedureSearchDTO;
import com.wyait.manage.utils.PageDataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProcedureServiceImpl implements ProcedureService{
    private static final Logger logger = LoggerFactory
            .getLogger(WorkStationServiceImpl.class);



    @Override
    public PageDataResult getProcedures(Integer page, Integer limit, ProcedureSearchDTO searchDTO) {
        return null;
    }
}
