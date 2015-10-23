package file.xml.convert;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ellucian.files.xml.dao.SubtaskExcelConstants;


/** FIRST: Unmarshaller, creating the JAVA class from XML (constants) */
public class ConvertXMLtoJAVA {

	static String constantsExcel = "";
	
	public static void main(String[] args) {

		//PARSE FROM XML TO JAVA (constants)
		 try {
			File file = new File("./properties/ConstantsGet.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(SubtaskExcelConstants.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			SubtaskExcelConstants constant = (SubtaskExcelConstants) jaxbUnmarshaller.unmarshal(file);
			System.out.println(constant.toString());
			constantsExcel = constant.getType();

		} catch (JAXBException e) {
			e.printStackTrace();
		}


	}

}
