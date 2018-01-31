package com.file.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Writer;

public class AppandTest {
	public static void Append1() throws IOException{
        String content="---第一种方法添加文件内容-------\r\n6666";
        Writer w = new FileWriter(new File("new.txt"),true);
        w.append(content);
        System.out.println("追加成功");
        String c1="1q1q\r\n";
        w.append(c1);
        w.flush();
        w.close();
    }
    public static void Append2() throws IOException{
        RandomAccessFile randomFile = new RandomAccessFile(new File("D://new.txt"), "rw"); 
        // 文件长度，字节数 
        long fileLength = randomFile.length(); 
        //将写文件指针移到文件尾。 
        randomFile.seek(fileLength); 
        randomFile.writeBytes("00000xxxxxxxx11111"); 
        randomFile.close(); 
    }
    public static void main(String[] args) throws IOException {
        Append1();
        Append2();
    }
 
}