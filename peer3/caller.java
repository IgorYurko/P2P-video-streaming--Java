import java.sql.*;
public class caller {

public static String caller1() {
String s=null;				

try {

Runtime.getRuntime().exec("wscript band1.vbs");
Thread.sleep(5000);
Connection con = null;
String url ="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};  DBQ=band.mdb; DriverID=22;READONLY=true;";    
con = DriverManager.getConnection(url,"","");
Statement st = con.createStatement();
ResultSet rs = st.executeQuery("SELECT * FROM bandwidth");
while(rs.next())
				{
				s=rs.getString(1);
					
				}
} catch (Exception e) {

System.exit(0);

}
return s;

}
}
