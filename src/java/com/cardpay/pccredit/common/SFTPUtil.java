package com.cardpay.pccredit.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.aspectj.org.eclipse.jdt.core.dom.ThisExpression;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cardpay.pccredit.intopieces.constant.Constant;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.wicresoft.jrad.base.database.id.IDGenerator;

/** 
 * @author 贺珈 
 * @version 创建时间：2015年12月28日 下午4:44:02 
 * 程序的简单说明 
 */
public class SFTPUtil {
	
	private static String host = "10.0.3.6";  
    private static String username="root";  
    private static String password="tynx123";  
    private static int port = 22;  
    private static ChannelSftp sftp = null;  
    private static String directory = "/usr/local/tmp/";  
    private static String uploadFile = "d:/test.txt";  
    
    /** 
     * connect server via sftp 
     */  
    public static void connect() {  
        try {  
            if(sftp != null){  
                System.out.println("sftp is not null");  
            }  
            JSch jsch = new JSch();  
            jsch.getSession(username, host, port);  
            Session sshSession = jsch.getSession(username, host, port);  
            System.out.println("Session created.");  
            sshSession.setPassword(password);  
            Properties sshConfig = new Properties();  
            sshConfig.put("StrictHostKeyChecking", "no");  
            sshSession.setConfig(sshConfig);  
            sshSession.connect();  
            System.out.println("Session connected.");  
            System.out.println("Opening Channel.");  
            Channel channel = sshSession.openChannel("sftp");  
            channel.connect();  
            sftp = (ChannelSftp) channel;  
            System.out.println("Connected to " + host + ".");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    /** 
     * Disconnect with server 
     */  
    public static void disconnect() {  
        if(sftp != null){  
            if(sftp.isConnected()){  
                sftp.disconnect();  
            }else if(sftp.isClosed()){  
                System.out.println("sftp is closed already");  
            }  
        }  
  
    }
    
    /** 
     * upload all the files to the server 
     */  
    public  static Map<String, String> upload(MultipartFile oldFile,String customerId) {  
    	Map<String, String> map = new HashMap<String, String>();
        try {  
        	if (oldFile != null && !oldFile.isEmpty()) {
	        	//连接sftp
	        	 connect();  
	        	//进入上传目录
	        	sftp.cd(directory);
	        	DateFormat format = new SimpleDateFormat("yyyyMMdd");
	    		String dateString = format.format(new Date());
	    		try {
	    			sftp.cd(directory+File.separator+dateString);
				} catch (Exception e) {
					sftp.cd(directory);
					sftp.mkdir(dateString);  
					sftp.cd(directory+File.separator+dateString);
				}
	    			
	    	   String id = IDGenerator.generateID();
	    	   String newFileName = id + "."
	    			   + oldFile.getOriginalFilename().split("\\.")[1];
	    	   CommonsMultipartFile cf= (CommonsMultipartFile)oldFile;
	    	   DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
	           File file = fi.getStoreLocation();
	    	   sftp.put(new FileInputStream(file), newFileName);
	    	   System.out.println("上传成功！");
	    	   disconnect();  
	           
	           map.put("fileName", oldFile.getOriginalFilename());
	   		   map.put("url", dateString+File.separator+newFileName);
        	}
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (SftpException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
          return map;
    }

    /**
     * 下载文件
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     * @param sftp
     */
	public static void download(HttpServletResponse response,
			String filePath, String fileName) {
		try {
			String dateString = filePath.split("\\\\")[0];
			byte[] buff = new byte[2048];
			int bytesRead;
			response.setHeader("Content-Disposition", "attachment; filename="
					+ java.net.URLEncoder.encode(fileName, "UTF-8"));
			connect();
			sftp.cd(directory+File.separator+dateString);
			BufferedInputStream bis = new BufferedInputStream(
					sftp.get(filePath.split("\\\\")[1]));
			BufferedOutputStream bos = new BufferedOutputStream(
					response.getOutputStream());
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
 /*   public static void main(String[] args) throws Exception{    
    	SFTPUtil ftp= new SFTPUtil();  
        ftp.connect();  
        ftp.upload();  
        ftp.disconnect();  
        System.exit(0); 
     }*/
    
    
}
