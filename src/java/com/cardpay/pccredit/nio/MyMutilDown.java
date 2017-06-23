package com.cardpay.pccredit.nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/test/startexcel/*")
@JRadModule("test.startexcel")
public class MyMutilDown {

	@Autowired
	private IntoPiecesService intoPiecesService;
	
	@ResponseBody
	@RequestMapping(value = "startExcel.json")
	public void TestDown(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long startTime = System.currentTimeMillis();    //获取开始时间
		File file = new File("D:/2.png");
		response.setHeader("Content-Type", "application/octet-stream");

		outputold(response,file.getAbsolutePath(),null);

		long endTime = System.currentTimeMillis();    //获取结束时间
		
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
		/*
    	BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(response.getOutputStream());
			startThread(4, file.length(), file.getAbsolutePath(), null,bos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bos.close();
		}
		*/
	}

	/**
     * 开启多线程下载
     * 
     * @param threadnum 线程数
     * @param fileLength 文件大小（用于确认每个线程下载多少东西）
     * @param sourseFilePath 源文件目录
     * @param targerFilePath 目标文件目录
	 * @throws InterruptedException 
     */
    public static void startThread(int threadnum, long fileLength,
            String sourseFilePath, String targerFilePath,BufferedOutputStream bos) throws Exception {
        System.out.println("================");

        CountDownLatch countDownLatch;
        
        long modLength = fileLength % threadnum;
        long targetLength = fileLength / threadnum;
        if (modLength != 0) {
        	countDownLatch = new CountDownLatch(threadnum+1);
        }
        else{
        	countDownLatch = new CountDownLatch(threadnum);
        }
        
        for (int i = 0; i < threadnum; i++) {
            System.out.println((targetLength * i) + "-----"
                    + (targetLength * (i + 1)));
            new FileWriteThread((targetLength * i), (targetLength * (i + 1)),sourseFilePath, targerFilePath,bos,countDownLatch).start();
        }
        if (modLength != 0) {
            new FileWriteThread((targetLength * 4), modLength, sourseFilePath,targerFilePath,bos,countDownLatch).start();
        }
        countDownLatch.await();
    }
    
    /**
     * 写线程：指定文件开始位置、目标位置、源文件、目标文件，  
     */
    static class FileWriteThread extends Thread {
        private long begin;
        private long end;
        private RandomAccessFile soursefile;
        //private RandomAccessFile targerFile;
        CountDownLatch countDownLatch;
        BufferedOutputStream bos;
        
        public FileWriteThread(long begin, long end, String sourseFilePath,
                String targerFilePath,BufferedOutputStream bos,CountDownLatch countDownLatch) {
            this.begin = begin;
            this.end = end;
            try {
                this.soursefile = new RandomAccessFile(sourseFilePath, "rw");
                //this.targerFile = new RandomAccessFile(targerFilePath, "rw");
                this.bos = bos;
                this.countDownLatch = countDownLatch;
            } catch (FileNotFoundException e) {
            	e.printStackTrace();
            }
        }

        public void run() {
            try {
                soursefile.seek(begin);
                //targerFile.seek(begin);
                int hasRead = 0;
                byte[] buffer = new byte[1024];
    			
                while (begin < end && -1 != (hasRead = soursefile.read(buffer))) {
                    begin += hasRead;
                    //targerFile.write(buffer, 0, hasRead);
    				bos.write(buffer, 0, hasRead);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    soursefile.close();
                    //targerFile.close();
                } catch (Exception e) {
                	e.printStackTrace();
                }
                finally{
                	countDownLatch.countDown();
                }
            }
        }
    }
    
    public void outputold(HttpServletResponse response, String filePathAndFileName, String mimeType) throws Exception {
    	File file = new File(filePathAndFileName);
		if(file.exists()){
			byte[] buff = new byte[2048];
			int bytesRead;
			response.setHeader("Content-Type", "application/vnd.ms-excel");
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
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
		}
	}
    
    private void output(HttpServletResponse response, String filePathAndFileName, String mimeType)
    	      throws IOException {

    	    File file = new File(filePathAndFileName);

    	    // set response headers
    	    response.setContentType((mimeType != null) ? mimeType : "application/octet-stream");
    	    response.setContentLength((int) file.length());

    	    // read and write file
    	    ServletOutputStream op = response.getOutputStream();
    	    // 128 KB buffer
    	    int bufferSize = 131072;
    	    FileInputStream fileInputStream = new FileInputStream(file);
    	    FileChannel fileChannel = fileInputStream.getChannel();
    	    // 6x128 KB = 768KB byte buffer
    	    ByteBuffer bb = ByteBuffer.allocateDirect(786432);
    	    byte[] barray = new byte[bufferSize];
    	    int nRead, nGet;

    	    try {
    	      while ((nRead = fileChannel.read(bb)) != -1) {
    	        if (nRead == 0)
    	          continue;
    	        bb.position(0);
    	        bb.limit(nRead);
    	        while (bb.hasRemaining()) {
    	          nGet = Math.min(bb.remaining(), bufferSize);
    	          // read bytes from disk
    	          bb.get(barray, 0, nGet);
    	          // write bytes to output
    	          op.write(barray);
    	        }
    	        bb.clear();
    	      }
    	    } catch (IOException e) {
    	      e.printStackTrace();
    	    } finally {
    	      bb.clear();
    	      fileChannel.close();
    	      fileInputStream.close();
    	    }
    	  }
    
    @ResponseBody
	@RequestMapping(value = "flashPicUpload.json")
	public void flashPicUpload(@RequestParam(value = "Filedata") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		String appId = request.getParameter("appId");
		String userId = request.getParameter("userId");
		String picType = request.getParameter("picType");
		try {
			GenerateImage(file,id,appId,userId,picType);
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("flashPicUpload",e);
		}
	}
    
    //File直接存硬盘
  	public void GenerateImage(MultipartFile file,String id,String appId,String userId,String imageType) throws IllegalStateException, IOException { // 对字节数组字符串进行Base64解码并生成图片

		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		String dateString = format.format(now);
		// 生成jpeg图片
		String newFileName = id + imageType;
		String imgFilePath = Constant.FILE_PATH_CHAT + dateString + File.separator + newFileName;
		File saveFile = new File(imgFilePath);
		if(!saveFile.exists()){
			saveFile.mkdirs();
		}
		file.transferTo(saveFile); 
		
		// 存聊天记录
		intoPiecesService.saveChatMessage(appId,id, userId,now,"1", "", imgFilePath, imageType, "");
  	}
}







