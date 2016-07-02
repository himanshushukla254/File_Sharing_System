/**
 *
 * @author Himanshu Shukla
 */

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.io.*;
import java.util.zip.*;

public class SimpleFileClient {

  public final static int SOCKET_PORT = 15123;      // you may change this
  public final static String SERVER = "172.18.7.115";  // IP
  public final static String
       FILE_TO_RECEIVED = "G:/def-downloaded";  

  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded

  public static void main (String [] args ) throws IOException {
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    try {
      sock = new Socket(SERVER, SOCKET_PORT); //make a socket connection to the server
      System.out.println("Connecting...");

      // receive file
      byte [] mybytearray  = new byte [FILE_SIZE]; // byte array conversion
      InputStream is = sock.getInputStream();
      fos = new FileOutputStream(FILE_TO_RECEIVED);
      bos = new BufferedOutputStream(fos);
      bytesRead = is.read(mybytearray,0,mybytearray.length);
      current = bytesRead;

      do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead> -1);

      bos.write(mybytearray, 0 , current);
      bos.flush();
      //fos.flush();
      System.out.println("File " + FILE_TO_RECEIVED
          + " downloaded (" + current + " bytes read)");
      
      ////////////////////////////////////////////////////////
      //decompression starts
   try{  
        FileInputStream fin=new FileInputStream("G:/def-downloaded");  
        InflaterInputStream in=new InflaterInputStream(fin);  
        FileOutputStream fout=new FileOutputStream("G:/client.mp3");  

        int i;  
        while((i=in.read())!=-1){  
        fout.write((byte)i);  
        fout.flush();  
        }  

        fin.close();  
        fout.close();  
        in.close();  

        }catch(Exception e){System.out.println(e);} 
    //decompression ends
    }
    finally {
      if (fos != null) {
          fos.flush();
          fos.close();
      }
      if (bos != null) {bos.close();bos.flush();}
      if (sock != null) sock.close();
    }
    //file recieved
    
  }

}
