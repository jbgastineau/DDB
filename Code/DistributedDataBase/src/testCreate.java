

public class testCreate {

	/**
	 * We create a table with the specific tableName introduced in this function
	 * 
	 * @author Alvar Viana
	 * 
	 * @param client the cient who runs it
	 * @param tableName the name of the talbe
	 */
	public testCreate(Client client, String tableName) {

		
		//We create an array with length one just to put in it a sentece to create a new table
		String[] input = new String[1];
		input[0] = "CREATE TABLE "+tableName+" (ID INT PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, AGE INT NOT NULL, ADDRESS CHAR(50), SALARY INT)";
		//We set the param and we run it
		client.setInputs(input);
		client.run();		
		
	}

}
