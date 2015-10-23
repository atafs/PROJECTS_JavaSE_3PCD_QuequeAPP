package  file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ellucian.dao.SubTaskJiraUseCases;
import log.LogMessage;


/** <h1> READER class: using libraries like: </h1><P>
 * <li> POI to read Excel <P> 
 * <li> FileReader class for IO use.. <P> 
 */
public class NReaderJira {
	
	//ATTRIBUTES 
	private String inputFile;
	private String fileName;
	//LIST OF 
	private List<String> listReadExcelSubTasks = new ArrayList<String>();
	private static final int NUMBER_OF_COLUMNS_USE_CASES = 3;
	
	//CONSTRUCTOR
	public NReaderJira(String inputFile) {
		this.inputFile = inputFile;
	}
	
	public NReaderJira() {
		inputFile = null;
	}

	//READ FROM FILE READER PROPERTIES
	public String readFileReader(String path, String string) {
		String toReturn = "";
		try{
			FileReader reader = new FileReader(path);
			Properties properties = new Properties();
			properties.load(reader);
			toReturn = properties.getProperty(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	//READ FROM EXCEL FILE: POI LIB
	public void readExcelSubTask(String path, SubTaskJiraUseCases subtask, LogMessage logger) {
		try {
			File myFile = new File(path);
			FileInputStream fis = new FileInputStream(myFile);

	        // Finds the workbook instance for XLSX file
	        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
	        // Return first sheet from the XLSX workbook
	        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
	        // Get iterator to all the rows in current sheet
	        Iterator<Row> rowIterator = mySheet.iterator();
	       
	        //COUNTERS
	        int counterRow = -1; 
	        int counterColumn = -1; 
	        int counterCell = -1;
	        int flagIsSecondColumn= -1;

	        // Traversing over each row of XLSX file
	        final int JUMP_NEXT_ROW = 1;
	        
	        //ROW
	        while (rowIterator.hasNext()) {
	        	
//	            //JUMP ITERATION IF TIOS ROW IS NOT A SUBTASK
//                if (flagIsSecondColumn == JUMP_NEXT_ROW) {
//    	        	flagIsSecondColumn = (-1);
//                	continue;
//				}
                
	        	//COUNTERS
	        	counterRow++;
	        	counterCell = -1;
	        	flagIsSecondColumn = -1;
	        	counterColumn = -1;
	        	boolean flagIsNewSubTask = true;	
	        	boolean flagIsID = false;	        	

	            Row row = rowIterator.next();
	            
	            //REMOVE TITLES
	            if (counterRow == 0) {
					continue;
				}

	            // For each row, iterate through each columns
	            Iterator<Cell> cellIterator = row.cellIterator();
	            
	            
	            //CELL FROM COLUMN
	            while (cellIterator.hasNext()) {
	                
		        	//COUNTERS
	            	counterColumn++;
	            	//JUMP ITERATION IF THIS CELL IS NOT A SUBTASK
	                if (!flagIsNewSubTask || flagIsSecondColumn == JUMP_NEXT_ROW) {
	                	cellIterator.next();
//						continue;
						break;
					}
	            	counterCell++;
	            
	                Cell cell = cellIterator.next();
	                switch (cell.getCellType()) {
	                case Cell.CELL_TYPE_STRING:	  
	                	
	                	if (flagIsID) {
	                		//ALGORITHM: TO INSERT ONLY TWO COLUMNS
		                	flagIsSecondColumn++;

		                	listReadExcelSubTasks.add(cell.getStringCellValue());
		                	subtask.getSubtasks().add(cell.getStringCellValue());		              
		                	logger.getLog().debug("cell.getStringCellValue="+cell.getStringCellValue());
		                	flagIsNewSubTask = true;
		                    break;
						}
	                	
	                	//FLAG: TO BREAK AND GO TO NEXT
	                	flagIsSecondColumn = 1;
	                    break;

	                case Cell.CELL_TYPE_NUMERIC:
	                	//ALGORITHM: TO CHECK IF THE IT PASSED BY THE NUMBER FIRST
	                	flagIsID = true;
	                	
	                	//ALGORITHM: TO INSERT ONLY TWO COLUMNS
	                	flagIsSecondColumn++;
	                	
	                	//ALGORITHM: IF NUMBER LESS THAN 100, THEN IT IS NOT A SUBTASK
	                	if (counterCell == NUMBER_OF_COLUMNS_USE_CASES || cell.getNumericCellValue() < 100) {
	                		flagIsNewSubTask = false;
	                		break;
						}
	                	
	                	String id = String.valueOf((int)cell.getNumericCellValue());
	                	listReadExcelSubTasks.add(id);
	                	subtask.getSubtasks().add(id);	      	
	                	logger.getLog().debug("cell.getNumericCellValue="+id);
	                    break;
	                    
	                default :
						logger.getLog().debug("NReaderJira.readExcelSubTask="+"  DEFAULT VALUE: NOT A CELL_TYPE_STRING: NOR CELL_TYPE_NUMERIC");
						
			          	//FLAG: TO BREAK AND GO TO NEXT
	                	flagIsSecondColumn = 1;
	                    break;
	                }
	             }
	          }
		} catch (Exception e) {
			logger.getLog().debug("Exception="+e);
		}
		
		
	}

	//GETTERS
	public String getInputFile() {
		return inputFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<String> getListReadExcelSubTasks() {
		return listReadExcelSubTasks;
	}
}
