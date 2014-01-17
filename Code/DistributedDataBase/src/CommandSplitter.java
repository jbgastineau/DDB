
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Alrfou
 */
public class CommandSplitter {
        public static Command[] split(Command command, int n){
                Command[] result = new Command[n];
                System.out.println(n);
                if(command.type == Command.CREATE_TABLE){
                        for(int i=0; i!=n; ++i){
                                result[i] = Command.createCopy(command);
                        }
                }else if(command.type == Command.DROP_TABLE){
                    //System.out.println("GUGGHJ"+ Command.DROP_TABLE);
                        for(int i=0; i!=n; ++i){
                                result[i] = Command.createCopy(command);
                        }
                }else if(command.type == Command.SELECT_TABLE){
                   // System.out.println("GUGGHJ"+Command.SELECT_TABLE);
                        for(int i=0; i!=n; ++i){
                                result[i] = Command.createCopy(command);
                        }
                }else if(command.type == Command.INSERT_TABLE){
                   // System.out.println("GUGGHJ"+Command.SELECT_TABLE);
                    int alg=n/2 +1;
                      final int BASE = alg;
                      final int TOTAL = alg;
                     int test[] = new int[TOTAL];
                     for ( int counter = 0; counter < test.length; counter++ )

                     {
                    // casting to integer 
                   int finalIndex;
                 do{

               test[counter] = (int) (BASE * Math.random() + 1); // casting to integer

                  finalIndex = 0;

                while(test[counter]!=test[finalIndex]){

               finalIndex++;

                        }

                   }while(counter != finalIndex);
                      System.out.printf( "%3d, ", test[counter] );
                     }
                    //INSERT INTO ADMIN VALUES(5,"HJHJ",35,"GJHGJGGJ",300)
                        for(int i=0; i!=alg; ++i){
                            
                            
                                result[test[i]-1] = Command.createCopy(command);
                        }
                }else if(command.type == Command.UPDATE_TABLE){
                   // System.out.println("GUGGHJ"+Command.SELECT_TABLE);
                        for(int i=0; i!=n; ++i){
                                result[i] = Command.createCopy(command);
                        }
                }
                else{
                        for(int i=0; i!=n; ++i){
                                result[i] = Command.createHelloNodeCommand();
                        }
                }
                
                return result;
        }
        
        public static Data combineData(int commandType, Data[] data){							// Anton, new parameter added
        	
        	if(commandType == Command.CREATE_TABLE || commandType == Command.DROP_TABLE){		// in case of create table command
        		
        		for (Data d : data) {															//	
					if(d.success == false){														// return data with error
						return d;																//
					}																			//
				}																				// or return good i.e. the first one
        		return data[0];									
        		
        	}else{
                String res = "";
                 HashMap<String,String> res1 = new HashMap<String,String>();
                for(int i=0; i!=data.length; ++i){
                        if(data[i] !=null){
                                res += data[i].str ;
                                if(data[i].res!=null)
                                 res1.putAll(data[i].res);
                                System.out.println("the colication data is"+i+ data[i].res);
                        }
                }
                System.out.println("the colication data is"+ res1);
                return new Data("Hello Client!"+'\n' + res,res1);
        	}
        }
        
}
