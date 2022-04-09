//package com.mkyong.xml.dom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLGenerator{

	
  public XMLGenerator(String[]arr)
          throws ParserConfigurationException, TransformerException {

      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      // root elements
      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("network_info");
      doc.appendChild(rootElement);
      
      
      
      //arr is the array containing the information
      for(int index=0;index<arr.length;index++) {
    	  Element category = doc.createElement("category");
    	  rootElement.appendChild(category);
    	  //id is category type. ex: SSID 
    	  category.setAttribute("id", arr[index]);
    	  index++;
    	  //text content is value. ex: wifi_name
    	  category.setTextContent(arr[index]);
      }
      try {
    	  String filePath = "networkInfo.xml";
    	  File file = new File(filePath);
    	  FileOutputStream output =new FileOutputStream(file);
    	  writeXml(doc, output);
    	  output.close();
	 } catch (IOException e) {
	     e.printStackTrace();
	 }
      
  }
  public XMLGenerator(ArrayList<String> list)
		  throws ParserConfigurationException, TransformerException {
	  
	  DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	  DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	  // root elements
	  Document doc = docBuilder.newDocument();
	  Element rootElement = doc.createElement("network_info");
	  doc.appendChild(rootElement);
	  
	  
	  int idNumber=1;
	  //arr is the array containing the information
	  Element firstCategory = doc.createElement("category");
	  rootElement.appendChild(firstCategory);
	  firstCategory.setAttribute("id", "run:");
	  firstCategory.setTextContent(""+idNumber);
	  idNumber++;
	  for(int index=0;index<list.size();index++) {
		  Element category = doc.createElement("category");
		  rootElement.appendChild(category);
		  String id = list.get(index);
		  if(!id.equalsIgnoreCase("INSERTNEXTVALUE!!!")) {
			  //id is category type. ex: SSID 
			  category.setAttribute("id", list.get(index));
			  index++;
			  //text content is value. ex: wifi_name
			  category.setTextContent(list.get(index));			  
		  }
		  else {
			  if(index+1<list.size()) {
				  Element category2 = doc.createElement("category");
				  rootElement.appendChild(category2);
				  category2.setAttribute("id", "run:");
				  category2.setTextContent(""+idNumber);
				  idNumber++;				  
			  }
		  }
	  }
	  try {
		  String filePath = "networkInfo.xml";
		  File file = new File(filePath);
		  FileOutputStream output =new FileOutputStream(file);
		  writeXml(doc, output);
		  output.close();
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	  
  }

  private static void writeXml(Document doc,
          OutputStream output)
throws TransformerException {

TransformerFactory transformerFactory = TransformerFactory.newInstance();
Transformer transformer = transformerFactory.newTransformer();
DOMSource source = new DOMSource(doc);
StreamResult result = new StreamResult(output);

transformer.transform(source, result);

}
  
}