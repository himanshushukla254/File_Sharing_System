/**
 *
 * @author Himanshu Shukla
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.zip.*;

public class SimpleFileServer {

  public final static int SOCKET_PORT = 15123;  // port number can be changed
  public  static String FILE_TO_SEND = "G:/AIB.mp4";  // you may change this

  public static void main (String [] args ) throws IOException {
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    //compression starts.............
    try{
    FileInputStream fin=new FileInputStream(FILE_TO_SEND);

    FileOutputStream fout=new FileOutputStream("G:/def");
    DeflaterOutputStream out=new DeflaterOutputStream(fout);

    int i;
    while((i=fin.read())!=-1){
    out.write((byte)i);
    out.flush();
    }
    fin.close();
    out.close();

    }catch(Exception e){System.out.println(e);}
    //compression done
    FILE_TO_SEND="G:/def";
    try {
      servsock = new ServerSocket(SOCKET_PORT); // object of serversocket class
      while (true) {
        System.out.println("Waiting...");
        try {
          sock = servsock.accept(); //returns socket and establishes the connection b/w client and server 
          System.out.println("Accepted connection : " + sock);
          // send file
          File myFile = new File (FILE_TO_SEND);
          byte [] mybytearray  = new byte [(int)myFile.length()]; //conversion to byte array starts here
          fis = new FileInputStream(myFile);
          bis = new BufferedInputStream(fis);
          bis.read(mybytearray,0,mybytearray.length); //read file in buffer
          os = sock.getOutputStream(); //return o/p stream attached with socket
          System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
          os.write(mybytearray,0,mybytearray.length);
          os.flush();
          System.out.println("Done.");
        }
        finally {
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock!=null) sock.close();
        }
      }
    }
    finally {
      if (servsock != null) servsock.close();
    }
  }
}
