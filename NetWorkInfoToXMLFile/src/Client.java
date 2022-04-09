 // import statements
 import java.net.*;
 import java.io.*;
 import java.util.Scanner;
 public class Client
 {
     // initializing socket and input output streams
     private DataOutputStream clientToServerDataOut  = null;	//Like a pipe in C
     private DataInputStream serverToClientInStream = null;		//Like a pipe in C
     private Scanner sc                = null;
     private Socket skt                = null;
     private final long MINUTE	=	60000;
     // constructor to create a socket with given IP and port address
     public Client(String address, int port)
     {
         // Establishing connection with server
         try
         {
              // creating an object of socket
             skt = new Socket(address, port);
             System.out.println("Connection Established!! ");
             System.out.println("input \"Finish\" to terminate the connection. ");
             System.out.println("Network is key word");
             // taking input from user
             sc = new Scanner(System.in);
             // opening output stream on the socket
             clientToServerDataOut = new DataOutputStream(skt.getOutputStream());
             serverToClientInStream = new DataInputStream( skt.getInputStream() );
         }
         catch(UnknownHostException uh)
         {
             System.out.println(uh);
         }
         catch(IOException io)
         {
             System.out.println(io);
         }
         // to store the input messages given by the user
         String str = "";
         // The reading continues until "Finish" is input
         while (!str.equalsIgnoreCase("Finish"))
         {
             try
             {
            	 str = sc.nextLine();
            	 if(str.equalsIgnoreCase("network")) {
            		 //60000 = 1 minute
            		long intervalTimeInMilliseconds = MINUTE/8;
         			int runCount = 2;
         			double totalTimeLapseInMilliseconds = intervalTimeInMilliseconds*runCount;
         			double currentTime = 0;
            		 
            		 clientToServerDataOut.writeUTF(str); // writes keyword: finish
            		 String answer = serverToClientInStream.readUTF(); //Reads response
            		 System.out.println(answer); //prints response
            		 
            		 clientToServerDataOut.writeUTF(""+intervalTimeInMilliseconds); // writes keyword: finish
            		 serverToClientInStream.readUTF(); //Reads response
            		 System.out.println(answer); //prints response
            		 
            		 
            		 //Setting up timing
        			Object obj = new Object();
        			synchronized(obj) {
        				while(currentTime<totalTimeLapseInMilliseconds) {
        					try {
        						obj.wait(intervalTimeInMilliseconds);
        						currentTime += intervalTimeInMilliseconds;
        					} catch (InterruptedException e) {
        						// TODO Auto-generated catch block
        						e.printStackTrace();
        					}
        					 NetworkInfoSnatcher snatcher = new NetworkInfoSnatcher();
	                   		 snatcher.writeToStream(clientToServerDataOut);
	                   		 answer = serverToClientInStream.readUTF();
	                   		 System.out.println(answer);
	                   		 if(currentTime>=totalTimeLapseInMilliseconds) {
	                   			 clientToServerDataOut.writeUTF("stop"); // tells server to stop listening for network info	                   			 
	                   		 }
        					
        				}			
        			}
        			
        			answer = serverToClientInStream.readUTF();
        			System.out.println(answer);
        			
        			
            			
            	 }
            	 else {
            		 clientToServerDataOut.writeUTF(str); // writing to the underlying output stream
            		 // reading from the underlying stream
            		 String answer = serverToClientInStream.readUTF();
            		 System.out.println(answer); 		 
            	 }
             }
             // For handling errors while writing to output stream
             catch(IOException io)
             {
                 System.out.println(io);
             }
         }
         System.out.println(" Connection Terminated!! ");
         // for closing the connection
         try
         {
        	 clientToServerDataOut.close();
        	 serverToClientInStream.close();
             skt.close();
         }
         catch(IOException io)
         {
             System.out.println(io);
         }
     }
     public static void main(String args[]) throws IOException
     {
    	// creating object of class Client
         Client client = new Client("127.0.0.1", 5000);       
     }
 }