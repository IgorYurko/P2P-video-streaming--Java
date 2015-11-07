import java.io.*;
import java.net.*;
public class sender1 extends Thread
{

int port=0;
int noofsplit=0;
int amount[]=new int[10];

  static String createSuffix(String str,int paramInt)
  {
int index=str.indexOf('.');
String format=str.substring(index+1);
String filenam = str.substring(0, index);
String s=filenam+"_"+paramInt+"."+format;
        
    return s;
  }



public sender1(int portno,String amt)
{
port=portno;
String sub="";
//System.out.println("Inside sender1"+amt);

int i=0,j=0;
char ch;

while(i<amt.length())
{ch=amt.charAt(i++);

if(ch==',')
{
noofsplit++;
amount[j++]=Integer.parseInt(sub);
sub="";
}
else{
sub=sub+ch;
}

}

amount[j]=Integer.parseInt(sub);

//System.out.println("amount of file"+i+"--"+amount[0]);
//System.out.println("amount of file"+i+"---"+amount[1]);
//System.out.println("amount of file"+i+"---"+amount[2]);

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
int noofsplitfiles=0;
for(int u=0;u<amount.length;u++)
{
if(amount[u]!=0) noofsplitfiles++;
}

BufferedOutputStream bos=new BufferedOutputStream(s.getOutputStream());
BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
if(s!=null) System.out.println("connected with client");
PrintStream p=new PrintStream(s.getOutputStream());
p.println("file u need:");
s1=br.readLine();
System.out.println("file client needs  :"+s1);




//System.out.println("noofsplitfiles"+noofsplitfiles);
p.println(Integer.toString(noofsplitfiles));
File splitfile[]=new File[noofsplitfiles+1];

long l=new File(createSuffix(s1,(int)noofsplitfiles)).length();
//System.out.println("last:"+l);
p.println(Long.toString(l));
OutputStream out = s.getOutputStream();           
	for ( i = 0 ;i< noofsplitfiles; i++) 
	{
		
splitfile[i]=new File(createSuffix(s1,amount[i]));
		

  if(splitfile[i].exists()) 
  {
   
		
		byte[] head = (splitfile[i].length()+"").getBytes();
		int d = head.length;
/*
		for(int k = d-1; d < 12; d++){
					out.write("0".getBytes());
				}
		out.write(head);
*/
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
  
	
	
  }else{System.out.println("file:"+splitfile[i]+" not exists");}	
}

        }
  

}catch(Exception e){}

}
}