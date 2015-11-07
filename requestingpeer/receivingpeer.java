import java.io.*;
import java.net.*;
class receivingpeer extends Thread
{
    static Socket s=null;
String ipaddress=null,parts=null;
    static PrintStream toSoc=null;

int portnumber=0;


static String reqmsg=null,videoname=null;

public static void appcontent(String videonam)
{
videoname=videonam;

//System.out.println("kkkk"+videoname);

try{

toSoc=new PrintStream(s.getOutputStream());
toSoc.println("ok1");
}catch(Exception e){}

}





public synchronized void run()
{
new receiver1(videoname,ipaddress,portnumber,parts);
}

receivingpeer(String ipaddr,int portno,String amount)
{
ipaddress=ipaddr;
portnumber=portno;
parts=amount;
}


public static void main(String args[])
{
		
	//String bandwidth=caller.caller1();
	//	double band=Double.parseDouble(bandwidth);
		double band=150;
		BufferedReader fromSoc,fromKbd;
		
		String line,msg,line1,msg1;
		try
		{
			s=new Socket(args[0],6016);
			System.out.println("connected");
			System.out.println("enter line to request video");
			fromKbd=new BufferedReader(new InputStreamReader(System.in));
            		line=fromKbd.readLine();
			toSoc=new PrintStream(s.getOutputStream());
			toSoc.println(line);
			fromSoc=new BufferedReader(new InputStreamReader(s.getInputStream()));
app2.main1();
String s1=fromSoc.readLine();
     	System.out.println(s1);

if(s1.equals("forrequest"))
{

			toSoc.println(videoname);
           	        toSoc.println(band);
}     
       String fulpart=fromSoc.readLine();
            int coun=1;
            if(fulpart.equals("full"))
            {
            	String ip,amnt;
            	int por;
            	ip=fromSoc.readLine();
            	por=Integer.parseInt(fromSoc.readLine());
            	amnt=fromSoc.readLine();
            	System.out.println("The video is got from :");
            	System.out.println("IP address:"+ip);
            	System.out.println("Port number:"+por);
            	System.out.println("Amount:"+amnt);
            	msg=fromSoc.readLine();
            	if(msg.equals("Ur Stream starts:)"))
            	{
            		System.out.println("wait for receiving....");
            		receiver recv=new receiver(videoname,ip,por);
            		while(true){}
            	}
            	else
            	{
            		System.out.println("file not found");
            		s.close();
            	}
            }
            else
            {		
            	int o=0;
            	coun=Integer.parseInt(fromSoc.readLine());
            	System.out.println("count"+coun);
            	String ip[]=new String[coun];
            	int por[]=new int[coun];
            	String amnt[]=new String[coun];
            	while(o<coun)
            	{
            		ip[o]=fromSoc.readLine();
            		por[o]=Integer.parseInt(fromSoc.readLine());
            		amnt[o]=fromSoc.readLine();
            		msg=fromSoc.readLine();
            		if(msg.equals("Ur Stream starts:)"))
            		{
            			System.out.println("The video is got from :");
            			System.out.println("IP address      :"+ip[o]);
            			System.out.println("Port number     :"+por[o]);
            			System.out.println("Amount of video :"+amnt[o]);
            			Thread t=new Thread(new receivingpeer(ip[o],por[o],amnt[o]));
            			t.start();
            		}
            		else
            		{
            			System.out.println("file not found"+msg);
            			s.close();
            		}
            		o++;
            	}
            }     
		}
		catch(Exception e)
		{
			System.out.println("exception"+e);
		}
}
}

