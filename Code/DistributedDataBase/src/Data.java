/**
 *
 * @author Alrfou
 */
import static com.sun.org.apache.xalan.internal.lib.ExsltDynamic.map;
import java.io.Serializable;

import javax.swing.JTextArea;
import java.util.*;


public class Data implements Serializable{
        /**
         * 
         */
        private static final long serialVersionUID = -7319694851464617653L;
        public String str;
        public HashMap<String,String> res;
        public boolean success = false;																// Anton, new field to indicate if a command was executed successfully
        
        /**
         * 
         */
        public Data() {
        }
        
        /**
         * @param str
         */
        public Data(String str,HashMap<String,String> str1){
                this.str = str;
                this.res=str1;
        }
         public Data(String str){
                this.str = str;
        }
        
        /**
     * @param res
         * @param console
         */
        public void setres(HashMap<String,String> res)
        {
            this.res=res;
        }
        public void display(int commandType, JTextArea console) {							// Anton, new parameter, just for fun
        	
        	if(commandType == Command.CREATE_TABLE || commandType == Command.DROP_TABLE){	//
        		console.append("+--------------------+\n");									//
        		console.append(str + '\n');													//
        		console.append("+--------------------+\n");									// see above
        		
        	}else{
        		Set<String> keys = res.keySet();
        		Iterator<String> keyIter = keys.iterator();
        		if(str!=null);
        		{
        			console.append("--------------------\n");
        			//console.append(str.trim() );
        			while (keyIter.hasNext()) 
        			{
        				console.append(res.get(keyIter));
                
        			}
                
            
        			//  console.append(res.toString().trim()+'\n' );
        			console.append("\n--------------------\n");
        			//console.append(res.toString());
        		}
            }
                
        }
}
