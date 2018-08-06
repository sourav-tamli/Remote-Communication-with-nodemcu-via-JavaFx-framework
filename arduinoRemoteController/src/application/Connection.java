/* this class is all about the connection.
   This class and also methods are responsible 
   to establish a connection between sever and 
   client application */


package application;


import java.net.Socket;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Connection {
	
	public Socket socket = null;
	Alert alert = new Alert (AlertType.INFORMATION);
	public String server_ip = "";
	public int server_port = 5000;
	
	// this method is used for establishing connection between "server" and "client"
	
	public Socket establishConnection () {
		
		if(socket == null ) {
		
		try {
			socket = new Socket("192.168.31.176", 5000);     //server ip address and port at which the process is operated
			System.out.println("Connected");
			if(socket == null) {
				alert.setTitle("Connection Details");
				alert.setHeaderText("Connection Error");
				alert.setContentText("Please check your ip address");
				alert.show();
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
	}

		return socket;
  }

}