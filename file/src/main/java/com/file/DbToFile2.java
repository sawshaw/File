package com.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author mercy
 *根据旧文件名生成文件
 */
public class DbToFile2 {
	public String result="D:/fileTest/result/";
	public String fileName="result.txt";
	public String sql="select * from filebak";
	public static void main(String[] args) throws IOException, SQLException {
		new DbToFile2().outFile();
	}
	//fileName="SGSNIP"字段;
	public void outFile() throws IOException, SQLException{
		Dbutils util=new Dbutils();
		Connection con=util.getCon();
		Statement stmt=con.createStatement();
		
		System.out.println("查询数据");
		//ResultSet rs=util.Query(sql);
		 ResultSet rs=stmt.executeQuery(sql);
		while(rs.next()){
			 int i=1;
			 String streamNo=(String) rs.getObject(i++);
			 System.out.println("streamNo:"+streamNo);
			 String timestamp=(String) rs.getObject(i++);
			 String msgId=(String) rs.getObject(i++);
			 String subscriptionId=(String) rs.getObject(i++);
			 String oa=(String) rs.getObject(i++);
			 String oaId=(String) rs.getObject(i++);
			 String da=(String) rs.getObject(i++);
			 String daId=(String) rs.getObject(i++);
			 String fa=(String) rs.getObject(i++);
			 String faId=(String) rs.getObject(i++);
			 String faPayType=(String) rs.getObject(i++);
			 String serviceType=(String) rs.getObject(i++);
			 String cdrType=(String) rs.getObject(i++);
			 String chargePartyType=(String) rs.getObject(i++);
			 String spId=(String) rs.getObject(i++);
			 String productOfferId=(String) rs.getObject(i++);
			 String contentId=(String) rs.getObject(i++);
			 String servicecapabiltyId=(String) rs.getObject(i++);
			 String beginTime=(String) rs.getObject(i++);
			 String srcDeviceType=(String) rs.getObject(i++);
			 String srcDeviceId=(String) rs.getObject(i++);
			 String destDeviceType=(String) rs.getObject(i++);
			 String destDeviceId=(String) rs.getObject(i++);
			 String times=(String) rs.getObject(i++);
			 String duration=(String) rs.getObject(i++);
			 String uplinkVolume=(String) rs.getObject(i++);
			 String downlinkVolume=(String) rs.getObject(i++);
			 String accessPointName=(String) rs.getObject(i++);
			 String sgsnip=(String) rs.getObject(i++);
			 String chargeResult=(String) rs.getObject(i++);
			 String infoFee=(String) rs.getObject(i++);
			 String channelFee=(String) rs.getObject(i++);
			 String fee=(String) rs.getObject(i++);
			 String constag=(String) rs.getObject(i++);
			 
		File file=new File(result+fileName);
		Writer w = new FileWriter(file,true);
		if(file.exists()){
			//append
			w.append(streamNo+"|"+timestamp+"|"+msgId+"|"+subscriptionId+"|"+oa+"|"+oaId+"|"+da+"|"+daId+"|"+fa+"|"+faId+"|"+faPayType+"|"+serviceType+"|"+cdrType+"|"+chargePartyType+"|"+spId+"|"+productOfferId+"|"+contentId+"|"+servicecapabiltyId+"|"+beginTime+"|"+srcDeviceType+"|"+srcDeviceId+"|"+destDeviceType+"|"+destDeviceId+"|"+times+"|"+duration+"|"+uplinkVolume+"|"+downlinkVolume+"|"+accessPointName+"|"+"|"+sgsnip+"|"+chargeResult+"|"+infoFee+"|"+channelFee+"|"+fee+"|"+constag+"|\n");
		}else{
			file.createNewFile();
			w.append(streamNo+"|"+timestamp+"|"+msgId+"|"+subscriptionId+"|"+oa+"|"+oaId+"|"+da+"|"+daId+"|"+fa+"|"+faId+"|"+faPayType+"|"+serviceType+"|"+cdrType+"|"+chargePartyType+"|"+spId+"|"+productOfferId+"|"+contentId+"|"+servicecapabiltyId+"|"+beginTime+"|"+srcDeviceType+"|"+srcDeviceId+"|"+destDeviceType+"|"+destDeviceId+"|"+times+"|"+duration+"|"+uplinkVolume+"|"+downlinkVolume+"|"+accessPointName+"|"+"|"+sgsnip+"|"+chargeResult+"|"+infoFee+"|"+channelFee+"|"+fee+"|"+constag+"|\n");
		}
		w.flush();
        w.close();
	}
		System.out.println("end");
	}

}
