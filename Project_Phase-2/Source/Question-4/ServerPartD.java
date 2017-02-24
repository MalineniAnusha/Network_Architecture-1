import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;

public class Server2D{

  private static ServerSocket soc = null;
  
  private static Socket csoc = null;

  // Client count can accept maximum 10
  private static final int maxCount;
  private static final threadpgm[] threadpgms = new threadpgm[maxCount];

  public static void main(String args[]) {
	System.out.println("Server Started");
    
    int portNumber = 2356;
    
    try {
      soc = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }

    //connection socket for each client
    while (true) {
      try {
        csoc = soc.accept();
        int i = 0;
        for (i = 0; i < maxCount; i++) {
          if (threadpgms[i] == null) {
            (threadpgms[i] = new threadpgm(csoc, threadpgms)).start();
            break;
          }
        }
        if (i == maxCount) {
          PrintStream output = new PrintStream(csoc.getOutputStream());
                    output.close();
          csoc.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}

//threadpgms to accept the clients
class threadpgm extends Thread {

  private String clientName = null;
  private DataInputStream inputStream = null;
  private PrintStream outStream = null;
  private Socket csoc = null;
  private final threadpgm[] threadpgms;
  private int maxCount;

  public threadpgm(Socket csoc, threadpgm[] threadpgms) {
    this.csoc = csoc;
    this.threadpgms = threadpgms;
    maxCount = threadpgms.length;
  }

  @SuppressWarnings("deprecation")
  public void run() {
    int maxCount = this.maxCount;
    threadpgm[] threadpgms = this.threadpgms;

    try {
    	inputStream = new DataInputStream(csoc.getInputStream());
      outStream = new PrintStream(csoc.getOutputStream());
      String name;
      while (true) {
        outStream.println("Enter Name");
        BufferedReader d
        = new BufferedReader(new InputStreamReader(inputStream));
        name = d.readLine().trim();
        if (name.indexOf('@') == -1) {
          break;
        }
      }

      synchronized (this) {
        for (int i = 0; i < maxCount; i++) {
          if (threadpgms[i] != null && threadpgms[i] == this) {
            clientName = "Whisper" + name;
            break;
          }
        }
      }
      while (true) {
    	  
    	  BufferedReader d
          = new BufferedReader(new InputStreamReader(inputStream));
    	  
        String line = d.readLine();
		System.out.println(name + ":" + line);
        if (line.startsWith("exit")) {
          break;
        }
        if (line.startsWith("Whisper")) {
          String[] words = line.split("\\s", 2);
          if (words.length > 1 && words[1] != null) {
            words[1] = words[1].trim();
            if (!words[1].isEmpty()) {
              synchronized (this) {
                for (int i = 0; i < maxCount; i++) {
                  if (threadpgms[i] != null && threadpgms[i] != this
                      && threadpgms[i].clientName != null
                      && threadpgms[i].clientName.equals(words[0])) {
                    threadpgms[i].outStream.println(name + ": " + words[1]);
                    this.outStream.println(name + ": " + words[1]);
                    break;
                  }
                }
              }
            }
          }
        } else {
          synchronized (this) {
            for (int i = 0; i < maxCount; i++) {
              if (threadpgms[i] != null && threadpgms[i].clientName != null) {
                threadpgms[i].outStream.println(name + ": " + line);
              }
            }
          }
        }
      }
      synchronized (this) {
        for (int i = 0; i < maxCount; i++) {
          if (threadpgms[i] != null && threadpgms[i] != this
              && threadpgms[i].clientName != null) {
		     System.out.println( name + "left");
          }
        }
      }
      outStream.println( name + "left");
      synchronized (this) {
        for (int i = 0; i < maxCount; i++) {
          if (threadpgms[i] == this) {
            threadpgms[i] = null;
          }
        }
      }
      inputStream.close();
      outStream.close();
      csoc.close();
    } catch (IOException e) {
    }
  }
}

