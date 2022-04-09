import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class workSpace {
	
	
	public static void other() throws IOException {
		/*
		 
            		 double intervalTimeInMilliseconds = 1000;
          			int runCount = 5;
          			double totalTimeLapseInMilliseconds = intervalTimeInMilliseconds*runCount;
          			double currentTime = 0;
            		 String writePath = "finalFile.xml";
            			File writeFile = new File(writePath);
            			
            			FileWriter fw2 = new FileWriter(writeFile);
            		    BufferedWriter bw2 = new BufferedWriter(fw2);
            		    bw2.write("");
            		    bw2.close();
            			
            			FileWriter fw = new FileWriter(writeFile, true);
            		    BufferedWriter bw = new BufferedWriter(fw);
            		    
            		    
            			Object obj = new Object();
            			synchronized(obj) {
            				while(currentTime<totalTimeLapseInMilliseconds) {
            					try {
            						obj.wait(1000);
            						currentTime += intervalTimeInMilliseconds;
            					} catch (InterruptedException e) {
            						// TODO Auto-generated catch block
            						e.printStackTrace();
            					}
            					String readPath = "networkInfo.xml";
            					File readFile = new File(readPath);
            					Scanner scanner = new Scanner(readFile);
            					while(scanner.hasNextLine()) {
            						bw.write(scanner.nextLine());
            					}
            					scanner.close();
            					System.out.println("here");
            					
            				}			
            			}
            			
            			
            			bw.close();
		 */
	}
}