package com.wyait.manage.service.basic;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.basic.WorkStationMapper;
import com.wyait.manage.entity.basic.WorkStationDTO;
import com.wyait.manage.entity.basic.WorkStationSearchDTO;
import com.wyait.manage.entity.basic.WorkStationVO;
import com.wyait.manage.pojo.basic.WorkStation;
import com.wyait.manage.utils.PageDataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkStationServiceImpl implements WorkStationService{
    private static final Logger logger = LoggerFactory
            .getLogger(WorkStationServiceImpl.class);

    @Autowired
    private WorkStationMapper workStationMapper;

    @Override
    public PageDataResult getWorkStations(Integer page, Integer limit, WorkStationSearchDTO workStationSearchDTO) {

        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<WorkStationDTO> workStations = workStationMapper.getWorkStations(workStationSearchDTO);
        //获取分页查询后的数据
        PageInfo<WorkStationDTO> pageInfo = new PageInfo<>(workStations);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(workStations);
        return pdr;
    }

    @Override
    public String setDelWorkStation(Integer id, Integer isDel) {
        return this.workStationMapper.setDelWorkStation(id, isDel) == 1 ? "ok": "删除失败，请您稍后再试";
    }

    @Override
    public WorkStationVO getWorkStation(Integer id) {
        return this.workStationMapper.getWorkStation(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {
            RuntimeException.class, Exception.class })
    public String setWorkStation(WorkStation workStation) {
        int workStationId;
        if(workStation.getId() != null){
            WorkStation existWorkStation = this.workStationMapper.findWorkStationByWorkStationCode(workStation.getWorkStationCode());
            if(null != existWorkStation
                    && String.valueOf(existWorkStation.getId()).equals(
                            String.valueOf(workStation.getId()))){
                return "该工位号已存在";
            }
            this.workStationMapper.updateByPrimaryKeySelective(workStation);
        }else {
            //判断工位是否已经存在
            WorkStation existWorkStation = this.workStationMapper.findWorkStationByWorkStationCode(workStation.getWorkStationCode());
            if(null != existWorkStation){
                return "该工位号已存在";
            }
            workStation.setIsDel(false);
            this.workStationMapper.insert(workStation);
        }
        return "ok";
    }
}
