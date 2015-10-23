package io;

import java.io.File;
import java.io.IOException;

import ellucian.dao.IssueJira;
import ellucian.dao.SubtasksCounter;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ManageReader {
	
	//ATTRIBUTES 
	private String inputFile;
	private static final int NUMBER_OF_COLUMNS = 9;

	//@MORPHIS TODO
	//ORIGINAL: public SubTaskJira(String bannerModule, String solutionCenter, String moduleType, String moduleName, String structuralComplexity, String migrationComplexity, String number, String phase, String module) 
	private static final int BANNER_MODULE_TITLE = 0;
	private static final int SOLUTION_CENTER_TITLE = 1;
	private static final int MODULE_TYPE_TITLE = 2;
	private static final int MODULE_NAME_TITLE = 3;
	private static final int STRUCTURAL_COMPLEXITY_TITLE = 4;
	private static final int MIGRATION_COMPLEXITY_TITLE = 5;
	private static final int NUMBER_TITLE = 6;
	private static final int PHASE_TITLE = 7;
	private static final int MODULE_TITLE = 8;

	
	//CONSTRUCTOR
	public ManageReader(String inputFile) {
		this.inputFile = inputFile;
	}
	
	public ManageReader() {
		inputFile = null;

	}
	
	//VOIDS AND FUNCS
	/** READ FROM EXCEL FILE */
	public void readExcelSubtaskCounter(ManageIssueJira manageIssuesJira) throws IOException {
		SubtasksCounter subtaskCounter = new SubtasksCounter();
		final int NUMBER_OF_COLUMNS_SUBTASK_COUNTER = 3;
		
		File inputWoorkbook = new File("C:\\Users\\Americo\\Documents\\Development_JIRA\\jira-Ellucian\\query\\reportSubtaskCounter.xls");
		Workbook w;

		try {
			w = Workbook.getWorkbook(inputWoorkbook);

			// SET THE FIRST SHEET
			Sheet sheet = w.getSheet(0);
			Cell[] rowExcel = new Cell[NUMBER_OF_COLUMNS_SUBTASK_COUNTER];

			for (int i = 0; i < sheet.getRows(); i++) {
				for (int j = 0; j < sheet.getColumns() + 1; j++) {

					// CHANGE ROW
					if (j == NUMBER_OF_COLUMNS_SUBTASK_COUNTER) {
						// AVOID EMPTY ROW
						if (rowExcel[0].getContents() == "") {
							break;
						}
						
						//COUNTER
						String names = rowExcel[1].getContents();
						String[] parts = names.split(" ");
						int numberParts = parts.length-1;
						
						// CREATE AND KEEP IN LIST
						subtaskCounter = new SubtasksCounter(
								rowExcel[0].getContents(), 
								rowExcel[1].getContents(),
								numberParts				
						);
						
						//ADD TO LIST
						manageIssuesJira.getSubtasksCounterList().add(subtaskCounter);
						break;
					}

					// CELL
					Cell cell = sheet.getCell(j, i);
					rowExcel[j] = cell;
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}
	
	/** READ FROM EXCEL FILE */
	public void readExcelToIssueJira(ManageIssueJira manageIssuesJira, ManageString manageString) throws IOException {

		IssueJira issue = new IssueJira();
		
		File inputWoorkbook = new File(inputFile);
		Workbook w;

		try {
			w = Workbook.getWorkbook(inputWoorkbook);

			// SET THE FIRST SHEET
			Sheet sheet = w.getSheet(0);
			Cell[] rowExcel = new Cell[NUMBER_OF_COLUMNS];

			for (int i = 0; i < sheet.getRows(); i++) {
				for (int j = 0; j < sheet.getColumns() + 1; j++) {

					// CHANGE ROW
					if (j == NUMBER_OF_COLUMNS) {
						// AVOID EMPTY ROW
						if (rowExcel[0].getContents() == "") {
							break;
						}
						// CREATE AND KEEP IN LIST
						issue = new IssueJira(
								rowExcel[BANNER_MODULE_TITLE].getContents(), 
								rowExcel[SOLUTION_CENTER_TITLE].getContents(),
								rowExcel[MODULE_TYPE_TITLE].getContents(), 
								rowExcel[MODULE_NAME_TITLE].getContents(), 
								rowExcel[STRUCTURAL_COMPLEXITY_TITLE].getContents(),
								rowExcel[MIGRATION_COMPLEXITY_TITLE].getContents(), 
								rowExcel[NUMBER_TITLE].getContents(), 
								rowExcel[PHASE_TITLE].getContents(),
								rowExcel[MODULE_TITLE].getContents()
						);
						
						//CLEAN NAME OF FORM
						String nameIssue = issue.getNameIssue();
						issue.setNameIssue(manageString.cleanNameIssueFromExcelAfter(nameIssue));
						
//						//CHANGE STRING TO CAPITAL LETTER
						String migrationComplexityCapital = issue.getMigrationComplexity();
						migrationComplexityCapital = manageString.changeCaseCapitalLetter(migrationComplexityCapital);
						issue.setMigrationComplexity(migrationComplexityCapital);
						String structuralComplexityCapital = issue.getStructuralComplexity();
						structuralComplexityCapital = manageString.changeCaseCapitalLetter(structuralComplexityCapital);
						issue.setStructuralComplexity(structuralComplexityCapital);
						
						//CHECK IF VALUES IN EXCEL FOR PHASE AND MODULE ARE EMPTY
						String phaseEmpty = issue.getPhase();
						//ELIMINAR ANY SPACES
						phaseEmpty = manageString.cleanEmptySpaces(phaseEmpty);
						if (phaseEmpty.equals("")) {
							phaseEmpty = "None";
							issue.setPhase(phaseEmpty);
						}
						String moduleEmpty = issue.getModule();
						//ELIMINAR ANY SPACES
						moduleEmpty = manageString.cleanEmptySpaces(moduleEmpty);
						if (moduleEmpty.equals("")) {
							moduleEmpty = "None";
							issue.setModule(moduleEmpty);
						}
						
						//RETURN NUMBER IN MIN: DAY * HOUR (8 * 60)
						double numberDouble = Double.parseDouble(issue.getNumber());
						numberDouble = numberDouble * 8 * 60;
						issue.setNumber(String.valueOf((int)numberDouble));

						//ADD TO LIST
						manageIssuesJira.getIssuesStatic().add(issue);
						break;
					}

					// CELL
					Cell cell = sheet.getCell(j, i);
					rowExcel[j] = cell;
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}
	
	

}
