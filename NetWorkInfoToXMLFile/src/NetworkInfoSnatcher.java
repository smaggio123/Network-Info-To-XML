import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NetworkInfoSnatcher{

	ArrayList<String> list = new ArrayList<String>();
	String [] keyWords = {"band","channel","radio type","Receiverate(Mbps)","Transmitrate(Mbps)","physical address","ssid","bssid"};
	
	public NetworkInfoSnatcher() {
	    String command="netsh wlan show interface";
	    try {
	        Process process = Runtime.getRuntime().exec(command);
	        BufferedReader reader=new BufferedReader( new InputStreamReader(process.getInputStream()));
	        String s; 
	        //System.out.println("The input stream is: ");
	        while ((s = reader.readLine()) != null){
	        	s=s.replaceAll(" ", "");
	        	
	            //Splits the string by the first occurrence of ':'
	        	String [] arr =s.split(":", 2);
	            //System.out.println("0: "+arr[0]);
	            //if(arr.length==2) System.out.println("1: "+arr[1]);
	            if(iskeyInfo(arr[0])) {
	            	appendToList(arr);
	            	//list.add(s);
	            }
	        }                   
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    
	}

	public void appendToList(String [] arr) {
		for(String s: arr) {
			list.add(s);
		}
	}
	
	public boolean iskeyInfo(String str) {
		//System.out.println("word is: "+str);
		str = str.replaceAll(" ","");
		for(String s:keyWords) {
			//System.out.println("Key word is: "+s);
			//System.out.println();
			if(str.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
	
	public void writeToStream(DataOutputStream dos) throws IOException {
		String str = listToString();
		dos.writeUTF(str);
	}
	
	public String listToString() {
		String str = "";
		
		for(int i = 0; i<list.size(); i++) {
			str+=list.get(i);
			if(i<list.size()-1) {
				str+="\n";
			}
		}
		return str;
	}
	
}