package Server;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class Message {

	Dataset<Row> datasetMessage;

	public Dataset<Row> getDatasetMessage() {
		return datasetMessage;
	}

	public void setDatasetMessage(Dataset<Row> datasetMessage) {
		this.datasetMessage = datasetMessage;
	}
	
	private static Message message = null;
	public static Message getInstance(){
		if(message == null) {
			message = new Message();
		}
		return message;
	}
	
}
