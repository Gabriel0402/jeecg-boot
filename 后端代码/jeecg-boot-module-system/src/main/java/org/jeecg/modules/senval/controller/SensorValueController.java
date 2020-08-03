package org.jeecg.modules.senval.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.senval.entity.SensorValue;
import org.jeecg.modules.senval.service.ISensorValueService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 节点数据
 * @Author: jeecg-boot
 * @Date:   2020-06-30
 * @Version: V1.0
 */
@Api(tags="节点数据")
@RestController
@RequestMapping("/senval/sensorValue")
@Slf4j
public class SensorValueController extends JeecgController<SensorValue, ISensorValueService> {
	@Autowired
	private ISensorValueService sensorValueService;
	
	/**
	 * 分页列表查询
	 *
	 * @param sensorValue
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "节点数据-分页列表查询")
	@ApiOperation(value="节点数据-分页列表查询", notes="节点数据-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SensorValue sensorValue,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SensorValue> queryWrapper = QueryGenerator.initQueryWrapper(sensorValue, req.getParameterMap());
		Page<SensorValue> page = new Page<SensorValue>(pageNo, pageSize);
		IPage<SensorValue> pageList = sensorValueService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param sensorValue
	 * @return
	 */
	@AutoLog(value = "节点数据-添加")
	@ApiOperation(value="节点数据-添加", notes="节点数据-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SensorValue sensorValue) {
		sensorValueService.save(sensorValue);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param sensorValue
	 * @return
	 */
	@AutoLog(value = "节点数据-编辑")
	@ApiOperation(value="节点数据-编辑", notes="节点数据-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SensorValue sensorValue) {
		sensorValueService.updateById(sensorValue);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "节点数据-通过id删除")
	@ApiOperation(value="节点数据-通过id删除", notes="节点数据-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		sensorValueService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "节点数据-批量删除")
	@ApiOperation(value="节点数据-批量删除", notes="节点数据-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.sensorValueService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "节点数据-通过id查询")
	@ApiOperation(value="节点数据-通过id查询", notes="节点数据-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SensorValue sensorValue = sensorValueService.getById(id);
		if(sensorValue==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(sensorValue);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param sensorValue
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SensorValue sensorValue) {
        return super.exportXls(request, sensorValue, SensorValue.class, "节点数据");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, SensorValue.class);
    }

}
