/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alrfou
 */
public class SQLQuery extends SelectClause {

    public static final int SELECT_STATEMENT = 1;
    public static final int UPDATE_STATEMENT = 2;
    public static final int INSERT_STATEMENT = 3;
    public static final int CREATE_STATEMENT = 4;
    public static final int DROP_STATEMENT = 5;
    public String[] clo;
    public String[] table;
    public String[] conditions;
    public String[] ORDERBY;
    public String returns;
    public String tablenames;

    public List<String> columns = new ArrayList<>();
    List<String> tables = new ArrayList<String>();
    private List<String> joins = new ArrayList<String>();
    private List<String> leftJoins = new ArrayList<String>();
    private List<String> wheres = new ArrayList<String>();
    private List<String> orderBys = new ArrayList<String>();
    private List<String> groupBys = new ArrayList<String>();
    private List<String> havings = new ArrayList<String>();

    // type of statement
    public int type = -1;
//    public State st;
    private boolean check = false;

    public SQLQuery parse(String str) {

        SQLQuery statement = new SQLQuery();
        //str = str.toUpperCase();
        String[] process = str.split(" ");
        process[0] = process[0].toUpperCase();
        switch (process[0]) {
            case "SELECT":
                boolean clck;
                str = str.toUpperCase();
                process = str.split(" ");
               type = SELECT_STATEMENT;
                SelectClause select = new SelectClause();
                check = checkSelect(process);
                if (check == false) {
                    System.out.print("Error in select statement");
                    break;
                } else {
                    String sp = select.getSelectedFields(str);
                    if(sp!=null)
                    {
                        sp = select.getSelectedFields(str).trim();
                    //getSelectedFields(str).trim();
                    clo = sp.split(",");

                    
                    for (String clo1 : clo) {
                        clck = select.Colunm(clo1);
                        if (clck == false) {
                            System.out.println("error>>>>> column syntax wrong");
                            columns = null;
                            break;
                        }
                        columns.add(clo1);
                    }
                    
                    sp = select.getSelectedTables(str);
                    
                    table = sp.split(",");
                    for (String tb : table) {
                        clck = select.Colunm(tb);
                        if (clck == false) {
                            columns = null;
                            tables = null;
                            System.out.println("error>>>>> table syntax wrong");

                            break;
                        }
                        tables.add(tb);
                    }
                    returns=selectStatement(columns, tables);
                   
                    }

                }
                break;
            case "UPDATE":
              type = UPDATE_STATEMENT;
              returns=str;
                break;
            case "INSERT":
               type = INSERT_STATEMENT;
                  //System.out.println(statement.type);
                InsertClause insert=new InsertClause();
                if(insert.insertsatement(str))
                {
                     
                    if(insert.check())
                    {
                        
                    insert.setTableName(str);
                       
                        
                        if(insert.gettTableName().length()==0)
                           System.out.println("SYNTAX ERROR!!!!");
                        else
                        {
                    if(insert.gettTableName()!=null)
                    {
                    tablenames=insert.gettTableName();
                    insert.setvalues(str);
                    returns=insert.toString();
                      // System.out.println(insert.toString());
                    }
                    else System.out.println("SYNTAX ERROR!!!!");
                    }
                   
                }
                }

                break;
            case "CREATE":
                type = CREATE_STATEMENT;
                 returns=str;
                break;
                case "DROP":
                type = DROP_STATEMENT;
                 //tablenames;//we should get table name;
                 returns=str;
                break;
            default:
                System.out.println("Statetement,NOT CORRECT STATEMSNT :" + process[0]);
            //return "ab";
            }

			//System.out.println("Statetement, CORRECT STATEMSNT :" + process[0]);
        return statement;
    }

 

    public boolean checkSelect(String[] str) {
        int k = 0;
        for (String str1 : str) {
            if ("FROM".equals(str1)) {
                k++;
            }
               // if("order".equals(str1))
            //{
            //    k++;
            //}

            // System.out.println(k);
        }
        return k == 1;
    }

    public String[] split1(String s, int x) {
        String[] ab = s.split(",");
        return ab;
    }

    /*public boolean where_cond(String cond)//Something wrong in this statement location.
     {
     String cond1;
     if(cond.toLowerCase().indexOf("And")>-1)
     {
     cond1="and";
             
     }
     else
     cond1="or";
     String[] splited = cond.toLowerCase().trim().split(cond1);
     if (splited.length==1) {
                            
     System.out.println(cond);
     return true;
                            
     } else 
     return false;
                            
     }*/
}