// import statements
 import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
 public class Server
 {
     //initializing input stream and socket
     private DataInputStream clientToServerInStream = null;
     private DataOutputStream serverToClientDataOut  = null;
     private Socket skt = null;
     private ServerSocket srvr = null;
     private ArrayList<String> networkInformationForXML = new ArrayList<String>();
     // constructor of the class Server
     public Server(int port) throws ParserConfigurationException, TransformerException
     {
         // Starting the server and waiting for a client 
         try
         {
             srvr = new ServerSocket(port);
             System.out.println("Server starts");
             System.out.println("Waiting for a client to connect ... ");  
             skt = srvr.accept(); // waiting for  a client to send connection request
             System.out.println("Connected with a Client!! ");
             // Receiving input messages from the client using socket
             clientToServerInStream = new DataInputStream( skt.getInputStream() );
             serverToClientDataOut = new DataOutputStream(skt.getOutputStream());
             String str = ""; // variable for reading messages sent by the client
             // Until "Finish" is sent by the client,
             // keep reading messages
             while (!str.equalsIgnoreCase("Finish"))
             {
                 try
                 {
                     // reading from the underlying stream
                     str = clientToServerInStream.readUTF();
                     
                     if(str.equalsIgnoreCase("network")) {
                    	 String response = "in network";
                    	 serverToClientDataOut.writeUTF(response); // writing to the underlying output stream
                         
                    	 String i = clientToServerInStream.readUTF(); //Reads response
                    	 serverToClientDataOut.writeUTF("Received time interval as: "+i); // writes keyword: finish
                    	 
                    	 long timeInterval = Long.parseLong(i);
                    	 //timeInterval+=500;
                		 
                    	 
                    	 //Resets file
                    	 String writePath = "finalFile.xml";
                    	 File writeFile = new File(writePath);
                    	 FileWriter fw2 = new FileWriter(writeFile); //No true, so in 'w' mode
                    	 BufferedWriter fileResetter = new BufferedWriter(fw2);
                    	 fileResetter.write("");
                    	 fileResetter.close();
                    	 //end reset file
                    	 
                    	//Setting up timing
                    	 Object obj = new Object();
                    	 synchronized(obj) {
                    		 while(str.equalsIgnoreCase("Stop")!=true) {
                    			 try {
                    				 obj.wait(timeInterval);
                    			 } catch (InterruptedException e) {
                    				 // TODO Auto-generated catch block
                    				 e.printStackTrace();
                    			 }	
                    			 str = clientToServerInStream.readUTF();
                        		 if(str.equalsIgnoreCase("Stop")!=true) {
                        			 String [] arr = str.split("\n");
                        			 appendToList(arr);
                        			 //Make xml file
                        			 //XMLGenerator generator = new XMLGenerator(arr);
                        			 
                        			 
                        			 /*
                        			 FileWriter appenderWriter = new FileWriter(writeFile, true); //Is true, so in 'A' mode
                        			 BufferedWriter appender = new BufferedWriter(appenderWriter);
                        			 String readPath = "networkInfo.xml";
                        			 File readFile = new File(readPath);
                        			 Scanner scanner = new Scanner(readFile);
                        			 while(scanner.hasNextLine()) {
                        				 appender.write(scanner.nextLine());
                        			 }
                        			 scanner.close();
                        			 appender.close();
                        			 */
                        			 System.out.println("here");
                        			 serverToClientDataOut.writeUTF("appended"); // writing back to client
                        		 }
                    		 }
                    	 }
                    		 FileWriter appenderWriter = new FileWriter(writeFile, true); //Is true, so in 'A' mode
                			 BufferedWriter appender = new BufferedWriter(appenderWriter);
                    		 //XMLGenerator generator = new XMLGenerator(arr);
                    		 XMLGenerator generator = new XMLGenerator(networkInformationForXML);
                    		 String readPath = "networkInfo.xml";
                			 File readFile = new File(readPath);
                			 Scanner scanner = new Scanner(readFile);
                			 while(scanner.hasNextLine()) {
                				 appender.write(scanner.nextLine());
                			 }
                			 scanner.close();
                			 appender.close();
                    		 System.out.println("exitted");
                    	 
                    	 
                    		 
                    	 
                    	 serverToClientDataOut.writeUTF("Writing stopped."); // writing back to client
                    	 
                    	
                    	 
                    	 
                     }
                     else {
                    	 String received = "received line: " + str;
                    	 serverToClientDataOut.writeUTF(received); // writing to the underlying output stream
                    	 // printing the read message on the console
                    	 System.out.println(str);                    	 
                     }
                 }
                // For handling errors
                 catch(IOException io)
                 {
                     System.out.println( io );
                 }
             }
             // closing the established connection
             skt.close();
             clientToServerInStream.close();
             serverToClientDataOut.close();
             System.out.println(" Connection Closed!! ");
         }
         // handling errors
         catch(IOException i)
         {
             System.out.println(i);
         }
     }
     public void appendToList(String[] arr) {
		// TODO Auto-generated method stub
		for(String s:arr) {
			networkInformationForXML.add(s);
		}
		networkInformationForXML.add("INSERTNEXTVALUE!!!");
	}
	public static void main(String argvs[]) throws ParserConfigurationException, TransformerException
     {
         // creating an object of the class ServerSide
         Server server = new Server(5000);
     }
 } 