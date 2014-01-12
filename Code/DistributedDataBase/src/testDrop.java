package Testing;

public class testDrop {

	public testDrop(Client client, NodeName name/*,int i*/) {
		
		
/*		String input = "DROP TABLE table"+i+;";
		client.setParam(name, input);
		client.run();
*/

		// 1
		String input = "DROP TABLE table1;";
		client.setParam(name, input);
		client.run();
		// 2
		input = "DROP TABLE table2;";
		client.setParam(name, input);
		client.run();
		// 3
		input = "DROP TABLE table3;";
		client.setParam(name, input);
		client.run();
		// 4
		input = "DROP TABLE table4;";
		client.setParam(name, input);
		client.run();
		// 5
		input = "DROP TABLE table5;";
		client.setParam(name, input);
		client.run();
		// 6
		input = "DROP TABLE table6;";
		client.setParam(name, input);
		client.run();
		// 7
		input = "DROP TABLE table7;";
		client.setParam(name, input);
		client.run();
		// 8
		input = "DROP TABLE table8;";
		client.setParam(name, input);
		client.run();
		// 9
		input = "DROP TABLE table9;";
		client.setParam(name, input);
		client.run();
		// 10		
		/* Here should be appear an error cause the table
		 * has been already deleted */
		input = "CREATE TABLE table1;";
		client.setParam(name, input);
		client.run();
	}
	
	

}
