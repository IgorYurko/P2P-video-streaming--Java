import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

class function
{
// to search the DB table for req. video
	boolean search(String file)
	{
		boolean found=false;  
		Connection con = null;
		String url ="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};  DBQ=db.mdb; DriverID=22;READONLY=true;";
		try
		{      
			con = DriverManager.getConnection(url,"","");
			try
			{
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT VideoCont FROM vino");
				while(rs.next())
				{
					String colvalue=rs.getString("VideoCont");
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


class tracker
{
public static void main(String args[])
{
   ServerSocket server,server1;
   Socket s,s1;
   String line=null;
   try
   {
	   server=new ServerSocket(6016);
	   while(true)
	   {			
		   System.out.println("server waiting");
		   s=server.accept();
		   System.out.println ("Connection from : " + 
		   s.getInetAddress().getHostAddress() + ':' + s.getPort());
		   Thread thr = new HandlerThread(s);
		   thr.start();
       }
   }
   catch(Exception e)
   {
			System.out.println("exception"+e);
   }
}
}


class HandlerThread extends Thread 
{
  Socket sk;
  BufferedReader fromSoc;
  PrintStream toSoc;
  String line,line2,line1,line3=null,line4=null,amt;
  double band,band1;
  int port,port1;
  Statement st;
  HandlerThread(Socket sk) 
  {
	  this.sk = sk;
  }
  public void run() 
  {
      try
      {
    	  String url="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)}; DBQ=db.mdb; DriverID=22;READONLY=true;";
    	  Connection con=DriverManager.getConnection(url,"","");
    	  st = con.createStatement();
    	  fromSoc=new BufferedReader(new InputStreamReader(sk.getInputStream()));
toSoc=new PrintStream(sk.getOutputStream());    	

String s=fromSoc.readLine();
     	System.out.println(s);

if(s.equals("ok"))
{
System.out.println("ok"); 
 toSoc.println("ok");
}
	line=fromSoc.readLine();
System.out.println("msg" +line);
	line=fromSoc.readLine();
System.out.println("msg" +line);
	line=fromSoc.readLine();
System.out.println("msg" +line);
	line=fromSoc.readLine();
System.out.println("msg" +line);
	line=fromSoc.readLine();
System.out.println("msg" +line);
	line=fromSoc.readLine();
System.out.println("msg" +line);
    	}catch(Exception e){}


}
}



