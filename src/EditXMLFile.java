import java.io.File;
import java.io.IOException;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class EditXMLFile {

	private static final Document Document = null;
	

	// Import XML Path
	public static void Edit(String XMLFilePath, String workingDir,String Old_FileName) {

		
		File XML_File = new File(XMLFilePath);
		String CurrentFileName = XML_File.getName();
		String OldFileName = Old_FileName;
 	
		String Final_OldName = OldFileName.replace(".xml",""); 
		String Final_CurrentFileName = CurrentFileName.replace(".xml", ""); 
		

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder;
			
	        try {
	            dBuilder = dbFactory.newDocumentBuilder();

	            // parse xml file and load into document
	            Document doc = dBuilder.parse(XML_File);

	            doc.getDocumentElement().normalize();

	            // update Element value
	            updateElementValue(doc,Final_OldName,Final_CurrentFileName);

	            // delete element
	           // deleteElement(doc);

	            // add new element
	            //addElement(doc);

	            // write the updated document to file or console
	            writeXMLFile(doc,Final_CurrentFileName);

	        } catch (SAXException | ParserConfigurationException | IOException e1) {
	            e1.printStackTrace();
	        }
			
		
		
		
	}

	  public static void writeXMLFile(Document doc, String FileName) {
		// TODO Auto-generated method stub
		  doc.getDocumentElement().normalize();
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(new File("C:\\work\\mXML\\Done_Dir\\"+FileName+".xml"));
	        transformer.setOutputProperty(OutputKeys.INDENT, "no");
	        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
	        try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
	        System.out.println("XML file updated successfully");
		  
		
	}

	/**
     * Update firstName element value to Upper case.
     * @param doc
     */
    public static void updateElementValue(Document doc, String OldName, String NewName) {
        
    	NodeList users = doc.getElementsByTagName("Dokument");
    	NodeList MBS = doc.getElementsByTagName("Predmet");
    	
        Element user = null;
        Element MBS_e = null;

        // loop for each user
        for (int i = 0; i < users.getLength(); i++) {
            user = (Element) users.item(i);
            Node name = user.getElementsByTagName("RelativnaPutanja").item(0).getFirstChild();
            Node name_CB = user.getElementsByTagName("RelativnaPutanjaCB").item(0).getFirstChild();
      
            // Checking Value in Element 
            String RelativePath = name.getTextContent().toString();
            String ReplaceValue = RelativePath.replaceAll(OldName,NewName);
            String ReplaceValue_CB = RelativePath.replaceAll(OldName,NewName);
            //name.setNodeValue(name.getNodeValue().toUpperCase());
           name.setTextContent(ReplaceValue);
           
           System.out.println(">>>>>>>>>>>>>>>>>>>>> ReplaceValue_CB " + ReplaceValue_CB) ;
           System.out.println(">>>>>>>>>>>>>>>>>>>>> ReplaceValue " + ReplaceValue) ;
           name_CB.setTextContent(ReplaceValue_CB.replace("__Color.pdf","__CB.pdf"));
        }
        
        for (int i = 0; i < MBS.getLength(); i++) {
        	// Here we change MBS value in XML
        	MBS_e = (Element) MBS.item(i);
        	Node MBS_Name = MBS_e.getElementsByTagName("MBS").item(0).getFirstChild();
        	
        	String LocalMBS_Name = NewName.toString();
        	String FinalMBS_Name = LocalMBS_Name.split("_")[0];
        	
        	MBS_Name.setTextContent(FinalMBS_Name);
        	
        	if(MBS_Name.getTextContent().equals(FinalMBS_Name)) {
        		System.out.println("MBS is successfully changed");
        	}
        	
        }
        
    }

    	
} 





