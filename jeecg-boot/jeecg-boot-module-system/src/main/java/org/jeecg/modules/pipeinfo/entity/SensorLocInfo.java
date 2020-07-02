package org.jeecg.modules.pipeinfo.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 节点位置信息
 * @Author: jeecg-boot
 * @Date:   2020-06-30
 * @Version: V1.0
 */
@ApiModel(value="pipe_info对象", description="管道信息")
@Data
@TableName("sensor_loc_info")
public class SensorLocInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**设备号*/
	@Excel(name = "设备号", width = 15)
	@ApiModelProperty(value = "设备号")
	private java.lang.String uid;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**纬度*/
	@Excel(name = "纬度", width = 15)
	@ApiModelProperty(value = "纬度")
	private java.lang.Double latitude;
	/**经度*/
	@Excel(name = "经度", width = 15)
	@ApiModelProperty(value = "经度")
	private java.lang.Double longitude;
	/**所属管道*/
	@ApiModelProperty(value = "所属管道")
	private java.lang.String pipeId;
	/**节点描述*/
	@Excel(name = "节点描述", width = 15)
	@ApiModelProperty(value = "节点描述")
	private java.lang.String desp;
}
