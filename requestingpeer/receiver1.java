import java.net.*;
import java.io.*;

public class receiver1 extends Thread{
String ip=null;
int port=0;
String format=null;
String amt=null;
int noofsplit;
int amount[]=new int[10];
static String videoname=null;
static int checkseq[]=new int[20];

receiver1(String video,String ipaddr,int portno,String amnt){
	videoname=video;
	noofsplit=1;
	ip=ipaddr;
	port=portno;
	amt=amnt;
	String sub="";
	int i=0,j=0;
	char ch;
	while(i<amt.length())
	{
		ch=amt.charAt(i++);
		if(ch==',')
		{
			noofsplit++;
			amount[j++]=Integer.parseInt(sub);
			sub="";
		}
		else
		{
			sub=sub+ch;
		}
	}
	amount[j]=Integer.parseInt(sub);
	this.start();
}

public synchronized void  run() {
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
	int noofsplitfiles=Integer.parseInt(s2);
	BufferedInputStream bis=new BufferedInputStream(s.getInputStream());
	BufferedOutputStream bos=new BufferedOutputStream(null);
	long len=Long.parseLong(br.readLine());
	int chk=((int)len/1024)*1024+1024;
	FileOutputStream r=new FileOutputStream("joined_file."+format,true);
	long byt=0;

	
	for (i =0; i <noofsplit; i++)
	{
		System.out.println("\n receving  splitfile_"+amount[i]+"."+format);
		out = new FileOutputStream("splitfile_"+amount[i]+"."+format);
		a=0;
		bos=new BufferedOutputStream(new FileOutputStream(new File("splitfile_"+amount[i]+"."+format)));
		int bytesread=0;
		byte[] buf0 = new byte[12];
		int bytesRead = 0;
/*
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
*/
	
		int fileLength = 1024*1024;
	if(amount[i]==7)  fileLength = 600*1024;
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

		FileInputStream ou=new FileInputStream("splitfile_"+amount[i]+"."+format);
		bytesRead=0;c=0;buf = new byte[1024];
	

//System.out.println("Split file:"+amount[i]+"received");

	//	writing to joined_file.vob
 	

checkseq[(amount[i])]=1;
	

int chkseq=0;	
int x=amount[i];
while(x-->0)
{
if(checkseq[x]==1) chkseq++;
}
//System.out.println(amount[i]+"----"+(chkseq+1));

//System.out.println("i:"+i+"\namount"+amount[i]+"\nchkseq"+chkseq+"\nseq"+checkseq[(amount[i])]);

//System.out.println("Split file:"+amount[i]+"received");

	if(chkseq==amount[i]-1)
	{
System.out.println("writing   " +amount[i]);
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
	}	

	for(int k=(amount[i]+1);k<=8;k++)
	{
	int y=1,z=0;
	while(y<k) {if(checkseq[y++]==1) z++;}
	//System.out.println("inside");
		if(checkseq[k]==1 && z==k-1)
		{

	System.out.println("writing splitfile_"+k+"."+format);
	ou=new FileInputStream("splitfile_"+k+"."+format);
	bytesRead=0;    
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
		}
		else{break;}
	}
	




	if(amount[i]==1)
Runtime.getRuntime().exec("C:/Program Files (x86)/VideoLAN/VLC/vlc.exe joined_file."+format);
		Thread.sleep(4000);
	
	
	

	}
	s.close();
 }catch(Exception e){}

}
}