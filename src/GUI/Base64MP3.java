package GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Base64;

// Clase para convertir un mp3 en string por base64.
public class Base64MP3 {
	
// Nombre de la cancion.
	public static String name;
  
// Recibe el directorio de la cancion y retorna un string.  
	public static String encodeToB64(String dir) {

	  Base64.Encoder enc1 = Base64.getEncoder();
	  byte[] bFile = readBytesFromFile(dir);
	  
	  String os1 = enc1.encodeToString(bFile);
	  System.out.println(os1.length());
	  
	  String[] array = dir.split("/");
	  String temp = array[array.length-1];
	  name = temp.substring(0, temp.length()-5);
	  
	  return os1;		  
   }
   
   private static byte[] readBytesFromFile(String filePath) {

       FileInputStream fileInputStream = null;
       byte[] bytesArray = null;

       try {

           File file = new File(filePath);
           bytesArray = new byte[(int) file.length()];

           //read file into bytes[]
           fileInputStream = new FileInputStream(file);
           fileInputStream.read(bytesArray);

       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (fileInputStream != null) {
               try {
                   fileInputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

       }
	return bytesArray;
   }
}
