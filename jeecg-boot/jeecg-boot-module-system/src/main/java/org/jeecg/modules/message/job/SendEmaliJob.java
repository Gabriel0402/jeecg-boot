package org.jeecg.modules.message.job;

import java.util.List;

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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 发送消息任务
 */

@Slf4j
public class SendEmailJob implements Job {

	@Autowired
	private ISysMessageService sysMessageService;

    @Autowired
	private PushMsgUtil pushMsgUtil;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		log.info(String.format("发送邮件 SendEmailJob !  时间:" + DateUtils.getTimestamp()));
        Map<String, String> map = null;
        boolean is_sendSuccess = pushMsgUtil.sendMessage("2", 'test', map, 'admin');
		log.info(String.format(is_sendSuccess);
	}

}
