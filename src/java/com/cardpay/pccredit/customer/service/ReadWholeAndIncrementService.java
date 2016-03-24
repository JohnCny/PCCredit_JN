package com.cardpay.pccredit.customer.service;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cardpay.pccredit.common.Dictionary;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.CustomerInforDStatusEnum;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.filter.CustomerInfoLszFilter;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.VideoAccessoriesFilter;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBaseLocal;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCc;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendManage;
import com.cardpay.pccredit.customer.model.CustomerFirsthendRygl;
import com.cardpay.pccredit.customer.model.CustomerFirsthendSafe;
import com.cardpay.pccredit.customer.model.CustomerFirsthendStudy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendWork;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.model.TyProductType;
import com.cardpay.pccredit.customer.model.TyRepayLsz;
import com.cardpay.pccredit.customer.model.TyRepayYehzVo;
import com.cardpay.pccredit.datapri.service.DataAccessSqlService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.constant.IntoPiecesException;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.ipad.model.ProductAttribute;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.pccredit.tools.CardFtpUtils;
import com.cardpay.pccredit.tools.DataFileConf;
import com.cardpay.pccredit.tools.ImportBankDataFileTools;
import com.cardpay.pccredit.tools.SPTxt;
import com.cardpay.pccredit.tools.UpdateCustomerTool;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * @author sc
 * 读取下发数据 文件 
 * 全量和增量的分开读取
 */
@Service
public class ReadWholeAndIncrementService {
	public Logger log = Logger.getLogger(UpdateCustomerTool.class);
	@Autowired
	private DataAccessSqlService dataAccessSqlService;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerInforDao customerInforDao;
	
	@Autowired
	private CustomerInforCommDao customerinforcommDao;
	
	@Autowired
	private NodeAuditService nodeAuditService;
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//客户原始信息
	private String[] fileTxt = {"kkh_grxx.txt","kkh_grjtcy.txt","kkh_grjtcc.txt","kkh_grscjy.txt","kkh_grxxll.txt","kkh_grgzll.txt","kkh_grrbxx.txt","cxd_dkcpmc.txt","kkh_hmdgl.txt","cxd_rygl.txt"};
	
	//流水账、余额汇总表、借据表
	private String[] fileTxtRepay ={"kdk_yehz.txt","kdk_lsz.txt","kdk_tkmx.txt"};
	
	
	/**
	 * 解析全量数据
	 * @throws IOException 
	 */
	public void readFile() throws IOException{
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		log.info(dateString+"******************开始读取原始信息文件********************");  
	        String gzFile = CardFtpUtils.bank_ftp_down_path+dateString;
	        for(int i=0;i<fileTxt.length;i++){
				String url = gzFile+File.separator+fileTxt[i];
				File f = new File(url);
				if(f.exists()){
						List<String> spFile = new ArrayList<String>();
						String fileN = "";
						//判断文件大小，超过50M的先分割
						if (f.exists() && f.isFile()){
							if(f.length()>20000000){
								int spCount = (int) (f.length()/20000000);
								SPTxt.splitTxt(url,spCount);
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								for(int j=0;j<spCount;j++){
									spFile.add(fileN+"_"+j+".txt");
								}
							}else{
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								spFile.add(fileN+".txt");
							}
						}
						for(String fn : spFile){
							try{
								if(fn.contains(fileN)) {
									/*if(fn.startsWith("cxd_rygl")){
										log.info("*****************人员管理参数表********************");  
										saveRyglDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}*/
									if(fn.startsWith("kkh_grxx")){
										log.info("*****************客户基本表********************");  
										saveBaseDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}
									/*if(fn.startsWith("kkh_grjtcy")){
										log.info("*****************客户家庭关系表********************");  
										saveCyDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grjtcc")){
										log.info("*****************客户家庭财产表********************");  
										saveCcDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grxxll")){
										log.info("*****************客户学习表********************");  
										saveStudyDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grgzll")){
										log.info("*****************客户工作履历表********************");  
										saveWorkDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grscjy")){
										log.info("*****************客户生产经营表********************");  
									saveManageDataFile(gzFile+File.separator+fn,dateString);
								}
									if(fn.startsWith("kkh_grrbxx")){
										log.info("*****************客户入保信息表********************");  
										saveSafeDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("cxd_dkcpmc")){
										log.info("*****************产品信息********************");  
										saveProductDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_hmdgl")){
										log.info("*****************黑名单********************");  
										saveHMDDataFile(gzFile+File.separator+fn,dateString);
									}*/
								} 
							}catch(Exception e){
								e.printStackTrace();
								throw new RuntimeException(e);
							}
						}
						f.delete();
				}
	        }
	        log.info(dateString+"******************完成读取原始信息文件********************");

	}
	
	/**
	 *解析增量数据
	 * @throws IOException 
	 */
	public void readFileRepay() throws IOException{
		//获取今日日期 yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		String gzFile = CardFtpUtils.bank_ftp_down_path+dateString;

		log.info(dateString+"******************开始读取贷款文件********************");  
        for(int i=0;i<fileTxtRepay.length;i++){
			String url = gzFile+File.separator+fileTxtRepay[i];
			File f = new File(url);
			if(f.exists()){
					List<String> spFile = new ArrayList<String>();
					String fileN = "";
					//判断文件大小，超过50M的先分割
					if (f.exists() && f.isFile()){
						if(f.length()>20000000){
							int spCount = (int) (f.length()/20000000);
							SPTxt.splitTxt(url,spCount);
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							for(int j=0;j<spCount;j++){
								spFile.add(fileN+"_"+j+".txt");
							}
						}else{
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							spFile.add(fileN+".txt");
						}
					}
					
					if(fileN.startsWith("kdk_lsz")){
						//删除流水号历史数据
						String sql = " truncate   table   ty_repay_lsz";
						commonDao.queryBySql(sql, null);
					}
					if(fileN.startsWith("kdk_yehz")){
						//删除余额汇总历史数据
						String sql = " truncate   table   ty_repay_yehz";
						commonDao.queryBySql(sql, null);
					}
					if(fileN.startsWith("kdk_tkmx")){
						//删除借据表历史数据
						String sql = " truncate   table   ty_repay_tkmx";
						commonDao.queryBySql(sql, null);
					}
					for(String fn : spFile){
						try{
							if(fn.contains(fileN)) {
								if(fn.startsWith("kdk_lsz")){
									log.info("*****************流水账信息********************");  
									//saveLSZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_yehz")){
									log.info("*****************余额汇总信息********************");  
									//saveYEHZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_tkmx")){
									log.info("*****************借据表信息********************");  
									//saveTKMXDataFile(gzFile+File.separator+fn,dateString);
								}
							} 
						}catch(Exception e){
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}
					f.delete();
			}
        }
        log.info(dateString+"******************完成读取贷款文件********************");

	}
}
