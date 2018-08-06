
//this class and methods are responsible to send data to the server


package application;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SendData {
	
	 Alert alert = new Alert (AlertType.INFORMATION); 
	 Connection connection = new Connection(); 
	 BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
	 private DataInputStream input = null;   
	 private DataOutputStream out  = null;      // OutputStream to send data to the sever socket
	 
	 
	 //public Socket socket = null;
	 
	 
	 public String send (Socket socket, int data) {
		 String str = "";
		 
		 //socket = connection.establishConnection();
		 
		 if (socket != null) {
			 try {
		
				 out=new DataOutputStream(socket.getOutputStream());
				//String str = ((Integer)data).toString();	
				//byte b[] = data.getBytes();
				   // System.out.println(data);
				    
					out.writeInt(data);          // writing data to the sever socket
					
					
					//receivedata.receive();
					input =  new DataInputStream(socket.getInputStream());
				 	br = new BufferedReader(new InputStreamReader(input));
				 	str = br.readLine();
				 	//System.out.println(data2);
					
			 } catch (IOException i) {
				 System.out.println(i);    //  if exception occurs then print the exception 
		 
			 } }else {
			 alert.setTitle("Data sending");
			 alert.setHeaderText("Error");
			 alert.setContentText("Client is unable to send data to the server");
			 alert.show();
		 }
	      return str;
	 }
	     
}
		 
		 
	 

	 
	


