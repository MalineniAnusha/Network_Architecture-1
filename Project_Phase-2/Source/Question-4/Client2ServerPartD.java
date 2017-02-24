import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2D implements Runnable {

  private static Socket c = null;
  private static PrintStream os = null;
  private static DataInputStream is = null;

  private static BufferedReader inputLine = null;
  private static boolean clClosed = false;
  
  public static void main(String[] args) {

    int portNumber = 2356;
    String host = "Server.NA-Project2.ch-geni-net.instageni.umkc.edu";

    try {
      c = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      os = new PrintStream(c.getOutputStream());
      is = new DataInputStream(c.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }

   
    if (c != null && os != null && is != null) {
      try {

        new Thread(new Client2D()).start();
        while (!clClosed) {
          os.println(inputLine.readLine().trim());
        }
        os.close();
        is.close();
        c.close();
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }
  }

  @SuppressWarnings("deprecation")
  public void run() {
    String responseLine;
    try {
    	BufferedReader k
        = new BufferedReader(new InputStreamReader(is));
    	
    	    	
      while ((responseLine = k.readLine()) != null) {
        System.out.println(responseLine);
        if (responseLine.indexOf("*** Bye") != -1)
          break;
      }
      clClosed = true;
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}

