import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;


class sendingpeer1 implements Runnable
{


    static Socket s=null;
    static BufferedReader fromSoc=null,fromKbd;
    static PrintStream toSoc=null;
	static boolean closed=false;
		

static String joinmsg=null,videoname=null,amount=null,parts=null;

public static void appcontent(String joinms, String videonam,String full, String par)
{
joinmsg=joinms;
videoname=videonam;
amount=full;
parts=par;
System.out.println("kkkk"+joinms+videonam+full+parts);

try{

	toSoc=new PrintStream(s.getOutputStream());
toSoc.println("ok");
}catch(Exception e){}
}


	public static void main(String args[])
	   {

		String line,msg,amt,amt1=null,amt2;
		//assigning port number to sending peer
		int portno=31235;
		//getting bandwith of peer
		double band=150.0;
        try
		{
        	//making tcp connection with tracker
        	s=new Socket(args[0],6016);
        	System.out.println("connected");
   fromSoc=new BufferedReader(new InputStreamReader(s.getInputStream()));
	toSoc=new PrintStream(s.getOutputStream());

		app.main1();
String s=fromSoc.readLine();
     	System.out.println(s);

if(s.equals("ok"))
{

System.out.println("ppp"+joinmsg+videoname+portno+band+amount+parts);

toSoc.println(joinmsg);
toSoc.println(videoname);
toSoc.println(portno);
toSoc.println(band);						
toSoc.println(amount);
toSoc.println(parts);
}
/*

        	//join message send to tracker
        	//System.out.println("enter msg to join");	
        	//fromKbd=new BufferedReader(new InputStreamReader(System.in));
			//line=fromKbd.readLine();
			toSoc=new PrintStream(s.getOutputStream());
			toSoc.println(joinmsg);
			
			//sending file name of video this peer has
			//fromSoc=new BufferedReader(new InputStreamReader(s.getInputStream()));
			//msg=fromSoc.readLine();
			//System.out.println(msg);
			//line=fromKbd.readLine();
			toSoc.println(videoname);
			
			//sending port no to register with tracker
			toSoc.println(portno);				
			
			//sending bandwidth of this peer
			toSoc.println(band);
			
			//sends the info whether this peer has full content or splitted file
			//amt=fromSoc.readLine();
			//System.out.println(amt); 
			//amt1=fromKbd.readLine();
			toSoc.println(amount);
			//amt2=fromSoc.readLine();
			
			//if(am.equals("ok")){}
			//else
            { 	//if this peer has splitted files send file no. as (eg:1,4,5)
				//System.out.println(amt2); 
				//parts=fromKbd.readLine();
				toSoc.println(parts);
            }	
*/
		}
		catch(Exception e)
		{
			System.out.println("exception"+e);
		}
	
		if(s!=null && toSoc!=null && fromSoc!=null)
		{ 
			try
			{
				//thread used for keep-on alive messages
				new Thread(new sendingpeer1()).start();
				
				//if file this peer has full video,create sender instance 
				if(amount.equals("full"))
				{sender send=new sender(portno);}
				//if file this peer has splitted video,create sender1 instance
				else
				{sender1 send1=new sender1(portno,parts);}
			}
			catch(Exception e)
			{}
	}
}


//thread is used for sending alive messages to tracker.
//this is to notify they are in cluster while tranferring files to requesting peer
public void run()
{
  String res;
  try
  {
  while((res=fromSoc.readLine())!=null)
     {
     toSoc.println("living");   
     }
  closed=true;
  }
  catch(Exception e)
  {
  System.out.println(e);
  }
}
}






		
