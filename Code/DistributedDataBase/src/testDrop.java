

public class testDrop {

	/**
	 * We drop the specified table
	 * 
	 * @author Alvar Viana
	 * 
	 * @param client the cient who runs it
	 * @param tableName the name of the talbe to be deleted
	 */
	public testDrop(Client client, String tableName) {

		//We create an array with length one just to put in it a sentece to create a new table
		String[] input = new String[1];
		input[0] = "DROP TABLE "+tableName;
		//We set the param and we run it
		client.setInputs(input);
		client.run();
	}
}

