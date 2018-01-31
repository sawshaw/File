package com.file.test;

public class UtilTest {
	 public static void main(String[] args) {
	        testInsert();
	        testQuery();
	    }
	    public static void testInsert(){
	        Student s=new Student();
	        s.setId(12);
	        s.setAge(23);
	        s.setName("xxt");
	        JdbcUtil u=new JdbcUtil();
	        String sql="insert into T_Student(id,age,name) values( ?,?,?)";
	        int result=u.update(sql, s.getId(),s.getAge(),s.getName());
	        System.out.println(result);
	    }
	    public static void testQuery(){
	        String sql="select * from T_Student where age=?";
	        Student s=new Student();
	        s.setAge(23);
	        JdbcUtil u=new JdbcUtil();
	        ResultSet rs=u.Query(sql, s.getAge());
	        try {
	            if(rs.next()){
	                //s.setName(rs.getString("name"));
	                System.out.println(rs.getString("name"));//s.getName(rs.getString("name"))
	            }
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	}
