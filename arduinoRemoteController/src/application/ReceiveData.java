
//this class and methods are resposible to receive real-time data from the server


package application;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.stream.Stream;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ReceiveData {
	
	
	 Alert alert = new Alert (AlertType.INFORMATION); 
	 BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
	 private DataInputStream input = null;          // "InputStream" to receive the incoming data from the server
	 
	 
	 
	 public void receive (Socket socket) {
		 String data ="";                     // data is a local variable with a initial value of "0"
		 MainPanelController main = new MainPanelController();
				 
		 if (socket != null) {
			 try {
				 	//input =  new DataInputStream(socket1.getInputStream());
				 	//br = new BufferedReader(new InputStreamReader(input));
				 	input =  new DataInputStream(socket.getInputStream());
				 	br = new BufferedReader(new InputStreamReader(input));
				 	while((data = br.readLine()) != null) {
				 		
				 		    if(data.equals("done")) {
				 			break;
				 		}
				 		
				 	System.out.println(data);
				 		
				 	}
			 } catch (Exception e){
				 System.out.println(e);
			 }
		 
	 } else {
		 alert.setTitle("Data receiving");
		 alert.setHeaderText("Error");
		 alert.setContentText("Client is unable to recieve data from the server");
		 alert.show();
	 }
		                       // returning the value of "data" to the calling function
  }
	 
	 
}	 
