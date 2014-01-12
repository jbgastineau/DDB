package Testing;

public class testCreate {

	public testCreate(Client client, NodeName name/*,int i*/) {

		
		
/*		String input = "CREATE TABLE table"+i+ "(column1 integer, column2 integer, column3 integer);";
		client.setParam(name, input);
		client.run();
*/		
		
		// 1
		String input = "CREATE TABLE table1(column1 integer, column2 integer, column3 integer);";
		client.setParam(name, input);
		client.run();
		// 2
		input = "CREATE TABLE table2(column1 integer, column2 integer, column3 integer);";
		client.setParam(name, input);
		client.run();
		// 3
		input = "CREATE TABLE table3(column1 integer, column2 integer, column3 integer);";
		client.setParam(name, input);
		client.run();
		// 4
		input = "CREATE TABLE table4(column1 integer, column2 integer, column3 integer);";
		client.setParam(name, input);
		client.run();
		// 5
		input = "CREATE TABLE table5(column1 integer, column2 integer, column3 integer);";
		client.setParam(name, input);
		client.run();
		// 6
		input = "CREATE TABLE table6(column1 integer, column2 integer, column3 integer);";
		client.setParam(name, input);
		client.run();
		// 7
		input = "CREATE TABLE table7(column1 integer, column2 integer, column3 integer);";
		client.setParam(name, input);
		client.run();
		// 8
		input = "CREATE TABLE table8(column1 integer, column2 integer, column3 integer);";
		client.setParam(name, input);
		client.run();
		// 9
		input = "CREATE TABLE table9(column1 integer, column2 integer, column3 integer);";
		client.setParam(name, input);
		client.run();
		// 10
		
		/* Here should be appear an error cause the talbe
		 * alredy exists */
		input = "CREATE TABLE table1(column1 integer, column2 integer, column3 integer);";
		client.setParam(name, input);
		client.run();
		
		
		
		
	}

}
