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
public class DbToFile {
	public String result="D:/fileTest/result/";
	public String fileName="";
	public String sql="select * from filebak";
	public static void main(String[] args) throws IOException, SQLException {
		new DbToFile().outFile();
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
			 String streamNo=rs.getString("streamingno");
			 System.out.println("streamNo:"+streamNo);
			 String timestamp=rs.getString("TimeStamp");
			 String msgId=rs.getString("MsgID");
			 String subscriptionId=rs.getString("subscriptionid");
			 String oa=rs.getString("OA");
			 String oaId=rs.getString("OaOperatorID");
			 String da=rs.getString("DA");
			 String daId=rs.getString("DaOperatorID");
			 String fa=rs.getString("FA");
			 String faId=rs.getString("FaOperatorID");
			 String faPayType=rs.getString("FAPayType");
			 String serviceType=rs.getString("ServiceType");
			 String cdrType=rs.getString("CdrType");
			 String chargePartyType=rs.getString("chargePartyType");
			 String spId=rs.getString("SPID");
			 String productOfferId=rs.getString("productOfferID");
			 String contentId=rs.getString("ContentID");
			 String servicecapabiltyId=rs.getString("servicecapabiltyid");
			 String beginTime=rs.getString("BeginTime");
			 String nullpoint=rs.getString("nullpoint");
			 String srcDeviceType=rs.getString("SrcDeviceType");
			 String srcDeviceId=rs.getString("SrcDeviceID");
			 String destDeviceType=rs.getString("destDeviceType");
			 String destDeviceId=rs.getString("DestDeviceID");
			 String times=rs.getString("Times");
			 String duration=rs.getString("Duration");
			 String uplinkVolume=rs.getString("uplinkVolume");
			 String downlinkVolume=rs.getString("downlinkVolume");
			 String accessPointName=rs.getString("accessPointName");
			 String sgsnip=rs.getString("SGSNIP");
			 String chargeResult=rs.getString("ChargeResult");
			 String infoFee=rs.getString("infoFee");
			 String channelFee=rs.getString("channelFee");
			 String fee=rs.getString("fee");
			 String constag=rs.getString("constag");
			 nullpoint="";
			 constag="";
		File file=new File(result+accessPointName);
		Writer w = new FileWriter(file,true);
		if(file.exists()){
			//append
			w.append(streamNo+"|"+timestamp+"|"+msgId+"|"+subscriptionId+"|"+oa+"|"+oaId+"|"+da+"|"+daId+"|"+fa+"|"+faId+"|"+faPayType+"|"+serviceType+"|"+cdrType+"|"+chargePartyType+"|"+spId+"|"+productOfferId+"|"+contentId+"|"+servicecapabiltyId+"|"+beginTime+"|"+nullpoint+"|"+srcDeviceType+"|"+srcDeviceId+"|"+destDeviceType+"|"+destDeviceId+"|"+times+"|"+duration+"|"+uplinkVolume+"|"+downlinkVolume+"|"+accessPointName+"|"+"|"+sgsnip+chargeResult+"|"+infoFee+"|"+channelFee+"|"+fee+"|"+constag+"\n");
		}else{
			file.createNewFile();
			w.append(streamNo+"|"+timestamp+"|"+msgId+"|"+subscriptionId+"|"+oa+"|"+oaId+"|"+da+"|"+daId+"|"+fa+"|"+faId+"|"+faPayType+"|"+serviceType+"|"+cdrType+"|"+chargePartyType+"|"+spId+"|"+productOfferId+"|"+contentId+"|"+servicecapabiltyId+"|"+beginTime+"|"+nullpoint+"|"+srcDeviceType+"|"+srcDeviceId+"|"+destDeviceType+"|"+destDeviceId+"|"+times+"|"+duration+"|"+uplinkVolume+"|"+downlinkVolume+"|"+accessPointName+"|"+"|"+sgsnip+chargeResult+"|"+infoFee+"|"+channelFee+"|"+fee+"|"+constag+"\n");
		}
		w.flush();
        w.close();
	}
		System.out.println("end");
	}

}
