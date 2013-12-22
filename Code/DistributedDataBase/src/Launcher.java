
public class Launcher {
	
	private static final int NUMBER_OF_NODES = 5;
	
	private static final String PORTS_LIST_4 = "6001,6002,6003,6004";
	private static final String PORTS_LIST_5 = "6001,6002,6003,6004,6005";

	private static final int[] PORTS = new int[]{6001, 6002, 6003, 6004, 6005};
	
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
					NodeFrame.main(new String[]{Integer.toString(index*380), "500", Integer.toString(PORTS[index]), PORTS_LIST_5});
				}
			}).start();
		}
	}

}
