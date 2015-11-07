import java.net.*;
import java.io.*;

public class receiver extends Thread{
String ip=null;
int port=0;
String format=null;
static String videoname=null;

receiver(String video,String ipaddr,int portno){
videoname=video;
ip=ipaddr;
port=portno;
this.start();
}

public void run() {
int i=0,a=0;
String s1,s2;
try{
	Socket s = new Socket(ip,port);
	InputStream in = s.getInputStream();
	FileOutputStream out = null;
	BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
	BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
	PrintStream p=new PrintStream(s.getOutputStream());
	s1=br.readLine();
	//System.out.println(s1);
	//s1=br1.readLine();
	s1=videoname;
	p.println(s1);
	s2=br.readLine();

	int index=s1.indexOf('.');
	format=s1.substring(index+1);
	System.out.print("for"+format);
	
	int noofsplitfiles=Integer.parseInt(s2);
	BufferedInputStream bis=new BufferedInputStream(s.getInputStream());
	BufferedOutputStream bos=new BufferedOutputStream(null);
	long len=Long.parseLong(br.readLine());
	int chk=((int)len/1024)*1024+1024;
	
	FileOutputStream r=new FileOutputStream("joined_file."+format,true);
	long byt=0;
	for (i =1; i <=noofsplitfiles; i++)
	{
		System.out.println("\n receving  splitfile_"+i+"."+format);
		out = new FileOutputStream("splitfile_"+i+"."+format);
		a=0;
		bos=new BufferedOutputStream(new FileOutputStream(new File("splitfile_"+i+"."+format)));
		int bytesread=0;
		byte[] buf0 = new byte[12];
		int bytesRead = 0;

BufferedReader fromSoc=new BufferedReader(new InputStreamReader(s.getInputStream()));

PrintStream toSoc=new PrintStream(s.getOutputStream());
String h=fromSoc.readLine();
//Systemoutprintln("h"+h);	
if(h.equals("send"))
		{		
toSoc.println("receive");
		}	




		while(bytesRead < 12)
		{
			if(bytesRead > 0)
			{
				bytesRead += in.read(buf0, bytesRead, 12 - bytesRead);
			}
			else bytesRead += in.read(buf0, 0, 12);
		}
		int fileLength = 0;
		try{
			fileLength = new Integer(new String(buf0));
			System.out.println("length of file received in KB : "+fileLength/1024);
		   } 
		   catch (NumberFormatException e )
		   {
				System.out.println(e);
				System.exit(-1);
		    }
		bytesRead = 0;
		byte[] buf = new byte[1024];
		int c;
		while(bytesRead  < fileLength)
		{
			if(fileLength - bytesRead > 1024)
			{
				c = in.read(buf, 0, 1024);
			}
			else{
				c = in.read(buf, 0, fileLength - bytesRead);
			}
			bytesRead += c;
			byt+=c;
			out.write(buf, 0, c);
		}
		out.close();

		FileInputStream ou=new FileInputStream("splitfile_"+i+"."+format);
		bytesRead=0;c=0;buf = new byte[1024];
		//writing to joined_file.vob
		while(bytesRead  < fileLength)
		{
			if(fileLength - bytesRead > 1024)
				{
				 c=ou.read(buf, 0, 1024);
				}
			else{
				c = ou.read(buf, 0, fileLength - bytesRead);
				}
			bytesRead += c;
			r.write(buf,0,c);
		}

		if(i==1)
		Runtime.getRuntime().exec("C:/Program Files/VideoLAN/VLC/vlc.exe joined_file."+format);
		Thread.sleep(4000);
	}
	s.close();
	}catch(Exception e){}

}
}