package bundle;


//DAO CLASS
public class PropertiesJira {
	
	//ATTRIBUTES: INPUT DATA ----------------------------------------------------------------
	private String path = "";
	//private String path = "properties/config.properties";
	//private static final String path = "src/main/java/com/sebasi/jira/io/file_reader/properties/config.properties";
	//-----------------------------------------------------------------------------------------------------
	private NReader reader;
	
	//PROPERTIES
	private String JIRA_URL = "";
	private String JIRA_ADMIN_USERNAME = "";
	private String JIRA_ADMIN_PASSWORD = "";
	private String PROJECT = "";
	private String ATTACH_DIR = "";
	private String FORM_ID = "";
	private String MIGRATION_COMPLEXITY_ID = "";
	private String STRUCTURAL_COMPLEXITY_ID = "";
	private String SOLUTION_CENTER_ID = "";
	private String BANNER_MODULE_ID = "";
//	private String BANNER_MODULE = "";
//	private String SOLUTION_CENTER = "";
	private String ISSUE_TYPE = "";
	private String SUBTASK_ISSUE_TYPE = "";
	
	private String MODULE_ID = "";
	private String PHASE_ID = "";
	
	
	private String queryGetNodePath = "";
	private String queryGetFormNameInExcel = "";
	private String queryGetFormNameInNode = "";
	private String queryIsFormInExcelAndNode = "";
	private String queryMultipleForms = "";

	
	private String pathEllucianJSON	 = "";
	private String pathEllucianReadExcel = "";
	private String pathToConfigProperties	= "";
	
	private String pathNodeIssue = "";
	private String pathNodeIssueExcel = ""; 
	
	private String BANNER_MODULE_ENUM = ""; 
	private String SOLUTION_CENTER_ENUM = ""; 
	private String FILE_EXTENSION_ENUM = ""; 

	//CONTRUCTOR
	public PropertiesJira() {
		//INITIALIZE
		
//		//SYSTEM PATH
//		BundlePath bundle = new BundlePath();
//		Locale supportedLocales = Locale.ENGLISH;
//		path = bundle.displayValue(supportedLocales, "pathToConfigProperties");
		//HARDCODED
//		path	= "properties/config.properties";
//		path = "properties/config.properties";
		path = "./properties/config.properties";

//		path = "C:\\Users\\Morphis\\Documents\\workspace-tool-jira\\jira-rest-java-client-parent\\properties\\config.properties";
//		path = "properties/config.properties";		

		
		//SYSTEM PROPERTIES
		reader = new NReader();
		JIRA_URL = reader.readFileReader(path, "JIRA_URL");
		JIRA_ADMIN_USERNAME = reader.readFileReader(path, "JIRA_ADMIN_USERNAME");
		JIRA_ADMIN_PASSWORD = reader.readFileReader(path, "JIRA_ADMIN_PASSWORD");
		PROJECT = reader.readFileReader(path, "PROJECT");
		ATTACH_DIR = reader.readFileReader(path, "ATTACH_DIR");
		FORM_ID = reader.readFileReader(path, "FORM_ID");
		MIGRATION_COMPLEXITY_ID = reader.readFileReader(path, "MIGRATION_COMPLEXITY_ID");
		STRUCTURAL_COMPLEXITY_ID = reader.readFileReader(path, "STRUCTURAL_COMPLEXITY_ID");
		SOLUTION_CENTER_ID = reader.readFileReader(path, "SOLUTION_CENTER_ID");
		BANNER_MODULE_ID = reader.readFileReader(path, "BANNER_MODULE_ID");
//		BANNER_MODULE = reader.readFileReader(path, "BANNER_MODULE");
//		SOLUTION_CENTER = reader.readFileReader(path, "SOLUTION_CENTER");
		ISSUE_TYPE = reader.readFileReader(path, "ISSUE_TYPE");
		SUBTASK_ISSUE_TYPE = reader.readFileReader(path, "SUBTASK_ISSUE_TYPE");
		
		MODULE_ID = reader.readFileReader(path, "MODULE_ID");
		PHASE_ID = reader.readFileReader(path, "PHASE_ID");

		queryGetNodePath = reader.readFileReader(path, "queryGetNodePath");
		queryGetFormNameInExcel = reader.readFileReader(path, "queryGetFormNameInExcel");
		queryGetFormNameInNode = reader.readFileReader(path, "queryGetFormNameInNode");
		queryIsFormInExcelAndNode = reader.readFileReader(path, "queryIsFormInExcelAndNode");
		queryMultipleForms = reader.readFileReader(path, "queryMultipleForms");
		
		
		pathEllucianJSON	 = reader.readFileReader(path, "pathEllucianJSON");
		pathEllucianReadExcel = reader.readFileReader(path, "pathEllucianReadExcel");
	
		pathNodeIssue = reader.readFileReader(path, "pathNodeIssue");
		pathNodeIssueExcel = reader.readFileReader(path, "pathNodeIssueExcel");
		
		BANNER_MODULE_ENUM = reader.readFileReader(path, "BANNER_MODULE_ENUM");
		SOLUTION_CENTER_ENUM = reader.readFileReader(path, "SOLUTION_CENTER_ENUM");
		FILE_EXTENSION_ENUM = reader.readFileReader(path, "FILE_EXTENSION_ENUM");
	}
	
	//GETTERS AND SETTERS
	public String getJIRA_URL() {
		return JIRA_URL;
	}

	public String getPath() {
		return path;
	}

	public NReader getReader() {
		return reader;
	}

	public String getJIRA_ADMIN_USERNAME() {
		return JIRA_ADMIN_USERNAME;
	}

	public String getJIRA_ADMIN_PASSWORD() {
		return JIRA_ADMIN_PASSWORD;
	}

	public String getPROJECT() {
		return PROJECT;
	}

	public String getATTACH_DIR() {
		return ATTACH_DIR;
	}

	public String getFORM_ID() {
		return FORM_ID;
	}

	public String getMIGRATION_COMPLEXITY_ID() {
		return MIGRATION_COMPLEXITY_ID;
	}

	public String getSTRUCTURAL_COMPLEXITY_ID() {
		return STRUCTURAL_COMPLEXITY_ID;
	}

	public String getSOLUTION_CENTER_ID() {
		return SOLUTION_CENTER_ID;
	}

	public String getBANNER_MODULE_ID() {
		return BANNER_MODULE_ID;
	}

//	public String getBANNER_MODULE() {
//		return BANNER_MODULE;
//	}
//
//	public String getSOLUTION_CENTER() {
//		return SOLUTION_CENTER;
//	}

	public String getISSUE_TYPE() {
		return ISSUE_TYPE;
	}

	public String getSUBTASK_ISSUE_TYPE() {
		return SUBTASK_ISSUE_TYPE;
	}

	public String getPathEllucianJSON() {
		return pathEllucianJSON;
	}

	public String getPathEllucianReadExcel() {
		return pathEllucianReadExcel;
	}

	public String getPathToConfigProperties() {
		return pathToConfigProperties;
	}

	public String getPathNodeIssue() {
		return pathNodeIssue;
	}

	public String getPathNodeIssueExcel() {
		return pathNodeIssueExcel;
	}
	
	public String getMODULE_ID() {
		return MODULE_ID;
	}

	public String getPHASE_ID() {
		return PHASE_ID;
	}

	public void setPathNodeIssue(String pathNodeIssue) {
		this.pathNodeIssue = pathNodeIssue;
	}

	public String getBANNER_MODULE_ENUM() {
		return BANNER_MODULE_ENUM;
	}

	public String getSOLUTION_CENTER_ENUM() {
		return SOLUTION_CENTER_ENUM;
	}

	public String getFILE_EXTENSION_ENUM() {
		return FILE_EXTENSION_ENUM;
	}

	public String getQueryGetNodePath() {
		return queryGetNodePath;
	}

	public String getQueryGetFormNameInExcel() {
		return queryGetFormNameInExcel;
	}

	public String getQueryGetFormNameInNode() {
		return queryGetFormNameInNode;
	}

	public String getQueryMultipleForms() {
		return queryMultipleForms;
	}

	public String getQueryIsFormInExcelAndNode() {
		return queryIsFormInExcelAndNode;
	}


	

	
	

}
