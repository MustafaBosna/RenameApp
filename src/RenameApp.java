import java.util.*;
import java.io.File;

public class RenameApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		File Impor_File = new File("C:\\work\\mXML\\Test1.txt");
		File Impor_File_2 = new File("C:\\work\\mXML\\Test2.txt");
		
		boolean b = Impor_File.renameTo(Impor_File_2);
		
		if(b == true) {
			//System.out.println("Succsesifulyy changed !! ") ;
		}
		
		// Import All files from hotfolder
		ImportAllFiles Import_Files = new ImportAllFiles();
		
		Import_Files.listDir("C:\\work\\mXML"); 
		
		

	}

}
