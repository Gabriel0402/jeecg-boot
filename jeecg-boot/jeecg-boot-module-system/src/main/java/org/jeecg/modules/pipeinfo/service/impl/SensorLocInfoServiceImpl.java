package org.jeecg.modules.pipeinfo.service.impl;

import org.jeecg.modules.pipeinfo.entity.SensorLocInfo;
import org.jeecg.modules.pipeinfo.mapper.SensorLocInfoMapper;
import org.jeecg.modules.pipeinfo.service.ISensorLocInfoService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 节点位置信息
 * @Author: jeecg-boot
 * @Date:   2020-06-30
 * @Version: V1.0
 */
@Service
public class SensorLocInfoServiceImpl extends ServiceImpl<SensorLocInfoMapper, SensorLocInfo> implements ISensorLocInfoService {
	
	@Autowired
	private SensorLocInfoMapper sensorLocInfoMapper;
	
	@Override
	public List<SensorLocInfo> selectByMainId(String mainId) {
		return sensorLocInfoMapper.selectByMainId(mainId);
	}
}
