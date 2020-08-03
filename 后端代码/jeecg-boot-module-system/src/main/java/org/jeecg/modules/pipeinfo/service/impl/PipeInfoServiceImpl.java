package org.jeecg.modules.pipeinfo.service.impl;

import org.jeecg.modules.pipeinfo.entity.PipeInfo;
import org.jeecg.modules.pipeinfo.entity.SensorLocInfo;
import org.jeecg.modules.pipeinfo.mapper.SensorLocInfoMapper;
import org.jeecg.modules.pipeinfo.mapper.PipeInfoMapper;
import org.jeecg.modules.pipeinfo.service.IPipeInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 管道信息
 * @Author: jeecg-boot
 * @Date:   2020-06-30
 * @Version: V1.0
 */
@Service
public class PipeInfoServiceImpl extends ServiceImpl<PipeInfoMapper, PipeInfo> implements IPipeInfoService {

	@Autowired
	private PipeInfoMapper pipeInfoMapper;
	@Autowired
	private SensorLocInfoMapper sensorLocInfoMapper;
	
	@Override
	@Transactional
	public void saveMain(PipeInfo pipeInfo, List<SensorLocInfo> sensorLocInfoList) {
		pipeInfoMapper.insert(pipeInfo);
		if(sensorLocInfoList!=null && sensorLocInfoList.size()>0) {
			for(SensorLocInfo entity:sensorLocInfoList) {
				//外键设置
				entity.setPipeId(pipeInfo.getId());
				sensorLocInfoMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(PipeInfo pipeInfo,List<SensorLocInfo> sensorLocInfoList) {
		pipeInfoMapper.updateById(pipeInfo);
		
		//1.先删除子表数据
		sensorLocInfoMapper.deleteByMainId(pipeInfo.getId());
		
		//2.子表数据重新插入
		if(sensorLocInfoList!=null && sensorLocInfoList.size()>0) {
			for(SensorLocInfo entity:sensorLocInfoList) {
				//外键设置
				entity.setPipeId(pipeInfo.getId());
				sensorLocInfoMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		sensorLocInfoMapper.deleteByMainId(id);
		pipeInfoMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			sensorLocInfoMapper.deleteByMainId(id.toString());
			pipeInfoMapper.deleteById(id);
		}
	}
	
}
