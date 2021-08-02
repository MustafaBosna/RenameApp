
import java.io.File;
import java.io.IOException;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Move_Files {
	
	public static void MoveFiles_FirstFilter(String CurrentName) {
		/*Move Files from mXML -> Work_Dir (Included XML and Directory)*/
		
		String RelativePath = "C:\\work\\mXML\\"; 
		String WorkPath = "C:\\work\\mXML\\Work_Dir\\"; 
		String exstension = ".xml" ;
		boolean ErrorMove = false;
		
		
		// Source part
		String SourceFile_Value = RelativePath +CurrentName+".xml".toString();
		String SourceDirectory = RelativePath +CurrentName.toString();
		// Destination
		String DestinationFile_Value = WorkPath +CurrentName+".xml".toString();
		String DestinationDirectory = WorkPath +CurrentName.toString();
		
		//System.out.println(SourceFile_Value);
		//System.out.println(DestinationFile_Value);
		
		File SourceFile = new File(SourceFile_Value);
		File File_SourceDirectory = new File(SourceDirectory);
		File DestinationFile = new File (DestinationFile_Value);
		File File_DestinationDirectory = new File (DestinationDirectory);
		
		try {
			Files.move(SourceFile.toPath(), DestinationFile.toPath(), REPLACE_EXISTING); // Transfer XML File
			Files.move(File_SourceDirectory.toPath(), File_DestinationDirectory.toPath(), REPLACE_EXISTING); // Transfer Directory
			
			
			
		} catch (IOException e) {
			ErrorMove = true;
			e.printStackTrace();
		} 
		
		if(ErrorMove != true) {
			File DeleteFile = new File(SourceFile_Value); 
			if(DeleteFile.delete()) {
				//System.out.println("File location: " + SourceFile_Value);
				//System.out.println("File is successfully deleted !! ");
			}
		}
		
	}
	
	

}
