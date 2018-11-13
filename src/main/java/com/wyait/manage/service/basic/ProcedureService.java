package com.wyait.manage.service.basic;

import com.wyait.manage.entity.basic.ProcedureSearchDTO;
import com.wyait.manage.utils.PageDataResult;

public interface ProcedureService {
    public PageDataResult getProcedures(Integer page, Integer limit, ProcedureSearchDTO searchDTO);
}
