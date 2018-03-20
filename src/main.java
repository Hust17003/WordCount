package wc;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;



public class main {
	//标志位
	static boolean isChar = false;
	static boolean isWord = false;
	static boolean isLine = false;
	static boolean isOut = false;
	//字符串定义
	static String str = "";
	static String result="result.txt";
	static String pathName = "";//读取文件名，需要把文件放置在代码目录之下否则就需要输入文件完整路径
	static String outputPath = "";//输出文件名，如果需要把文件存储在代码目录之外的位置则需要输入完整的路径
    public static void main(String[] argv){
	    	if(argv!= null){
	    		/*argv[0]存储"wc.exe",argv[1]存储文件内容操作"-s-w-l(中间无空格)"
	    		  argv[2]存储"需要读取判断的文件名"，argv[3]存储"-o",argv[4]存储输出文件名*/
	    		if(argv[0].equals("wc.exe")){//检测输入语法正确性
	    			String[] cmd = argv[1].split("-");//过滤"-"，将标志字存入数组
	    			if(cmd.length>0){
	    				int i=1;
	    				while(i<cmd.length){//利用循环使得能够判断strArray[1]中的所有标志位
		    				if(argv[2] != null)
		    					pathName = argv[2];
		    				else
		    					System.out.println("error input!");
		    				switch(cmd[i]){
		    				case "c":
		    					isChar = true;
		    					break;//跳出switch
		    				case "w":
		    					isWord = true;
		    					break;
		    				case "l":
		    					isLine = true;
		    					break;
		    				default:
		    					System.out.println("error input!");
		    				}
		    				i++;
		    			}
	    			}else{
	    				System.out.println("error input!");
	    			}
	    			
	    			if(argv.length > 3)//如果存在-o参数，判断并设置标志位
		    			if(argv[3].equals("-o") && argv[4] != null){
		    				isOut = true;
		    				outputPath = argv[4];
		    			}
		    			else{
		    				System.out.println("error input!");
		    			}
	    		}else{
	    			System.out.println("error input!");
	    		}
	    	}else{
	    		System.out.println("please input.");
	    	}
	    	readFile(pathName);//读取文件
    	}		
    
    //读指定文件并计数
    public static void readFile(String filePath){
        try {
        	//由于程序能够在执行一个命令之后继续执行下一个新的命令，所以计数变量和内容都不能声名为全局变量，只能为局部变量
        	int countChar = -1;
        	int countWord = 1;
        	int countLine = 0;
        	String content = "";//写入文件内容
            File filename = new File(filePath);  
            InputStreamReader reader = new InputStreamReader(
            		new FileInputStream(filename)); 
            BufferedReader br = new BufferedReader(reader);  
            String str;
            while(( str=br.readLine() ) != null)//按行读取文件内容
            {
            	countChar += str.length();//字符长度就是字符个数
            	countChar++;//每一行会有一个换行符，所以加一
            	countWord += str.split(",| {1,}").length-1;//split() 由一个或一个以上空格或",",把一个字符串分割成字符串数组,字符串数组的长度，就是单词个数
            	countLine++;//因为是按行读取，所以每次增加一即可计算出行的数目
            }
            br.close();
            if(isChar == true){
            	String s1 = filePath + ",字符数:" + countChar + "\r\n";
            	System.out.printf(s1);
            	content += s1;
            	isChar = false;
            }
            if(isWord == true){
            	String s2 = filePath + ",单词数:" + countWord + "\r\n";
            	System.out.printf(s2);
            	content += s2;
            	isWord = false;
            }
            if(isLine == true){
            	String s3 = filePath + ",行数:" + countLine + "\r\n";
            	System.out.printf(s3);
            	content += s3;
            	isLine = false;
            }
            if(isOut == true){
            	writeFile(content,outputPath);
            	isOut = false;
            }
            writeFile(content,result);//输出结果文件result.txt
        }catch (Exception e) {  
            e.printStackTrace();  
        }
		//return null;
    } 
    
    //输出结果文件
    public static void writeFile(String content,String str){
    	BufferedWriter bw = null;
	    try {
	        File file = new File(str); 
	        if (!file.exists()) {  
	            file.createNewFile();  
	        }  
	        FileWriter fw = new FileWriter(file.getAbsoluteFile());  
	        bw = new BufferedWriter(fw);  
	        bw.write(content);  
	        bw.close();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
    }
}
