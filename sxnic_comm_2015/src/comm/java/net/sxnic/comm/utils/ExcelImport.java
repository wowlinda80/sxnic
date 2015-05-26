package net.sxnic.comm.utils;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 电子表格导入方法类
 * @author 张国业
 * @version 1.00
 */
public class ExcelImport {

    //private HSSFRow row = null ;
    //private HSSFCell cell = null ;
    
    private HSSFSheet sheet;
    
    private HSSFWorkbook workbook;
    
    //private String strExcelFileName;

	/**
     * 构造方法
     *
     */
    public ExcelImport()
    {
    	//strExcelFileName = "";
    }
    
    /**
     * 构造方法
     * @param excelFile 文件
     */
    public void importFile(File excelFile){
    	
    	try{
    		//读取Excel工作表
    		workbook = new HSSFWorkbook(FileUtils.openInputStream(excelFile));;
    		//workbook.getSheet("sheet1");
    	
    	} catch (Exception e){
    		
    		
    	}
    	
    }
    
    public void setSheet(String sheetName){
    	
    	sheet = workbook.getSheet(sheetName);
    }
    
    /**
     * 设定得分列表
     * @param index 行号  cellNum 列数
     */
    public List<String> getScoreListRow(int index, int start, int end){
    	
    	List<String> scoreList = new ArrayList<String>();
    	
    	HSSFRow row = sheet.getRow(index);
    	HSSFCell cell = null;
    	
    	//逐行逐列的设定打分值
    	for(int i=start;i<end;i++){
    		
    		cell = row.getCell(i);
    		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    		
    		if(StringUtils.isNotBlank(cell.getStringCellValue())){
    			//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    			//从Excel文件中获得打分值
    			scoreList.add(cell.getStringCellValue());
    		}
    	}
    	
    	return scoreList;
    }

	public HSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

    public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

}