package org.jeecg.modules.pipeinfo.mapper;

import java.util.List;
import org.jeecg.modules.pipeinfo.entity.SensorLocInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 节点位置信息
 * @Author: jeecg-boot
 * @Date:   2020-06-30
 * @Version: V1.0
 */
public interface SensorLocInfoMapper extends BaseMapper<SensorLocInfo> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<SensorLocInfo> selectByMainId(@Param("mainId") String mainId);
}
