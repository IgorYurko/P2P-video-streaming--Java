import java.io.*;
import java.net.*;
public class sender1 extends Thread
{
int port=0;
int noofsplit=0;
int amount[]=new int[10];

//creating split file name with requested filename
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
//amt contains string with splitted file numbers eg:1,3,4
port=portno;
String sub="";
int i=0,j=0;
char ch;
//parsing file numbers and put numbers in amount array
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
this.start();
}

//This sending peer has video content in partial
//sender listens to portno to make connection with requesting peer

public void run()
{
int a=0,i=0;
String s1;
int splitsize=1024;

try{
ServerSocket servsock=new ServerSocket(port);
    while (true) 
    {
   //creating tcp connection with receiver(requesting peer)
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
    p.println(Integer.toString(noofsplitfiles));
    
    //getting last split file size
    File splitfile[]=new File[noofsplitfiles+1];
    long l=new File(createSuffix(s1,(int)noofsplitfiles)).length();
    p.println(Long.toString(l));
    OutputStream out = s.getOutputStream();           

    //creating requested file instance and creating split files each of 1MB
    for ( i = 0 ;i< noofsplitfiles; i++) 
	{
    	splitfile[i]=new File(createSuffix(s1,amount[i]));
	    //checking if split files exists
    	if(splitfile[i].exists()) 
	    {
   		byte[] head = (splitfile[i].length()+"").getBytes();
		int d = head.length;
		//to make splitted file size as 12 digits 
		//if splitted file size if of 8 digits,4 0's are send to requester for synchronization
	/*
		for(int k = d-1; d < 12; d++){
					out.write("0".getBytes());
				}
		out.write(head);
*/
		
		FileInputStream in = new FileInputStream(splitfile[i]);
		System.out.println("sending... "+splitfile[i]);
   		byte[] mb = new byte[1024];
   		//sending split file 1024 bytes for each iteration
   		for (int c = in.read(mb); c > -1; c = in.read(mb)) 
			{
					out.write(mb, 0, c);
			}
	    }else{System.out.println("file:"+splitfile[i]+" not exists");}	
	}
  }
 }catch(Exception e){}

}
}