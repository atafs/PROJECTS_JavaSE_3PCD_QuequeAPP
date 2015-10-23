package ellucian.files.xml.dao;

	import javax.xml.bind.annotation.XmlAttribute;
	import javax.xml.bind.annotation.XmlElement;
	import javax.xml.bind.annotation.XmlRootElement;

	@XmlRootElement
	public class SubtaskExcelConstants {

		private String type;
		private int id;

		public String getType() {
			return type;
		}

		@XmlElement
		public void setType(String type) {
			this.type = type;
		}

		public int getId() {
			return id;
		}

		@XmlAttribute
		public void setId(int id) {
			this.id = id;
		}
		
		@Override
		public String toString() {
			String toReturn = "";
			toReturn += " => id=" + id + "\t";
			toReturn += "type=" + type;
			return toReturn;
		}

	}
