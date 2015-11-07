import java.io.*;
import java.net.*;
import java.sql.*;
class p1
{
 static void fun()
  {
 PrintStream toSoc;
		
try
 {
ServerSocket f=new ServerSocket(5590);
Socket t=f.accept();
		BufferedReader fromSoc=new BufferedReader(new InputStreamReader(t.getInputStream()));
		String resp=fromSoc.readLine();
			System.out.println(resp);
 }

catch(Exception e)
 {
  System.out.println("EXE:"+e);
 }
}

    


	public static void main(String args[])
	{
		Socket s;
		BufferedReader fromSoc,fromKbd;

		PrintStream toSoc;
		String video,ip;
           int port,count;
		try
		{
            
s=new Socket("169.254.63.10",6016);
int port1=5590;

toSoc=new PrintStream(s.getOutputStream());
toSoc.println(port1);
String url="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)}; DBQ=D://db.mdb; DriverID=22;READONLY=true;";


        Connection con=DriverManager.getConnection(url,"","");
        Statement st = con.createStatement();
             
 ip="169.254.205.92";
 port=6016;
 String myvideo="sachin";
 st.executeUpdate(" insert into p1 values('"+ip+"' , '"+port+"' , '"+myvideo+"' );  ");			


			

System.out.println("Requested Video:");
fromKbd=new BufferedReader(new InputStreamReader(System.in));
			video=fromKbd.readLine();
            	toSoc=new PrintStream(s.getOutputStream());
			toSoc.println(video);
                toSoc.println(port1);
                toSoc.println(ip);

	
fun();
			
			}
		catch(Exception e)
		{
			System.out.println("exception"+e);
		}
	}
}