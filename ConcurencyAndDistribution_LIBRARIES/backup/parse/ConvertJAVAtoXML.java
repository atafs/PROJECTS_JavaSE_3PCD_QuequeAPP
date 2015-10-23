package ellucian.parse;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import ellucian.files.xml.dao.SubtaskExcelConstants;

/** SECOND: ONLY WORKS WHEN DOING THE UNMARSHALL FIRST (constants) */
public class ConvertJAVAtoXML {
	public static void main(String[] args) {

		SubtaskExcelConstants constants = new SubtaskExcelConstants();
		constants.setId(102);
		constants.setType(ConvertXMLtoJAVA.constantsExcel + " ABC");

		  try {

			File file = new File("./properties/ConstantsSet.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(SubtaskExcelConstants.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(constants, file);
			jaxbMarshaller.marshal(constants, System.out);

		      } catch (JAXBException e) {
			e.printStackTrace();
		      }

		}
	}
