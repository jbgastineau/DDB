package Testing;

public class testInsert {

	public testInsert(Client client, NodeName name) {
		
		String input;
		// 1
		input = "INSERT INTO table1 VALUES (1,1,1);";
		client.setParam(name, input);
		client.run();
		// 1 2
		input = "INSERT INTO table1 VALUES (1,2,1);";
		client.setParam(name, input);
		client.run();
		// 1 3
		input = "INSERT INTO table1 VALUES (1,3,1);";
		client.setParam(name, input);
		client.run();
		// 2 1
		input = "INSERT INTO table2 VALUES (1,1,2);";
		client.setParam(name, input);
		client.run();
		// 2 2
		input = "INSERT INTO table2 VALUES (1,2,2);";
		client.setParam(name, input);
		client.run();
		// 2 3
		input = "INSERT INTO table2 VALUES (1,3,2);";
		client.setParam(name, input);
		client.run();
		// 3 1
		input = "INSERT INTO table3 VALUES (1,1,3);";
		client.setParam(name, input);
		client.run();
		// 3 2
		input = "INSERT INTO table3 VALUES (1,2,3);";
		client.setParam(name, input);
		client.run();
		// 3 3
		input = "INSERT INTO table3 VALUES (1,3,3);";
		client.setParam(name, input);
		client.run();
		// 4 1
		input = "INSERT INTO table4 VALUES (1,1,4);";
		client.setParam(name, input);
		client.run();
		// 4 2
		input = "INSERT INTO table4 VALUES (1,2,4);";
		client.setParam(name, input);
		client.run();
		// 4 3
		input = "INSERT INTO table4 VALUES (1,3,4);";
		client.setParam(name, input);
		client.run();
		// 5 1
		input = "INSERT INTO table5 VALUES (1,1,5);";
		client.setParam(name, input);
		client.run();
		// 5 2
		input = "INSERT INTO table5 VALUES (1,2,5);";
		client.setParam(name, input);
		client.run();
		// 5 3
		input = "INSERT INTO table5 VALUES (1,3,5);";
		client.setParam(name, input);
		client.run();
		// 6 1
		input = "INSERT INTO table6 VALUES (1,1,6);";
		client.setParam(name, input);
		client.run();
		// 6 2
		input = "INSERT INTO table6 VALUES (1,2,6);";
		client.setParam(name, input);
		client.run();
		// 6 3
		input = "INSERT INTO table6 VALUES (1,3,6);";
		client.setParam(name, input);
		client.run();
		// 7 1
		input = "INSERT INTO table7 VALUES (1,1,7);";
		client.setParam(name, input);
		client.run();
		// 7 2
		input = "INSERT INTO table7 VALUES (1,2,7);";
		client.setParam(name, input);
		client.run();
		// 7 3
		input = "INSERT INTO table7 VALUES (1,3,7);";
		client.setParam(name, input);
		client.run();
		// 8 1
		input = "INSERT INTO table8 VALUES (1,1,8);";
		client.setParam(name, input);
		client.run();
		// 8 2
		input = "INSERT INTO table8 VALUES (1,2,8);";
		client.setParam(name, input);
		client.run();
		// 8 3
		input = "INSERT INTO table8 VALUES (1,3,8);";
		client.setParam(name, input);
		client.run();
		// 9 1
		input = "INSERT INTO table9 VALUES (1,1,9);";
		client.setParam(name, input);
		client.run();
		// 9 2
		input = "INSERT INTO table9 VALUES (1,2,9);";
		client.setParam(name, input);
		client.run();
		// 9 3
		input = "INSERT INTO table9 VALUES (1,3,9);";
		client.setParam(name, input);
		client.run();

	}

}
