
public class Launcher {
	
	private static final int NUMBER_OF_NODES = 5;
	
	//private static final String PORTS_LIST_4 = "localhost:6001, localhost:6002, localhost:6003, localhost:6004";
	private static final String PORTS_LIST_5 = "localhost:6001, localhost:6002, localhost:6003, localhost:6004, localhost:6005";

	private static final int[] PORTS = new int[]{6001, 6002, 6003, 6004, 6005};
	
	private static final String[] DBs = new String[]{"test1.db", "test2.db", "test3.db", "test4.db", "test5.db"};
	
	public static void main(String[] args) {
		// launch client
		new Thread(new Runnable() {
			@Override
			public void run() {
				ClientFrame.main(new String[]{"0", "10"});
			}
		}).start();
		
		// launch nodes
		for(int i=0; i!=NUMBER_OF_NODES; ++i){
			final int index = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					NodeFrame.main(new String[]{Integer.toString(index*380), "600", Integer.toString(PORTS[index]), PORTS_LIST_5, DBs[index]});
				}
			}).start();
		}
	}

}
