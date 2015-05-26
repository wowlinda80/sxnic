package net.sxnic.comm.attachment;

/**
 * 附件大小超过设置大小的异常类
 * @author 孙宇飞
 *
 */
@SuppressWarnings("serial")
public class FileSizException extends Exception {
	
	public FileSizException(){
		super();
	}
	
	public FileSizException(String str){
		super(str);
	}

}
