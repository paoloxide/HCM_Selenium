package common;

import static util.ReportLogger.log;
import static util.ReportLogger.logDebug;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Parameters;

public class ExcelUtilities {
	
	public static XSSFSheet ExcelWSheet;
    public static XSSFWorkbook ExcelWBook;
    public static XSSFCell Cell;
    public static XSSFRow Row;
	public static int TestCaseRow;
	
//	These methods will be used for getting and setting data from MS Excel (Datadriven Framework)
//    
//    @author lenard.g.magpantay
   
  
  
  //This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
  public static String getCellData(int RowNum, int ColNum) throws Exception{
      String CellData = null;

  	try{	        	   
      	  Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);	        	  
      	  Cell.setCellType(Cell.CELL_TYPE_STRING);
      	  CellData = Cell.getStringCellValue();
       
  	  		return CellData;
            
            }catch (Exception e){
              return"";
            }
  }	   

	    
  //This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
  public static void setExcelFile(String Path,String SheetName) throws Exception {
      try {
          // Open the Excel file
       FileInputStream ExcelFile = new FileInputStream(Path);
       // Access the required test data sheet
       ExcelWBook = new XSSFWorkbook(ExcelFile);
       ExcelWSheet = ExcelWBook.getSheet(SheetName);
       log("Excel sheet opened");
       } catch (Exception e){
           throw (e);
       }
}
    
  
  public static int getRowContains(String TestNo, int colNum, String sTestCaseName, int colNum2) throws Exception{
		int i;

		try {
			
			int rowCount = getRowUsed();
			outer:
			for ( i=1 ; i < rowCount; i++){
  				if  (getCellData(i,colNum).equalsIgnoreCase(TestNo) && getCellData(i,colNum2).equalsIgnoreCase(sTestCaseName)){
	    			break outer;
				} 
			}
			return i;
				}catch (Exception e){
			logDebug("Class ExcelUtil | Method getRowContains | Exception desc : " + e.getMessage());
			throw(e);
			}
		}
	
	public static int getRowUsed() throws Exception {
		try{
			int RowCount = ExcelWSheet.getLastRowNum();
			log("Total number of Row used return as < " + RowCount + " >.");		
			return RowCount;
		}catch (Exception e){
			logDebug("Class ExcelUtil | Method getRowUsed | Exception desc : "+e.getMessage());
			System.out.println(e.getMessage());
			throw (e);
		}

	}
	
	public static String getTestCaseName(String TestCase)throws Exception{
		String value = TestCase;
		
		try
		{
			int posi = value.indexOf(TestCase);
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");	
			value = value.substring(posi + 1);
			return value;
		} 
    
		catch (Exception e)
		{
//			logDebug("Class Utils | Method getTestCaseName | Exception desc : "+e.getMessage());
			throw (e);
		}
	}
		
//	  //This method is to write in the Excel cell, Row num and Col num are the parameters
		@Parameters({"ExcelFilePath"})
      @SuppressWarnings("static-access")
		public static void setCellData(String Result,  int RowNum, int ColNum, String ExcelFilePath) throws Exception    {
             try{
                Row  = ExcelWSheet.getRow(RowNum);
              Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
              if (Cell == null) {
                  Cell = Row.createCell(ColNum);
                  Cell.setCellValue(Result);
                  } else {
                      Cell.setCellValue(Result);
                  }
    // Constant variables Test Data path and Test Data file name
                    FileOutputStream fileOut = new FileOutputStream(ExcelFilePath);
                    ExcelWBook.write(fileOut);
                    fileOut.flush();
                  fileOut.close();
                  }catch(Exception e){
                      throw (e);
              }
          }

}
