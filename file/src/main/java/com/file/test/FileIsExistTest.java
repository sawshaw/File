package com.file.test;
import java.io.File;
import java.io.IOException;
public class FileIsExistTest {

	    public static void main(String[] args) {

	        File file = new File("D:/fileTest/result/result.txt");
	        FileIsExistTest.judeFileExists(file);

	        File dir = new File("D:/fileTest/result");
	        FileIsExistTest.judeDirExists(dir);
	    }

	    // 判断文件是否存在
	    public static void judeFileExists(File file) {

	        if (file.exists()) {
	            System.out.println("file exists");
	        } else {
	            System.out.println("file not exists, create it ...");
	            try {
	                file.createNewFile();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }

	    }

	    // 判断文件夹是否存在
	    public static void judeDirExists(File file) {

	        if (file.exists()) {
	            if (file.isDirectory()) {
	                System.out.println("dir exists");
	            } else {
	                System.out.println("the same name file exists, can not create dir");
	            }
	        } else {
	            System.out.println("dir not exists, create it ...");
	            file.mkdir();
	        }

	    }

	}