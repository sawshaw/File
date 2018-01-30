package com.file;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mercy
 *文件解析器
 */
public class TextParse_bak {
	public static Logger logger = LoggerFactory.getLogger(TextParse_bak.class);
	 //每次读取的最大文件数
	 private int maxfiles = 24;
	 //锁所在目录
	 private String fileLockPath="D:/fileTest/lock/";
	 //锁定文件锁
	 private String fileLock="FILE.LOCK";
	 private int flag=1;
	 private String localDir="D:/fileTest/old/";
	 //每隔1秒处理一个文件
	 private int intevalTime = 1000;
	 //文件备份目录
	 private String localBakDir="D:/fileTest/new/";
	 private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	 public void run(){
		// while(true){
		 //检测是否存在锁
		 if(isFileLocked()){
			 //不存在文件锁则创建文件锁
			 if(!getFileLock()){
				 return;
			 }
			 String fileList=getFileList(this.localDir);
			 for(String myfile: fileList.split("|")){
				 if(myfile!=null&&myfile.length()>0){
					 //做导入文件到表操作
					 //doInsertTables(myfile);
					 //移除文件操作
					 if (!removeFiles(myfile, this.localDir, this.localBakDir, ""))
			            {
						  //移动文件失败就配一个新文件名再移动
			              String newFileName = "RP_" + myfile + "_" + getCurTimeString();
			              removeFiles(myfile, this.localDir, this.localBakDir, newFileName);
			            }
					 try {
			              logger.info("sleep for a moment...");
			              Thread.sleep(this.intevalTime);
			            } catch (InterruptedException e) {
			              logger.error(e.toString());
			            }
				 }
			 }
		 }
		 //释放文件锁
		releaseFileLock();
		 //}
		 
	 }
	 
	 //判断是否有文件锁
	 public boolean isFileLocked(){
		 File lockFile = new File(this.fileLockPath + this.fileLock);
		 if (lockFile.exists()) {
			 logger.info("the file is locked");
		      return false;
		    }
		 return true;
		 
	 }
	 //获取文件锁
	 public boolean getFileLock(){
		 File f = new File(this.fileLockPath + this.fileLock);
		 try {
			System.out.println("创建文件锁");
			f.createNewFile();
		} catch (IOException e) {
			System.out.println("get file lock error...."+e.getMessage());
			return false;
		}
		return true;
		 
	 }
	 //释放文件锁
	 public boolean releaseFileLock(){
		 File f = new File(this.fileLockPath + this.fileLock);
		 try{
			 f.delete();
		 }catch(Exception e){
			 logger.info("release file lock error...."); 
			 return false;
		 }
		 return true;
		 
	 }
	
	 //获取文件名列表
	 public String getFileList(String filePath){
	    String fileList = "";
	    File myfile = new File(filePath);
	    String[] fs = myfile.list();
	    int i = 0;
	    for (String f : fs) {
	    	if ((f.endsWith(".txt")) || (f.endsWith(".TXT"))) {
	    		fileList = fileList + f + ",";
	    		i++; 
	    		if (i >= this.maxfiles)
	    			break;
	    	}
	    }
	    if (fileList.endsWith(",")) {
	    	fileList = fileList.substring(0, fileList.length() - 1);
	    }
	    return fileList;
	  }
	 //移动源文件到目标目录
	 public boolean removeFiles(String f, String filePath, String dPath, String newfilename)
	  {
	    if (f.length() < 0) 
	    	return false;
	    File fl = new File(filePath + f);
	    if ((newfilename != null) && (newfilename.trim().length() > 0)){
	    	f = newfilename;
	    }
	    File nf = new File(dPath + f);
	    if (nf.exists()) {
	    	logger.info("目标数据已经存在，");
	      return false;
	    }
	    fl.renameTo(nf);
	    return true;
	  }
	 //解析文件
	 //格式 
	 //82750854       ,1688588530     ,20170401,000244,22        ,1       ,0.00      ,      ,020       ,1682
	 //82750854       ,1688588510     ,20170401,000404,23        ,1       ,0.00      ,      ,020       ,1682
	 //82750854       ,1688588505     ,20170401,000535,39        ,1       ,5.00      ,      ,020       ,1682
	 //36454810       ,1688311160     ,20170401,000755,17        ,1       ,30.00     ,      ,020       ,1682
	 public static void doInsertTables(String fileName){
		 StringBuffer sbuf = new StringBuffer();
	        try{
	        //
	        InputStream is = TextParse_bak.class.getResourceAsStream(fileName);
	        //InputStream is = new FileInputStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String line = reader.readLine();
			while (null != line) {
				sbuf.append(line).append("\n"); 
				//转换
				String[] lines=line.split(",");
				for(String lin:lines){
					System.out.println("==="+lin);
				}
				System.out.println("|||||||");
				//for循环转换成对象
				line = reader.readLine();
			}
			reader.close();//关闭reader就行了，is.close()不必 
	        }catch(IOException e){
	        	System.out.println("读取异常...");
	        }
			System.out.println("输出读取的txt\n"+sbuf.toString()+"\n");
	}
	 private static String getCurTimeString() {
	    return sdf.format(new Timestamp(System.currentTimeMillis()));
	 }

	public int getMaxfiles() {
		return maxfiles;
	}

	public void setMaxfiles(int maxfiles) {
		this.maxfiles = maxfiles;
	}

	public String getFileLockPath() {
		return fileLockPath;
	}

	public void setFileLockPath(String fileLockPath) {
		this.fileLockPath = fileLockPath;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getLocalDir() {
		return localDir;
	}

	public void setLocalDir(String localDir) {
		this.localDir = localDir;
	}

	public int getIntevalTime() {
		return intevalTime;
	}

	public void setIntevalTime(int intevalTime) {
		this.intevalTime = intevalTime;
	}

	public String getLocalBakDir() {
		return localBakDir;
	}

	public void setLocalBakDir(String localBakDir) {
		this.localBakDir = localBakDir;
	}

	public void setFileLock(String fileLock) {
		this.fileLock = fileLock;
	}
	class student{
		private int id;
		private String name;
		private float core;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public float getCore() {
			return core;
		}
		public void setCore(float core) {
			this.core = core;
		}
	}
	public static void main(String[] args){
		//String fileName="/test.txt";
		//doInsertTables(fileName);
		new TextParse_bak().run();
	}
}