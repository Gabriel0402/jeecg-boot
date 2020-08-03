package org.jeecg.modules.message.job;

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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.jeecg.common.util.SpringContextUtils;


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

	@Autowired
    private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
    private String sendUser;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		
		log.info(String.format("发送邮件 SendEmailJob !  时间:" + DateUtils.getTimestamp()));
		JavaMailSender mailSender = (JavaMailSender) SpringContextUtils.getBean("mailSender");
		MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
		try {
            helper = new MimeMessageHelper(message, true);
            // 设置发送方邮箱地址
            helper.setFrom(sendUser);
            helper.setTo("343031263@qq.com");
            helper.setSubject("中国高科技招聘会");
            helper.setText("亲爱的学者，您好，这封邮件帮助您参加常州大学的照片，请联系邮箱。", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // Map<String, String> map = null;
        // boolean is_sendSuccess = pushMsgUtil.sendMessage("2", "test", map, "343031263@qq.com");
		// log.info(String.format(""+is_sendSuccess));
	}

}
