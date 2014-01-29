

public class testSelect {
	
	/**
	 * We select everything from the table defined in the parameters
	 * 
	 * @author Alvar Viana
	 * 
	 * @param client the client who will run the test
	 * @param tableName
	 */
	public testSelect(Client client, String tableName) {		
		
		//We create an array with length one just to put in it a sentece to create a new table
		String[] input = new String[1];
		input[0] = "SELECT * FROM "+tableName;
		//We set the param and we run it
		client.setInputs(input);
		client.run();
	}
}
