import java.io.Serializable;

import javax.swing.JTextArea;


public class Data implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7319694851464617653L;
	public String str;
	
	/**
	 * 
	 */
	public Data() {
	}
	
	/**
	 * @param str
	 */
	public Data(String str){
		this.str = str;
	}
	
	/**
	 * @param console
	 */
	public void display(JTextArea console) {
		console.append("--------------------\n");
		console.append(str + '\n');
		console.append("--------------------\n");
	}
}
