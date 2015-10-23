package io;

import ellucian.enums.TypeOfIssue;

/** NString Class: has utility methods that are useful to manipulate strings */
public class ManageString {
	
	//METHODS #################################################
	/** FUNCTION: that cleans in the path ./namePath the './' */
	public String cleanPathDotDash(String path) {
		//ORIGINAL CODE: nodeFileCleaned = nodeFileCleaned.replace(".\\", "");
		path = path.replace(".\\", "");
		return path;
	}
	
	/** FUNCTION: that cleans the name of the issue from the Excel Template ('_FRM.xfmb') */
	public String cleanNameIssueFromExcelAfter(String nameIssue) {
		//ORIGINAL: '_FRM.xfmb'
		nameIssue = nameIssue.replace("_FRM.xfmb", "");
		return nameIssue;
	}
	
	/** FUNCTION: that cleans the name of the issue from the Excel Template ('TC_'; 'TCC_', etc)*/
	public String cleanNameIssueFromExcelBefore(String nameIssue) {
		//CLEAN NAME FROM TC_
		String formNameWithoutTC = nameIssue.replace("TC_", "");
		formNameWithoutTC = formNameWithoutTC.replace("TCC_", "");
		formNameWithoutTC = formNameWithoutTC.replace("TBR_", "");

	    String[] parts = formNameWithoutTC.split("_");
	    //CASE: NAME IS IN THE MIDDLE (ex: TC_<formName>_yyy.kkkk)
	    formNameWithoutTC = parts[0];
	    return formNameWithoutTC;
	}

	/** FUNCTION: that cleans the name of the Form i between underscores */
	public String cleanNameForm(String form) {
		String typeSubTask = "";
		typeSubTask = getLastPartStringUnderscore(form);
		return typeSubTask;
	}
	
	/** FUNCTION: place word in Capital letter */
	public String changeCaseCapitalLetter(String nameCapitalized) {
		String toReturn = "";
		String[] parts = nameCapitalized.split(" ");
		int counter = 0;
		for (String string : parts) {
			if ((parts.length - 1) == counter) {
				string = string.toLowerCase();
				String s1 = string.substring(0, 1).toUpperCase();
				string = s1 + string.substring(1); 
				toReturn += string;
				return toReturn;
			}
			string = string.toLowerCase();
			String s1 = string.substring(0, 1).toUpperCase();
			string = s1 + string.substring(1); 
			toReturn += string + " ";
			counter++;
		}
	    return toReturn;
	}
	
	/** FUNCTION: to check if function in func (CapitalLetter, LowerCase, UpperCase) */
	public boolean isFunction(String function) {
		
		//LOCAL VARIABLES
		String func = (String)TypeOfIssue.FUNCTION.getValue();
		String funcLowerCase = (String)TypeOfIssue.FUNCTION.getValue().toLowerCase();
		String funcUpperCase = (String)TypeOfIssue.FUNCTION.getValue().toUpperCase();
		String funcCapitalCase = (String)changeCaseCapitalLetter(TypeOfIssue.FUNCTION.getValue().toUpperCase());
		
		//CONDITION: CHECK THESE COMBINATIONS OF STRINGS
		boolean toReturn = false;
		if (function.equals(func)) {
			toReturn = true;
		} else if (function.equals(funcLowerCase)) {
			toReturn = true;
		} else if (function.equals(funcUpperCase)) {
			toReturn = true;
		} else if (function.equals(funcCapitalCase)) {
			toReturn = true;
		} 
		return toReturn;
	}
	
	/** FUNCTION: to check if validation in valid (CapitalLetter, LowerCase, UpperCase) */
	public boolean isValidation(String validation) {
		
		//LOCAL VARIABLES
		String valid = (String)TypeOfIssue.VALIDATION.getValue();
		String funcLowerCase = (String)TypeOfIssue.VALIDATION.getValue().toLowerCase();
		String funcUpperCase = (String)TypeOfIssue.VALIDATION.getValue().toUpperCase();
		String funcCapitalCase = (String)changeCaseCapitalLetter(validation);
		
		//CONDITION: CHECK THESE COMBINATIONS OF STRINGS
		boolean toReturn = false;
		if (validation.equals(valid)) {
			toReturn = true;
		} else if (validation.equals(funcLowerCase)) {
			toReturn = true;
		} else if (validation.equals(funcUpperCase)) {
			toReturn = true;
		} else if (validation.equals(funcCapitalCase)) {
			toReturn = true;
		} 
		return toReturn;
	}
	
	/** FUNCTION: get the last element of String */
	public String getLastPartStringUnderscore(String string) {
		String[] parts = string.split("_");
		int last = parts.length-1;
		string = parts[last];
		return string;
	}
	
	/** FUNCTION: to check if the excel is of type validation */
	public boolean isDocTypeExcelValidation(String name) {	
		String type = getFuncOrValidName(name);
//		if (type.equals(TypeOfIssue.VALIDATION.getValue())) {
		if (isValidation(type)) {
			return true;
		}
		return false;
	}
	
	/** FUNCTION: to check if the excel is of type function */
	public boolean isDocTypeExcelFunction(String name) {
		String type = getFuncOrValidName(name);
//		if (type.equals(TypeOfIssue.FUNCTION.getValue())) {
		if (isFunction(type)) {
			return true;
		}
		return false;
	}
	
	/** FUNCTION: to remove the extension of the file name */
	public String getFuncOrValidName(String string) {
		String underscore = string;
		String[] parts = underscore.split("_");
		String extensionRemoved = parts[parts.length-1].substring(0, parts[parts.length-1].lastIndexOf('.'));
		return extensionRemoved;
	}

	/** FUNCTION: to remove all spaces (space bar, tab, etc from string)*/
	public String cleanEmptySpaces(String string) {
		//Anything that is a space character (including space, tab characters etc)
		String toReturn = string.replaceAll("\\s","");
		return toReturn;
	}
}
