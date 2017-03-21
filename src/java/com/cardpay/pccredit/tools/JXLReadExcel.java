package com.cardpay.pccredit.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;

import com.cardpay.pccredit.creditEvaluation.vo.ApplicantInfoVo;
import com.cardpay.pccredit.creditEvaluation.vo.CreditConditionVo;
import com.cardpay.pccredit.creditEvaluation.vo.LivingConditionVo;
import com.cardpay.pccredit.creditEvaluation.vo.OperateConditionVo;
import com.cardpay.pccredit.creditEvaluation.vo.RepayAbilitiesVo;
import com.cardpay.pccredit.creditEvaluation.vo.TModelForm;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.manager.service.DailyReportScheduleService;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;

import sun.misc.BASE64Encoder;
/**
 * @功能描述 POI 读取 Excel 转 HTML 支持 03xls 和 07xlsx 版本  包含样式
 */
public class JXLReadExcel {
	/*public static void main(String[] args) {
		JXLReadExcel jxl = new JXLReadExcel();
		String path = "C:\\Users\\Administrator\\Desktop\\小微贷标准版0806模板---周素平12万.xlsx";
		jxl.readExcelToHtml(path, 0, true);
	}*/
	private CommonDao commonDao;
	
	
    /**
     * 程序入口方法
     * @param filePath 文件的路径
     * @param isWithStyle 是否需要表格样式 包含 字体 颜色 边框 对齐方式
     * @return <table>...</table> 字符串
     */
    public String[] readExcelToHtml(String filePath, boolean isWithStyle){
        
    	String sheet[] = new String[18];
        InputStream is = null;
        String approveValue="";
//        String htmlExcel = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            File sourcefile = new File(filePath);
            is = new FileInputStream(sourcefile);
            Workbook wb = WorkbookFactory.create(is);
            for(int i=0;i<wb.getNumberOfSheets();i++)
            {
            	System.out.println(wb.getSheetAt(i).getSheetName());
            	if(wb.getSheetAt(i).getSheetName().indexOf("建议")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        //取申请金额（第三行第四列）
                        Sheet st = wb.getSheetAt(0);
                        Row row = st.getRow(2);
                        Cell cell = row.getCell(4);
                        approveValue = getCellValue(cell);
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_jy,ImportParameter.editAble_jy,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                      //取申请金额（第三行第四列）
                        Sheet st = wb.getSheetAt(0);
                        Row row = st.getRow(2);
                        Cell cell = row.getCell(4);
                        approveValue = getCellValue(cell);
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_jy,ImportParameter.editAble_jy,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[0] = content_base64;
            	}
            	if(wb.getSheetAt(i).getSheetName().indexOf("基本状况")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_jbzk,ImportParameter.editAble_jbzk,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_jbzk,ImportParameter.editAble_jbzk,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[1] = content_base64;
            	}
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("经营状态")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_jyzk,ImportParameter.editAble_jyzk,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_jyzk,ImportParameter.editAble_jyzk,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[2] = content_base64;
            	}
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("生存状态")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_sczt,ImportParameter.editAble_sczt,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_sczt,ImportParameter.editAble_sczt,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[3] = content_base64;
            	}
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("道德品质")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_ddpz,ImportParameter.editAble_ddpz,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_ddpz,ImportParameter.editAble_ddpz,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[4] = content_base64;
            	}
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("资产负债")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_zf,ImportParameter.editAble_fz,true);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_zf,ImportParameter.editAble_fz,true);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[5] = content_base64;
            	}
            	else if(wb.getSheetAt(i).getSheetName().indexOf("利润简表")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_lrjb,ImportParameter.editAble_lrjb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_lrjb,ImportParameter.editAble_lrjb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[6] = content_base64;
            	}
				else if(wb.getSheetAt(i).getSheetName().indexOf("标准利润表")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_bzlrb,ImportParameter.editAble_bzlrb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_bzlrb,ImportParameter.editAble_bzlrb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[7] = content_base64;
            	}
				else if(wb.getSheetAt(i).getSheetName().indexOf("主营业务明细表")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_zyyw,ImportParameter.editAble_zyyw,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_zyyw,ImportParameter.editAble_zyyw,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[8] = content_base64;
            	}
				else if(wb.getSheetAt(i).getSheetName().indexOf("现金流量表")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_xjllb,ImportParameter.editAble_xjllb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_xjllb,ImportParameter.editAble_xjllb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[9] = content_base64;
				}
				else if(wb.getSheetAt(i).getSheetName().indexOf("交叉检验")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_jc,ImportParameter.editAble_jc,true);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_jc,ImportParameter.editAble_jc,true);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[10] = content_base64;
				}
            	
				else if(wb.getSheetAt(i).getSheetName().indexOf("点货单")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_dhd,ImportParameter.editAble_dhd,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_dhd,ImportParameter.editAble_dhd,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[11] = content_base64;
				}
				else if(wb.getSheetAt(i).getSheetName().indexOf("固定资产")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_gdzc,ImportParameter.editAble_gdzc,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_gdzc,ImportParameter.editAble_gdzc,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[12] = content_base64;
				}
				else if(wb.getSheetAt(i).getSheetName().indexOf("应付预收")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_yfys,ImportParameter.editAble_yfys,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_yfys,ImportParameter.editAble_yfys,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[13] = content_base64;
				}
				else if(wb.getSheetAt(i).getSheetName().indexOf("应收预付")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_ysyf,ImportParameter.editAble_ysyf,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_ysyf,ImportParameter.editAble_ysyf,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[14] = content_base64;
				}
				else if(wb.getSheetAt(i).getSheetName().indexOf("流水分析")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_lsfx,ImportParameter.editAble_lsfx,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_lsfx,ImportParameter.editAble_lsfx,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[15] = content_base64;
				}
				else if(wb.getSheetAt(i).getSheetName().indexOf("决议表")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_jyb,ImportParameter.editAble_jyb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_jyb,ImportParameter.editAble_jyb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[16] = content_base64;
				}
            	sheet[17] = approveValue;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sheet;
    }
    
    
    
    public Map<String, String> getExcelInfo(Workbook wb,int index,boolean isWithStyle,String[] rowAndcol,String[] editAble,boolean averageWidth) throws Exception{
       try {
    	   int maxRow = Integer.parseInt(rowAndcol[0])-1;//最大行
    	   String maxCol = rowAndcol[1];//最大列
    	   
    	   StringBuffer sb = new StringBuffer();
    	   Sheet sheet = wb.getSheetAt(index);//获取第一个Sheet的内容
    	   int lastRowNum = sheet.getLastRowNum();
    	   if(lastRowNum>maxRow){
    		   lastRowNum = maxRow;
    	   }
    	   Map<String, String> map[] = getRowSpanColSpanMap(sheet);
    	   sb.append("<table id='MyTable' style='border-collapse:collapse;' width='100%'>");
    	   Row row = null;        //兼容
    	   Cell cell = null;    //兼容
    	   
    	   Map<String, String> resultMap = new HashMap<String, String>();
    	   for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
    		   row = sheet.getRow(rowNum);
    		   if (row == null) {
    			   sb.append("<tr><td > &nbsp;</td></tr>");
    			   continue;
    		   }
    		   sb.append("<tr>");
    		   int lastColNum = row.getLastCellNum();
    		   if(lastColNum > columnToIndex(maxCol)){
    			   lastColNum = columnToIndex(maxCol);
    		   }
    		   for (int colNum = 0; colNum < lastColNum; colNum++) {
    			   cell = row.getCell(colNum);
    			   if (cell == null) {    //特殊情况 空白的单元格会返回null
    				   sb.append("<td>&nbsp;</td>");
    				   continue;
    			   }
					String	stringValue = getCellValue(cell);
					//System.out.println(stringValue);
    			   if (map[0].containsKey(rowNum + "," + colNum)) {
    				   String pointString = map[0].get(rowNum + "," + colNum);
    				   map[0].remove(rowNum + "," + colNum);
    				   int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
    				   int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
    				   int rowSpan = bottomeRow - rowNum + 1;
    				   int colSpan = bottomeCol - colNum + 1;
    				   sb.append("<td rowspan= '" + rowSpan + "' colspan= '"+ colSpan + "' ");
    			   } else if (map[1].containsKey(rowNum + "," + colNum)) {
    				   map[1].remove(rowNum + "," + colNum);
    				   continue;
    			   } else {
    				   sb.append("<td ");
    			   }
    			   
    			   String tmp = indexToColumn(colNum+1) +(rowNum+1);
    			   sb.append("name='"+tmp+"' ");
    			   if(editAble != null && Arrays.asList(editAble).contains(tmp)){//判断是否可编辑
    				   sb.append("onclick='return EditCell();' ");
    			   }
    			   
    			   //判断是否需要样式
    			   if(isWithStyle){
    				   dealExcelStyle(wb, sheet, cell, sb,averageWidth);//处理单元格样式
    			   }
    			   
    			   sb.append(">");
    			   
    			   if (stringValue == null || "".equals(stringValue.trim())) {
    				   sb.append("&nbsp;");
    			   } else {
    				   // 将ascii码为160的空格转换为html下的空格（&nbsp;）
    				   stringValue = stringValue.replaceAll(",", "");
    				   sb.append(stringValue.replace(String.valueOf((char) 160),"&nbsp;"));
    			   }
//    			   if(padAble != null && Arrays.asList(padAble).contains(tmp)){//生成pad展示数据string
//    				   padString+=stringValue+"@@";
//    			   }
    			   sb.append("</td>");
    		   }
    		   sb.append("</tr>");
    	   }
//    	   padString=padString.substring(0, padString.length()-2);
//    	   resultMap.put("padData", padString);
    	   
    	   sb.append("</table>");
    	   resultMap.put("computerData", sb.toString());
    	   return resultMap;
		
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
    }
    
    private Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {

        Map<String, String> map0 = new HashMap<String, String>();
        Map<String, String> map1 = new HashMap<String, String>();
        int mergedNum = sheet.getNumMergedRegions();
        CellRangeAddress range = null;
        for (int i = 0; i < mergedNum; i++) {
            range = sheet.getMergedRegion(i);
            int topRow = range.getFirstRow();
            int topCol = range.getFirstColumn();
            int bottomRow = range.getLastRow();
            int bottomCol = range.getLastColumn();
            map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
            // System.out.println(topRow + "," + topCol + "," + bottomRow + "," + bottomCol);
            int tempRow = topRow;
            while (tempRow <= bottomRow) {
                int tempCol = topCol;
                while (tempCol <= bottomCol) {
                    map1.put(tempRow + "," + tempCol, "");
                    tempCol++;
                }
                tempRow++;
            }
            map1.remove(topRow + "," + topCol);
        }
        Map[] map = { map0, map1 };
        return map;
    }
    
    
    /**
     * 获取表格单元格Cell内容
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell) {
	        String result = new String();  
	        switch (cell.getCellType()) {  
	        case Cell.CELL_TYPE_NUMERIC:// 数字类型  
	        case Cell.CELL_TYPE_FORMULA:
        		if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
        			SimpleDateFormat sdf = null;  
        			if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
        				sdf = new SimpleDateFormat("HH:mm");  
        			} else {// 日期  
        				sdf = new SimpleDateFormat("yyyy-MM-dd");  
        			}  
        			Date date = cell.getDateCellValue();  
        			result = sdf.format(date);  
        		} else if (cell.getCellStyle().getDataFormat() == 14 
        				|| cell.getCellStyle().getDataFormat() == 20
        				|| cell.getCellStyle().getDataFormat() == 31 
        				|| cell.getCellStyle().getDataFormat() == 32 
        				|| cell.getCellStyle().getDataFormat() == 57 
        				|| cell.getCellStyle().getDataFormat() == 58) {  
        			// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
        			double value = cell.getNumericCellValue();  
        			Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
        			result = sdf.format(date);  
        		} else {  
        			double value = cell.getNumericCellValue();  
        			//CellStyle style = cell.getCellStyle();  
        			DecimalFormat format = new DecimalFormat();  
        			//String temp = style.getDataFormatString();  
        			// 单元格设置成常规  
        			//if (temp.equals("General")) {  
        			//	format.applyPattern("#");  
        			//}  
        			result = format.format(value);  
        		}  
        		break;  
			
	        case Cell.CELL_TYPE_STRING:// String类型  
	            result = cell.getStringCellValue().toString(); 
	            break;  
	        case Cell.CELL_TYPE_BLANK:  
	            result = "";  
	            break; 
	        default:  
	            result = "";  
	            break;  
	        }  
	        return result;  
    }
    
    /**
     * 处理表格样式
     * @param wb
     * @param sheet
     * @param cell
     * @param sb
     * @throws Exception 
     */
    private void dealExcelStyle(Workbook wb,Sheet sheet,Cell cell,StringBuffer sb,boolean averageWidth) throws Exception{
        
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle != null) {
            short alignment = cellStyle.getAlignment();
            sb.append("align='" + convertAlignToHtml(alignment) + "' ");//单元格内容的水平对齐方式
            short verticalAlignment = cellStyle.getVerticalAlignment();
            sb.append("valign='"+ convertVerticalAlignToHtml(verticalAlignment)+ "' ");//单元格中内容的垂直排列方式
            
            if (wb instanceof XSSFWorkbook) {
                            
                XSSFFont xf = ((XSSFCellStyle) cellStyle).getFont(); 
                short boldWeight = xf.getBoldweight();
                sb.append("style='");
                sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
                sb.append("font-size: " + xf.getFontHeight() / 2 + "%;"); // 字体大小
                if(averageWidth){
                	sb.append("width:" + 10 + "%;");
                }
                else{
                	int columnWidth = sheet.getColumnWidth(cell.getColumnIndex()) ;
                	sb.append("width:" + columnWidth + "px;");
                }
                
                XSSFColor xc = xf.getXSSFColor();
                if (xc != null && !"".equals(xc)) {
                    sb.append("color:#000000;"); // 字体颜色
                }
                
                XSSFColor bgColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
                //System.out.println("BackgroundColorColor: "+cellStyle.getFillBackgroundColorColor());
                //System.out.println("ForegroundColor: "+cellStyle.getFillForegroundColor());//0
                //System.out.println("BackgroundColorColor: "+cellStyle.getFillBackgroundColorColor());
                //System.out.println("ForegroundColorColor: "+cellStyle.getFillForegroundColorColor());
                if (bgColor != null && !"".equals(bgColor)) {
                    sb.append("background-color:#C0C0C0;"); // 背景颜色
                }
                sb.append("border-top:solid #000000 1px;");
                sb.append("border-right:solid #000000 1px;");
                sb.append("border-bottom:solid #000000 1px;");
                sb.append("border-left:solid #000000 1px;");
                    
            }else if(wb instanceof HSSFWorkbook){
                
                HSSFFont hf = ((HSSFCellStyle) cellStyle).getFont(wb);
                short boldWeight = hf.getBoldweight();
                short fontColor = hf.getColor();
                sb.append("style='");
                HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette(); // 类HSSFPalette用于求的颜色的国际标准形式
                HSSFColor hc = palette.getColor(fontColor);
                sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
                sb.append("font-size: " + hf.getFontHeight() / 2 + "%;"); // 字体大小
                String fontColorStr = convertToStardColor(hc);
                if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
                    sb.append("color:" + fontColorStr + ";"); // 字体颜色
                }
                
                if(averageWidth){
                	sb.append("width:" + 10 + "%;");
                }
                else{
                	int columnWidth = sheet.getColumnWidth(cell.getColumnIndex()) ;
                	sb.append("width:" + columnWidth + "px;");
                }
                short bgColor = cellStyle.getFillForegroundColor();
                hc = palette.getColor(bgColor);
                String bgColorStr = convertToStardColor(hc);
                if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
                    sb.append("background-color:" + bgColorStr + ";"); // 背景颜色
                }
                sb.append( getBorderStyle(palette,0,cellStyle.getBorderTop(),cellStyle.getTopBorderColor()));
                sb.append( getBorderStyle(palette,1,cellStyle.getBorderRight(),cellStyle.getRightBorderColor()));
                sb.append( getBorderStyle(palette,3,cellStyle.getBorderLeft(),cellStyle.getLeftBorderColor()));
                sb.append( getBorderStyle(palette,2,cellStyle.getBorderBottom(),cellStyle.getBottomBorderColor()));
            }

            sb.append("' ");
        }
    }
    
    /**
     * 单元格内容的水平对齐方式
     * @param alignment
     * @return
     */
    private String convertAlignToHtml(short alignment) {

        String align = "left";
        switch (alignment) {
        case CellStyle.ALIGN_LEFT:
            align = "left";
            break;
        case CellStyle.ALIGN_CENTER:
            align = "center";
            break;
        case CellStyle.ALIGN_RIGHT:
            align = "right";
            break;
        default:
            break;
        }
        return align;
    }

    /**
     * 单元格中内容的垂直排列方式
     * @param verticalAlignment
     * @return
     */
    private String convertVerticalAlignToHtml(short verticalAlignment) {

        String valign = "middle";
        switch (verticalAlignment) {
        case CellStyle.VERTICAL_BOTTOM:
            valign = "bottom";
            break;
        case CellStyle.VERTICAL_CENTER:
            valign = "center";
            break;
        case CellStyle.VERTICAL_TOP:
            valign = "top";
            break;
        default:
            break;
        }
        return valign;
    }
    
    private String convertToStardColor(HSSFColor hc) {

        StringBuffer sb = new StringBuffer("");
        if (hc != null) {
            if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {
                return null;
            }
            sb.append("#");
            for (int i = 0; i < hc.getTriplet().length; i++) {
                sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
            }
        }

        return sb.toString();
    }
    
    private String fillWithZero(String str) {
        if (str != null && str.length() < 2) {
            return "0" + str;
        }
        return str;
    }
    
    static String[] bordesr={"border-top:","border-right:","border-bottom:","border-left:"};
    static String[] borderStyles={"solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid","solid","solid","solid","solid"};

    private String getBorderStyle(  HSSFPalette palette ,int b,short s, short t){
         
        if(s==0)return  bordesr[b]+borderStyles[s]+"#d0d7e5 1px;";;
        String borderColorStr = convertToStardColor( palette.getColor(t));
        borderColorStr=borderColorStr==null|| borderColorStr.length()<1?"#000000":borderColorStr;
        return bordesr[b]+borderStyles[s]+borderColorStr+" 1px;";
        
    }
    
    private String getBorderStyle(int b,short s, XSSFColor xc){
         
         if(s==0)return  bordesr[b]+borderStyles[s]+"#d0d7e5 1px;";;
         if (xc != null && !"".equals(xc)) {
             String borderColorStr = xc.getARGBHex();//t.getARGBHex();
             borderColorStr=borderColorStr==null|| borderColorStr.length()<1?"#000000":borderColorStr.substring(2);
             return bordesr[b]+borderStyles[s]+borderColorStr+" 1px;";
         }
         
         return "";
    }
    
    public static String getBASE64(String s) { 
    	if (s == null) return null; 
    	return (new BASE64Encoder()).encode( s.getBytes() ); 
	} 

    private static int columnToIndex(String column) {
        if (!column.matches("[A-Z]+")) {
                try {
                        throw new Exception("Invalid parameter");
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
        int index = 0;
        char[] chars = column.toUpperCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
                index += ((int) chars[i] - (int) 'A' + 1)
                                * (int) Math.pow(26, chars.length - i - 1);
        }
        return index;
    }
    
    private static String indexToColumn(int index) throws Exception {
        if (index <= 0) {                 
        		throw new Exception("Invalid parameter");         
        	}         
        index--;         
        String column = "";         
        do {                 
        	if (column.length() > 0) {
                        index--;
                }
                column = ((char) (index % 26 + (int) 'A')) + column;
                index = (int) ((index - index % 26) / 26);
        } while (index > 0);
        return column;
    }
    
    
  public String[] readExcelToHtml1(String filePath, boolean isWithStyle){
        
    	String sheet[] = new String[13];
        InputStream is = null;
        String approveValue="";
//      String htmlExcel = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            File sourcefile = new File(filePath);
            is = new FileInputStream(sourcefile);
            Workbook wb = WorkbookFactory.create(is);
            for(int i=0;i<wb.getNumberOfSheets();i++)
            {
            	//System.out.println(wb.getSheetAt(i).getSheetName());
            	if(wb.getSheetAt(i).getSheetName().indexOf("建议")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        //取申请金额（第三行第四列）
                        Sheet st = wb.getSheetAt(0);
                        Row row = st.getRow(2);
                        Cell cell = row.getCell(4);
                        approveValue = getCellValue(cell);
                        //get excel msg save model data
                        saveModelForm(wb);
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_jy,ImportParameter.editAble_jy,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        //取申请金额（第三行第四列）
                        Sheet st = wb.getSheetAt(0);
                        Row row = st.getRow(2);
                        Cell cell = row.getCell(4);
                        approveValue = getCellValue(cell);
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_jy,ImportParameter.editAble_jy,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[0] = content_base64;
            	}
            	if(wb.getSheetAt(i).getSheetName().indexOf("基本状况")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_jbzk,ImportParameter.editAble_jbzk,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_jbzk,ImportParameter.editAble_jbzk,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[1] = content_base64;
            	}
            	
            	/*if(wb.getSheetAt(i).getSheetName().indexOf("经营状态")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_jyzk,ImportParameter.editAble_jyzk,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_jyzk,ImportParameter.editAble_jyzk,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[2] = content_base64;
            	}
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("生存状态")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_sczt,ImportParameter.editAble_sczt,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_sczt,ImportParameter.editAble_sczt,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[3] = content_base64;
            	}
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("道德品质")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_ddpz,ImportParameter.editAble_ddpz,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_ddpz,ImportParameter.editAble_ddpz,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[4] = content_base64;
            	}*/
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("资产负债")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_zf,ImportParameter.editAble_fz,true);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_zf,ImportParameter.editAble_fz,true);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[2] = content_base64;
            	}
            	/*else if(wb.getSheetAt(i).getSheetName().indexOf("利润简表")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_lrjb,ImportParameter.editAble_lrjb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_lrjb,ImportParameter.editAble_lrjb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[6] = content_base64;
            	}*/
				else if(wb.getSheetAt(i).getSheetName().indexOf("标准利润表")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_bzlrb,ImportParameter.editAble_bzlrb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_bzlrb,ImportParameter.editAble_bzlrb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[3] = content_base64;
            	}
				/*else if(wb.getSheetAt(i).getSheetName().indexOf("主营业务明细表")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_zyyw,ImportParameter.editAble_zyyw,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_zyyw,ImportParameter.editAble_zyyw,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[8] = content_base64;
            	}*/
				else if(wb.getSheetAt(i).getSheetName().indexOf("现金流量表")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_xjllb,ImportParameter.editAble_xjllb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_xjllb,ImportParameter.editAble_xjllb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[4] = content_base64;
				}
				else if(wb.getSheetAt(i).getSheetName().indexOf("交叉检验")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_jc,ImportParameter.editAble_jc,true);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_jc,ImportParameter.editAble_jc,true);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[5] = content_base64;
				}
            	
				/*else if(wb.getSheetAt(i).getSheetName().indexOf("点货单")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_dhd,ImportParameter.editAble_dhd,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_dhd,ImportParameter.editAble_dhd,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[11] = content_base64;
				}*/
				else if(wb.getSheetAt(i).getSheetName().indexOf("固定资产")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_gdzc,ImportParameter.editAble_gdzc,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_gdzc,ImportParameter.editAble_gdzc,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[6] = content_base64;
				}
				else if(wb.getSheetAt(i).getSheetName().indexOf("应付预收")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_yfys,ImportParameter.editAble_yfys,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_yfys,ImportParameter.editAble_yfys,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[7] = content_base64;
				}
				else if(wb.getSheetAt(i).getSheetName().indexOf("应收预付")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_ysyf,ImportParameter.editAble_ysyf,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_ysyf,ImportParameter.editAble_ysyf,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[8] = content_base64;
				}
				/*else if(wb.getSheetAt(i).getSheetName().indexOf("流水分析")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_lsfx,ImportParameter.editAble_lsfx,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_lsfx,ImportParameter.editAble_lsfx,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[15] = content_base64;
				}*/
				else if(wb.getSheetAt(i).getSheetName().indexOf("决议表")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_jyb,ImportParameter.editAble_jyb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_jyb,ImportParameter.editAble_jyb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[9] = content_base64;
				}
				else if(wb.getSheetAt(i).getSheetName().indexOf("抵贷通经营表")>=0){
					if (wb instanceof XSSFWorkbook) {
						XSSFWorkbook xWb = (XSSFWorkbook) wb;
						 //取申请金额（第三行第四列）
                        Sheet st = wb.getSheetAt(10);
                        Row row = st.getRow(4);
                        Cell cell = row.getCell(1);
                        if(getCellValue(cell)!=null&&getCellValue(cell)!=""){
                        	approveValue = getCellValue(cell);
                        }
						map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_ddtscjy,ImportParameter.editAble_ddtscjy,false);
					}else if(wb instanceof HSSFWorkbook){
						 //取申请金额（第三行第四列）
                        Sheet st = wb.getSheetAt(10);
                        Row row = st.getRow(4);
                        Cell cell = row.getCell(1);
                        if(getCellValue(cell)!=null&&getCellValue(cell)!=""){
                        	approveValue = getCellValue(cell);
                        }
						HSSFWorkbook hWb = (HSSFWorkbook) wb;
						map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_ddtscjy,ImportParameter.editAble_ddtscjy,false);
					}
					String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[10] = content_base64;
				}
				else if(wb.getSheetAt(i).getSheetName().indexOf("抵贷通消费表")>=0){
					if (wb instanceof XSSFWorkbook) {
						XSSFWorkbook xWb = (XSSFWorkbook) wb;
						 //取申请金额（第三行第四列）
                        Sheet st = wb.getSheetAt(11);
                        Row row = st.getRow(4);
                        Cell cell = row.getCell(1);
                        if(getCellValue(cell)!=null&&getCellValue(cell)!=""){
                        	approveValue = getCellValue(cell);
                        }
						map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.RowAndCol_ddtgrxf,ImportParameter.editAble_ddtgrxf,false);
					}else if(wb instanceof HSSFWorkbook){
						HSSFWorkbook hWb = (HSSFWorkbook) wb;
						 //取申请金额（第三行第四列）
                        Sheet st = wb.getSheetAt(11);
                        Row row = st.getRow(4);
                        Cell cell = row.getCell(1);
                        if(getCellValue(cell)!=null&&getCellValue(cell)!=""){
                        	approveValue = getCellValue(cell);
                        }
						map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.RowAndCol_ddtgrxf,ImportParameter.editAble_ddtgrxf,false);
					}
					String content_base64 = getBASE64(map.get("computerData").toString());
					sheet[11] = content_base64;
				}
            	sheet[12] = approveValue;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sheet;
    }
  
  /**
   * @author songchen
   * @time   2017年3月2日 11:37:09
   */
  public void  saveModelForm(Workbook wb){
	    /*start */
	    try{
        String cardNo = getCellValue(wb.getSheetAt(0).getRow(14).getCell(6));//身份证号号码 (0,14,6)
        String cname  = getCellValue(wb.getSheetAt(0).getRow(14).getCell(1)); //姓名  (0,14,1)
        String sex    = getCellValue(wb.getSheetAt(0).getRow(14).getCell(4)); //性别(0,14,4)
        String domicileLocation = getCellValue(wb.getSheetAt(0).getRow(15).getCell(1));//户籍所在地(0,15,1)
        String address			= getCellValue(wb.getSheetAt(0).getRow(15).getCell(4));//详细地址(0,15,4)
        String phoneNo          = getCellValue(wb.getSheetAt(0).getRow(16).getCell(4));//电话(0,16,4)
        String spouseIdNo 		= getCellValue(wb.getSheetAt(0).getRow(23).getCell(7));//配偶身份证号(0,23,7)
        String companyAddress   = getCellValue(wb.getSheetAt(1).getRow(1).getCell(1));//店铺/企业地址(1,1,1)
        String industry			= getCellValue(wb.getSheetAt(1).getRow(27).getCell(1));//所属行业(1,27,1)
        String operatingTime	= getCellValue(wb.getSheetAt(1).getRow(3).getCell(9));//经营时间 -取业务开始时期 然后 年戳计算（1,3,9）
      
        /*String dwellingType=getCellValue(wb.getSheetAt(0).getRow(17).getCell(2));  // 居住类型 (0,17,2)
		String decorationSituation=getCellValue(wb.getSheetAt(0).getRow(18).getCell(3)); // 装修情况(0,18,3)
		String housingArea=getCellValue(wb.getSheetAt(0).getRow(17).getCell(7));   // 住房面积(0,17,7)
		String ownedPropertyQuantity=getCellValue(wb.getSheetAt(0).getRow(20).getCell(1));// 自有房产数量(0,20,1)
		String numberOfMortgage=getCellValue(wb.getSheetAt(0).getRow(20).getCell(3));// 按揭房产数量(0,20,3)
		String housePrice=getCellValue(wb.getSheetAt(0).getRow(20).getCell(7));// 房产总价(0,20,7)
		String totalPropertyArea= getCellValue(wb.getSheetAt(0).getRow(20).getCell(9)); // 自有房产总面积(0,20,9)
		String numberOfPrivateVehicles= getCellValue(wb.getSheetAt(0).getRow(22).getCell(1));// 自有车辆数量(0,22,1)
		String numberOfLoans=getCellValue(wb.getSheetAt(0).getRow(22).getCell(3)); // 贷款车辆数量(0,22,3)
		String vehiclePrice=getCellValue(wb.getSheetAt(0).getRow(22).getCell(7)); // 车辆总价(0,22,7)
		String others=getCellValue(wb.getSheetAt(0).getRow(23).getCell(1)); // 除经营生意外是否有其他工作(0,23,1)
		String personalBankAccountBalance="";								// 个人银行帐户余额(1,13,6)???
		String businessAccountBalance= "";   								// 生意帐户余额(1,13,6)???
		String totalCreditCardCredit=  getCellValue(wb.getSheetAt(1).getRow(12).getCell(3));  // 信用卡授信总额(1,12,3)
		String averageMonthlyRepaymentAmountOfIncome=getCellValue(wb.getSheetAt(0).getRow(10).getCell(5));  // 月平均还款金额占收入比例(0,10,5)
		String guaranteeForOthers=getCellValue(wb.getSheetAt(1).getRow(14).getCell(1));          // 是否为他人担保(1,14,1)
		String theProportionOfTheAmountOfTheSecuredAssets= "";  //TODO 担保金额占本人自有资产比例    去担余额???
		String securedUse=getCellValue(wb.getSheetAt(1).getRow(14).getCell(5));  // 担保用途(1,14,5)
		String guaranteePeriod= getCellValue(wb.getSheetAt(1).getRow(14).getCell(7)); // 担保期限(1,14,7)
*/		
		
		/*String organizationType=getCellValue(wb.getSheetAt(1).getRow(1).getCell(5));          // 组织类型(1,1,5)     
		String operatingArea=getCellValue(wb.getSheetAt(1).getRow(1).getCell(7));             // 经营场所面积(1,1,7) 
		String proportionofShareholders=getCellValue(wb.getSheetAt(1).getRow(2).getCell(4));  // 股东占比情况(1,2,4) 
		String employees=getCellValue(wb.getSheetAt(1).getRow(2).getCell(6));                 // 雇员人数(1,2,6)     
		String businessLicense=getCellValue(wb.getSheetAt(1).getRow(3).getCell(2));           // 营业执照(1,3,1)     
		String storeType=getCellValue(wb.getSheetAt(1).getRow(5).getCell(3));                 // 店铺类型(1,5,2)     
		String shopDecoration=getCellValue(wb.getSheetAt(1).getRow(6).getCell(3));            // 店铺装修情况(1,6,3) 
*/		
		
		String ownFunds= getCellValue(wb.getSheetAt(0).getRow(3).getCell(6));                      	 //自有资金(0,3,6)                                             
	  	String spouseIncome=getCellValue(wb.getSheetAt(0).getRow(24).getCell(4));                  	 //配偶年收入:(0,24,4),
	  	String totalNonOperatingAssets=getCellValue(wb.getSheetAt(0).getRow(25).getCell(9));       	 //非经营总资产(0,25,9) 
	  	String monthlyProfit="";                	   												 //月利润    ???                          
	  	String applicationPeriod=getCellValue(wb.getSheetAt(0).getRow(3).getCell(1));                //申请期限:(0,3,1),                     
	  	String nonPperatingTotalLiabilities=getCellValue(wb.getSheetAt(0).getRow(26).getCell(9));  	 //非经营总负债(0,26,9)
	  	
	  	
	    /*String maritalStatus=getCellValue(wb.getSheetAt(0).getRow(14).getCell(9));                     	 //婚姻状况: (0,14,9),
		String highestDegree=getCellValue(wb.getSheetAt(0).getRow(16).getCell(1));                     	 //最高学位: (0,16,1),
		String familyEvaluationOfApplicants=getCellValue(wb.getSheetAt(1).getRow(20).getCell(1));      	 //家人对申请人评价: (1,20,1),
		String neighborEvaluation=getCellValue(wb.getSheetAt(1).getRow(20).getCell(6));                 //邻居对申请人评价: (1,20,6),
		String evaluationOfImportantContactPerson=getCellValue(wb.getSheetAt(1).getRow(21).getCell(1)); //重要联系人对申请人评价: (1,21,1),
		String evaluationOfBusinessAssociates=getCellValue(wb.getSheetAt(1).getRow(21).getCell(6));     //生意 联人对申请人评价:(1,21,6),
		String socialWelfareSituation=getCellValue(wb.getSheetAt(1).getRow(19).getCell(1));             //社会公益状况: (1,19,1),
		String violationOfLaw=getCellValue(wb.getSheetAt(1).getRow(19).getCell(3));                    	 //违法违纪情况: (1,19,3),
		String familyHarmony=getCellValue(wb.getSheetAt(1).getRow(16).getCell(3));                     	 //家庭是否和睦: (1,16,3),
		String economicDependence=getCellValue(wb.getSheetAt(1).getRow(16).getCell(5));                	 //经济上依赖的人数: (1,16,5),
		String badHabits=getCellValue(wb.getSheetAt(1).getRow(16).getCell(7));                          //不良嗜好: (1,16,7),
		String badPublicRecords=getCellValue(wb.getSheetAt(1).getRow(16).getCell(9));                   //不良公共记录: (1,16,9),
		String politicalSituation=getCellValue(wb.getSheetAt(1).getRow(17).getCell(7));                	 //政治情况: (1,17,7),
		String commercialInsurance=getCellValue(wb.getSheetAt(1).getRow(17).getCell(9));               	 //商业保险情况:(1,17,9),
		String socialRelations=getCellValue(wb.getSheetAt(1).getRow(18).getCell(9));                   	 //社会关系: (1,18,9),
		String parentalSupport=getCellValue(wb.getSheetAt(1).getRow(19).getCell(5));                   	 //赡养父母状况:(1,19,5),
		String dfamilyHarmony=getCellValue(wb.getSheetAt(1).getRow(19).getCell(8));                    	 //亲属和睦状况: (1,19,8),
		String creditStatus=getCellValue(wb.getSheetAt(1).getRow(11).getCell(1));                      	 //信用状况: (1,11,1),
		String creditCardOverdue="";                   													 //信用卡逾期情况: (),
		String creditCardTotalNum=getCellValue(wb.getSheetAt(1).getRow(12).getCell(1));                	 //信用卡总数: (1,12,1)                             
*/        /*end */ 
		TModelForm form = new TModelForm();
		form.setCardNo(cardNo.trim());
		form.setCname(cname);
		form.setSex(sex);
		form.setDomicileLocation(domicileLocation);
		form.setAddress(address);
		form.setPhoneNo(phoneNo);
		form.setSpouseIdNo(spouseIdNo);
		form.setCompanyAddress(companyAddress);
		form.setIndustry(industry);
		form.setOperatingTime(operatingTime);
		form.setOwnFunds(ownFunds);
		form.setSpouseIncome(spouseIncome);
		form.setTotalNonOperatingAssets(totalNonOperatingAssets);
		form.setMonthlyProfit(monthlyProfit);
		form.setApplicationPeriod(applicationPeriod);
		form.setNonPperateTotalLiabilities(nonPperatingTotalLiabilities);
		form.setCreatedTime(new Date());
		AddIntoPiecesService addIntoPiecesService =Beans.get(AddIntoPiecesService.class);
		addIntoPiecesService.saveModelForm(form);
	  }catch(Exception e){
		  e.printStackTrace();
		  Log.error("调查模板存取参数错误!");
		 
	  }
  }
}