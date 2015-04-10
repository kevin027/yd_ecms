package com.tools.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(FileUtil.class);

	private static String fileRoot = null;

	public static String getFileRoot() {
		return fileRoot;
	}

	static {
		// 从配置文件中获取系统相关文件的根目录
		fileRoot = "C:/temp";
	}
	/**
	 * 获取文件的后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName) {
		if (fileName == null) {
			return "";
		}
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			return "";
		}
		return fileName.substring(index);
	}
	/**
	 * 获取没有后缀的文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName) {
		if (fileName == null) {
			return "";
		}
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			return "";
		}
		return fileName.substring(0,index);
	}	
	/**
	 * 检查指定的文件是否存在
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static boolean checkFileExist(String filePath) throws Exception {
		File f = new File(filePath);
		return f.isFile() && f.exists();
	}	
	
	/**
	 * 检查文件夹是否存在
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static boolean checkFile(String filePath) throws Exception{
		File f = new File(filePath);
		return f.exists();
	}

	// 把图片打成字符数组
	public static byte[] fileToByteArray(String filePath) {
		ByteArrayOutputStream bStrm = null;
		InputStream is = null;
		File file = null;
		try {
			file = new File(filePath);
			is = new FileInputStream(file);
			int ch;
			bStrm = new ByteArrayOutputStream();
			while ((ch = is.read()) != -1) {
				bStrm.write(ch);
			}
			return bStrm.toByteArray();
		} catch (Exception e) {
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception ee) {
				}
			}
			if (bStrm != null) {
				try {
					bStrm.close();
				} catch (Exception ee) {
				}
			}
		}
	}

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		System.out.println(FileUtil.getSuffix("123.txt"));
//		System.out.println(FileUtil.getFileName("123.txt"));
//	}

	     /**   
	      * 删除文件，可以是单个文件或文件夹   
	      * @param   fileName    待删除的文件名   
	      * @return 文件删除成功返回true,否则返回false   
	      */    
	     public static boolean delete(String fileName){     
	         File file = new File(fileName);     
	         if(!file.exists()){     
	             System.out.println("删除文件失败："+fileName+"文件不存在");     
	             return false;     
	         }else{     
	             if(file.isFile()){     
	                      
	                 return deleteFile(fileName);     
	             }else{     
	                 return deleteDirectory(fileName);     
	             }     
	         }     
	     }     
	          
	     /**   
	      * 删除单个文件   
	      * @param   fileName    被删除文件的文件名   
	      * @return 单个文件删除成功返回true,否则返回false   
	      */    
	     public static boolean deleteFile(String fileName){     
	         File file = new File(fileName);     
	         if(file.isFile() && file.exists()){     
	             file.delete();     
	             System.out.println("删除单个文件"+fileName+"成功！");     
	             return true;     
	         }else{     
	             System.out.println("删除单个文件"+fileName+"失败！");     
	             return false;     
	         }     
	     }     
	          
	     /**   
	      * 删除目录（文件夹）以及目录下的文件   
	      * @param   dir 被删除目录的文件路径   
	      * @return  目录删除成功返回true,否则返回false   
	      */    
	     public static boolean deleteDirectory(String dir){     
	         //如果dir不以文件分隔符结尾，自动添加文件分隔符     
	         if(!dir.endsWith(File.separator)){     
	             dir = dir+File.separator;     
	         }     
	         File dirFile = new File(dir);     
	         //如果dir对应的文件不存在，或者不是一个目录，则退出     
	         if(!dirFile.exists() || !dirFile.isDirectory()){     
	             System.out.println("删除目录失败"+dir+"目录不存在！");     
	             return false;     
	         }     
	         boolean flag = true;     
	         //删除文件夹下的所有文件(包括子目录)     
	         File[] files = dirFile.listFiles();     
	         for(int i=0;i<files.length;i++){     
	             //删除子文件     
	             if(files[i].isFile()){     
	                 flag = deleteFile(files[i].getAbsolutePath());     
	                 if(!flag){     
	                     break;     
	                 }     
	             }     
	             //删除子目录     
	             else{     
	                 flag = deleteDirectory(files[i].getAbsolutePath());     
	                 if(!flag){     
	                     break;     
	                 }     
	             }     
	         }     
	              
	         if(!flag){     
	             System.out.println("删除目录失败");     
	             return false;     
	        }     
	              
	         //删除当前目录     
	         if(dirFile.delete()){     
	             System.out.println("删除目录"+dir+"成功！");     
	             return true;     
	         }else{     
	             System.out.println("删除目录"+dir+"失败！");     
	             return false;     
	         }     
	     }   
	

}
