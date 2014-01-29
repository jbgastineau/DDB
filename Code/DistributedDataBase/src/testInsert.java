

public class testInsert {

	/**
	 * 
	 * We insert in our table defined in the parameters
	 * 
	 * @author Alvar Viana
	 * 
	 * @param client the client who will run the command
	 * @param tableName the table in wich we are inserting 
	 * @param id content of the table
	 * @param name content of the table
	 * @param age content of the table
	 * @param address content of the table
	 * @param salary content of the table
	 */
	public testInsert(Client client, String tableName, int id, 
			String name, int age, String address, int salary) {

	  //We create an array with length one just to put in it a sentece to create a new table
	  String[] input = new String[1];
	  input[0]= "INSERT INTO "+tableName+" VALUES ("+id+", "+name+", "+age+", "+address+", "+salary+")";
	  //We set the param and we run it
	  client.setInputs(input);
	  client.run();
	  
	}
}

