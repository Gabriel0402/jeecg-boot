package org.jeecg.modules.pipeinfo.vo;

import java.util.List;
import org.jeecg.modules.pipeinfo.entity.PipeInfo;
import org.jeecg.modules.pipeinfo.entity.SensorLocInfo;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 管道信息
 * @Author: jeecg-boot
 * @Date:   2020-06-30
 * @Version: V1.0
 */
@Data
@ApiModel(value="pipe_infoPage对象", description="管道信息")
public class PipeInfoPage {

	/**主键*/
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
	
	@ExcelCollection(name="节点位置信息")
	@ApiModelProperty(value = "节点位置信息")
	private List<SensorLocInfo> sensorLocInfoList;
	
}
