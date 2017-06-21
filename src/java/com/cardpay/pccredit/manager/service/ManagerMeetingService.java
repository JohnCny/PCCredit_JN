package com.cardpay.pccredit.manager.service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.ManagerMeetingDao;
import com.cardpay.pccredit.manager.filter.ManagerMeetingFilter;
import com.cardpay.pccredit.manager.form.ManagerPerformmanceForm;
import com.cardpay.pccredit.manager.model.InformationOfficer;
import com.cardpay.pccredit.manager.model.ManagerMeetingInfo;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class ManagerMeetingService {

	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private ManagerMeetingDao managerMeetingDao;
	
	public void insertMeetingInfo(ManagerMeetingInfo managerMeetingInfo) {
		
		commonDao.insertObject(managerMeetingInfo);
		
	}

	public QueryResult<ManagerMeetingInfo> findManagerMeetingByFilter(ManagerMeetingFilter filter) {
		List<ManagerMeetingInfo> list = managerMeetingDao.findManagerMeetingByFilter(filter);
		int size = managerMeetingDao.findManagerMeetingSizeByFilter(filter);
		QueryResult<ManagerMeetingInfo> qr = new QueryResult<ManagerMeetingInfo>(size,list);
		return qr;
	}

	public void exportManagerMeeting(QueryResult<ManagerMeetingInfo> result,HttpServletResponse response,Date registerMeetingDate) {

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("sheet1");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0); 
        row.setHeight((short)768);
        HSSFCell cellTmp = row.createCell((short) 0);
        String titles="济南农村商业银行客户经理上会登记表";
        String meetingdate="";
        SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
        if(registerMeetingDate!=null){
        	meetingdate= sf.format(registerMeetingDate);
        }
        if(meetingdate!=""){
        	titles="济南农村商业银行"+meetingdate+"日客户经理业绩进度";
        }
        cellTmp.setCellValue(titles);  //设置表格标题
		// 设置标题字体
		HSSFFont font16 = wb.createFont();
		font16.setFontHeightInPoints((short) 20);
		font16.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font16.setFontName("华文楷体");
		
		// 设置标题字体
		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 12);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font1.setFontName("宋体");
		
		// 设置单元格居中
		HSSFCellStyle styleCenter = wb.createCellStyle();
		styleCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleCenter.setFont(font16);
		
		// 设置居右
		HSSFCellStyle styleFirst = wb.createCellStyle();
		styleFirst.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleFirst.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		styleFirst.setFont(font1);
		// 设置居右
		HSSFCellStyle styleSecond = wb.createCellStyle();
		styleFirst.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleFirst.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleFirst.setFont(font1);
		
		// 合并单元格
		CellRangeAddress region = new CellRangeAddress(0, 0, 0,5);
		sheet.addMergedRegion(region);
		cellTmp.setCellStyle(styleCenter);
		
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        // 创建一个居中格式
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        
        // 设置第二行 制表日期
        row = sheet.createRow((int) 1);
        HSSFCell tmp = row.createCell((short) 4);
        tmp.setCellValue("制表日期："+new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        CellRangeAddress reg = new CellRangeAddress(1, 1,4,5);
        sheet.addMergedRegion(reg);
        tmp.setCellStyle(styleSecond);
        
        // excel 正文内容
        row = sheet.createRow((int) 2);
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("序号");  
        cell.setCellStyle(style);
        	        
        cell = row.createCell((short) 1);  
        cell.setCellValue("客户经理");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 2);  
        cell.setCellValue("客户姓名");  
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 3);  
        cell.setCellValue("上会日期");  
        cell.setCellStyle(style);
        sheet.setColumnWidth(3, 25*256);
        
        cell = row.createCell((short) 4);  
        cell.setCellValue("一次/二次");  
        cell.setCellStyle(style);
        sheet.setColumnWidth(4, 10*256);
        
        cell = row.createCell((short) 5);  
        cell.setCellValue("状态");  
        cell.setCellStyle(style);
        sheet.setColumnWidth(5, 25*256);

        List<ManagerMeetingInfo> managermeetinginfo =result.getItems();
        for(int i=0;i<managermeetinginfo.size();i++){
        	row = sheet.createRow((int) i + 3);
        	ManagerMeetingInfo meetinginfo = managermeetinginfo.get(i);
        	row.createCell((short) 0).setCellValue(i+1);            //序号
        	row.createCell((short) 1).setCellValue(meetinginfo.getManagerName());         //客户经理                       
        	row.createCell((short) 2).setCellValue(meetinginfo.getCustomerName());     	//客户姓名       
        	row.createCell((short) 3).setCellValue(sf.format(meetinginfo.getRegisterMeetingDate()));      //上会日期
        	row.createCell((short) 4).setCellValue(meetinginfo.getNumberOfTimes());    //一次/二次 
        	row.createCell((short) 5).setCellValue(meetinginfo.getStatus());    //状态       
        }
        try {
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/vnd.ms-excel;charset=GBK");
        response.setHeader("Content-Disposition", "attachment;filename="
        + new String("济南农村商业银行客户经理上会登记表.xls".getBytes(), "iso-8859-1"));
        OutputStream out = response.getOutputStream();  
        wb.write(out);
     
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updateMeetingInfo(ManagerMeetingInfo managerMeetingInfo) {
		commonDao.updateObject(managerMeetingInfo);
	}
}
