
public class CommandSplitter {
	public static Command[] split(Command command, int n){
		Command[] result = new Command[n];
		
		//if(command.){
			for(int i=0; i!=n; ++i){
				result[i] = Command.createHelloNodeCommand();
			}
		//}
		
		return result;
	}
	
	public static Data combineData(Data[] data){
		String res = "";
		for(int i=0; i!=data.length; ++i){
			if(data[i] !=null){
				res += data[i].str;
			}
		}
		return new Data("Hello Client!" + res);
	}
}
