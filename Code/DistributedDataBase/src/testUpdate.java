

public class testUpdate {

	/**
	 * 
	 * We test the update ejecuting this comand
	 * 
	 * @author Alvar Viana
	 * 
	 * @param client the client who runs the client
	 * @param tableName the table in wich we are inserting 
	 * @param id content of the table
	 * @param name content of the table
	 * @param age content of the table
	 * @param address content of the table
	 * @param salary content of the table
	 */
	public testUpdate(Client client, String tableName, int id, 
			String name, int age, String address, int salary) {

		//We create an array with length one just to put in it a sentece to create a new table
		String[] input = new String[1];
		input[0] = "UPDATE "+tableName+" SET NAME="+name+", AGE="+age+", ADDRESS="+address+", SALARY="+salary+" WHERE ID="+id;
		//We set the param and we run it
		client.setInputs(input);
	}
}
