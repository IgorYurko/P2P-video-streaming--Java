import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;


class sendingpeer2 implements Runnable
{


    static Socket s=null;
    static BufferedReader fromSoc=null,fromKbd;
    static PrintStream toSoc=null;
	static boolean closed=false;
		

static String videoname=null,amount=null,parts=null;

public static void appcontent(String videonam,String amt)
{
videoname=videonam;
amount=amt;
//System.out.println("kkkk"+videonam+amount);

try{

	toSoc=new PrintStream(s.getOutputStream());
toSoc.println("ok");
}catch(Exception e){}
}


	public static void main(String args[])
	   {

		String line,amt,amt1=null,amt2;
		//assigning port number to sending peer
		int portno=31236;
		//getting bandwith of peer
	String bandwidth=caller.caller1();
		double band=Double.parseDouble(bandwidth);
        try
		{
        	//making tcp connection with tracker

        	s=new Socket(args[0],6016);
 fromSoc=new BufferedReader(new InputStreamReader(s.getInputStream()));
	toSoc=new PrintStream(s.getOutputStream());
        	System.out.println("connected");
System.out.println("enter msg to join");	
fromKbd=new BufferedReader(new InputStreamReader(System.in));
			line=fromKbd.readLine();
			toSoc=new PrintStream(s.getOutputStream());
			toSoc.println(line);



  

		app.main1();
String s=fromSoc.readLine();
  //   	System.out.println(s);

if(s.equals("forjoin"))
{

//System.out.println("ppp"+videoname+portno+band+amount+parts);

//toSoc.println(joinmsg);
toSoc.println(videoname);
toSoc.println(portno);
toSoc.println(band);						
toSoc.println(amount);

}

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
				new Thread(new sendingpeer2()).start();
				
				//if file this peer has full video,create sender instance 
				if(amount.equals("full"))
				{sender send=new sender(portno);}
				//if file this peer has splitted video,create sender1 instance
				else
				{sender1 send1=new sender1(portno,amount);}
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






		
