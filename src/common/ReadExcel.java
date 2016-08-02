package common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class ReadExcel {

  private String inputFile;

  public void setInputFile(String inputFile) {
    this.inputFile = inputFile;
  }

  public List read() throws IOException  {
    File inputWorkbook = new File(inputFile);
    Workbook w;
    List<String> list = new ArrayList<String>();
    try {
    	
//      w = Workbook.getWorkbook(inputWorkbook);
    	WorkbookSettings ws = new WorkbookSettings();
    	ws.setLocale(new Locale("en", "EN"));

    	Workbook workbook = Workbook.getWorkbook( new File(inputFile),ws);
      // Get the first sheet
      Sheet sheet = workbook.getSheet(0);
      // Loop over first 10 column and lines

      for (int j = 0; j < sheet.getColumns(); j++) {
//        for (int i = 0; i < sheet.getRows(); i++) {
          Cell cell = sheet.getCell(j, 0);
         if(cell.getContents().equalsIgnoreCase("username")){
        	 Cell cell1=  sheet.getCell(j, 1);
        	 String userName = cell1.getContents();
        	list.add(userName);
         }
         
         if(cell.getContents().equalsIgnoreCase("password")){
        	 Cell cell2=  sheet.getCell(j, 1);
        	 String password = cell2.getContents();
         	list.add(password);
          }
        
      	}
      }
      catch (BiffException e) {
      e.printStackTrace();
    }
	return list;
  }

  public List readExcel() throws IOException {
    ReadExcel test = new ReadExcel();
    test.setInputFile("c:/test.xls");
    List loginDetails = test.read();
	return loginDetails;
  }

} 