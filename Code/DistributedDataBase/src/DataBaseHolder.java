
/**
 *
 * @author Alrfou
 */
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextArea;


public class DataBaseHolder {
        
        private JTextArea console;
        private Connection c = null;
        private String dbName = null;
       
        public DataBaseHolder(JTextArea console) {
                this.console = console;
        }
        
        public void connect(String dbName){
        	this.dbName = dbName;
                
            try {
                    Class.forName("org.sqlite.JDBC");
                    System.out.println(dbName);
                    
                    c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            }catch(Exception e){
                    console.append(e.getClass().getName() + ": " + e.getMessage() + '\n');
            }
            console.append("Database " + dbName + " opened successfully\n");									// Anton, change output message
        }
        
        public void disconnect(){																				// Anton, new method for disconnecting from database
        	if(c!=null){
        		try {
					c.close();
				} catch (SQLException e) {
					console.append(e.getMessage() + '\n');
				}
        		c = null;
        	}
        }
        /*
        
        } catch (Exception e) {
      System.out.println(e);
    }
        */
        public Data execute(Command command){
                
                Data result = null;
               // System.out.println("THE TYPE IS "+command.type);
                if(command.type == Command.CREATE_TABLE){
                        Statement stmt = null;
                         //System.out.println("Create tables");
                        try {
                            
                            
                                stmt = c.createStatement();
                               
                                String sql = command.input.toUpperCase();
                                stmt.executeUpdate(sql);
                                stmt.close();
                                console.append("Table created successfully" + '\n');
                                result = new Data("Table created successfully");
                                result.success = true;															// Anton, new field in data
                        }catch(Exception e) {
                                console.append(e.getClass().getName() + ": " + e.getMessage() + '\n');
                                result = new Data(e.getMessage());
                        }        
                }else if(command.type == Command.DROP_TABLE){
                	
                	disconnect();																				// Anton, to avoid error message "database table is locked" 
                	connect(dbName);																			//
                	
                        Statement stmt = null;
                        try {
                                stmt = c.createStatement();
                                String sql = command.input.toUpperCase();
                                stmt.executeUpdate(sql);
                                stmt.close();
                                console.append("Table dropped successfully" + '\n');
                                result = new Data("Table dropped successfully");
                                result.success = true;															// Anton, new field in data
                        }catch(Exception e) {
                                console.append(e.getClass().getName() + ": " + e.getMessage() + '\n');
                                result = new Data(e.getMessage());
                        }        
                }else /*if(command.type==Command.SELECT_TABLE)													// Anton, commented see new method executeSelect
                {
                       //System.out.println("insert tables1");
                            Statement stmt = null;
                             List<String> arr = new ArrayList<>();
                             StringBuilder resl = new StringBuilder();
                              String res2;

                             try {
                                    
                                    String QRY = command.input;
                                     stmt = c.createStatement();
                                    ResultSet rs = stmt.executeQuery(QRY);
                                    ResultSetMetaData md = rs.getMetaData() ;
                                    HashMap<String,String> CS;
                                    SQLQuery sql=new SQLQuery();
                                    sql.parse(QRY);
                                    String tab=GetPrimarykey(sql.tables.get(0));
                                     System.out.println(tab+"is Primary key");
                                   String s = null ;
                                   CS=new HashMap<String, String>();
                                    while( rs.next() )
                                     {
                                         res2="";
                                         
                                       // if(md.getColumnLabel(i)==GetPrimarykey(sql.tablenames))
                                         
                                         //
                                       //  System.out.println(sql.getSelectedTables(sql.returns));
                                          for( int i = 1; i <= md.getColumnCount(); i++ )
                                            {
                                                //s="";
                                                System.out.println(md.getColumnLabel(i)+ rs.getString(i));
                                                if(md.getColumnName(i).equals(tab))
                                                    s=rs.getString(i);
                                                System.out.println(s+":is the value of primary key");
//                                                    res3.append(rs.getString(i));
                                                
                                                  // System.out.println(i+s.toString()+"       "+GetPrimarykey(sql.tables.get(1)));
                                                   res2+=rs.getString(i)+" ";
                                                   resl.append(rs.getString(i)).append(" || ");
                                                    //System.out.println(resl ) ;
             
                                             }
                                          
                                              arr.add(res2.toString());
                                              if(s!=null)
                                            CS.put(s, res2.toString());
                                              //hasmap<s,res2.toString()>.
                                              resl.append('\n');
                                              console.append(resl.toString() + '\n');
                                               
                                             // resl.delete(0,resl.length());
                                              // System.out.println() ;
                                              // 
                                              }
                                       if(resl.toString() !=null)
                                       {
                                             result = new Data(resl.toString(),CS);
                                             //result.setres(arr);
                                       }
                                              
                                                  
                                             // for(String c:arr)
                                              //System.out.println(c);
                                              System.out.println("--------------------\n"+ CS);
                                              // Close the result set, statement and the connection
                                              
 
    
                               rs.close();
                               stmt.close();
      
                             } catch (SQLException e) {
                             console.append(e.getClass().getName() + ": " + e.getMessage() + '\n');
                                result = new Data(e.getMessage()+'\n');
                           }
                     // return arr;
  
                }else*/ if(command.type==Command.INSERT_TABLE)
                {
                      Statement stmt = null;
                         //System.out.println("Create tables");
                        try {
                            
                            
                                stmt = c.createStatement();
                               
                                String sql = command.input;
                                stmt.executeUpdate(sql);
                                stmt.close();
                                console.append("1 row inserted successfully" + '\n');								// Anton, new output
                                result = new Data("1 row inserted successfully");									// Anton, new output
                                result.success = true;																// Anton, new field in data
                        }catch(Exception e) {
                                console.append(e.getClass().getName() + ": " + e.getMessage() + '\n');
                                result = new Data(e.getMessage());
                        } 
                }else if(command.type==Command.UPDATE_TABLE)
                {
                     Statement stmt = null;
                         //System.out.println("Create tables");
                        try {
                            
                            
                                stmt = c.createStatement();
                               
                                String sql = command.input;
                                stmt.executeUpdate(sql);
                                stmt.close();
                                console.append("Table UPDATED successfully" + '\n');
                                result = new Data("Table UPDATED successfully");
                        }catch(Exception e) {
                                console.append(e.getClass().getName() + ": " + e.getMessage() + '\n');
                                result = new Data(e.getMessage());
                        } 
                    
                }
                
                return result;
        }
        
        public void executeSelect(Command command, ObjectOutputStream out){							// Anton, new function to process SELECT command
        	Statement stmt = null;
        	String res2 = "";

        	try {
                try{
                	String QRY = command.input;
                	stmt = c.createStatement();
                	ResultSet rs = stmt.executeQuery(QRY);
                	ResultSetMetaData md = rs.getMetaData() ;
                	SQLQuery sql=new SQLQuery();
                	sql.parse(QRY);
                	String tab=GetPrimarykey(sql.tables.get(0));
                	System.out.println(tab+"is Primary key");
                	String s = null ;
                	while( rs.next() )
                	{
                		res2="";
                		for( int i = 1; i <= md.getColumnCount(); i++ )
                		{
                			System.out.println(md.getColumnLabel(i)+ rs.getString(i));
                			if(md.getColumnName(i).equals(tab)){
                				s=rs.getString(i);
                			}
                			System.out.println(s+":is the value of primary key");
                			res2+=rs.getString(i)+" ";
         
                		}
                		
                		Data result = new Data(res2.toString());
                		result.success = true;
                		synchronized (out) {
                			out.writeObject(result);
						}
                		console.append(res2.toString() + " sent to the main node\n");

                	}
                                          
                	rs.close();
                	stmt.close();
                	
                } catch (SQLException e) {
                	console.append(e.getClass().getName() + ": " + e.getMessage() + '\n');
                	Data result = new Data(e.getMessage());
                	synchronized (out) {
            			out.writeObject(result);
					}
                }
        	} catch (IOException e) {
        		console.append(e.getMessage() + '\n');
			}
        }
        
        public  boolean checktable(String table)
        {
            
            String[] types={"TABLE"};
            try
            {
                DatabaseMetaData metadate=c.getMetaData();
                ResultSet resultSet=metadate.getTables(null, null, "%", types);
                while(resultSet.next())
                {
                    String tableName=resultSet.getString(3);
                    if(tableName.toUpperCase().equals(table.toUpperCase()))
                        return true;         
                                      
                }
                
                
            }
            catch (SQLException se) {
                System.out.println(se);
            }
            return false;
            
          }
         public String GetPrimarykey(String table)
        {
            try {
                ResultSet rs ;
                String li="";
                DatabaseMetaData meta = c.getMetaData();
                try
                {
                    rs = meta.getPrimaryKeys(null, null, table);
                    
                    List list = new ArrayList();
                    
                    while (rs.next()) {
                        String columnName = rs.getString("COLUMN_NAME");
                        System.out.println("getPrimaryKeys(): columnName=" + columnName);
                        list.add(columnName);
                        li=columnName;
                        return li;
                    }
                    //System.out.println(li);
                    return li;
                    
                }
                catch (SQLException se) {
                    System.out.println(se);
                }
//            rs.close();
                return li;
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;

  }

}
