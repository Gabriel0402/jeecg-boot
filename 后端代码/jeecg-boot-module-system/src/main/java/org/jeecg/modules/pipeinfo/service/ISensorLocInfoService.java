package org.jeecg.modules.pipeinfo.service;

import org.jeecg.modules.pipeinfo.entity.SensorLocInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 节点位置信息
 * @Author: jeecg-boot
 * @Date:   2020-06-30
 * @Version: V1.0
 */
public interface ISensorLocInfoService extends IService<SensorLocInfo> {

	public List<SensorLocInfo> selectByMainId(String mainId);
}
