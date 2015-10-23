package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import ellucian.NodeJira;
import ellucian.dao.IssueJira;
import ellucian.dao.SubTaskJiraUseCases;
import ellucian.dao.SubtasksCounter;
import jxl.Workbook;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ManageIO {
	
	// ATTRIBUTES
	private File nodeFile;	
	
	// CONSTRUCTOR
	public ManageIO(File nodeFile) {
		this.nodeFile = nodeFile;
	}

	// GETTER
	public File getNodeFile() {
		return nodeFile;
	}

	// WRITE TO EXCEL
	public void writeToExcel(String path, ArrayList<Object> cells) {
		try {
			// EXCEL
			WritableWorkbook workbook = Workbook.createWorkbook(new File(path));
			WritableSheet sheet = workbook.createSheet("MorphisSheet", 0);

			// ADDING CELLS
			for (Object cell : cells) {
				sheet.addCell((WritableCell) cell);
			}

			// WRITE
			workbook.write();
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** PROCEDURE: prints to a doc a list of Nodes */
	public void printTXTListIssueJira(String pathEllucianTXT, File nodeFile, NodeJira node, String pathNodeIssue) {
		// PRINT TO FILE TXT
		queryGetNodePath(pathEllucianTXT, node, pathNodeIssue);
	}
	
	/** AUX METHOD: printTXTListIssueJira(...) */
	public void queryGetNodePath(String pathPrintWriter, NodeJira node, String pathNodeIssue) {

//		File file = new File(pathPrintWriter);
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(pathPrintWriter);

			// PRINT TO FILE TXT
			for (String string : node.getNodeList()) {
				// can add info to delete in string path in common
				String temp = string.replace(pathNodeIssue + File.separator, "");
				printWriter.println(temp);
			}
			printWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			printWriter.close();
		}
	}
	
	/** PROCEDURE: prints to a doc a list of FORMS in EXCEL Template */
	public void queryGetFormNameInExcel(String queryGetFormNameInExcel, List<IssueJira> issuesStatic) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(queryGetFormNameInExcel);
			// PRINT TO FILE TXT
			printWriter.println("-------------GetFormNameInExcel\n");
			for (IssueJira issueJira : issuesStatic) {
				// can add info to delete in string path in common
				String temp = "Name = " + issueJira.getNameIssue();
				printWriter.println(temp);
			}
			printWriter.println("\n-------------endedWithSuccess");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			printWriter.close();
		}
	}
	
	/** PROCEDURE: prints to a doc a list of FORMS in Nodes */
	public void queryGetFormNameInNode(String queryGetFormNameInExcel, List<SubTaskJiraUseCases> subTaskJiraUseCasesList) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(queryGetFormNameInExcel);
			// PRINT TO FILE TXT
			printWriter.println("-------------GetFormNameInNode\n");
			for (SubTaskJiraUseCases subtask : subTaskJiraUseCasesList) {
				// can add info to delete in string path in common
				String temp = "Name = " + subtask.getModuleName();
				printWriter.println(temp);
			}
			printWriter.println("\n-------------endedWithSuccess");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			printWriter.close();
		}
	}
	
	/** PROCEDURE: prints to a doc a list that compares if the forms in excel are in the nodes as well */
	public void queryIsFormInExcelAndNode(String queryIsFormInExcelAndNode, ManageIssueJira manageIssuesJira) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(queryIsFormInExcelAndNode);
			int counter = 0;
			// PRINT TO FILE TXT
			printWriter.println("-------------IsFormInExcelAndNode\n");
			for (IssueJira issueJira : manageIssuesJira.getIssuesStatic()) {
				for (SubTaskJiraUseCases subtask : manageIssuesJira.getSubTaskJiraUseCasesList()) {
					if ( issueJira.getNameIssue().equals(subtask.getModuleName()) ) {
						counter++;
						String temp = "NAME = " + issueJira.getNameIssue() + " => COUNTER = " + counter;
						printWriter.println(temp);
						break;
					} 
				}
			}
			printWriter.println("\n-------------endedWithSuccess");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			printWriter.close();
		}
	}

	/** PROCEDURE: prepare the query to use in the -r (console option) to create report to deliver of work done*/
	public void queryMultipleForms(String queryMultipleForms, ManageIssueJira manageIssuesJira) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(queryMultipleForms);
			int counter = 0;
			int listLastElement = manageIssuesJira.getIssuesStatic().size()-1;
			String toReturn = "";
			// PRINT TO FILE TXT
			toReturn += "-------------queryMultipleForms in APP (remove -r \"\" to use it in JIRA)\n\n";
			toReturn += "-r \" ";
			for (IssueJira issueJira : manageIssuesJira.getIssuesStatic()) {
				//CHECK IF IT IS THE LAST ELEMENT OF THE LIST
				if (counter == listLastElement) {
					toReturn += "text ~ \'" + issueJira.getNameIssue() + "_FRM" + "\'";
					toReturn += " \"";
					break;
				}
				toReturn += "text ~ \'" + issueJira.getNameIssue() + "_FRM"  + "\' OR ";
				counter++;
			}
			printWriter.println(toReturn);
			printWriter.println("\n-------------endedWithSuccess => FORMS = " + (counter+1));
			printWriter.println("\nATTENCTION: USE ONLY 50 REGISTERS AT A TIME");
			printWriter.println("\t- THE LIMIT OF READ PER PAGE IN JIRA TO THIS APP");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			printWriter.close();
		}
	}
	
	/** PROCEDURE: prepare the query to use in the -r (console option) to create report to deliver of work done*/
	public void countNumberOfSubtasksFromFile(ManageIssueJira manageIssuesJira){
		
		//READ
		ManageReader reader = new ManageReader();
		try {
			reader.readExcelSubtaskCounter(manageIssuesJira);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//WRITE
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter("C:\\Users\\Americo\\Documents\\Development_JIRA\\jira-Ellucian\\query\\querycountNumberOfSubtasksFromFile.txt");
			// PRINT TO FILE TXT
			for (SubtasksCounter subtaskcounter : manageIssuesJira.getSubtasksCounterList()) {
				// can add info to delete in string path in common
				String toReturn= "";
				toReturn += subtaskcounter.getCounterOfSubtasks() + " = ";
				toReturn += subtaskcounter.getFormName() + " ";
				printWriter.println(toReturn);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			printWriter.close();
		}
		
	} 
	
	
	//PARAGRAPH
	// PARAGRAPH
	public void paragraph() {
		System.out.println();
	}

}
