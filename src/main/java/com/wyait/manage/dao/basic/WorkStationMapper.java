package com.wyait.manage.dao.basic;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import com.wyait.manage.entity.basic.WorkStationDTO;
import com.wyait.manage.entity.basic.WorkStationSearchDTO;
import com.wyait.manage.entity.basic.WorkStationVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.basic.WorkStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkStationMapper {

    WorkStation selectByPrimaryKey(Integer id);

    /**
     * 分页查询工位数据
     * @return
     */
    List<WorkStationDTO> getWorkStations(@Param("workStationSearchDTO")WorkStationSearchDTO workStationSearchDTO);

    /**
     * 删除/恢复工位
     * @param id
     * @param isDel
     * @return
     */
    int setDelWorkStation(@Param("id") Integer id, @Param("isDel") Integer isDel);

    /**
     * 查询工位信息
     * @param id
     * @return
     */
    WorkStationVO getWorkStation(Integer id);

    /**
     * 根据工位号获取工位数据
     * @param workStationCode
     * @return
     */
    WorkStation findWorkStationByWorkStationCode(String workStationCode);

    int insert(WorkStation workStation);

    int updateByPrimaryKeySelective(WorkStation workStation);
}
