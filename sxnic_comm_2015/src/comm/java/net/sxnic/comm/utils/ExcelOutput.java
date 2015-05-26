package net.sxnic.comm.utils;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 电子表格输出方法类
 * @author 李向东
 * @version 1.00
 */
public class ExcelOutput {

    private HSSFCellStyle style = null ;
    private HSSFWorkbook wb = null ;
    private FileOutputStream fileOut = null ;
    private HSSFSheet sheet1 = null ;
    private HSSFRow row = null ;
    private HSSFCell cell = null ;
    
    private String strExcelFileName;

    /**
     * 构造方法
     *
     */
    public ExcelOutput()
    {
    	strExcelFileName = "";
    }
    
	/**
	 * 创建电子表格表头，不带文件的
	 */
	public void creBegin() {
		try {
			// Creating a new Excel Workbook
			wb = new HSSFWorkbook();
			sheet1 = wb.createSheet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /** 
     * 创建电子表格表头
     * @param fstrFileName 属性名称
     */
    public void creBegin(String fstrFileName)
    {
//    	strExcelFileName = "F:/"+ fstrFileName +".xls" ;
    	strExcelFileName = fstrFileName;
        try {
			//Creating a new Excel Workbook
			wb = new HSSFWorkbook () ;
			fileOut = new FileOutputStream ( strExcelFileName ) ;

			//Creating a new sheet1 inside the workbook
			wb = new HSSFWorkbook () ;
			sheet1 = wb.createSheet () ;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 设置 列宽
     * @param fintIndex 序列
     * @param fintWidth 宽度
     */
    public void setColumnWidth(int fintIndex,int fintWidth)
    {
        sheet1.setColumnWidth(fintIndex,fintWidth);
    }
    
    /**
     * 设置 行高
     * @param fintIndex 序列
     * @param fintWidth 宽度
     */
    public void setDefaultRowHeight(short height)
    {
        sheet1.setDefaultRowHeight(height);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    }
    
    /**
     * 创建一行
     * @param 序列
     */
    public void creRow(int fintIndex)
    {
    	row = sheet1.createRow ( ( short ) fintIndex) ;
    }
    
    /**
     * 创建 单元格标题
     * @param fintIndex 序列
     * @param fstrData 内容
     */
    public void creCellTitle(int fintIndex,String fstrData)
    {
        cell = row.createCell (fintIndex) ;
//        cell.setEncoding ( cell.ENCODING_UTF_16 ) ;
        cell.setCellValue (fstrData ) ;
//        style = wb.createCellStyle () ;
//        style.setFillForegroundColor ( HSSFColor.TAN.index ) ;
//        style.setFillPattern ( HSSFCellStyle.SOLID_FOREGROUND ) ;
//        cell.setCellStyle ( style ) ;
    }
    
    /**
     * 创建单元格
     * @param fintIndex 序列
     * @param fstrData 内容
     */
    public void creCell(int fintIndex,String fstrData)
    {
        cell = row.createCell ( fintIndex) ;
//        cell.setEncoding ( cell.ENCODING_UTF_16 ) ;
        cell.setCellValue (fstrData ) ;
    }
    public InputStream creInputStreamOver() {
		InputStream inStream = null;
		try {
			// 写文件，完成保存任务
			inStream = workbook2InputStream(wb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sheet1 = null;
			wb = null;
			try {
				inStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inStream;
	}

	public InputStream workbook2InputStream(HSSFWorkbook workbook) {
		InputStream inStream = null;
		try {
			// 用流输出导出数据的excel文件
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook.write(baos);
			baos.flush();
			byte[] aa = baos.toByteArray();
			inStream = new ByteArrayInputStream(aa, 0, aa.length);
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inStream;
	}
    /**
     * 创建电子表格表尾
     *
     */
    public void creOver()
    {
        try {
			//写文件，完成保存任务
			wb.write(fileOut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 设定自动折行
     *
     */
    public void setWrapTex(){
    	style.setWrapText(true);
    }
    
    public void setAlignment(){
    	  style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	  style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	  style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	  style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	  style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
    }
    
    public static void  main(String[] args)
    {
    	ExcelOutput eo=new ExcelOutput();
    	eo.creBegin("e:/aa.xls");
    	eo.creRow(0);
    	eo.creCellTitle(0, "山西省三多发送到非三分三多方所得");
    	eo.creRow(1);
    	eo.creCellTitle(0, "sdfdfd");
    	for (int i = 2; i < 5; i++) {
			eo.creRow(i);
			eo.creCell(0, "内容");
		}
    	eo.creOver();
    }
    
}