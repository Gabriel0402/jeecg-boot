package org.jeecg.modules.pipeinfo.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.pipeinfo.entity.SensorLocInfo;
import org.jeecg.modules.pipeinfo.entity.PipeInfo;
import org.jeecg.modules.pipeinfo.vo.PipeInfoPage;
import org.jeecg.modules.pipeinfo.service.IPipeInfoService;
import org.jeecg.modules.pipeinfo.service.ISensorLocInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 管道信息
 * @Author: jeecg-boot
 * @Date:   2020-06-30
 * @Version: V1.0
 */
@Api(tags="管道信息")
@RestController
@RequestMapping("/pipeinfo/pipeInfo")
@Slf4j
public class PipeInfoController {
	@Autowired
	private IPipeInfoService pipeInfoService;
	@Autowired
	private ISensorLocInfoService sensorLocInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pipeInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "管道信息-分页列表查询")
	@ApiOperation(value="管道信息-分页列表查询", notes="管道信息-分页列表查询")
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
	 * @param pipeInfoPage
	 * @return
	 */
	@AutoLog(value = "管道信息-添加")
	@ApiOperation(value="管道信息-添加", notes="管道信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PipeInfoPage pipeInfoPage) {
		PipeInfo pipeInfo = new PipeInfo();
		BeanUtils.copyProperties(pipeInfoPage, pipeInfo);
		pipeInfoService.saveMain(pipeInfo, pipeInfoPage.getSensorLocInfoList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pipeInfoPage
	 * @return
	 */
	@AutoLog(value = "管道信息-编辑")
	@ApiOperation(value="管道信息-编辑", notes="管道信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PipeInfoPage pipeInfoPage) {
		PipeInfo pipeInfo = new PipeInfo();
		BeanUtils.copyProperties(pipeInfoPage, pipeInfo);
		PipeInfo pipeInfoEntity = pipeInfoService.getById(pipeInfo.getId());
		if(pipeInfoEntity==null) {
			return Result.error("未找到对应数据");
		}
		pipeInfoService.updateMain(pipeInfo, pipeInfoPage.getSensorLocInfoList());
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "管道信息-通过id删除")
	@ApiOperation(value="管道信息-通过id删除", notes="管道信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pipeInfoService.delMain(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "管道信息-批量删除")
	@ApiOperation(value="管道信息-批量删除", notes="管道信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pipeInfoService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "管道信息-通过id查询")
	@ApiOperation(value="管道信息-通过id查询", notes="管道信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PipeInfo pipeInfo = pipeInfoService.getById(id);
		if(pipeInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(pipeInfo);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "节点位置信息通过主表ID查询")
	@ApiOperation(value="节点位置信息主表ID查询", notes="节点位置信息-通主表ID查询")
	@GetMapping(value = "/querySensorLocInfoByMainId")
	public Result<?> querySensorLocInfoListByMainId(@RequestParam(name="id",required=true) String id) {
		List<SensorLocInfo> sensorLocInfoList = sensorLocInfoService.selectByMainId(id);
		return Result.ok(sensorLocInfoList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pipeInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PipeInfo pipeInfo) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<PipeInfo> queryWrapper = QueryGenerator.initQueryWrapper(pipeInfo, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<PipeInfo> queryList = pipeInfoService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<PipeInfo> pipeInfoList = new ArrayList<PipeInfo>();
      if(oConvertUtils.isEmpty(selections)) {
          pipeInfoList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          pipeInfoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<PipeInfoPage> pageList = new ArrayList<PipeInfoPage>();
      for (PipeInfo main : pipeInfoList) {
          PipeInfoPage vo = new PipeInfoPage();
          BeanUtils.copyProperties(main, vo);
          List<SensorLocInfo> sensorLocInfoList = sensorLocInfoService.selectByMainId(main.getId());
          vo.setSensorLocInfoList(sensorLocInfoList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "管道信息列表");
      mv.addObject(NormalExcelConstants.CLASS, PipeInfoPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("管道信息数据", "导出人:"+sysUser.getRealname(), "管道信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<PipeInfoPage> list = ExcelImportUtil.importExcel(file.getInputStream(), PipeInfoPage.class, params);
              for (PipeInfoPage page : list) {
                  PipeInfo po = new PipeInfo();
                  BeanUtils.copyProperties(page, po);
                  pipeInfoService.saveMain(po, page.getSensorLocInfoList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
    }

}
