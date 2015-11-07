import java.io.*;
import java.net.*;
public class sender extends Thread
{

int port=0;
  static String createSuffix(String str,int paramInt)
  {
int index=str.indexOf('.');
String format=str.substring(index+1);
String filenam = str.substring(0, index);
String s=filenam+"_"+paramInt+"."+format;
        
    return s;
  }



public sender(int portno)
{
port=portno;
this.start();
}

public void run()
{
int a=0,i=0;
String s1;
int splitsize=1024;

try{


ServerSocket servsock=new ServerSocket(port);
while (true) {
	System.out.println("Waiting...");
	Socket s = servsock.accept();
     System.out.println("Accepted connection : " + s);


BufferedOutputStream bos=new BufferedOutputStream(s.getOutputStream());
BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
if(s!=null) System.out.println("connected with client");
PrintStream p=new PrintStream(s.getOutputStream());
p.println("file u need:");
s1=br.readLine();
System.out.println("file client needs  :"+s1+ "\nchecking availability");
File f=new File(s1);
long noofsplitfiles=(f.length())/(1024*1024)+1;
p.println(Long.toString(noofsplitfiles));
File splitfile[]=new File[(int)noofsplitfiles+1];

long l=new File(createSuffix(s1,(int)noofsplitfiles)).length();
p.println(Long.toString(l));
OutputStream out = s.getOutputStream();

  if(f.exists()) 
  {
                HJSplit.split(s1,s1,splitsize); 
	for ( i = 1; i <= noofsplitfiles; i++) 
	{
		splitfile[i]=new File(createSuffix(s1,i));
		byte[] head = (splitfile[i].length()+"").getBytes();
		int d = head.length;

		for(int k = d-1; d < 12; d++){
					out.write("0".getBytes());
				}
		out.write(head);
		//System.out.write(head);
	FileInputStream in = new FileInputStream(splitfile[i]);
		//Thread.sleep(1000);
	    System.out.println("sending... "+splitfile[i]);
   	//BufferedInputStream bis=new BufferedInputStream(new                   FileInputStream(splitfile[i]));
		
		byte[] mb = new byte[1024];
		for (int c = in.read(mb); c > -1; c = in.read(mb)) 
		{
					out.write(mb, 0, c);
		}
	//Thread.sleep(10000);			
	//System.out.println("hi");
        }
  }
	
	
  else{System.out.println("not exists");}	
}

}catch(Exception e){}

}
}