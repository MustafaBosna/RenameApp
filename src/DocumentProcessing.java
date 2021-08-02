import java.io.File;
import java.io.IOException;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class DocumentProcessing {
	
	static String OLD_FILE_NAME = null;

	public static void ChangeFileName(String CurrentName) {
		/* Change PDF Name to Prepared Format */

		String FormatXMLName = null;
		String FormatPDFName = null;
		String workingDir = "C:\\work\\mXML\\Work_Dir";

		File f = new File(workingDir);
		File[] list = f.listFiles();

		if (list == null) {
			return;
		}

		for (File entry : list) {
			String Exstension = "";

			Exstension = getFileExtension(entry.getAbsolutePath());
			

			// IF Exstension is empty -> watch in directory
			if (Exstension == "") { // Check Exstension

				if (entry.isDirectory()) {
					File Directory = new File(entry.getAbsolutePath()); // Find Directory with Path
					File[] Directory_list = Directory.listFiles(); // List

					if (Directory_list == null) {
						return;
					}

					for (File Dir_entry : Directory_list) {
						// Variables
						String Dir_Ext = getFileExtension(Dir_entry.getAbsolutePath());
						// Change PDF Name to Custom Format
						if (Dir_Ext.equals("pdf")) {
							// New PDF File Name
							String New_FileName = ChangePDFName_toCustomFormat(Dir_entry.getName(),
									Dir_entry.getAbsolutePath());
							
							// Path to current Folder
							String PathToFolder = MainOldPath(Dir_entry.getName(), workingDir);
							
							// Prepare New PDF Path and FileName for rename action
							String New_FileNameWithPath = PathToFolder + "\\" + New_FileName;

							String ChangePDFName = RenameFilesInDirectory(Dir_entry.getAbsolutePath(),
									New_FileNameWithPath);
						}
					}
				}
			}
			// Working on XML Rename and Change Path inside XML
			if (Exstension.equals("xml")) {
				// Change XML FileName
				String XML_Path = entry.getAbsolutePath();
				String PathToFolder = MainOldPath(entry.getName(), workingDir);
				String XML_Result = ProcessingXMLFile(PathToFolder,workingDir);
				
				// Create Class/Object from EditXMLFile.java
				if(XML_Result.equals("Ok")) {
					
					// New XML File Name
					String NewXML_FileName = ChangePDFName_toCustomFormat(entry.getName(),
							entry.getAbsolutePath());
					
					// Path to XML File
					String PathToXMLFile = workingDir + "\\" + NewXML_FileName;

					
					EditXMLFile EditingXML_File = new EditXMLFile(); // EditXMLFile.java
					EditingXML_File.Edit(PathToXMLFile,workingDir,OLD_FILE_NAME);
					
					//Rename Directory 
					String Directory_Status = Rename_Directory(NewXML_FileName,OLD_FILE_NAME); 
						
					
				}
		
			}
		}
	}

	private static String ProcessingXMLFile(String Path, String workingDir) {
		
		String result = "";
		String XML_Path = Path.toString(); 
		// Read XML File Name
		String XML_File_OldName = new File(XML_Path).getName();
		OLD_FILE_NAME = XML_File_OldName;
		// Create new XML Format Name
		String New_FileName = ChangePDFName_toCustomFormat(XML_File_OldName,workingDir);
		// Rename XML File in Direcotry
		/*
		 * @Path --> OLD XML PATH 
		 * @New_XML_Path --> NEW XML PATH
		 */
		String New_XML_Path = workingDir+"\\"+New_FileName;
		
		String RenameAction_XML = RenameFilesInDirectory(Path,New_XML_Path);
		
		if(RenameAction_XML.equals(New_XML_Path)) {
			System.out.println("Rename action on XML File is successfully") ;
			result="Ok";
		}
		
		
		return result;
	}

	private static String MainOldPath(String DirectoryName, String workingDir) {
		// Copy Folder Name for current Document from DocumentName
		String[] FirstDelimiter = DirectoryName.split("__");
		String Main_OldPDFDir = FirstDelimiter[0].toString();
		String Old_Main_Path = workingDir + "\\" + Main_OldPDFDir; 

		return Old_Main_Path;
	}
	
	private static String Rename_Directory(String New_XMLFile, String Old_XMLFile) {
		
		String Final_New_XMLFile = New_XMLFile.replace(".xml","");
		String Final_Old_XMLFile = Old_XMLFile.replace(".xml","");
		
		Path sourceFilePath = Paths.get("C:\\work\\mXML\\Work_Dir\\"+Final_New_XMLFile);
	    Path targetFilePath = Paths.get("C:\\work\\mXML\\Done_Dir\\"+Final_New_XMLFile);
	    
	    File sourceFile = new File("C:\\work\\mXML\\Work_Dir\\"+Final_Old_XMLFile);
	    File sourceFilePath_Delete = new File("C:\\work\\mXML\\Work_Dir\\"+Final_New_XMLFile+".xml");
		File destFile = new File("C:\\work\\mXML\\Done_Dir\\"+Final_New_XMLFile);
		
		if(sourceFile.renameTo(destFile)){
			System.out.println("File Directory successfully renamed");
		} else {
		    System.out.println("Failed to rename Directory");
		}
		
		if(sourceFilePath_Delete.delete()) {
			System.out.println("File deleted successfully from Work_Dir Direcotry");
		}else
        {
            System.out.println("Failed deleted from Work_Dir Direcotry");
        }


	    return New_XMLFile;
	}


	private static String getFileExtension(String fullName) {
		// Function for checking exstension type
		String fileName = new File(fullName).getName();
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}

	private static String ChangePDFName_toCustomFormat(String CurrentName, String Path) {
		String newName = null;
		// Convert from format: 6-1-15_096-0-Reg-14-001138 ==>
		// 96-06-0001-15_096-0-Reg-14-001138

		String[] FirstDelimiter = CurrentName.split("_");
		String Old_MBS = FirstDelimiter[0].toString();
		String MBS_Limiter = "-"; // Limiter for MBS " - ";
		String[] MBS_LimiterResult = Old_MBS.split(MBS_Limiter);
		String New_MBS = "96-"; // Only ** MBS Here is static Value 96- **
		String New_MBS_FileName = ""; // MBS With PATH

		for (int i = 0; i < MBS_LimiterResult.length; i++) {

			String MBS_Control = MBS_LimiterResult[i].toString();

			int MBS_String_length = MBS_Control.length();

			switch (i) {
			// First MBS Number
			case 0:
				if (MBS_String_length == 1) {
					New_MBS += "0" + MBS_Control;
				}

				if (MBS_String_length == 2) {
					New_MBS += MBS_Control;
				}

				break;
			// Second MBS Number
			case 1:

				if (MBS_String_length == 1) {
					New_MBS += "-000" + MBS_Control;
				}
				if (MBS_String_length == 2) {
					New_MBS += "-00" + MBS_Control;
				}
				if (MBS_String_length == 3) {
					New_MBS += "-0" + MBS_Control;
				}
				if (MBS_String_length == 4) {
					New_MBS += "-" + MBS_Control;
				}
				break;

			// Third MBS number
			case 2:
				if (MBS_String_length == 1) {
					New_MBS += "-0" + MBS_Control;
				}

				if (MBS_String_length == 2) {
					New_MBS += "-" + MBS_Control;
				}
				break;

			}

		}
		// Complete New MBS and Relative Path read old Relative Path

		New_MBS_FileName += New_MBS + "_";

		for (int i = 1; i < FirstDelimiter.length; i++) {

			if (i == (FirstDelimiter.length - 1)) {
				New_MBS_FileName += FirstDelimiter[i].toString();
			} else {
				New_MBS_FileName += FirstDelimiter[i].toString() + "_";
			}
		}

		return New_MBS_FileName;
	}

	private static String RenameFilesInDirectory(String OldPath, String NewPath) {
		if (!OldPath.equals("") && !NewPath.equals("")) {

			File OLD_PDF = new File(OldPath);
			File NEW_PDF = new File(NewPath);

			if (OLD_PDF.exists()) {
				System.out.println("OLD_PDF not exists");
			}

			if (NEW_PDF.exists()) {
				System.out.println("NEW_PDF not exists");
			}

			boolean success_Rename = OLD_PDF.renameTo(NEW_PDF);

			if (!success_Rename) {
				System.out.println("Error in: ChangePDFName_toCustomFormat, Rename action is not correctly");
			}
			if (success_Rename) {
				System.out.println("Rename action is done !");
			}

		}
		return NewPath;

	}
}
