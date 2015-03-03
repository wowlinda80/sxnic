package net.sxnic.comm.attachment;
/**
 * 文件类型不符合的异常类
 * @author 孙宇飞
 *
 */
@SuppressWarnings("serial")
public class FileTypeException extends Exception {
	
	public FileTypeException(){
		super();
	}
	
	public FileTypeException(String str){
		super(str);
	}

}
