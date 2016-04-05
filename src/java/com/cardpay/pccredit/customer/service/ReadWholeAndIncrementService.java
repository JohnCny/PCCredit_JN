package com.cardpay.pccredit.customer.service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.comdao.ReadWholeAndIncrementComdao;
import com.cardpay.pccredit.tools.CardFtpUtils;
import com.cardpay.pccredit.tools.DataFileConf;
import com.cardpay.pccredit.tools.ImportBankDataFileTools;
import com.cardpay.pccredit.tools.SPTxt;
import com.cardpay.pccredit.tools.UpdateCustomerTool;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * @author sc
 * 读取下发数据 文件 
 * 全量和增量的分开读取
 */
@Service
public class ReadWholeAndIncrementService {
	public Logger log = Logger.getLogger(UpdateCustomerTool.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private ReadWholeAndIncrementComdao andIncrementComdao;
	
	@Autowired
	CustomerInforService  customerInforService;
	/*//客户原始信息
	private String[] fileTxt = {"kkh_grxx.txt","kkh_grjtcy.txt","kkh_grjtcc.txt","kkh_grscjy.txt","kkh_grxxll.txt","kkh_grgzll.txt","kkh_grrbxx.txt","cxd_dkcpmc.txt","kkh_hmdgl.txt","cxd_rygl.txt"};
	*/
	//流水账、余额汇总表、借据表
	private String[] fileTxtRepay ={"kdk_yehz.txt","kdk_lsz.txt","kdk_tkmx.txt"};
	
	private String[] fileTxt = {"t_cclmtapplyinfo.txt","t_cipersonfamily.txt"};
	/**
	 * 解析增量数据
	 * 济南
	 *  @throws IOException 
	 */
	public void readFileIncrement() throws IOException{
		//获取今日日期 yyyyMMdd格式
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
									if(fn.startsWith("t_cclmtapplyinfo")){
										log.info("*****************Cc授信申请基本信息（结果表）********************");  
										//customerInforService.saveCCLMTAPPLYINFODataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************对私客户不良记录********************");
										customerInforService.saveCIPERSONBADRECORDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************对私客户基本信息********************");
										customerInforService.saveCIPERSONBASINFODataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_cipersonfamily")){
										log.info("*****************对私家庭成员信息********************");
										//customerInforService.saveCIPERSONFAMILYDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************借据月末余额表（结果表）********************");
										customerInforService.saveFCLOANINFODataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************认定结果表（历史表）********************");
										customerInforService.saveFCRESULTHISDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************五级分类统计表********************");
										customerInforService.saveFCSTATISTICSDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************GC担保对应表********************");
										customerInforService.saveGCASSURECORRESPONDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************GC担保信息表********************");
										saveGCASSUREMAINDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************GC合同基本表********************");
										saveGCCONTRACTMAINDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************GC从合同多方信息表********************");
										saveGCASSUREMULTICLIENTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************GC押品主表********************");
										saveGCGUARANTYMAINDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************Gc贷款证表 ********************");
										saveGCLOANCARDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************Gc贷款证合同关联关系表 ********************");
										saveGCLOANCARDCONTRACTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************Gc凭证信息表 ********************");
										saveGCLOANCREDITDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************台账——综合业务信息表  ********************");
										saveMIBUSIDATADataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************台账——贷款卡片********************");
										saveMILOANCARDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************黑名单客户结果表 ********************");
										saveBWLISTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************还款情况表  ********************");
										saveRAREPAYLISTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************不良贷款信息  ********************");
										saveSPECIALASSETDataFile(gzFile+File.separator+fn,dateString);
									}
								} 
							}catch(Exception e){
								e.printStackTrace();
								throw new RuntimeException(e);
							}
						}
						//f.delete();
				}
	        }
	        log.info(dateString+"******************完成读取原始信息文件********************");

	}
	
	
	/**
	 * 清空表数据
	 * 济南
	 * @param fileN
	 */
    private void deleteTableDatas(String fileN){
    	if(fileN.startsWith("")){
			//删除【参数字典列表】历史数据
			String sql = " truncate   table   T_PARAM_PARAM";
			commonDao.queryBySql(sql, null);
		}
		if(fileN.startsWith("")){
			//删除【参数字典基本信息表】历史数据
			String sql = " truncate   table   T_PARAM_CLASS";
			commonDao.queryBySql(sql, null);
		}
		if(fileN.startsWith("")){
			//删除【客户类型表】历史数据
			String sql = " truncate   table   T_PARTY_TYPE";
			commonDao.queryBySql(sql, null);
		}
		
		if(fileN.startsWith("")){
			//删除【机构表】历史数据
			String sql = " truncate   table   T_RBAC_GROUP";
			commonDao.queryBySql(sql, null);
		}
		
		if(fileN.startsWith("")){
			//删除【客户类型表】历史数据
			String sql = " truncate   table   T_RBAC_USER";
			commonDao.queryBySql(sql, null);
		}
    }
    
	
	/**
	 *  解析全量数据
	 *  济南
	 *  @throws IOException 
	 */
	public void readFileWhole() throws IOException{
		//获取今日日期 yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		String gzFile = CardFtpUtils.bank_ftp_down_path+dateString;

		log.info(dateString+"******************开始读取全量数据文件********************");  
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
					
					//清空表数据
					this.deleteTableDatas(fileN);
					
					for(String fn : spFile){
						try{
							if(fn.contains(fileN)) {
								if(fn.startsWith("")){
									log.info("*****************参数字典基本信息表 【T_PARAM_CLASS】********************");  
									this.saveParamClassDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("")){
									log.info("*****************参数字典列表【T_PARAM_PARAM】********************"); 
									this.saveParamParmDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("")){
									log.info("*****************客户类型表【T_PARTY_TYPE】********************");
									this.saveParamTypeDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("")){
									log.info("*****************机构表【T_RBAC_GROUP】 ********************");
									this.saveRbacGroupDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("")){
									log.info("*****************用户表【T_RBAC_USER】********************");
									this.saveRbacUserDataFile(gzFile+File.separator+fn,dateString);
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
        log.info(dateString+"******************完成读取全量数据文件********************");
	}
	
	
	
	
	
	
//=========================================【全量start】==================================================================================================//
	
	/**
	 * 参数字典基本信息表
	 * @param fileName
	 * @param date
	 */
	public void saveParamClassDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_PARAM_CLASS.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertParamClass(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 参数字典列表
	 * @param fileName
	 * @param date
	 */
	public void saveParamParmDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_PARAM_PARAM.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertParamParm(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 客户类型表
	 * @param fileName
	 * @param date
	 */
	public void saveParamTypeDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_PARAM_TYPE.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertParamType(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 机构表
	 * @param fileName
	 * @param date
	 */
	public void saveRbacGroupDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_RBAC_GROUP.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertRbacGroup(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 用户表
	 * @param fileName
	 * @param date
	 */
	public void saveRbacUserDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_RBAC_USER.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertRbacUser(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//=========================================【全量end】==================================================================================================//
	
	
	
	
	
	
	
//=========================================【增量start】==================================================================================================//
	/**
	 * GC担保信息表
	 * T_GCASSUREMAIN
	 * @param fileName
	 * @param date
	 */
	public void saveGCASSUREMAINDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCASSUREMAIN.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCASSUREMAIN(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * T_GCCONTRACTMAIN
	 * GC合同基本表
	 * @param fileName
	 * @param date
	 */
	public void saveGCCONTRACTMAINDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCCONTRACTMAIN.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCCONTRACTMAIN(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * GC从合同多方信息表
	 * T_GCASSUREMULTICLIENT
	 * @param fileName
	 * @param date
	 */
	public void saveGCASSUREMULTICLIENTDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCASSUREMULTICLIENT.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCASSUREMULTICLIENT(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * GC押品主表
	 * T_GCGUARANTYMAIN
	 * @param fileName
	 * @param date
	 */
	public void saveGCGUARANTYMAINDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCGUARANTYMAIN.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCGUARANTYMAIN(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Gc贷款证表 
	 * T_GCLOANCARD
	 * @param fileName
	 * @param date
	 */
	public void saveGCLOANCARDDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCLOANCARD.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCLOANCARD(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Gc贷款证合同关联关系表
	 * T_GCLOANCARDCONTRACT
	 * @param fileName
	 * @param date
	 */
	public void saveGCLOANCARDCONTRACTDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCLOANCARDCONTRACT.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCLOANCARDCONTRACT(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	/**
	 * Gc凭证信息表
	 * T_GCLOANCREDIT
	 * @param fileName
	 * @param date
	 */
	public void saveGCLOANCREDITDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCLOANCREDIT.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCLOANCREDIT(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	/**
	 * 台账——综合业务信息表 
	 * T_MIBUSIDATA
	 * @param fileName
	 * @param date
	 */
	public void saveMIBUSIDATADataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_MIBUSIDATA.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertMIBUSIDATA(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 台账——贷款卡片
	 * T_MILOANCARD
	 * @param fileName
	 * @param date
	 */
	public void saveMILOANCARDDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_MILOANCARD.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertMILOANCARD(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 黑名单客户结果表
	 * T_PARTY_BWLIST
	 * @param fileName
	 * @param date
	 */
	public void saveBWLISTDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_PARTY_BWLIST.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertBWLIST(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 还款情况表 
	 * T_RAREPAYLIST
	 * @param fileName
	 * @param date
	 */
	public void saveRAREPAYLISTDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_PARTY_BWLIST.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertRAREPAYLIST(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 不良贷款信息
	 * T_SARM_SPECIALASSET
	 * @param fileName
	 * @param date
	 */
	public void saveSPECIALASSETDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_SARM_SPECIALASSET.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertSPECIALASSET(datas);
			// 释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
