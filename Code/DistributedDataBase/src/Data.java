
public class Data implements Storebale{
	public String str;
	
	public Data() {
	}
	
	public Data(String str){
		this.str = str;
	}

	@Override
	public String storeToString() {
		return str;
	}

	@Override
	public void restoreFromString(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
}
