package customerportal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import common.Util;

public class ReadExcelData {

	/**
	 * @param args
	 * @return 
	 * @throws BiffException 
	 * @throws IOException
	 */
	public static  HashMap<String, String> getServiceCodes (String myfilename) throws IOException {
		 HashMap<String, String> map = new HashMap<>(); 

		try {
			InputStream myfile;
			myfile = new FileInputStream(myfilename);
			Workbook mywb = Workbook.getWorkbook(myfile);
			 Sheet sheet = mywb.getSheet(0);
			
			 Util.printInfo("rows  :" + sheet.getRows());
			 Util.printInfo("cols  :" +	 sheet.getColumns());
		
			 for(int i =0;i<sheet.getRows();i++)
			 {
					 Cell cell1 = sheet.getCell(0, i);
					 Cell cell2 = sheet.getCell(1, i);
//					 Util.printInfo("cell1 contents : " + cell1.getContents());
//					 Util.printInfo("cell2 contents : " + cell2.getContents());
					 map.put(cell1.getContents(), cell2.getContents());
			 }
			 Util.printInfo("Size of hash map : " + map.size());
			 
			
		}catch (BiffException e) {
			      e.printStackTrace();
			    }
		 return map;

	}

}
