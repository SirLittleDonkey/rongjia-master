package com.wyait.manage.service.basic;

import com.wyait.manage.entity.basic.WorkStationSearchDTO;
import com.wyait.manage.entity.basic.WorkStationVO;
import com.wyait.manage.pojo.basic.WorkStation;
import com.wyait.manage.utils.PageDataResult;

public interface WorkStationService {
    public PageDataResult getWorkStations(Integer page, Integer limit, WorkStationSearchDTO workStationSearchDTO);

    public String setDelWorkStation(Integer id, Integer isDel);

    public WorkStationVO getWorkStation(Integer id);

    public String setWorkStation(WorkStation workStation);
}
