package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentBatch;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentList;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnpadImageBrowseService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadImageBrowseController {
	
	@Autowired
	private JnpadImageBrowseService jnpadImageBrowseService ;
	
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	@Autowired
	private CommonDao commonDao;
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/uploadYx.json", method = { RequestMethod.GET })
	public String display_server(HttpServletRequest request) {
		
		List<LocalImageForm> imagerList = jnpadImageBrowseService.findLocalImage(request.getParameter("imageClasses"),request.getParameter("customerId"),request.getParameter("productId"));
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("imagerList",imagerList);
		map.put("size",imagerList.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/uploadYxPc.json", method = { RequestMethod.GET })
	public String display_server_pc(HttpServletRequest request) {
		String batchId =request.getParameter("batchId");
		List<String> imagerList = jnpadImageBrowseService.findLocalImagePc(request.getParameter("customerId"),request.getParameter("applicationId"),batchId);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("imagerList",imagerList);
		map.put("size",imagerList.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/downLoadYxzlJn.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.EXPORT)
	public AbstractModelAndView downLoadYxzlJn(HttpServletRequest request,HttpServletResponse response){
		try {
			String s =request.getParameter("id");
			
			jnpadImageBrowseService.downLoadYxzlJn(response,s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//查看pc端上传图片
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/downLoadYxzlJnPc.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.EXPORT)
	public AbstractModelAndView downLoadYxzlJnPc(HttpServletRequest request,HttpServletResponse response){
		try {
			String s =request.getParameter("url");
			
			jnpadImageBrowseService.downLoadYxzlJnPc(response,s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//删除已上传图片
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/deleteImage.json", method = { RequestMethod.GET })
	public String deleteImage(HttpServletRequest request) {
		String imageId =request.getParameter("imageId");
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		try {
		jnpadImageBrowseService.deleteImage(imageId);
		map.put("mess", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mess", "删除失败");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	//查询pc图片分支列表
			@ResponseBody
			@RequestMapping(value = "/ipad/JnpadImageBrowse/findPcImageBatch.json")
			public String sunds_ocx(HttpServletRequest request) {
				Map<String,Object> map = new LinkedHashMap<String,Object>();
				String appId = RequestHelper.getStringValue(request, "appId");
				String custId = RequestHelper.getStringValue(request, "custId");
				map.put("appId", appId);
				
				QzApplnAttachmentList att = addIntoPiecesService.findAttachmentListByAppId(appId);
				if(att==null){
					QzApplnAttachmentList attlist = new QzApplnAttachmentList();
					attlist.setApplicationId(appId);
					attlist.setCustomerId(custId);
					attlist.setChkValue("19");
					commonDao.insertObject(attlist);
				}
				//查找sunds_ocx信息
				List<QzApplnAttachmentBatch> batch_ls = addIntoPiecesService.findAttachmentBatchByAppId(appId);
				//如果batch_ls为空 说明这是以前录得件 根据chk_value增加batch记录
				if(batch_ls == null || batch_ls.size() == 0){
					addIntoPiecesService.addBatchInfo(appId,custId);
					batch_ls = addIntoPiecesService.findAttachmentBatchByAppId(appId);
				}
				//查询客户信息
				CustomerInfor vo = addIntoPiecesService.findBasicCustomerInfor(custId);
				map.put("batch_ls", batch_ls);
				map.put("customerInfor",vo);
				map.put("appId",appId);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(map, jsonConfig);
				return json.toString();
			}
}
