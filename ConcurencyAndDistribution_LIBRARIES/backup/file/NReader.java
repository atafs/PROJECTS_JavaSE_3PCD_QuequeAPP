package  file;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;
//import io.excel.properties.SubTaskJira;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ellucian.dao.SubTaskJira;
import ellucian.dao.SubTaskJiraUseCases;
import ellucian.enums.FileExtension;

//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class NReader {
	
	//ATTRIBUTES 
	private String inputFile;
	//ELLUCIAN DATA
	private SubTaskJira subTask;
	private List<SubTaskJira> subTaskJiraList = new ArrayList<SubTaskJira>();
	private static final int NUMBER_OF_COLUMNS = 9;
	//SUBTASKS
	private SubTaskJiraUseCases subTaskUseCase;
	private List<SubTaskJiraUseCases> subTaskJiraUseCasesList = new ArrayList<SubTaskJiraUseCases>();
	private static final int NUMBER_OF_COLUMNS_USE_CASES = 3;
	
	//CONSTRUCTOR
	public NReader(String inputFile) {
		this.inputFile = inputFile;
	}
	
	public NReader() {
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
	
	//READ FROM EXCEL FILE
	public void readExcel() throws IOException {
		
		File inputWoorkbook = new File(inputFile);
		Workbook w;
		
		try {
			w = Workbook.getWorkbook(inputWoorkbook);
			
			//SET THE FIRST SHEET
			Sheet sheet = w.getSheet(0);
			Cell[] rowExcel = new Cell[NUMBER_OF_COLUMNS];
			
			for (int i = 0; i < sheet.getRows(); i++) {
				for (int j = 0; j < sheet.getColumns()+1; j++) {
					
					//CHANGE ROW
					if (j == NUMBER_OF_COLUMNS) {
						//AVOID EMPTY ROW
						if (rowExcel[0].getContents() == "") {
							break;
						}
						//CREATE AND KEEP IN LIST
						subTask = new SubTaskJira(rowExcel[0].getContents(), 
								rowExcel[1].getContents(), 
								rowExcel[2].getContents(), 
								rowExcel[3].getContents(), 
								rowExcel[4].getContents(), 
								rowExcel[5].getContents(), 
								rowExcel[6].getContents(), 
								rowExcel[7].getContents(), 
								rowExcel[8].getContents());				
						subTaskJiraList.add(subTask);
						break;
					}
					
					//CELL
					Cell cell = sheet.getCell(j,i);
					rowExcel[j] = cell;
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	} 
	
	//READ FROM EXCEL FILE
	public void readExcelUseCases(String path) throws IOException {
		
		File inputWoorkbook = new File(path);
		String nameForm = inputWoorkbook.getName();
		nameForm = nameForm.replace("." + FileExtension.EXCEL.getValue(), "");
		//TOIMPROVE
		nameForm = nameForm.replace(".xls", "");
		
		Workbook w;
		
		try {
			w = Workbook.getWorkbook(inputWoorkbook);
			
			//SET THE FIRST SHEET
			Sheet sheet = w.getSheet(0);
			Cell[] rowExcel = new Cell[NUMBER_OF_COLUMNS_USE_CASES];
			
			for (int i = 0; i < sheet.getRows(); i++) {
				for (int j = 0; j < sheet.getColumns()+1; j++) {
					
					//CHANGE ROW
					if (j == NUMBER_OF_COLUMNS_USE_CASES) {
						//AVOID EMPTY ROW
						if (rowExcel[0].getContents() == "") {
							break;
						}
						//CREATE AND KEEP IN LIST
						subTaskUseCase = new SubTaskJiraUseCases(rowExcel[0].getContents(), rowExcel[1].getContents(), rowExcel[2].getContents());
						subTaskJiraUseCasesList.add(subTaskUseCase);
						break;
					}
					
					//CELL
					Cell cell = sheet.getCell(j,i);
					rowExcel[j] = cell;
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	} 
	
	//GETTERS
	public String getInputFile() {
		return inputFile;
	}

	public SubTaskJira getSubTask() {
		return subTask;
	}

	public List<SubTaskJira> getSubTaskJiraList() {
		return subTaskJiraList;
	}

	public void setSubTaskJiraList(List<SubTaskJira> subTaskJiraList) {
		this.subTaskJiraList = subTaskJiraList;
	}

	public SubTaskJiraUseCases getSubTaskUseCase() {
		return subTaskUseCase;
	}

	public List<SubTaskJiraUseCases> getSubTaskJiraUseCasesList() {
		return subTaskJiraUseCasesList;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	//PARAGRAPH
	public void paragraph() {
		System.out.println();
	}
}
