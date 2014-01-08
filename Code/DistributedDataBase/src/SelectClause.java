
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Alrfou
 */
import java.util.*;
public class SelectClause extends SelectBuilder{
    private SelectBuilder slbuil=new SelectBuilder();
    
    public SelectClause()
    {
        
    }
    
    
      public   String getSelectedFields(String sql)
      {
          String s=sql.substring(7, sql.toLowerCase().indexOf("from")).trim();
          
          if(s.startsWith(",", 0))
          {
              System.out.println("Error statment");
              return null;
              
          }
                                                      
       return sql.substring(7, sql.toLowerCase().indexOf("from")).trim();
        
      }
      
     public   String getSelectedTables(String sql)
     {
         int coun;
       if(-1== sql.toLowerCase().indexOf("where"))
       coun=sql.length();
       else
           coun=sql.toLowerCase().indexOf("where");
       
         return sql.substring(sql.toLowerCase().indexOf(" from ") + 5,coun).trim();
      }
     
      public   String getwherecond(String sql)
     {
        int count;
   
        if(-1== sql.indexOf("order by"))
         count=sql.length();
        else
          count=sql.indexOf("order by");  
        return sql.substring(sql.toLowerCase().indexOf(" where ") + 7,count).trim();
    
     }
        public   String getorderby(String sql)
     {
        int count;
   
            count=sql.length();
        
        return sql.substring(sql.toLowerCase().indexOf(" order by ") + 9,count).trim();
    
     }
      
      public String[] split(String s,int x)
      {
          String[] ab = s.split(",");
          return ab;
      }
      
      public boolean Colunm(String col)
      {
          String[] splited = col.trim().split(" ");
        return splited.length==1;
                            
                  }
      
      public String selectStatement(List<String> col,List<String> table)
      {
          if(col!=null && null!=table)
        {
           for(int i=0;i<col.size();i++)
          {
           slbuil.column(col.get(i));
           }
          for(int j=0;j<table.size();j++)
            slbuil.from(table.get(j));
        //System.out.println(slbuil.toString());
        return slbuil.toString();
        }
          return null;
         
      } 
}