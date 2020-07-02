package org.jeecg.modules.pipeinfo.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 管道信息
 * @Author: jeecg-boot
 * @Date:   2020-06-30
 * @Version: V1.0
 */
@ApiModel(value="pipe_info对象", description="管道信息")
@Data
@TableName("pipe_info")
public class PipeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**管道设备ID*/
	@Excel(name = "管道设备ID", width = 15)
    @ApiModelProperty(value = "管道设备ID")
    private java.lang.String pipeId;
	/**管道描述*/
	@Excel(name = "管道描述", width = 15)
    @ApiModelProperty(value = "管道描述")
    private java.lang.String pipeDes;
	/**首纬度*/
	@Excel(name = "首纬度", width = 15)
    @ApiModelProperty(value = "首纬度")
    private java.lang.Double hlat;
	/**首经度*/
	@Excel(name = "首经度", width = 15)
    @ApiModelProperty(value = "首经度")
    private java.lang.Double hlong;
	/**尾纬度*/
	@Excel(name = "尾纬度", width = 15)
    @ApiModelProperty(value = "尾纬度")
    private java.lang.Double rlat;
	/**尾经度*/
	@Excel(name = "尾经度", width = 15)
    @ApiModelProperty(value = "尾经度")
    private java.lang.Double rlong;
}
