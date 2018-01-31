package com.file;

/**
 * @author mercy
 * 修改数据库内容
 */
public class DbUpdate {
	public static void main(String[] args) {
		new DbUpdate().sqlInsert();
	}
	public void sqlInsert(){
		//要改的文件写入新表
		String insertSql="INSERT into fielbak select * from file where DA IN('160176','160178','160117','160118','160119') and SPID='CZT' AND productOfferID='CZT-01'";
		Dbutils util=new Dbutils();
		int result=util.update(insertSql);
		System.out.println("result:"+result);
	}
	//updateSql
	String sql="update file SET SPID='CAR',productOfferID='CAR-01' WHERE DA IN('160176','160178','160117','160118','160119')";
}
