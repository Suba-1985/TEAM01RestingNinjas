package utilities;




import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_Reader {

	
	 
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	
	public Excel_Reader(String excelPath, String sheetName)
	{
		try {
			workbook=new XSSFWorkbook(excelPath);
			sheet=workbook.getSheet(sheetName);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public String getCellValue(int rowNum,int colNum)
	{
		DataFormatter format=new DataFormatter();
		Cell cell=sheet.getRow(rowNum).getCell(colNum);
		if(cell==null)
		{
			return "";
		}
		try {
			String cellValue=format.formatCellValue(cell);
			return cellValue;
		}catch (Exception e)
		{
			return "ERROR:Failed to read cell value";
		}
	}
		
		
	}
	
	

