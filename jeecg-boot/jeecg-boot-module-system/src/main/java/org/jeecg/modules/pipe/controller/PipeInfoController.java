package org.tang.modules.pipe.demo.controller;

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
import org.tang.modules.pipe.demo.entity.PipeInfo;
import org.tang.modules.pipe.demo.service.IPipeInfoService;

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
 * @Description: 节点信息
 * @Author: jeecg-boot
 * @Date:   2020-06-16
 * @Version: V1.0
 */
@Api(tags="节点信息")
@RestController
@RequestMapping("/demo/pipeInfo")
@Slf4j
public class PipeInfoController extends JeecgController<PipeInfo, IPipeInfoService> {
	@Autowired
	private IPipeInfoService pipeInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pipeInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "节点信息-分页列表查询")
	@ApiOperation(value="节点信息-分页列表查询", notes="节点信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PipeInfo pipeInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PipeInfo> queryWrapper = QueryGenerator.initQueryWrapper(pipeInfo, req.getParameterMap());
		Page<PipeInfo> page = new Page<PipeInfo>(pageNo, pageSize);
		IPage<PipeInfo> pageList = pipeInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pipeInfo
	 * @return
	 */
	@AutoLog(value = "节点信息-添加")
	@ApiOperation(value="节点信息-添加", notes="节点信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PipeInfo pipeInfo) {
		pipeInfoService.save(pipeInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pipeInfo
	 * @return
	 */
	@AutoLog(value = "节点信息-编辑")
	@ApiOperation(value="节点信息-编辑", notes="节点信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PipeInfo pipeInfo) {
		pipeInfoService.updateById(pipeInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "节点信息-通过id删除")
	@ApiOperation(value="节点信息-通过id删除", notes="节点信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pipeInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "节点信息-批量删除")
	@ApiOperation(value="节点信息-批量删除", notes="节点信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pipeInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "节点信息-通过id查询")
	@ApiOperation(value="节点信息-通过id查询", notes="节点信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PipeInfo pipeInfo = pipeInfoService.getById(id);
		if(pipeInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(pipeInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pipeInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PipeInfo pipeInfo) {
        return super.exportXls(request, pipeInfo, PipeInfo.class, "节点信息");
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
        return super.importExcel(request, response, PipeInfo.class);
    }

}
