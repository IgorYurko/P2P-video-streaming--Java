On Error Resume Next
strComputer = "."

    Dim objConn     ' Our Connection Object
    Dim objRS       ' Our Recordset Object
    Dim strSQL      ' Our SQL string to access the database
    Dim strConnection    ' Our Connection Object string to access the database
    Set objConn = CreateObject("ADODB.Connection")
    Set objRS = CreateObject("ADODB.Recordset")
    objConn.Open "Provider=Microsoft.Jet.OLEDB.4.0; Data Source=D:\db.mdb"




Set objWMIService = GetObject("winmgmts:\\" & strComputer & "\root\cimv2")
Set colItems = objWMIService.ExecQuery("Select * from Win32_PerfRawData_Tcpip_NetworkInterface where Name='Intel(R) PRO/Wireless 3945ABG Network Connection'",,48)
For Each objItem in colItems
'Wscript.Echo "BytesReceivedPersec: " & objItem.BytesReceivedPersec
'Wscript.Echo "BytesSentPersec: " & objItem.BytesSentPersec
Wscript.Echo "BytesTotalPersec: " & objItem.BytesTotalPersec
'Wscript.Echo "Caption: " & objItem.Caption
'Wscript.Echo "CurrentBandwidth: " & objItem.CurrentBandwidth
strSQL = "INSERT INTO peer VALUES("&objItem.BytesSentPersec/1000&");"
objConn.Execute (strSQL)
Next



   ' wscript.echo "hai"
    
    'Set objRS = objConn.Execute (strSQL)
      ' wscript.echo "hai"
		'Do While not objRS.EOF
   		 'wscript.echo "The information is " & objRS(1)
    		'objRS.MoveNext
		'Loop




