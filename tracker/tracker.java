/*
 * This program is used for maintaining details about all peers in a cluster
 */
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

class function
{

/*
 * The function search is used to search video in DB when requesting peer asks for video
 * @param file : file name to be searched
 */
	
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
	   
/*
 * Used to create tcp connection with peers 
 * Thread is used to accept connection from many peers
 * server is waiting at port 6016
 */
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
String line,line2,line1,line3=null,line4=null,amt,ban;
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
    	  /*
    	   * The tracker access database "db.mdb" to maintain peer information
    	   */
    	  String url="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)}; DBQ=db.mdb; DriverID=22;READONLY=true;";
    	  Connection con=DriverManager.getConnection(url,"","");
    	  st = con.createStatement();
    	  fromSoc=new BufferedReader(new InputStreamReader(sk.getInputStream()));
    	  toSoc=new PrintStream(sk.getOutputStream());    	
    	  line=fromSoc.readLine();
    	  
    	  /*
    	   * This condition is used whether peer is joining peer(with video) 
    	   * or peer which requests for video
    	   */
    	  if( line.equals("join") )
    	  {
    		  String s=fromSoc.readLine();
    		  System.out.println(s);
    		  if(s.equals("ok"))
    		  {
    			  System.out.println("ok recv"); 
    			  toSoc.println("forjoin");
    		  }
    		  line = sk.getInetAddress().getHostAddress();
    		  String port;
    		  String video=fromSoc.readLine();
    		  port = fromSoc.readLine();
    		  ban=fromSoc.readLine();
    		  band=Double.parseDouble(ban);
    		  amt=fromSoc.readLine();
    		  
    		  /*
    		   *Inserting joining peer information in table 
    		   */
    		  st.executeUpdate(" insert into vino values('"+line+"' , '"+port+"' , '"+video+"' , '"+line3+"','"+band+"','"+amt+"' );  ");
    		  st.executeQuery("select * from vino");
    		  String str=null;
    		
    		 /*
    		 * this loop is for checking transient peers 
    		 */
    		  
    		  while(true)
    		  {
    			  Thread.sleep(1000); 
    			  toSoc.println("hai");
    			  str=fromSoc.readLine();
    		  }
    	  }
 
    	  
    	  /*
    	   * this peer joining in a cluster is requesting for a video
    	   */
    	  
    	  else if(line.equals("request"))
    	  {
    		  String s=fromSoc.readLine();
    		  if(s.equals("ok1"))
    		  {
    			  toSoc.println("forrequest");
    		  }
    		  String line4,line3=null,ban,amt;
    		  int port1 = sk.getPort();
    		  
    		  //getting video name,bandwidth,ipaddr from requester
    		  line1=fromSoc.readLine();
    		  ban=fromSoc.readLine();
    		  line3 = sk.getInetAddress().getHostAddress();
    		  band1=Double.parseDouble(ban);
    		  
    		  //searching for requested file
    		  function obj=new function();
    		  boolean fs;
    		  String summa=null;
    		  line4=null;
    		  fs = obj.search(line1);
    		  int count=0;
    		  String ip[]=new String[10];
    		  int por[]=new int[10];
    		  String amnt[]=new String[10];
    		  ResultSet rs,rs1;
    		  
    		  /*
    		   * the condtion will be true if requested video is present in any other peer
    		   * with video content
    		   */
    		  if(fs == true)
    		  {
    			  st.executeUpdate(" insert into vino values('"+line3+"' , '"+port1+"' , '"+line4+"' ,'"+line1+"','"+band1+"','"+summa+"'  );  ");
    			  /*
    			   * if video is available fully in a peer we prefer peer with high bandwidth,
    			   * thus we sort the peer list according to bandwidth
    			   */
    			  rs =st.executeQuery("SELECT * FROM vino WHERE VideoCont='"+line1+"' AND Amt='full' ORDER BY 'Bandwidth in bps' DESC");
    			  String bwful=null;
    			  if(rs.next())
    				  bwful=rs.getString(5);
    			  double highbwfull=0;
    			  if(bwful!=null)
    				  highbwfull=Double.parseDouble(bwful);
    			  
    			  /*
    			   * if video is available in parts in a peer we prefer peer with high bandwidth,
    			   * thus we sort the peer list according to bandwidth
    			   */
    			  
    			  
    			  rs1 =st.executeQuery("SELECT * FROM vino WHERE VideoCont='"+line1+"' AND NOT(Amt='full') ORDER BY 'Bandwidth in bps' DESC");
    			  String bwpart=null;
    			  if(rs1.next())
    				  bwpart=rs1.getString(5);
    			  double highbwpart=0;
    			  if(bwpart!=null)
    				  highbwpart=Double.parseDouble(bwpart);
    			  
    			  /*
    			   * if the bandwidth of peer with full video content is high among peers, 
    			   * then that peer is choosen for sending video
    			   */
    			  
    			  if(highbwfull>highbwpart)
    			  {
    				  toSoc.println("full");
    			  }
    			  
    			  /*
    			   * if the bandwidth of peer with splitted video content is high among peers, 
    			   * then peers with splitted video contents are choosen for sending video
    			   */
    			  else
    			  {
    				  toSoc.println("not full");
    			  }
    			  
    			  
    			  
    			  //peer with full video content is choosen for sending video
    			  if(highbwfull>highbwpart)
    			  {
    				  rs =st.executeQuery("SELECT * FROM vino WHERE VideoCont='"+line1+"' AND Amt='full' AND 'Bandwidth in bps="+bwful+"'");
    				  rs.next();
    				  String ip1,amnt1;
    				  int por1;
    				  
    				  //the peer information is send to requesting peer for establishing connections
    				  if(rs.getString(3).equals(line1))
    				  {
    					  ip1=rs.getString(1);
    					  por1=rs.getInt(2);
    					  amnt1=rs.getString(6);
    					  toSoc.println(ip1);
    					  toSoc.println(por1);
    					  toSoc.println(amnt1);
    					  
    				  }
    				  toSoc.println("Ur Stream starts:)");
    				  
    			  }
    			  
    			//peers with splitted video content are choosen for sending video
    			  else
    			  {	
    				  rs =st.executeQuery("SELECT * FROM vino WHERE VideoCont='"+line1+"' AND not(Amt='full') ");
    				  String noofsplits="";
    				
    				//the peer informations are send to requesting peer for establishing connections
    				  System.out.println("\nThe content is available in following in peers in order of bandwidth");
    				  while(rs.next())
    				  	{
    					  		ip[count]=rs.getString(1);
    						  	por[count]=rs.getInt(2);
    						  	amnt[count]=rs.getString(6);
    						  	System.out.println("ip      :"+ip[count]+"\nport    "+por[count]+"\nAmount  "+amnt[count]);
    						  	noofsplits=noofsplits.concat(amnt[count]);
    						  	noofsplits=noofsplits.concat(",");
    						  	count++;
    				  	}
    				  
    				  /*
    				   * calculating the number of split files in a peer
    				   */
    				  int noofsplit=0,i=0;
    				  char ch;
    				  while(i<noofsplits.length())
    				  {ch=noofsplits.charAt(i++);
    				  if(ch==',') noofsplit++;
    				  }
    				  toSoc.println(count);
    				  while(count!=0)
    				  {
    					  toSoc.println(ip[count-1]);
    					  toSoc.println(por[count-1]);
    					  toSoc.println(amnt[count-1]);
    					  count--;
    					  toSoc.println("Ur Stream starts:)");
    					  
    				  }
    			  }
    			  
    		  }
    		  
    		  /*
    		   * if video is not present in any of peers in cluseter, a message is returned to 
    		   * requesting peer
    		   */
    		  else
    			  toSoc.println("Ur requested video not available:(");
    		  sk.close();
    	  }


    	  /*
    	   * all peers joins in cluster with password 
    	   * "join" for peer with video content
    	   * "request" for peer requesting for video
    	   */
    	  else
    	  {
    		  toSoc.println("wrong passwod");
    		  sk.close();
    	  }
    	  
    	  if( line != null)	System.out.println("ID:"+line);
    	  if( line2 !=null)	System.out.println("Contentt:"+line2);
    	  fromSoc.close();
    	  toSoc.close();
    	  sk.close();
      }
      catch(SQLException e)
      {
    	  System.out.println(e);
      }
      
      catch(Exception e)
      {
    	  /*
    	   * deletes peer information if peer leaves the cluster 
    	   * (for transient peer)
    	   */

    	  try
    	  {
    		  if(st==null) 
    		  st.executeUpdate("delete from vino where Port= "+port+";");
    	  }
    	  catch(Exception m)
    	  { System.out.println("exception:"+m); } 
    	  
      }
  }
}



