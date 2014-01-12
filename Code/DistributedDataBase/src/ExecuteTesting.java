package Testing;

public class ExecuteTesting {

	public static void main(String[] args) {
		
		ClientFrame clFr = new ClientFrame();
		Client newClient = new Client(clFr.getConsole());
		
		NodeName name = null;/* What should i put here?*/ 
		
		new testCreate(newClient,name);
		new testDrop(newClient,name);	
		
		new testCreate(newClient,name);
		new testInsert(newClient,name);
		new testSelect(newClient,name);
		new testUpdate(newClient,name);

	}

}
