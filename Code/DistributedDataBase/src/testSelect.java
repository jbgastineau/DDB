package Testing;

public class testSelect {

	public testSelect(Client client, NodeName name/*,int i*/) {

/*		String input = "SELECT * FROM table"+i+";";
		client.setParam(name, input);
		client.run();
*/		
		
		// 1
		String input = "SELECT * FROM table1;";
		client.setParam(name, input);
		client.run();
		// 2
		input = "SELECT * FROM table2;";
		client.setParam(name, input);
		client.run();
		// 3
		input = "SELECT * FROM table3;";
		client.setParam(name, input);
		client.run();
		// 4
		input = "SELECT * FROM table4;";
		client.setParam(name, input);
		client.run();
		// 5
		input = "SELECT * FROM table5;";
		client.setParam(name, input);
		client.run();
		// 6
		input = "SELECT * FROM table6;";
		client.setParam(name, input);
		client.run();
		// 7
		input = "SELECT * FROM table7;";
		client.setParam(name, input);
		client.run();
		// 8
		input = "SELECT * FROM table8;";
		client.setParam(name, input);
		client.run();
		// 9
		input = "SELECT * FROM table9;";
		client.setParam(name, input);
		client.run();
	}

}
