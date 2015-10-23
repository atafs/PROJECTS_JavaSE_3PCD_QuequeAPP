package main.console;

import com.atlassian.jira.rest.api.issue.FieldOperation;
import com.atlassian.jira.rest.api.issue.IssueFields;
import com.atlassian.jira.rest.api.issue.IssueUpdateRequest;
import com.atlassian.jira.rest.client.api.*;
import com.atlassian.jira.rest.client.api.domain.AssigneeType;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.BasicIssueType;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.Component;
import com.atlassian.jira.rest.client.api.domain.EntityHelper;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.Subtask;
import com.atlassian.jira.rest.client.api.domain.TimeTracking;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.ComponentInput;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.jira.rest.v2.issue.IssueInputParametersAssembler;
import com.atlassian.jira.rest.v2.issue.UpdateIssueResource;
import com.atlassian.util.concurrent.Promise;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import ellucian.EstadoDaArteExcel;
import ellucian.ManageIssue;
import ellucian.NodeJira;
import ellucian.dao.IssueJira;
import ellucian.dao.PropertiesJira;
import ellucian.dao.SubTaskJira;
import ellucian.dao.SubTaskJiraUseCases;
import ellucian.enums.BannerModule;
import ellucian.enums.ConsoleOptions;
import ellucian.enums.FileExtension;
import ellucian.enums.Module;
import ellucian.enums.SolutionCenter;
import ellucian.enums.TypeOfIssue;
import ellucian.enums.TypeOfSubtask;
import ellucian.files.NReaderJira;
import ellucian.files.xml.dao.SubtaskExcelConstants;
import ellucian.json.JsonToJavaConverter;
import ellucian.json.JsonToJavaConverter.VerifyValue;
import ellucian.manage.ManageIO;
import ellucian.manage.ManageIssueJira;
import ellucian.manage.ManageReader;
import ellucian.manage.ManageString;
import log.LogMessage;
import sun.awt.AWTAccessor.ComponentAccessor;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jettison.json.JSONArray;


public class MainIntegration {
	
	//ATTRIBUTES
	private static URI uri;
	private static boolean quiet = false;
	
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();

	private static JiraRestClient restClient;
	
	
	//ATTRIBUTES #############################################
	//PROPERTIES JIRA
	private static PropertiesJira propertiesJira;
	private static SubtaskExcelConstants subtaskExcelConstants;

	//
	private static List<SubTaskJiraUseCases> subtasksStatic = new ArrayList<SubTaskJiraUseCases>();

	private static int counter = 0;
	
	//
	private static String bannerModuleTemp = "";
    private static String solutionCenterTemp = "";
    private static String projectNameReport = "";
    
    //

    public static void main( String[] args ) throws URISyntaxException, IOException{
    	parseArgs(args);

		// INSTANTIATE PATHS
    	propertiesJira = new PropertiesJira();
		String queryGetNodePath = propertiesJira.getQueryGetNodePath();
		String queryGetFormNameInExcel = propertiesJira.getQueryGetFormNameInExcel();
		String queryGetFormNameInNode = propertiesJira.getQueryGetFormNameInNode();
		String queryIsFormInExcelAndNode = propertiesJira.getQueryIsFormInExcelAndNode();
		String queryMultipleForms = propertiesJira.getQueryMultipleForms();

			
		String pathEllucianReadExcel = propertiesJira.getPathEllucianReadExcel();
		String pathNodeIssue = propertiesJira.getPathNodeIssue(); 
		
		String BANNER_MODULE_ENUM = propertiesJira.getBANNER_MODULE_ENUM();
		String SOLUTION_CENTER_ENUM = propertiesJira.getSOLUTION_CENTER_ENUM();
		String FILE_EXTENSION_ENUM = propertiesJira.getFILE_EXTENSION_ENUM();
		
		// LISTS TODELETE
		BannerModule[] banners = BannerModule.values();
		SolutionCenter[] countries = SolutionCenter.values();//are solution centers
		FileExtension[] extensions = FileExtension.values();
		
		//@MORPHIS TODO
		//... function: List that keep the broken String (BANNER_MODULE_ENUM, SOLUTION_CENTER_ENUM, FILE_EXTENSION_ENUM);
    	
		// INSTANTIATE
		NodeJira node = new NodeJira();
		File nodeFile = new File(pathNodeIssue);
		ManageIssue managerIssue = new ManageIssue();	
		
		// INSTANTIATE UTILITIES
		ManageIO manageIO = new ManageIO(nodeFile);
		ManageString manageString = new ManageString();
		ManageReader manageReader = new ManageReader(pathEllucianReadExcel);
		ManageIssueJira manageIssuesJira = new ManageIssueJira(new ArrayList<IssueJira>(), new ArrayList<SubTaskJiraUseCases>());
		
		// @MORPHIS TODO SAVE DATA FROM EXCEL TO SUBTASKJIRA 
		manageReader.readExcelToIssueJira(manageIssuesJira, manageString);
		
		// #######################################################

		// CREATE A LIST WITH ALL NODES AND ADD TO PROPERTIES FULL PATH
		String nodeFileCleaned = nodeFile.getAbsoluteFile().toString();
//		nodeFileCleaned = nodeFileCleaned.replace(".\\", "");
		nodeFileCleaned = manageString.cleanPathDotDash(nodeFileCleaned);
		propertiesJira.getPathNodeIssue(); 
		propertiesJira.setPathNodeIssue(nodeFileCleaned);
		node.displayIt(nodeFile, queryGetNodePath);
		
		// ALGORITHM FOR SAVING NODES INFORMATION DATA IN A DAO CLASS
		managerIssue.saveFromNodesToJava(manageIssuesJira, managerIssue, node);
    			
		// @MORPHIS SAVE DATA FROM EXCEL TO SUBTASKJIRA 
		managerIssue.saveFromExcelToSubTaskJira(pathEllucianReadExcel, logger, managerIssue);

		// UPDATE VALUES IN LISTS
		manageIssuesJira.updateValuesInSubTask(managerIssue, manageIssuesJira, manageString, node);
    	
		//CLEAN NAME
		for (int i = 0; i < managerIssue.getReader().getSubTaskJiraList().size(); i++) {
			//CLEAN STRING FROM UNDERSCORE
			String cleanString = managerIssue.getFuncOrValidNameRemoveUnderscore(managerIssue.getReader().getSubTaskJiraList().get(i).getModuleName(), logger);		
		}

		//#####################################################

        //ESTABILISHING A CONNECTION 
		final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        uri = new URI(propertiesJira.getJIRA_URL());
		restClient = factory.createWithBasicHttpAuthentication(uri, propertiesJira.getJIRA_ADMIN_USERNAME(), propertiesJira.getJIRA_ADMIN_PASSWORD());
       
		//CREATE PROJECT ##########################################
        ProjectRestClient projectClient = restClient.getProjectClient();
        Project project = projectClient.getProject(propertiesJira.getPROJECT()).claim();
		
        //##########################################################################

		String[] testArgs = args;
		if (testArgs.length == 0) {
			//NO ARG = -y = EXECUTE ALL
			testArgs = new String[1];
			testArgs[0] = "-y";
		}
		String[] parts = testArgs;
		
		//LOGGER
		for (int j = 0; j < parts.length; j++) {
	        logger.getLog().info("PARAMS INPUT FOR REPORT: " + parts[j].trim());

		}
		
		/* LOCAL VARIABLES ######################################### */
		//DEFAULT = CLIENT ELLUCIAN
		projectNameReport = propertiesJira.getPROJECT(); 

		//DEFAULT = NOT PRINTING IN EXCEL THE USE CASES (just the form)
		boolean FlagWithUseCases = false;
		
		//DEFAULT = OF THE FILENAME OF THE EXCEL FILE IF NONE IS WRITEN IN THE CMD
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd=HH-mm-ss");
		Date date = new Date();
        logger.getLog().debug("DATE=" + dateFormat.format(date));
		String fileNameForExcel = "report=" + dateFormat.format(date);
		
		//DEFAULT = QUERY TO INSERT IN THE EstadoDeArteExcel CLASS
		String query = "project = ELLBHR AND issuetype = Form AND 'Banner Module' = 'General'  AND 'Solution Center' = 'GLOBAL' ";

        /* ALGORITHM: FOR SELECTING DIFFERENT OPTIONS ############## */
		int i;
		for (i = 0; i < parts.length; i++) {
			
			/* ##################################################### */
			/* CALL - CREATION OF TASK, ATTACHMENT, SUBTASK OR ALL */
			if (parts[i].equals(ConsoleOptions.TASK.getValue()) || parts[i].equals(ConsoleOptions.ATTACHMENT.getValue()) || parts[i].equals(ConsoleOptions.SUB_TASK.getValue()) || parts[i].equals(ConsoleOptions.QUERY.getValue()) || parts[i].equals(ConsoleOptions.ALL.getValue())) {
				//NO ARGS = it will execute all (create Issue/Form, load Attachments and create Subtasks) except the report
				if (parts[i].equals("-y")) {
					//EXECUTE THE CREATION OF ALL
					logger.getLog().info("EXECUTE THE CREATION OF ALL! ");
					
					//T/t: EXECUTE CREATION OF TASKS/ISSUES
					logger.getLog().info("EXECUTE CREATION OF TASKS/ISSUES! ");	
					//CREATEISSUE
			        for (int k = 0; k < manageIssuesJira.getIssuesStatic().size(); k++) {
						counter = k;
			        	try {							
							// CLEAN_DESIGN 
							createIssues(project,  restClient, counter, manageIssuesJira);

						} catch (Exception e) {
							e.printStackTrace();
						}        
					}   
					
			        //A/a: EXECUTE CREATION OF ATTACHMENTS 
					logger.getLog().info("EXECUTE CREATION OF ATTACHMENTS ! ");
					String pathRoot = propertiesJira.getPathNodeIssue();
			        try {					
						// CLEAN_DESIGN 
						createAllAttach(propertiesJira.getPROJECT(), restClient, pathRoot, manageIssuesJira);
			        } catch (Exception e) {
						e.printStackTrace();
					} 
					
			      //S/s: EXECUTE CREATION OF SUBTASKS
					logger.getLog().info("EXECUTE CREATION OF SUBTASKS! ");

			        try {
						//CREATE SUBTASK
						createSubTasks(project, restClient, manageIssuesJira);
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
				
				/* ##################################################### */
		    	//-t = create Tasks/issues
				if (parts[i].equals("-t")) {
					//T/t: EXECUTE CREATION OF TASKS/ISSUES
					logger.getLog().info("EXECUTE CREATION OF TASKS/ISSUES! ");	
					//CREATEISSUE
			        for (int k = 0; k < manageIssuesJira.getIssuesStatic().size(); k++) {
						counter = k;
			        	try {							
							// CLEAN_DESIGN 
							createIssues(project,  restClient, counter, manageIssuesJira);
						} catch (Exception e) {
							e.printStackTrace();
						}        
			        	//logger.getLog().debug(managerIssue.getIssues().get(counter).toString());
			        	logger.getLog().debug(manageIssuesJira.getIssuesStatic().get(counter).toString());
					}   			}
				
				/* ##################################################### */
				//-q = query using backend java (doc TXT)
				if (parts[i].equals("-q")) {
					
					//Q/q: EXECUTE PRINTS TO FILE TXT 
					logger.getLog().info("EXECUTE QUERY TO TXT! ");
					try {
						// GET LIST OF PATHS IN NODE (directory) 
						manageIO.printTXTListIssueJira(queryGetNodePath, nodeFile, node, pathNodeIssue);
						
						// GET LIST OF NAMES IN EXCEL TEMPLATE
						manageIO.queryGetFormNameInExcel(queryGetFormNameInExcel, manageIssuesJira.getIssuesStatic());
					
			    		// GET LIST OF NAMES IN NODES FILESYSTEM
						manageIO.queryGetFormNameInNode(queryGetFormNameInNode, manageIssuesJira.getSubTaskJiraUseCasesList());
								
						// IS THE FORM IN EXCEL TEMPLATE AND FILESYSTEM
						manageIO.queryIsFormInExcelAndNode(queryIsFormInExcelAndNode, manageIssuesJira);

						// GET QUERY TO SEARCH IN JIRA (from forms created)
						manageIO.queryMultipleForms(queryMultipleForms, manageIssuesJira);
						
						// GET COUNTER OF REPORT FROM THE SUBTASKS EXTRACTED FROM JIRA
						manageIO.countNumberOfSubtasksFromFile(manageIssuesJira);

					} catch (Exception e) {
						e.printStackTrace();
					} 			
				
				}
				
				/* ##################################################### */
				//-a = load Attachments(excel - xlsx)
				if (parts[i].equals("-a")) {
					//A/a: EXECUTE CREATION OF ATTACHMENTS 
					logger.getLog().info("EXECUTE CREATION OF ATTACHMENTS! ");
			        String pathRoot = propertiesJira.getPathNodeIssue();
					try {					
						// CLEAN_DESIGN 
						createAllAttach(propertiesJira.getPROJECT(), restClient, pathRoot, manageIssuesJira);
					} catch (Exception e) {
						e.printStackTrace();
					} 			
				}
				
				/* ##################################################### */
				//-s = create Subtasks
				if (parts[i].equals("-s")) {
					//S/s: EXECUTE CREATION OF SUBTASKS
					logger.getLog().info("EXECUTE CREATION OF SUBTASKS! ");

			        try {
						//CREATE SUBTASK
						createSubTasks(project, restClient, manageIssuesJira);
					} catch (Exception e) {
						e.printStackTrace();
					}			
				}
			
			/* ######################################################## */
			/* CALL - CREATION OF REPORT (can have options to change file name, insert query and choose with or without UseCases) */	
			} else if(parts[i].equals(ConsoleOptions.REPORT.getValue())){
				i++;
//				while (counter != parts.length) {
				
				/* ##################################################### */		
				// -f <fileNameForExcel> = write the name of the excel file
				if (parts[i].equals("-f")) {
					// NEXT ELEMENT
					i++;
					// REPLACE DEFAULT VALUE
					fileNameForExcel = parts[i];
					i++;

				}

				/* ##################################################### */
				// -b <FlagWithUseCases> = print all the use cases as well
				if (parts[i].equals("-b")) {
					// REPLACE DEFAULT VALUE
					FlagWithUseCases = true;
					i++;
				}

				// <query> = if it is not -f nor -b, then it is the query
				// REPLACE DEFAULT VALUE
				// i++;
				query = parts[i];

				/* ##################################################### */
				// <query> = if it is not -f nor -b, then it is the query
				// REPLACE DEFAULT VALUE
//				i++;
//				query = parts[i];
//				}

				/* RUN APPLICATION FOR CREATION OF EXCEL */
				//PATH
				File file = new File(".//excel//report//" + fileNameForExcel + ".xlsx");
				String fullPathName = file.getAbsolutePath();
				fullPathName = fullPathName.replace("\\.", "");
				
				//CALL: METHOD THAT CREATES THE EXCEL FILE QITH REPORT (with logs for simplicity if there is the need for debugging)
		        logger.getLog().info("REPORT: fullPathName=" + fullPathName + "; projectNameReport=" + projectNameReport + "; restClient=" + restClient + "; FlagWithUseCases=" + FlagWithUseCases + "; query=" + query);
				EstadoDaArteExcel report;
				try {
					report = new EstadoDaArteExcel(fullPathName, projectNameReport, restClient, FlagWithUseCases, query);
				} catch (Exception e) {
					e.printStackTrace();
				}
//		        logger.getLog().debug("REPORT=" + report.toString());
		        
//				//DONE
//				logger.getLog().info("EXITING NOW THE APPLICATION IN MAIN");
//				//END APP
//				System.exit(0);
			
			} else if(parts[i].equals(ConsoleOptions.HELPER.getValue()) || parts[i].equals("-help")){	
				
				// Print usage instructions
		        StringBuilder intro = new StringBuilder();
		        intro.append("**********************************************\r\n");
		        intro.append("* JIRA Java REST Client ('JRJC')             *\r\n");
		        intro.append("**********************************************\r");
		        System.out.println(intro.toString());
		        
				//SLEEP (delay of prints)
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String helper1 = "";
				helper1 += "*  => Options Menu (case sensitive)\n";
				helper1 += "*  \t\"\": No Option chosen (do it all)  \n";
				helper1 += "*  \t-t: create Tasks/Issues  \n";
				helper1 += "*  \t-a: load Attachments(excel - xlsx)  \n";
				helper1 += "*  \t-s: create Subtasks  \n";
				helper1 += "*  \t-q: create Query (print to TXT)  \n";
				helper1 += "*  \t-r <option> <query>: create Report  \n";
				helper1 += "*  \t\tOptions Menu: (in this order) \n";
				helper1 += "*  \t\t-f <nameFile>: choose nameFile for the excel doc  \n";
				helper1 += "*  \t\t-b: choose to print all use cases (excel)  \n";
				helper1 += "*  \t\t<query>: is required  \n\n";

				
				helper1 += "*  => Description of the logic: \n";
				helper1 += "*  \t=> the args have to be placed in a specific order. \n";
				helper1 += "*  \t=> if a -r is found, the app will look for  -f, -b and <query> \n";
				helper1 += "*  \t=> (default value for file: report_2015_07_16_15h59min48s \n";
				helper1 += "*  \t=> (default value for query: General'; 'GLOBAL'\"  \n";
				helper1 += "*  \t=> (default value for use cases: false (not printing the uc) ) \n";
				helper1 += "*  \t=> Even after it founds the -r, it will continuo to iterate \n\n";
				
				helper1 += "*  => POWER BY: MORPHIS!!!";
				System.out.println(helper1);
			}
		
//		System.err.println("ENDING MAIN");
		//jiraRestClient.destroy();
		}

		// SLEEP: TO WAIT FOR OTHER THREADS TO FINISH
		try {
			Thread.sleep(2000);
			restClient.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println("ENDED WITH SUCCESS!!!");
//		System.exit(0);
    }
    
    private static void println(Object o) {
		if (!quiet) {
			System.out.println(o);
		}
	}

	private static void parseArgs(String[] argsArray) throws URISyntaxException {
		final List<String> args = Lists.newArrayList(argsArray);
		if (args.contains("-q")) {
			quiet = true;
			args.remove(args.indexOf("-q"));
		}

		if (!args.isEmpty()) {
			uri = new URI(args.get(0));
		}
	}

	/** CLEAN_DESIGN: createIssues */
	private static void createIssues( Project project, JiraRestClient restClient, int i, ManageIssueJira manageIssuesJira) throws Exception {
		create1Issue( project, restClient, manageIssuesJira.getIssuesStatic().get(i).getNameIssue(), "",  manageIssuesJira.getIssuesStatic().get(i).getMigrationComplexity(), manageIssuesJira.getIssuesStatic().get(i).getStructuralComplexity(), Integer.parseInt(manageIssuesJira.getIssuesStatic().get(i).getNumber()), manageIssuesJira.getIssuesStatic().get(i).getBannerModule(), manageIssuesJira.getIssuesStatic().get(i).getSolutionCenter(), manageIssuesJira.getIssuesStatic().get(i).getPhase(), manageIssuesJira.getIssuesStatic().get(i).getModule());
	
	}
	
	/**
	 * @param <IssueInputParameters> */
	private static <IssueInputParameters> void create1Issue( Project project, JiraRestClient restClient, String name, String version, String migComplexity, String strComplexity, int timeEstimate, String bannerModule, String solutionCenter, String phase, String module ) throws Exception
	{
		//CHANGED: NOT ALLOW THE CREATION OF MORE THAN ONE ISSUE/FORM ######################
        final int N_RESULT = 5;
        int i;
        boolean haveDone = true;
        IssueRestClient irc = restClient.getIssueClient();

        SearchRestClient searchCli = restClient.getSearchClient();
        
        String auxCond = "";
        
	    if (bannerModule.isEmpty())
	    	bannerModule = BannerModule.GENERAL.getValue();;
        
	    if (!bannerModule.isEmpty())
	    	auxCond = " AND 'Banner Module' = '" + bannerModule + "' "; 
	    
	    if (!solutionCenter.isEmpty())
	    	auxCond = auxCond + " AND 'Solution Center' = '" + solutionCenter + "' ";	    

	    	String query = "project = " + propertiesJira.getPROJECT() +  
					" AND  summary ~ " + name +"_FRM" + 
					" AND issuetype = " + propertiesJira.getISSUE_TYPE() +
					auxCond;
        	SearchResult sr = searchCli.searchJql(query).claim();
        	
//        	//TODELETE
//        	IssueType issueType = EntityHelper.findEntityByName(project.getIssueTypes(), propertiesJira.getISSUE_TYPE()); 
//        	Issue nnn = new Issue(query, uri, query, null, project, issueType, null, query, null, null, null, null, null, null, null, null, null, null, null, null, null, null, uri, null, null, null, null, null, null, null, null);
//        	for (Issue issue : sr.getIssues()) {
//				//issue.getCommentsUri().g
//			}
        	

        	/* @morphis TODO IMPROVEMENT: update fields in Issue/Form using UPDATE (to keep the same) */
    		IssueRestClient issueClient;
        	
    		
    		if ( sr.getTotal() >= 1 ){
    			//AVOID CREATE MORE ISSUES/FORMS WITH THE SAME NAME
        		logger.getLog().info("ISSUE/FORM ALREADY CREATED (EXIT WITHOUT DOING NOTHING): name="+name);
        		return;			
        	}
        	  		
		//#################################################################################
		
		issueClient = restClient.getIssueClient();
		IssueType issueType = EntityHelper.findEntityByName(project.getIssueTypes(), propertiesJira.getISSUE_TYPE()); // "Task" "Form" "Bug"
	    IssueInputBuilder issueInputBuilder = new IssueInputBuilder((BasicProject)project, (BasicIssueType)issueType );

	    if (bannerModule.isEmpty()) {
	    	bannerModule = BannerModule.GENERAL.getValue();
		}
	    	
    	issueInputBuilder.setFieldValue(propertiesJira.getBANNER_MODULE_ID(), ComplexIssueInputFieldValue.with("value", bannerModule));  	
	    issueInputBuilder.setFieldValue(propertiesJira.getSOLUTION_CENTER_ID(), ComplexIssueInputFieldValue.with("value", solutionCenter));
	    issueInputBuilder.setFieldValue(propertiesJira.getFORM_ID(),  ImmutableList.of(name.toUpperCase()));
   		issueInputBuilder.setFieldValue(propertiesJira.getMIGRATION_COMPLEXITY_ID(), ComplexIssueInputFieldValue.with("value", migComplexity));
   		issueInputBuilder.setFieldValue(propertiesJira.getSTRUCTURAL_COMPLEXITY_ID(), ComplexIssueInputFieldValue.with("value", strComplexity));    	

   		//CHECK: ONLY EXECUTE IF VALUE DIFFERENT NULL OU NONE (is None by default)
   		if (!module.equals(Module.NONE.getValue())) {
   	   		issueInputBuilder.setFieldValue(propertiesJira.getMODULE_ID(), ComplexIssueInputFieldValue.with("value", module));    	
		}
   		if (!phase.equals(Module.NONE.getValue())) {
   	   		issueInputBuilder.setFieldValue(propertiesJira.getPHASE_ID(), ComplexIssueInputFieldValue.with("value", phase));    	
		}

	    issueInputBuilder.setFieldValue("timetracking", new TimeTracking(timeEstimate,null,null)); // cas ???
	    issueInputBuilder.setSummary( name.toUpperCase() + "_FRM");
	    
//	    issueInputBuilder.setFixVersionsNames(Lists.newArrayList(version));
//	    issueInputBuilder.setFieldValue("labels",ImmutableList.of(label1, label2));
//	    issueInputBuilder.setSummary( name.toUpperCase() + " : Missing Generation");
	    BasicIssue basicCreatedIssue = issueClient.createIssue(issueInputBuilder.build()).claim();

	    //System.out.println("Created Issue ID: " + basicCreatedIssue.getKey() + " (" + name + ", " + version + ", " + migComplexity + ", " + strComplexity + ", " +  String.valueOf(timeEstimate) + ", " + solutionCenter + ")" ) ;	
	    logger.getLog().info("Created Issue ID: " + basicCreatedIssue.getKey() + " (" + name + ", " + migComplexity + ", " + strComplexity + ", " +  String.valueOf(timeEstimate) + ", " + solutionCenter + ", " + phase + ", " + module + ")");
	}
	
	/** CLEAN_DESIGN: createAllAttach */
	private static void createAllAttach( String ProjectName, JiraRestClient restClient, String pathToConfigProperties, ManageIssueJira manageIssuesJira) throws Exception
    {
		for (int i = 0; i < manageIssuesJira.getIssuesStatic().size(); i++) {
			createAllAttach(ProjectName, restClient, pathToConfigProperties, manageIssuesJira.getIssuesStatic().get(i).getBannerModule(), manageIssuesJira.getIssuesStatic().get(i).getSolutionCenter(), manageIssuesJira.getIssuesStatic().get(i).getNameIssue());
		}
    }
	
	/** */
	private static void createAllAttach( String ProjectName, JiraRestClient restClient, String pathNodeIssue, String bannerModule, String solutionCenter, String nameForm) throws Exception
    {
		
		if (bannerModule.isEmpty())
	    	bannerModule = BannerModule.GENERAL.getValue();
	    
	    if (solutionCenter.isEmpty())
	    	solutionCenter = BannerModule.GENERAL.getValue();

    	String sDir =  pathNodeIssue + "\\" + bannerModule + "\\" + solutionCenter + "\\";
    	File dir = new File(sDir);
    	
    	//CHECK IF LIST IS EMPTY (there is no information in nodes: file system)
    	if (dir.list() == null) {
    		logger.getLog().info("NO ATTACHMENTS IN NODE TO CREATE... sDir="+sDir);
    		return;
		}
    	
		boolean flagHasNoAttachment = true;
    	for (String sFile : dir.list()) { 	   
    		//CHECK IF THE FORM NAME EXTRACTED FROM FILE SYSTEM IS IN THE EXCEL
				if (nameForm.equals(sFile)) {
		    		crete1Attach( ProjectName, restClient, sFile, sDir, bannerModule, solutionCenter); 
		    		flagHasNoAttachment = false;
				} 
			}
//    	}
		if (flagHasNoAttachment) {
    		logger.getLog().info("NO ATTACHMENTS IN NODE TO CREATE... sDir="+sDir);
		}
    }
	
	/** */
	private static void crete1Attach( String ProjectName, JiraRestClient restClient, String formName, String sDir, String bannerModule, String solutionCenter) throws Exception
    {
		 final int N_RESULT = 5;
	        int i;
	        boolean haveDone = true;
	        IssueRestClient irc = restClient.getIssueClient();

	        SearchRestClient searchCli = restClient.getSearchClient();
	        
	        String auxCond = "";
	        
		    if (bannerModule.isEmpty())
		    	bannerModule = BannerModule.GENERAL.getValue();;
	        
		    if (!bannerModule.isEmpty())
		    	auxCond = " AND 'Banner Module' = '" + bannerModule + "' "; 
		    
		    if (!solutionCenter.isEmpty())
		    	auxCond = auxCond + " AND 'Solution Center' = '" + solutionCenter + "' ";	    

		    	String query = "project = " + propertiesJira.getPROJECT() +  
						" AND  summary ~ " + formName +"_FRM" + 
						" AND issuetype = " + propertiesJira.getISSUE_TYPE() +
						auxCond;
	        	SearchResult sr = searchCli.searchJql(query).claim();

	        	
	        	if ( sr.getTotal() == 0 )
	        		{
	    		    logger.getLog().info("Form " + formName + "=> NAO EXISTE NO FILESYSTEM");
	        		return;
	        		}
	        	if ( sr.getTotal() > 1 )
	        		{
	    		    logger.getLog().info("Form " + formName + "=> EXISTE MAIS QUE UM FORM");
	        		return;
	        		}
	        	  
	            
	            
		        Iterable<Issue> issues = sr.getIssues();
		        
		        for (final BasicIssue basicIssue : issues) {
		        	
		   //         System.out.println("Found basicIssue with ID " + basicIssue.getKey());
		        	Issue issue = irc.getIssue(basicIssue.getKey()).claim();
		        	File dir = new File(sDir+formName);
		        	for (File aFile : dir.listFiles()) {
		        		irc.addAttachments(issue.getAttachmentsUri() , aFile).claim();
		    		    //.out.println("Created Attach: " + basicIssue.getKey() + " (" + formName + ", " + aFile.getAbsolutePath() + ")" ) ;	
		    		    logger.getLog().info("Created Attach: " + basicIssue.getKey() + " (" + formName + ", " + aFile.getAbsolutePath() + ")");
		        		
		        	}

		        	
	/*	            File file = new File("\\\\migration02\\managers\\ORIGINALS\\UseCases\\FOAIDEN\\FOAIDEN Help.docx");
		            
		            irc.addAttachments(issue.getAttachmentsUri() , file).claim();
	*/
		        }
	        
    }
	
	/** */
	private static void createSubTasks( Project project, JiraRestClient client, ManageIssueJira manageIssueJira) throws Exception
	{

////	    System.err.println("\n---------------PRINT07 - LIST OF PATHS TO SUBTASKS - MANAGER------------------------");
			NReaderJira reader = new NReaderJira();
	    	for (SubTaskJiraUseCases subtask : manageIssueJira.getSubTaskJiraUseCasesList()) {
				
	    		reader.readExcelSubTask( subtask.getPath(), subtask, logger);
	    		logger.getLog().debug(subtask);
	    	}
    	
//				System.err.println("\n--------------PRINT CELLS: 2-READER------------------");
				for ( SubTaskJiraUseCases subtask: manageIssueJira.getSubTaskJiraUseCasesList()) {
					// ALGORITHM FOR READING THE LIST OF THE SUBTASKS
					String id = "";
					String title = "";
					String description = "";
					int counter = 0;
					int i = 0;
					
					while (i < subtask.getSubtasks().size() || counter == -1) {
						switch (counter) {
						case 0:
							id = subtask.getSubtasks().get(i);
							counter++;
							i++;
							break;
						case 1:
							title = subtask.getSubtasks().get(i);
							counter++;
							i++;
							
							//SEND LAST REGISTER
							if (i == subtask.getSubtasks().size()) {
								counter = -1;
							}
							break;
						case 2:
							//AVOID NULL VALUES FOR THIS COLUMN
							description = subtask.getSubtasks().get(i);
							if (subtask.getSubtasks().get(i) == "") {
								description = "(...)";
							} 
							
							//ALGORITHM: JUMP IS VALUE IN ARRAY IS THE NEXT NUMBER BECAUSE IT HAS NO VALUE
							char firstLetterOfStringDescription = description.charAt(0);
							boolean isDigitDescription = (firstLetterOfStringDescription >= '0' && firstLetterOfStringDescription <= '9');
							
							char firstLetterOfStringId = description.charAt(0);
							boolean isDigitId = (firstLetterOfStringId >= '0' && firstLetterOfStringId <= '9');
							
							try {
								int subtaskNumber = 0;
								if (isDigitId) {
									subtaskNumber = Integer.parseInt(id);
								}
								
								if (isDigitDescription) {
									int valueInExcel =  Integer.parseInt(description);
									valueInExcel--;
									if (valueInExcel == subtaskNumber) {
										description = "(...)";
										i--;
									}
								}
							} catch (Exception e) {
								logger.getLog().info("CANNOT DO A PARSEINT: id="+id);
								logger.getLog().info("description="+description);
								e.printStackTrace();
							}
	
							counter = -1;
							i++;
							break;
						case -1:
							
							//
						    for (int j = 0; j < manageIssueJira.getIssuesStatic().size(); j++) {
						        String bannerModuleSubtask = manageIssueJira.getIssuesStatic().get(j).getBannerModule();
						        String solutionCenterSubtask = manageIssueJira.getIssuesStatic().get(j).getSolutionCenter();
						        String nameFormSubtask = manageIssueJira.getIssuesStatic().get(j).getNameIssue();
						    	if (subtask.getModuleName().equals(nameFormSubtask)
						    			&& subtask.getBannerModule().equals(bannerModuleSubtask) 
						    			&& subtask.getCountry().equals(solutionCenterSubtask) ) {
							    	
									logger.getLog().debug("subtask.getModuleName()=" + subtask.getModuleName() + ", " + id + ", " + title + " , " + description);
									create1SubTask(project, restClient, subtask.getModuleName(), id,title, subtask.getType(), subtask.getBannerModule(), subtask.getCountry());
//									i--;
						    	}
						    }

							counter = 0;
							id = "";
							title = "";
							description = "";
							break;
						default:
							logger.getLog().debug("ERROR IN LOADING SUBTASKS");
							break;
						}
					}	
				}
			}
	
	private static void create1SubTask( Project project, JiraRestClient client, String formName, String name, String description, String label1, String bannerModule, String solutionCenter ) throws Exception
	{
		if (bannerModule.isEmpty())
			bannerModule = BannerModule.GENERAL.getValue();;
		
		Issue formIssue = getFormIssue( project.getName(), client, formName, bannerModule, solutionCenter);
		if( formIssue != null)
		{
	 		IssueRestClient issueClient = client.getIssueClient();
			IssueType issueType = EntityHelper.findEntityByName(project.getIssueTypes(), propertiesJira.getSUBTASK_ISSUE_TYPE()); // "Sub-task" "Test case" 
	
			
		    IssueInputBuilder issueInputBuilder = new IssueInputBuilder((BasicProject)project, (BasicIssueType)issueType );
	
		    issueInputBuilder.setFieldValue(propertiesJira.getFORM_ID(),  ImmutableList.of(formName.toUpperCase()));
		    issueInputBuilder.setFieldValue("labels", ImmutableList.of(label1));
		    
		    issueInputBuilder.setFieldValue(propertiesJira.getBANNER_MODULE_ID(), ComplexIssueInputFieldValue.with("value", bannerModule));
		    issueInputBuilder.setFieldValue(propertiesJira.getSOLUTION_CENTER_ID(), ComplexIssueInputFieldValue.with("value", solutionCenter));
	
		    issueInputBuilder.setSummary( name);
		    issueInputBuilder.setDescription(description);
		    
//		    issueInputBuilder.setFixVersions(formIssue.getFixVersions());  //      Lists.newArrayList(version));
//		    issueInputBuilder.setFieldValue("timetracking", new TimeTracking(timeEstimate,null,null));	    
//		    issueInputBuilder.setReporterName("Administrator"); // 
//		    issueInputBuilder.setReporter(new BasicUser(project.getUri(), "admin", "Administrator")); //
	
		    Map<String, Object> parent = new HashMap<String, Object>();
		    parent.put("key", formIssue.getKey());
		    FieldInput parentField = new FieldInput("parent", new ComplexIssueInputFieldValue(parent));
		    issueInputBuilder.setFieldInput(parentField);
		    
		    BasicIssue basicCreatedIssue = issueClient.createIssue(issueInputBuilder.build()).claim();
//		    System.out.println("Created Issue ID: " + basicCreatedIssue.getKey() + " (" + formName + ", " + name + ", " + description + ", " + label1 + ", " + solutionCenter + ")" ) ;	
		    logger.getLog().info("Created SubTask ID: " + basicCreatedIssue.getKey() + " (" + formName + ", " + name + ", " + label1 + ", " + solutionCenter + ")" );
		}
	}
	
	/** */
	 private static Issue getFormIssue( String ProjectName, JiraRestClient client, String formName, String bannerModule, String solutionCenter) throws Exception
	    {
	        final int N_RESULT = 5;
	        String auxCond = "";
		    
	        if (bannerModule.isEmpty())
		    	bannerModule = BannerModule.GENERAL.getValue();;
		    
		    if (!bannerModule.isEmpty())
		    	auxCond = " AND 'Banner Module' = '" + bannerModule + "' "; 
		    if (!solutionCenter.isEmpty())
		    	auxCond = auxCond + " AND 'Solution Center' = '" + solutionCenter + "' ";
	        
	        IssueRestClient irc = client.getIssueClient();

	        SearchRestClient searchCli = client.getSearchClient();

     	SearchResult sr = searchCli.searchJql(	"project = " + propertiesJira.getPROJECT() +  
     											" AND  summary ~ " + formName + "_FRM" + 
     											" AND issuetype = " + propertiesJira.getISSUE_TYPE() +
     											auxCond).claim();

     	
//	            System.out.println("Form " + formName + "(" + sr.getTotal() + ")");
		if (sr.getTotal() == 0) {
			logger.getLog().debug(" ISSUE NÃO EXISTE");
			// System.out.println("Form " + formName + " NÃO EXISTE");
			return null;
		}
		if (sr.getTotal() > 1) {
			logger.getLog().debug(" EXISTE MAIS DE UMA ISSUE COM O MESMO NOME");
			// System.out.println("Form " + formName + " ESTRANHO");
			return null;
		}
     	  
		Iterable<Issue> issues = sr.getIssues();

		for (final BasicIssue basicIssue : issues) {

			// System.out.println("Found basicIssue with ID " + basicIssue.getKey());
			return irc.getIssue(basicIssue.getKey()).claim();
	        }
		return null;
	    }
	 
	    private static String fieldValueStringByName(Issue issue, String fieldName) throws Exception
	    {
	    	IssueField field = issue.getFieldByName(fieldName);
	    	if ( field != null)
	    
	    		if ( field.getValue() instanceof JSONArray)
	    		{
	    			JSONArray ja = (JSONArray) field.getValue();
	    			return ja.toString().substring(2, ja.toString().length()-2);
	    		}
	    	
	    	return "";
	    }
	    
	    /**
	     * @throws URISyntaxException  */
	    private static void checkIssueFieldValue(SearchResult sr, String value) throws URISyntaxException {
	    	//ISSUE KEY 
    		Iterable<Issue> issues = sr.getIssues();
    		
    		//TODELETE
    		URI uriValue = null;
			String keyform = null;
    		VerifyValue verifyValue = null;
    		
    		//LOOP
    		for (Issue issue : issues) {
    			
    			//PRINT
    			String jiraURI = issues.iterator().next().getSelf().toString();
    			String jiraURI2 = issue.getSelf().getPath();
//        		logger.getLog().info("jiraURI2="+jiraURI2 + " => " + "jiraURI="+jiraURI);

        		//LOOP
    			for (IssueField issueField : issue.getFields()) {
    				issueField.getId();
//            		logger.getLog().info("issueField.getId()="+issueField.getId());

            		issueField.getValue();
//            		logger.getLog().info("issueField.getValue()="+issueField.getValue());
            		
            		if (issueField.getId().equals("customfield_10802")) {
                   		//TODELETE
                		JsonToJavaConverter convert = new JsonToJavaConverter();
                		verifyValue = convert.convertFromJsonToJavaClass(issueField.getValue().toString());
//                		logger.getLog().info(verifyValue.toString());

    
                		//VERIFY VALUES
                		if (value.equals(verifyValue.getValue())) {
                			
                			//###############################################
                			//TODO TODELETE Attempt to update fields in JIRA
                			try {
								uriValue = new URI(verifyValue.getSelf());
								keyform = "High";    
	                    		
	                			//TODELETE
//	                 			ComponentRestClient update = restClient.getComponentClient();
//	                    		ComponentInput componentInput = new ComponentInput(keyform, keyform, keyform, AssigneeType.PROJECT_DEFAULT);
//	            				update.updateComponent(uriValue, componentInput);
	                    		


                			
                			//TODELETE
//                			FieldInput input = new FieldInput(verifyValue.getId(), "High");
//                    		logger.getLog().info("input="+input);

//                    		//TODELETE
//                    		List<ComplexIssueInputFieldValue> fieldList = new ArrayList<ComplexIssueInputFieldValue>();
//                    		 
////                    		for (String aValue : valuesList){
//                    		  Map<String, Object> mapValues = new HashMap<String, Object>();
//                    		  mapValues.put("value", "High");
//                    		  ComplexIssueInputFieldValue fieldValue = new ComplexIssueInputFieldValue(mapValues);
//                    		  fieldList.add(fieldValue);
////                    		}
//                    		issueInputBuilder.setFieldValue("customfield_10200", fieldList);
                    		
//                    		//TODELETE
//                    		ComplexIssueInputFieldValue valueTemp = new ComplexIssueInputFieldValue(Collections.singletonMap("value", (Object) "1"));
//                    		IssueInputBuilder builder = new IssueInputBuilder(project, issueType, summary) ;
            				//###############################################
                			} finally {
                				
                			}
                		}
					}
				}
			}
	    }
	 

	 


}
