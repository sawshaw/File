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
 *数据库入库
 */
public class TextParse {
	public static Logger logger = LoggerFactory.getLogger(TextParse.class);
	 //每次读取的最大文件数
	 private int maxfiles = 500;
	 //锁所在目录
	 public String fileLockPath="D:/fileTest/lock/";
	 //锁定文件锁
	 private String fileLock="FILE.LOCK";
	 private int flag=1;
	 public  String localDir="D:/fileTest/old/";
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
			 for(String myfile: fileList.split(",")){
				 if(myfile!=null&&myfile.length()>0){
					 //做导入文件到表操作
					 doInsertTables(myfile);
					 //移除文件操作
					 System.out.println("移动文件");
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
			System.out.println("the file is locked");
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
		 System.out.println("释放锁");
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
		 System.out.println("获取文件名列表");
	    String fileList = "";
	    File myfile = new File(filePath);
	    String[] fs = myfile.list();
	    int i = 0;
	    for (String f : fs) {
	    	//if ((f.endsWith(".txt")) || (f.endsWith(".TXT"))) {
	    		fileList = fileList + f + ",";
	    		i++; 
	    		if (i >= this.maxfiles)
	    			break;
	    	}
	   // }
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
	 public void doInsertTables(String fileName){
		 StringBuffer sbuf = new StringBuffer();
	        try{
	        //
	        	fileName=this.localDir+fileName;
	        //InputStream is = TextParse.class.getResourceAsStream(fileName);
	        InputStream is = new FileInputStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String line = reader.readLine();
			while (null != line) {
				sbuf.append(line).append("\n"); 
				//转换
				int i=0;
				String[] lines=line.split("\\|");
				for(String lin:lines){
					System.out.println(i++);
					System.out.println("==="+lin);
				}
				//写数据库
				insertTable(lines);
				System.out.println("换行");
				//for循环转换成对象
				line = reader.readLine();
			}
			reader.close();//关闭reader就行了，is.close()不必 
	        }catch(IOException e){
	        	System.out.println("读取异常...");
	        }
			System.out.println("输出读取的txt\n"+sbuf.toString()+"\n");
	}
	 private static void insertTable(String[] lines) {
		//Model model=setModel(lines);
		Dbutils util=new Dbutils();
		String sql="insert into file2 values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i=0;
		util.update(sql, lines[0],lines[1],lines[2],lines[3],lines[4],lines[5],lines[6],lines[7],lines[8],lines[9],lines[10],lines[11],lines[12],lines[13],lines[14],lines[15],lines[16],lines[17],lines[18],lines[19],lines[20],lines[21],lines[22],lines[23],lines[24],lines[25],lines[26],lines[27],lines[28],lines[29],lines[30],lines[31],lines[32],lines[33]);
		
	}
	 private static Model setModel(String[] lines){
		 Model model=new Model();
			int i=0;
			model.setStreamNo(lines[i++]);
			model.setTimestamp(lines[i++]);
			model.setMsgId(lines[i++]);
			model.setSubscriptionId(lines[i++]);
			model.setOa(lines[i++]);
			model.setOaId(lines[i++]);
			model.setDa(lines[i++]);
			model.setDaId(lines[i++]);
			model.setFa(lines[i++]);
			model.setFaId(lines[i++]);
			model.setFaPayType(lines[i++]);
			model.setServiceType(lines[i++]);
			model.setCdrType(lines[i++]);
			model.setChargePartyType(lines[i++]);
			model.setSpId(lines[i++]);
			model.setProductOfferId(lines[i++]);
			model.setContentId(lines[i++]);
			model.setServicecapabiltyId(lines[i++]);
			model.setBeginTime(lines[i++]);
			model.set(lines[i++]);
			model.setSrcDeviceType(lines[i++]);
			model.setSrcDeviceId(lines[i++]);
			model.setDestDeviceType(lines[i++]);
			model.setDestDeviceId(lines[i++]);
			model.setTimes(lines[i++]);
			model.setDuration(lines[i++]);
			model.setUplinkVolume(lines[i++]);
			model.setDownlinkVolume(lines[i++]);
			model.setAccessPointName(lines[i++]);
			model.setSgsnip(lines[i++]);
			model.setChargeResult(lines[i++]);
			model.setInfoFee(lines[i++]);
			model.setChannelFee(lines[i++]);
			model.setFee(lines[i++]);
			model.setConstag(lines[i++]);
			return model;
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
		new TextParse().run();
	}
}