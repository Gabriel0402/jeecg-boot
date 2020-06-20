package org.tang.modules.pipe.demo.service.impl;

import org.tang.modules.pipe.demo.entity.PipeInfo;
import org.tang.modules.pipe.demo.mapper.PipeInfoMapper;
import org.tang.modules.pipe.demo.service.IPipeInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 节点信息
 * @Author: jeecg-boot
 * @Date:   2020-06-16
 * @Version: V1.0
 */
@Service
public class PipeInfoServiceImpl extends ServiceImpl<PipeInfoMapper, PipeInfo> implements IPipeInfoService {

}
