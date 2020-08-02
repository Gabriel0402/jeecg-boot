package org.jeecg.modules.message.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.message.entity.SysMessage;
import org.jeecg.modules.message.handle.ISendMsgHandle;
import org.jeecg.modules.message.util.PushMsgUtil;
import org.jeecg.modules.message.handle.enums.SendMsgStatusEnum;
import org.jeecg.modules.message.handle.enums.SendMsgTypeEnum;
import org.jeecg.modules.message.service.ISysMessageService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.jeecg.modules.senval.entity.SensorValue;
import org.jeecg.modules.senval.service.ISensorValueService;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 发送消息任务
 */

@Slf4j
public class ScheduleTask implements Job {

	@Autowired
	private ISysMessageService sysMessageService;

    @Autowired
    private ISensorValueService sensorValueService;


	@Value("${spring.mail.username}")
    private String sendUser;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		
		log.info(String.format("更新sensor value!  时间:" + DateUtils.getTimestamp()));
		SensorValue sensorValue = new SensorValue();
		sensorValue.id=null;
		sensorValue.uid="1";
		sensorValue.updateTime=new Date(System.currentTimeMillis());
		sensorValue.value=getRandomDoubleBetweenRange(0.0,15.0);
		sensorValueService.save(sensorValue);

		
        // Map<String, String> map = null;
        // boolean is_sendSuccess = pushMsgUtil.sendMessage("2", "test", map, "343031263@qq.com");
		// log.info(String.format(""+is_sendSuccess));
	}

	public static double getRandomDoubleBetweenRange(double min, double max){
		double x = (Math.random()*((max-min)+1))+min;
		return x;
	}

}
