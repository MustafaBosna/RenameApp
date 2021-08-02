import java.util.*;
import java.io.File;
import java.io.IOException;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

// {}
public class ImportAllFiles {
	
	public static void listDir(String dir) {
		// Variables
		
		// Import Class
		List DirectoryList = new ArrayList();
		Move_Files Move_FirstFilter = new Move_Files(); // Move_Files.java
		DocumentProcessing Processing = new DocumentProcessing(); // DocumentProcessing.java

		
		
		File f = new File(dir);
		File[] list = f.listFiles();
		
		  if (list == null) {
	            return;
	        }
		  
		  for (File entry : list) {
			  
			  
			  
	            if (entry.isDirectory()) {
	            	// entry.move("C:\\work\\mXML","C:\\work\\mXML\\Work_Dir");
	            	
	            	if(
	            		!entry.getName().equals("Work_Dir") &&
	            		!entry.getName().equals("Error_Dir") &&
	            		!entry.getName().equals("Temp_Dir") &&
	            		!entry.getName().equals("Done_Dir")
	            	){
	            		// DirectoryList.add(entry) ;
	            		String CurrentFolderName =entry.getName().toString();
	            		Move_FirstFilter.MoveFiles_FirstFilter(CurrentFolderName); // Transfer Files to Working folder
	            		Processing.ChangeFileName(CurrentFolderName); // ChangeFilesName to prepared format
	            		
	            		 
	            		
	            	}
	       	
	            }
	     } 
	}
	
}
