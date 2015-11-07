import java.io.*;
import java.net.*;
import java.sql.*;

class searcher
{
 /* to search the DB table for req. video*/ 
 boolean search(String file)
  {

  boolean found=false;  
      
  Connection con = null;
  String url ="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};  DBQ=D://db.mdb; DriverID=22;READONLY=true;";

 try
   {      
      con = DriverManager.getConnection(url,"","");
      try
      {
          Statement st = con.createStatement();
          ResultSet rs = st.executeQuery("SELECT video FROM p3");
          while(rs.next())
	      {
             String colvalue=rs.getString("video");
             if(colvalue.equalsIgnoreCase(file))
                 found=true;
               
           }
        
      }
     
     catch (SQLException s)
      {
        System.out.println("SQL statement is not executed!");
      }
  }
    catch (Exception e)
    {
      System.out.println(e);
    }
   return found; 
 }
}



class p3
{
static void fun()
{
PrintStream toSoc;
		
try{
ServerSocket f=new ServerSocket(9090);
while(true)
{
Socket t=f.accept();
		BufferedReader fromSoc=new BufferedReader(new InputStreamReader(t.getInputStream()));
		String video=fromSoc.readLine();
			System.out.println(video);
  searcher obj=new searcher();
  boolean fs;
  fs = obj.search(video);
if(fs==true)
{
System.out.println("stream will starts");
toSoc=new PrintStream(t.getOutputStream());
toSoc.println("stream starts");

}
else
{
toSoc=new PrintStream(t.getOutputStream());
  toSoc.println("sorry");
}
}

}catch(Exception e){System.out.println(e);
}

}
	public static void main(String args[])
	{
  		int myport=9090;
		Socket s;
           ServerSocket ss;
		BufferedReader fromSoc,fromKbd;
		PrintStream toSoc;
		String line,msg;
		try
		{
			s=new Socket("169.254.63.10",6016);
			System.out.println("enter line");
			System.out.println("connected");
toSoc=new PrintStream(s.getOutputStream());
			
toSoc.println(myport);
  String url="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)}; DBQ=D://db.mdb; DriverID=22;READONLY=true;";

        Connection con=DriverManager.getConnection(url,"","");
        Statement st = con.createStatement();
String ip="169.254.63.10";
int port=6016;
String video="rooney";

st.executeUpdate(" insert into p3 values('"+ip+"' , '"+port+"' , '"+video+"' );  ");

fun();


				
		}
		catch(Exception e)

		{
			System.out.println("exception"+e);
		}
	}

 


}
