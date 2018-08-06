package application;



import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.management.timer.Timer;
import javax.naming.InitialContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.Alert.AlertType;

public class MainPanelController implements Initializable {

	@FXML
	private Button connectbtn;
	
	@FXML
	private Button disconnectbtn;
	
	@FXML
	private Button lightOnbtn;
	
	@FXML
	private Button lightOffbtn;
	
	@FXML
	private Button monitorbtn;
	
	
	
	@FXML
	private TextField celLbl;                              //Lebel to show the temperature data in deg celcius unit
	
	@FXML
	private TextField farLbl;                              //Lebel to show the temperature data in Farenheit unit
	
	@FXML
	public TextField resistanceLbl;
	
	@FXML
	private ImageView connectionStatus;                   //Image for showing the connection status
	
	@FXML
	private ImageView lightOnStatus;                      //Image for showing the Light On status
	
	@FXML
	private ImageView lightOffStatus;                     //Image for showing the Light Off status
	 
	@FXML
	private ImageView tempOkCondition;                    //Image for showing the best temperature condition
	
	@FXML
	private ImageView tempNeedAction;                     //Image for showing the tepertaure condition when some action should be needed
	
	@FXML
	private ImageView tempForceOff;                       //Image for showing the condition when the burner needs forcefully shutdown
	
	@FXML
	private ImageView resistanceOk;                       //Image for showing the OK condition of measuring resistance
	
	@FXML
	private ImageView resistanceNeedAction;               //Image for showing the condition when some action should be needed
	
	@FXML
	private RadioButton radioStop;
	
	@FXML
	private TextArea txtarea;
	
	@FXML
	private LineChart<Number, Number> resistanceChart;
	
	@FXML
	private LineChart<Number, Number> tempChart;
	
	@FXML
	private NumberAxis xAxis;
	
	@FXML
	private NumberAxis yAxis;
	
	@FXML
	private NumberAxis xAxis1;
	
	@FXML
	private NumberAxis yAxis1;
	
	
	Connection connection = new Connection();            	//here we make an object of the "Connection" class to call its methods 
	SendData send_data = new SendData();                 	//here we make an object of "SendData" class to call its methods
	ReceiveData receive_data = new ReceiveData();        	//here we make an object of "ReceiveData" class to call its methods
	ArrayList<String> arrayData = new ArrayList<String>();
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 
	private DataInputStream input = null;
	Alert alert = new Alert (AlertType.INFORMATION);        // making an alert window for information purpose
	public Socket socket = null;
	
	
	XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
	 
	
	
	@FXML
	public void connection (ActionEvent event) {
		    
		    Image connectionImage = new Image("/ing/connected.png");      //setting-up an image which shows that the application is connected or not with the server-side
		    Image disconnectedImage = new Image("/ing/disconnected.png"); //setting-up an image which shows in the imageView when the application is not connected with server-side
		    connectionStatus.setImage(disconnectedImage);
		    socket = connection.establishConnection();              //here we call the "establishConnection" method of "Connection" class
		    if(socket != null) {
		    	connectionStatus.setImage(connectionImage);
		    	connectbtn.setStyle("-fx-background-color: Green");
		    	
		    	
		    } else {
		    	alert.setTitle("Connection Status");
		    	alert.setHeaderText("Message about connection");
		    	alert.setContentText("Not connected");
		    	alert.show();
		    }
		    
	}
	
	// to turn the light on
	
	@FXML
	 public void lightOn (ActionEvent event) {
			String onStatus = "";
			Image lightStstus = new Image("/ing/lightOn.png");
			onStatus = send_data.send(socket, 1);                   //when we click the lightOnbtn then it sends "1" to on the light and also the connection "socket" as an asrgument
			System.out.println(onStatus);
			if (onStatus.equals("HIGH")) {
				lightOffStatus.setImage(null);
				lightOnStatus.setImage(lightStstus);
				lightOffbtn.setStyle("-fx-background-color: White");
				lightOnbtn.setStyle("-fx-background-color: Green");
			} else {
			 alert.setTitle("Status");
			 alert.setHeaderText("Error");
			 alert.setContentText("Server is not responded, please connect your panel with server or check your ip address");
			 alert.show();
		 
	 } }
	 
	 
	// to turn the light off
	 
	 @FXML
	 public void lightOff (ActionEvent event) {
		 //if (connection.socket != null) {
		     String offStatus = "";
		     Image lightStatus = new Image("/ing/lightOff.png");
			 offStatus = send_data.send(socket, 2);                           // when we click the lightOffbtn it sends "0" to off the light
			 if (offStatus.equals("LOW")) {
				 lightOnStatus.setImage(null);
				 lightOffStatus.setImage(lightStatus);
				 lightOnbtn.setStyle("-fx-background-color: White");
				 lightOffbtn.setStyle("-fx-background-color: red");
			 } else {
			 alert.setTitle("Status");
			 alert.setHeaderText("Error");
			 alert.setContentText("Server is not responded, please connect your panel with server or check your ip address");
			 alert.show();
		 }
	 }
	 
	 
	//to monitor the process
	 
	 @FXML
	 public void monitor (ActionEvent event)  {
		 
		 int data = 0;
		
		
		 
		 String readStatus = "";
		 
		// String receiveData = "";
		 readStatus = send_data.send(socket, 3);
		 System.out.println(readStatus);
		 
		 Thread thread1 = new Thread() {
		
			@Override
			 public void run() {
				                
				          
			 			try {   String receiveData = "";
			 			        double temp_degc = 0, temp_far = 0;
			 			        
			 					input =  new DataInputStream(socket.getInputStream());
			 					br = new BufferedReader(new InputStreamReader(input));
			 					
			 					 int count = 1;
			 					while((receiveData = br.readLine()) != null ) {
			 						
			 						
			 						if(radioStop.isSelected()) {
			 							break;
			 						}
			 						
			 						temp_degc = ((5.0 * (Integer.parseInt(receiveData)) * 100.0) / 1024);
			 						temp_far =  ((temp_degc * 1.8) + 32);        //celcius to farenheit conversion 
			 						celLbl.setText(String.valueOf(temp_degc));
			 						farLbl.setText(String.valueOf(temp_far));
			 						
			 			
			 				
			 					   
			 						series.getData().add(new XYChart.Data<Number, Number>(count, temp_degc));
			 						
			 				
			 						count = count + 3;
			 						if(temp_degc <= 60) {
			 							Image okCondition = new Image ("/ing/temperatureOk.png");
			 							tempNeedAction.setImage(null);
			 							tempOkCondition.setImage(okCondition);
			 						}
			 						if(temp_degc > 60) {
			 							Image hazardousCondition = new Image("/ing/highTemp.jpg");
			 							tempOkCondition.setImage(null);
			 							tempNeedAction.setImage(hazardousCondition);
			 						}
			 					}
			 					
			 					
			 					
			 
			
			 				} catch(Exception e) {
			 				  System.out.println(e);
			
			 				}
			 		    
				 	
				 	     }
		 			};
		 					thread1.start();
		 					
		 					
			
			
		 
	 
	 
	 
	 
	 
	 
	 
		 
		 
		 
		    /* double temp_degc = 0, temp_far = 0;
		 
			 temp_degc = ((5.0 * data * 100.0) / 1024);   //calculating the temperature from the incoming sensor value in degC
			 temp_far =  ((temp_degc * 1.8) + 32);        //celcius to farenheit conversion 
			 celLbl.setText(String.valueOf(temp_degc));
			 farLbl.setText(String.valueOf(temp_far));*/
			 
		 
		 
	 


     
	

}
	 
	// To initialize the parameters before starting the application (to do this you have to implement the "Initializable" interface 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		 //resistanceChart.setAnimated(false);
		 //xAxis.setAutoRanging(false);
		 xAxis.setLabel("Time");
		 xAxis.setLowerBound(1);
		 xAxis.setUpperBound(50);
		 yAxis.setLabel("Resistance (ohm)");
		 yAxis.setAutoRanging(false);
		 yAxis.setLowerBound(1);
		 yAxis.setUpperBound(60);
		 xAxis1.setLabel("Time");
		 xAxis1.setLowerBound(1);
		 xAxis1.setUpperBound(100);
		 yAxis1.setLabel("Temperature (deg c)");
		 //yAxis1.setAutoRanging(false);
		 
		 //resistanceChart.getData().add(series);
		 tempChart.getData().add(series);
        
	}

	
  /* @FXML
   public void press(ActionEvent event) {
	   //XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
	   series.getData().add(new XYChart.Data<Number, Number>(1, 20));
       series.getData().add(new XYChart.Data<Number, Number>(2, 100));
       //resistanceChart.getData().add(series);
   }*/
	

	
}
	

