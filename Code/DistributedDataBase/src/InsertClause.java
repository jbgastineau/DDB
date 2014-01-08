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
public class InsertClause{
    
    public String tablename;

    /**
     *
     */
    public List<String> columns= new ArrayList<>();
    private final List<String> values=new ArrayList<>();
    //Insert insert=new Insert();

   public String msg;
   public int A=0;

   
    
   public void setallcolunm(String s)
   {
       String cont;
       String check;
       String[] colum;
       int x1,x2;
       x1=s.toLowerCase().indexOf("values");
       x2=s.toLowerCase().indexOf("into");
       cont=s.substring(x2+4, x1).trim();
        //System.out.println("the statement is :"+ cont);
       if(cont.indexOf(")") >-1 && cont.indexOf("(")>-1)
       {
           check=cont.substring(cont.indexOf("(")+1, cont.indexOf(")"));
          // System.out.println("the statement is :"+ check);
           colum=check.split(",");
           columns.addAll(Arrays.asList(colum));
           
            
           
       }   else
           columns=null;
   }
    public List<String> getallcolunm()
       {
           return columns;
       }
   
   public boolean chkcol()
   {
       return columns==null;
   }
   public void setTableName(String s)
   {
       String cont;
       String check;
       String Table;//we do not need this varviable.
       int x1,x2;
       boolean cv=chkcol();
       x1=s.toLowerCase().indexOf("values");
       x2=s.toLowerCase().indexOf("into");
       cont=s.substring(x2+4, x1).trim();
       //System.err.println(cont.length());
       setallcolunm(s);
       if(cont!=null)
       {
       if(columns==null)
       {
           tablename=cont;
           
       }
       else
           tablename=cont.substring(0,cont.indexOf("("));
       }
       else
       tablename=null;
            
        
     
       
        
   }
      
      
   
   public String gettTableName()
   {
       return tablename;
   }
   public void setvalues(String s)
   {
       int x;
       String val,val2;
       String[] value;
       x=s.toLowerCase().indexOf("values");
       val=s.substring(x+6);
       //System.out.println(val);
       if(val.indexOf("(")>-1 && val.indexOf(")")>-1)
       {
       val2=val.substring(val.indexOf("(")+1, val.indexOf(")"));
       //System.out.println(val2);
       value=val2.split(",");
       values.addAll(Arrays.asList(value));
       }else
       {
           A=4;
           
       }
       
       
   }
   public List<String> getvalues()
   {
       return values;
   }
   public boolean check()
   {
       //System.out.println(columns);
       if(columns==null)
           return true;
       if(values.size()==columns.size())
           return true;
       else
       {
           System.out.println("SYNTAX ERROR!!!!");
       return false;
       }
               
   }
   
    @Override
    public String toString() {
        if(check())
        {
    StringBuilder buf = new StringBuilder("insert into " + tablename);
    if(columns != null && columns.size() > 0) {
      //buf.append(" " + columns_.toString());
      buf.append("(").append(columns.get(0).trim());
      for(int i=1; i<columns.size(); i++) {
        buf.append(",").append(columns.get(i).trim());
      }
      buf.append(")");
    }

    
    buf.append(" ");
     buf.append("values ");
      buf.append("(").append(values.get(0).trim());
      for(int i=1; i<values.size(); i++) {
        buf.append(",").append(values.get(i).trim());
      }
      buf.append(")");
        return buf.toString();
  }
        return null;
    }
    
public boolean insertsatement(String pro)
{
    String msg1;
    pro=pro.toLowerCase();
    if(pro.indexOf("into")==-1)
    {
        msg1="ERROR.....the insert statement does not contain into!!!";
        System.out.println(msg1);
        return false;
    }
    else if(pro.indexOf("values")==-1)
    {
        msg1="ERROR....the insert statement does not contain values!!!!";
        System.out.println(msg1);
        return false;
    }
   /*  else if(pro.indexOf("(")==-1)
    {
        msg1="ERROR....the insert statement does not contain (!!!!";
        System.out.println(msg1);
        return false;
    }*/
   
    return true;
}

    public void ErrorStatement()
    {
        if(A==1)
            System.out.println("Error Statement Update does not contain 'into'!!!");
        if(A==2)
            System.out.println("Error Statement Update the statment does not contain table 'name'!!!");
        if(A==3)
            System.out.println("Error Statement Update the Statment does not contain '(' mark !!!");
        if(A==4)
            System.out.println("Error Statement the statement does not contain '(' or ')' after values");
        
    }

    
    
    
}