package com.cardpay.pccredit.intopieces.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.SFTPUtil;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCy;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.LocalExcelDao;
import com.cardpay.pccredit.intopieces.dao.LocalImageDao;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.Dcbzlr;
import com.cardpay.pccredit.intopieces.model.Dcddpz;
import com.cardpay.pccredit.intopieces.model.Dclrjb;
import com.cardpay.pccredit.intopieces.model.Dclsfx;
import com.cardpay.pccredit.intopieces.model.Dcsczt;
import com.cardpay.pccredit.intopieces.model.Dzjbzk;
import com.cardpay.pccredit.intopieces.model.Dzjy;
import com.cardpay.pccredit.intopieces.model.Dzjyzt;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.LocalImage;
import com.cardpay.pccredit.intopieces.model.Pic;
import com.cardpay.pccredit.intopieces.model.PicPojo;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentBatch;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentDetail;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentList;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.intopieces.web.AddIntoPiecesForm;
import com.cardpay.pccredit.intopieces.web.LocalExcelForm;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
import com.cardpay.pccredit.manager.model.BatchTask;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.pccredit.tools.DataFileConf;
import com.cardpay.pccredit.tools.ImportBankDataFileTools;
import com.cardpay.pccredit.tools.JXLReadExcel;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class AddIntoPiecesService {

	// TODO 路径使用相对路径，首先获得应用所在路径，之后建立上传文件目录，图片类型使用IMG，文件使用DOC

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private LocalExcelDao localExcelDao;
	
	@Autowired
	private LocalImageDao localImageDao;
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private CustomerInforDao customerInforDao;

	@Autowired
	private NodeAuditService nodeAuditService;
	
	@Autowired
	private ProcessService processService;
	
	/* 查询调查报告信息 */
	public QueryResult<LocalExcelForm> findLocalExcelByProductAndCustomer(AddIntoPiecesFilter filter) {
		List<LocalExcelForm> ls = localExcelDao.findByProductAndCustomer(filter);
		int size = localExcelDao.findCountByProductAndCustomer(filter);
		QueryResult<LocalExcelForm> qr = new QueryResult<LocalExcelForm>(size,ls);
		return qr;
	}
	
	public QueryResult<LocalExcelForm> findLocalExcelByProductAndCustomer1(AddIntoPiecesFilter filter) {
		List<LocalExcelForm> ls = localExcelDao.findByProductAndCustomer1(filter);
		int size = localExcelDao.findCountByProductAndCustomer(filter);
		QueryResult<LocalExcelForm> qr = new QueryResult<LocalExcelForm>(size,ls);
		return qr;
	}

	//导入调查报告
	public void importExcel(MultipartFile file,String productId, String customerId) {
		// TODO Auto-generated method stub
		//本地测试
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file,customerId);
		//指定服务器上传
		//Map<String, String> map = SFTPUtil.uploadJn(file, customerId);
		String fileName = map.get("fileName");
		String url = map.get("url");
		LocalExcel localExcel = new LocalExcel();
		localExcel.setProductId(productId);
		localExcel.setCustomerId(customerId);
		localExcel.setCreatedTime(new Date());
		if (StringUtils.trimToNull(url) != null) {
			localExcel.setUri(url);
		}
		if (StringUtils.trimToNull(fileName) != null) {
			localExcel.setAttachment(fileName);
		}
		
		//读取excel内容
		JXLReadExcel readExcel = new JXLReadExcel();
		//本地测试
		String sheet[] = readExcel.readExcelToHtml1(url, true);
		//服务器
		//String sheet[] = SFTPUtil.readExcelToHtml(url, true);
		for(String str : sheet){
			if(StringUtils.isEmpty(str)){
				throw new RuntimeException("导入失败，请检查excel文件与模板是否一致！");
			}
		}
		/*localExcel.setSheetJy(sheet[0]);
		localExcel.setSheetJbzk(sheet[1]);
		localExcel.setSheetJyzt(sheet[2]);
		localExcel.setSheetSczt(sheet[3]);
		localExcel.setSheetDdpz(sheet[4]);
		localExcel.setSheetFz(sheet[5]);
		localExcel.setSheetLrjb(sheet[6]);
		localExcel.setSheetBzlrb(sheet[7]);
		localExcel.setZyyw(sheet[8]);
		localExcel.setSheetXjllb(sheet[9]);
		localExcel.setSheetJc(sheet[10]);
		localExcel.setSheetDhd(sheet[11]);
		localExcel.setSheetGdzc(sheet[12]);
		localExcel.setSheetYfys(sheet[13]);
		localExcel.setSheetYsyf(sheet[14]);
		localExcel.setSheetLsfx(sheet[15]);
		localExcel.setApproveValue(sheet[17]);
		localExcel.setJyb(sheet[16]);*/
		localExcel.setSheetJy(sheet[0]);
		localExcel.setSheetJbzk(sheet[1]);
		localExcel.setSheetFz(sheet[2]);
		localExcel.setSheetBzlrb(sheet[3]);
		localExcel.setSheetXjllb(sheet[4]);
		localExcel.setSheetJc(sheet[5]);
		localExcel.setSheetGdzc(sheet[6]);
		localExcel.setSheetYfys(sheet[7]);
		localExcel.setSheetYsyf(sheet[8]);
		localExcel.setJyb(sheet[9]);
		localExcel.setApproveValue(sheet[10]);
		//删除旧模板
		String sql = "delete from local_excel where customer_id='"+customerId+"' and product_id='"+productId+"'";
		commonDao.queryBySql(LocalExcel.class, sql, null);
		//添加模板
		commonDao.insertObject(localExcel);
	}
	
	//补充调查模板先删除原有的调查模板信息再新增
	public void importExcelSupple(MultipartFile file,String productId, String customerId,String appId) {
		// TODO Auto-generated method stub
		//本地
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file,customerId);
		//指定服务器上传
		//Map<String, String> map = SFTPUtil.uploadJn(file, customerId);
		String fileName = map.get("fileName");
		String url = map.get("url");
		//删除
		localImageDao.deleteByProductIdAndCustomerId(productId,customerId);
		
		LocalExcel localExcel = new LocalExcel();
		localExcel.setProductId(productId);
		localExcel.setCustomerId(customerId);
		localExcel.setCreatedTime(new Date());
		localExcel.setApplicationId(appId);
		
		if (StringUtils.trimToNull(url) != null) {
			localExcel.setUri(url);
		}
		if (StringUtils.trimToNull(fileName) != null) {
			localExcel.setAttachment(fileName);
		}
		
		//读取excel内容
		JXLReadExcel readExcel = new JXLReadExcel();
		//本地测试
		String sheet[] = readExcel.readExcelToHtml1(url, true);
		//服务器
		//String sheet[] = SFTPUtil.readExcelToHtml(url, true);
		for(String str : sheet){
			if(StringUtils.isEmpty(str)){
				throw new RuntimeException("导入失败，请检查excel文件与模板是否一致！");
			}
		}
		localExcel.setSheetJy(sheet[0]);
		localExcel.setSheetJbzk(sheet[1]);
		localExcel.setSheetFz(sheet[2]);
		localExcel.setSheetBzlrb(sheet[3]);
		localExcel.setSheetXjllb(sheet[4]);
		localExcel.setSheetJc(sheet[5]);
		localExcel.setSheetGdzc(sheet[6]);
		localExcel.setSheetYfys(sheet[7]);
		localExcel.setSheetYsyf(sheet[8]);
		localExcel.setJyb(sheet[9]);
		localExcel.setApproveValue(sheet[10]);
		
		//添加模板
		commonDao.insertObject(localExcel);
	
	}

	/* 查询影像资料信息 */
	public QueryResult<LocalImageForm> findLocalImageByProductAndCustomer(AddIntoPiecesFilter filter) {
		List<LocalImageForm> ls = localImageDao.findByProductAndCustomer(filter);
		int size = localImageDao.findCountByProductAndCustomer(filter);
		QueryResult<LocalImageForm> qr = new QueryResult<LocalImageForm>(size,ls);
		return qr;
	}

	/* 查询影像资料信息 */
	public QueryResult<LocalImageForm> findLocalImageByApplication(AddIntoPiecesFilter filter) {
		List<LocalImageForm> ls = localImageDao.findByApplication(filter);
		int size = localImageDao.findCountByApplication(filter);
		QueryResult<LocalImageForm> qr = new QueryResult<LocalImageForm>(size,ls);
		return qr;
	}
	
	/* 查询影像资料信息 */
	public LocalImage findLocalImageByApplication(String id) {
		LocalImage localImage = localImageDao.findById(id);
		return localImage;
	}
	
	/* 删除资料信息 */
	public void deleteImage(String id) {
		commonDao.deleteObject(LocalImage.class, id);
	}
	
	public void importImage(MultipartFile file, String productId,
			String customerId,String applicationId) {
		//本地测试
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file,customerId);
		//指定服务器上传
		//Map<String, String> map = SFTPUtil.uploadJn(file, customerId);
		String fileName = map.get("fileName");
		String url = map.get("url");
		LocalImage localImage = new LocalImage();
		localImage.setProductId(productId);
		localImage.setCustomerId(customerId);
		if(applicationId != null){
			localImage.setApplicationId(applicationId);
		}
		localImage.setCreatedTime(new Date());
		if (StringUtils.trimToNull(url) != null) {
			localImage.setUri(url);
		}
		if (StringUtils.trimToNull(fileName) != null) {
			localImage.setAttachment(fileName);
		}
		
		commonDao.insertObject(localImage);
	}

	public void addIntopieces(AddIntoPiecesForm addIntoPiecesForm,String userId) {
		// TODO Auto-generated method stub
		CustomerApplicationInfo app = new CustomerApplicationInfo();
		app.setCustomerId(addIntoPiecesForm.getCustomerId());
		app.setProductId(addIntoPiecesForm.getProductId());
		app.setCreatedBy(userId);
		app.setCreatedTime(new Date());
		app.setStatus(Constant.APPROVE_INTOPICES);
		app.setId(IDGenerator.generateID());
		
		//将调查表和影像件 关联到该app
		LocalExcel localExcel = localExcelDao.findById(addIntoPiecesForm.getExcelId());
		localExcel.setApplicationId(app.getId());
		//保存申请额度
		app.setApplyQuota(localExcel.getApproveValue());
		//保存进件表
		commonDao.insertObject(app);
		commonDao.updateObject(localExcel);	
		List<LocalImage> ls = localImageDao.findAllByProductAndCustomer(addIntoPiecesForm.getProductId(),addIntoPiecesForm.getCustomerId());
		for(LocalImage obj : ls){
			obj.setApplicationId(app.getId());
			commonDao.updateObject(obj);
		}
		
		//添加流程 20160328 sc
		addProcess(app.getId(),addIntoPiecesForm.getProductId());
	}
	
	public void addProcess(String appId,String productId){
		//添加申请件流程
		WfProcessInfo wf=new WfProcessInfo();
		wf.setProcessType(WfProcessInfoType.process_type);
		wf.setVersion("1");
		commonDao.insertObject(wf);
		List<NodeAudit> list=nodeAuditService.findByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(),productId);
		boolean startBool=false;
		boolean endBool=false;
		//节点id和WfStatusInfo id的映射
		Map<String, String> nodeWfStatusMap = new HashMap<String, String>();
		for(NodeAudit nodeAudit:list){
			if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
				startBool=true;
			}
			
			if(startBool&&!endBool){
				WfStatusInfo wfStatusInfo=new WfStatusInfo();
				wfStatusInfo.setIsStart(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())?"1":"0");
				wfStatusInfo.setIsClosed(nodeAudit.getIsend().equals(YesNoEnum.YES.name())?"1":"0");
				wfStatusInfo.setRelationedProcess(wf.getId());
				wfStatusInfo.setStatusName(nodeAudit.getNodeName());
				wfStatusInfo.setStatusCode(nodeAudit.getId());
				commonDao.insertObject(wfStatusInfo);
				
				nodeWfStatusMap.put(nodeAudit.getId(), wfStatusInfo.getId());
				
				if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
					//添加初始审核
					CustomerApplicationProcess customerApplicationProcess=new CustomerApplicationProcess();
					String serialNumber = processService.start(wf.getId());
					customerApplicationProcess.setSerialNumber(serialNumber);
					customerApplicationProcess.setNextNodeId(nodeAudit.getId()); 
					customerApplicationProcess.setApplicationId(appId);
					commonDao.insertObject(customerApplicationProcess);
					
					CustomerApplicationInfo applicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, appId);
					applicationInfo.setSerialNumber(serialNumber);
					commonDao.updateObject(applicationInfo);
				}
			}
			
			if(nodeAudit.getIsend().equals(YesNoEnum.YES.name())){
				endBool=true;
			}
		}
		//节点关系
		List<NodeControl> nodeControls = nodeAuditService.findNodeControlByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(), productId);
		for(NodeControl control : nodeControls){
			WfStatusResult wfStatusResult = new WfStatusResult();
			wfStatusResult.setCurrentStatus(nodeWfStatusMap.get(control.getCurrentNode()));
			wfStatusResult.setNextStatus(nodeWfStatusMap.get(control.getNextNode()));
			wfStatusResult.setExamineResult(control.getCurrentStatus());
			commonDao.insertObject(wfStatusResult);
		}
	}
	
	public LocalExcel findLocalEXcelByApplication(String appId){
		return localExcelDao.findByApplication(appId);
	}

	public void change_localExcel(LocalExcel localExcel) {
		// TODO Auto-generated method stub
		commonDao.updateObject(localExcel);
	}
	
	/**
	 * 下载客户影像资料
	 * @param id
	 * @throws Exception 
	 */
	public void downLoadYxzlById(HttpServletResponse response,String id) throws Exception{
		LocalImage v = commonDao.findObjectById(LocalImage.class, id);
		if(v!=null){
			//本地测试
			UploadFileTool.downLoadFile(response, v.getUri(), v.getAttachment());
			String url = v.getUri();
			if(url.contains("pccreditFile")){
				UploadFileTool.downLoadFile(response, v.getUri(), v.getAttachment());
			}else{
				SFTPUtil.download(response, v.getUri(), v.getAttachment());
			}
			//服务器
			//SFTPUtil.download(response, v.getUri(), v.getAttachment());
		}
	}
	
	public void downLoadYxzlJn(HttpServletResponse response,String id) throws Exception{
		QzApplnAttachmentDetail v = commonDao.findObjectById(QzApplnAttachmentDetail.class, id);
		if(v!=null){
			//本地测试
			UploadFileTool.downLoadFile(response,v.getUrl(), v.getFileName()==null?v.getOriginalName():v.getFileName());
			//服务器
			//SFTPUtil.download(response,v.getUrl(), v.getFileName()==null?v.getOriginalName():v.getFileName());
		}
	}
	
	//save jy
	public void saveJy(Dzjy dzjy){
		commonDao.insertObject(dzjy);
	}
	
	//update jy
    public void updateJy(Dzjy dzjy){
    	commonDao.updateObject(dzjy);
	}
	
	//查询jy
	public Dzjy findLocalImageByApplication(String customerId,String productId) {
		Dzjy dzjy = localImageDao.findJy(customerId,productId);
		return dzjy;
	}
	
	//查询 基本状况
	public Dzjbzk  findDzjbzk(String customerId,String productId){
		Dzjbzk jbzk = localImageDao.findDzjbzk(customerId,productId);
		return jbzk;
	}
	
	//save jbzk
	public void saveJbzk(Dzjbzk jbzk){
		commonDao.insertObject(jbzk);
	}
	
	//update jbzk
	public void updateJbzk(Dzjbzk jbzk){
		commonDao.updateObject(jbzk);
	}
	
	
	//查询经营状态
	public Dzjyzt  findDzjyzt(String customerId,String productId){
		Dzjyzt jyzt = localImageDao.findDzjyzt(customerId,productId);
		return jyzt;
	}
	
	//save 
	public void saveJyzt(Dzjyzt jyzt){
		commonDao.insertObject(jyzt);
	}
	
	//update
	public void updateJyzt(Dzjyzt jyzt){
		commonDao.updateObject(jyzt);
    }
	
	//查询生存状态
	public Dcsczt findDcsczt(String customerId,String productId){
		Dcsczt  sczt = localImageDao.findDcsczt(customerId,productId);
	    return sczt;
	}
	
	//save 
	public void saveSczt(Dcsczt sczt){
		commonDao.insertObject(sczt);
	}
	
	//update
	public void updateSczt(Dcsczt sczt){
		commonDao.updateObject(sczt);
    }
	
	
	//查询道德品质
	public Dcddpz findDcddpz(String customerId,String productId){
		Dcddpz  ddpz = localImageDao.findDcddpz(customerId,productId);
	    return ddpz;
	}
	
	//save 
	public void saveDdpz(Dcddpz ddpz){
		commonDao.insertObject(ddpz);
	}
	
	//update
	public void updateDdpz(Dcddpz ddpz){
		commonDao.updateObject(ddpz);
    }
	
	//查询利润简表
	public Dclrjb findDclrjb(String customerId,String productId){
		Dclrjb  lrjb = localImageDao.findDclrjb(customerId,productId);
	    return lrjb;
	}
	//save
	public void saveDclrjb(Dclrjb lrjb){
		commonDao.insertObject(lrjb);
	}
	
	//update
	public void updateDclrjb(Dclrjb lrjb){
		commonDao.updateObject(lrjb);
	}
	
	//查询标准利润表
	public Dcbzlr findDcbzlr(String customerId,String productId){
		Dcbzlr  bzlr = localImageDao.findDcbzlr(customerId,productId);
	    return bzlr;
	}
	
	//save
	public void saveDcbzlr(Dcbzlr bzlr){
		commonDao.insertObject(bzlr);
	}
	
	//update
	public void updateDcbzlr(Dcbzlr bzlr){
		commonDao.updateObject(bzlr);
	}
	
	
	//查询流水分析
	public Dclsfx findDclsfx(String customerId,String productId){
		Dclsfx  lsfx = localImageDao.findDclsfx(customerId,productId);
	    return lsfx;
	}
	
	//save
	public void saveDclsfx(Dclsfx lsfx){
		commonDao.insertObject(lsfx);
	}
	
	//update
	public void updateDclsfx(Dclsfx lsfx){
		commonDao.updateObject(lsfx);
	}
	
	
	//导入调查报告
	public void importTxt(MultipartFile file) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Map<String, String> map = UploadFileTool.uploadTxtFileBySpring(file);
		String fileName = map.get("fileName");
		String url = map.get("url");
		try{
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/batchTask.xml");
			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseTextDataFile(url, confList,"");
			for(Map<String, Object> mapList : datas){
				BatchTask  task = new BatchTask();
				task.setBatchCode(mapList.get("batchCode").toString());
				task.setBatchName(mapList.get("batchName").toString());
				task.setStatus(mapList.get("status").toString());
				task.setCreatedTime(sdf.parse(mapList.get("createdTime").toString()));
				commonDao.insertObject(task);
				//customerInforDao.insertBatchTask(mapList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 差错数据处理
	 * @param sql
	 */
	public void dataErrorProceeExcute(String sql){
		Map<String, Object> params = new HashMap<String, Object>();
		commonDao.queryBySql(sql, params);
	}
	
	public void doMethod(String applicationId){
		CustomerApplicationInfo applicationInfo= commonDao.findObjectById(CustomerApplicationInfo.class, applicationId);
		//更新状态为--audit
		applicationInfo.setStatus("audit");
		commonDao.updateObject(applicationInfo);
	}
	
	public QzApplnAttachmentList findAttachmentListByAppId(String applicationId) {
		return localImageDao.findAttachmentListByAppId(applicationId);
	}
	
	
	public List<QzApplnAttachmentBatch> findAttachmentBatchByAppId(String applicationId) {
		return localImageDao.findAttachmentBatchByAppId(applicationId);
	}
	
	public void addBatchInfo(String appId){
		QzApplnAttachmentList att = this.findAttachmentListByAppId(appId);
		if(att != null){
			for(int i=0 ; i<=30 ; i++){
				if(att.getBussType().equals("1")){
					if(att.getChkValue() != null && !att.getChkValue().equals("")){
						if((Integer.parseInt(att.getChkValue()) & (int)Math.pow(2, i)) == (int)Math.pow(2, i)){
							QzApplnAttachmentBatch batch = new QzApplnAttachmentBatch();
							batch.setAttId(att.getId());
							batch.setName(Constant.ATT_BATCH_1.get((int)Math.pow(2, i)));
							batch.setType((int)Math.pow(2, i)+"");
							commonDao.insertObject(batch);
						}
					}
				}
			}
		}
	}
	
	
	public String findBatchId(String batch_id){
		String sql = "select * from QZ_APPLN_ATTACHMENT_LIST where id in "
				+ "(select att_id from QZ_APPLN_ATTACHMENT_BATCH where id ='"+batch_id+"')";
		return  commonDao.queryBySql(QzApplnAttachmentList.class, sql, null).get(0).getApplicationId();
	}
	
	
	public CustomerInfor findBasicCustomerInfor(String custId){
		String sql = "select * from basic_customer_information where id ='"+custId+"'";
		return  commonDao.queryBySql(CustomerInfor.class, sql, null).get(0);
	}
	//浏览文件并缓存到服务器目录
	public void browse_folder(MultipartFile file,String batch_id) throws Exception {
		//Map<String, String> map  = UploadFileTool.uploadYxzlFileBySpring_qz(file,batch_id);
		Map<String, String> map = SFTPUtil.uploadYxzlFileBySpring_qz(file,batch_id);
		String newFileName = map.get("newFileName");
		String url = map.get("url");
		QzApplnAttachmentDetail detail = new QzApplnAttachmentDetail();
		detail.setBatchId(batch_id);
		detail.setOriginalName(file.getOriginalFilename());
		detail.setFileName(newFileName);
		detail.setPicSize(file.getSize() + "");
		detail.setUrl(url);
		commonDao.insertObject(detail);
	}
	
	public void browse_folder_complete(String batch_id,HttpServletRequest request){
		//将is_upload 置为0
		String sql = "update QZ_APPLN_ATTACHMENT_BATCH set is_upload = '1' where id='"+batch_id+"'";
		commonDao.queryBySql(sql, null);
	}
	
	
	public QueryResult<QzApplnAttachmentDetail> display_detail(IntoPiecesFilter filter) {
		List<QzApplnAttachmentDetail> pList = localImageDao.findDetailByFilter(filter);
		int size = localImageDao.findDetailCountByFilter(filter);
		QueryResult<QzApplnAttachmentDetail> queryResult = new QueryResult<QzApplnAttachmentDetail>(size, pList);
		return queryResult;
	}

	public QueryResult<PicPojo> display_server(IntoPiecesFilter filter,HttpServletRequest request)  {
		// TODO Auto-generated mfilter.getBatchId()ethod stub
		String sql = "select * from QZ_APPLN_ATTACHMENT_BATCH where id = '"+filter.getBatchId()+"'";
		QzApplnAttachmentBatch batch = commonDao.queryBySql(QzApplnAttachmentBatch.class, sql, null).get(0);
		sql = "select * from QZ_APPLN_ATTACHMENT_LIST where id = '"+batch.getAttId()+"'";
		QzApplnAttachmentList att = commonDao.queryBySql(QzApplnAttachmentList.class, sql, null).get(0);
		
		String xmlStr = null;
		/*if(filter.getFirst_flag() != null && filter.getFirst_flag().equals("1")){
			request.getSession().setAttribute(filter.getBatchId(), null);
		}
		String sessionTmp = request.getSession().getAttribute(filter.getBatchId())==null?null:request.getSession().getAttribute(filter.getBatchId()).toString();
		if(StringUtils.isEmpty(sessionTmp)){
			xmlStr = sundsHelper.queryBatchFile(filter.getBatchId(),att.getDocid() + batch.getType());
			request.getSession().setAttribute(filter.getBatchId(), xmlStr);
		}
		else{
			xmlStr = sessionTmp;
		}
		Pic pic = sundsHelper.parseXml(xmlStr, filter.getPage(), filter.getLimit(),request,filter.getBatchId(),filter.getFirst_flag());
		*/
		Pic pic = this.parseXml(xmlStr, filter.getPage(), filter.getLimit(),request,filter.getBatchId(),filter.getFirst_flag());
		List<PicPojo> pList = pic.getPics();
		int size = pic.getTotalCount();
		QueryResult<PicPojo> queryResult = new QueryResult<PicPojo>(size, pList);
		
		return queryResult;
	}
	
	public Pic parseXml(String xmlStr,int page,int limit,HttpServletRequest request,String sessionDocId,String First_flag) {
		List<PicPojo> pics = new ArrayList<PicPojo>();
		Pic pic = new Pic();
		PicPojo pojo = new PicPojo();
		pojo.setDoc_id("");
		pojo.setFile_name("");
		pojo.setFile_no("");
		pojo.setUrl("/usr/pccreditFile/bda2c4cb55b9196f0155b92548790021/295641646244730420.jpg");
		pojo.setPic_size("");
		pics.add(pojo);
		pic.setTotalCount(2);
		pic.setPics(pics);
		return pic;
	}
	
	
	
	public void delete_batch(String batchId,HttpServletRequest request) {
		String sql = "select * from QZ_APPLN_ATTACHMENT_BATCH where id = '"+batchId+"'";
		QzApplnAttachmentBatch batch = commonDao.queryBySql(QzApplnAttachmentBatch.class, sql, null).get(0);
		sql = "select * from QZ_APPLN_ATTACHMENT_LIST where id = '"+batch.getAttId()+"'";
		QzApplnAttachmentList att = commonDao.queryBySql(QzApplnAttachmentList.class, sql, null).get(0);
		String docId = att.getDocid() + batch.getType();
		
		//sundsHelper.delBatch(batchId, docId);
		
		//删除对应detail
		sql = "delete from QZ_APPLN_ATTACHMENT_DETAIL where batch_id = '"+batchId+"'";
		commonDao.queryBySql(sql, null);
		//将att的upload_value减去对应的批次值
		int tmp = Integer.parseInt(att.getUploadValue())-Integer.parseInt(batch.getType());
		att.setUploadValue(tmp + "");
		commonDao.updateObject(att);
		//将对应batch的is_upload状态置为null
		sql = "update QZ_APPLN_ATTACHMENT_BATCH set is_upload = null where ID = '"+batchId+"'";
		commonDao.queryBySql(sql, null);
		
		
		request.getSession().setAttribute(batchId, null);
	}
	
	
}
