
public class CommandSplitter {
	public static Command[] split(Command command, int n){
		Command[] result = new Command[n];
		
		if(command.type == Command.CREATE_TABLE){
			for(int i=0; i!=n; ++i){
				result[i] = Command.createCopy(command);
			}
		}else if(command.type == Command.DROP_TABLE){
			for(int i=0; i!=n; ++i){
				result[i] = Command.createCopy(command);
			}
		}else{
			for(int i=0; i!=n; ++i){
				result[i] = Command.createHelloNodeCommand();
			}
		}
		
		return result;
	}
	
	public static Data combineData(Data[] data){
		String res = "";
		for(int i=0; i!=data.length; ++i){
			if(data[i] !=null){
				res += data[i].str + ";";
			}
		}
		return new Data("Hello Client!" + res);
	}
}
