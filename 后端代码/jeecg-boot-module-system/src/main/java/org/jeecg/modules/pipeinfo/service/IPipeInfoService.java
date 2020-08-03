package org.jeecg.modules.pipeinfo.service;

import org.jeecg.modules.pipeinfo.entity.SensorLocInfo;
import org.jeecg.modules.pipeinfo.entity.PipeInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 管道信息
 * @Author: jeecg-boot
 * @Date:   2020-06-30
 * @Version: V1.0
 */
public interface IPipeInfoService extends IService<PipeInfo> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(PipeInfo pipeInfo,List<SensorLocInfo> sensorLocInfoList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(PipeInfo pipeInfo,List<SensorLocInfo> sensorLocInfoList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
