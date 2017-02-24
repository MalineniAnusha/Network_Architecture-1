import java.net.*;
import java.io.*;
public class FileTransferFromServer {

	public static void main (String [] args ) throws IOException {
		int filesize=2000000; 
		int bytesRead=0;
		int currentTot = 0;
		String line = null;
		ServerSocket serverSocket = new ServerSocket(56789);
		Socket socket = serverSocket.accept();
		System.out.println("Accepted connection from the Client : " + socket);

		byte [] bytearray  = new byte [filesize];
		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream("ReceivedFile.txt");
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bytesRead = is.read(bytearray,0,bytearray.length);
		currentTot = bytesRead;

		bos.write(bytearray, 0 , currentTot);
		bos.flush();
		bos.close();
		System.out.println("***** File is successfully RECEIVED to from client *****");

		BufferedReader br = new BufferedReader(new FileReader("ReceivedFile.txt")); 
		
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}

		FileWriter fw = new FileWriter("ReceivedFile.txt" ,true); 
		fw.append("\n\n This is added line from a server");
		fw.close();

		File transferFile = new File ("ReceivedFile.txt");
		bytearray  = new byte [(int)transferFile.length()];
		FileInputStream fin = new FileInputStream(transferFile);
		BufferedInputStream bin = new BufferedInputStream(fin);
		bin.read(bytearray,0,bytearray.length);
		OutputStream os = socket.getOutputStream();
		System.out.println("Sending Files...");
		os.write(bytearray,0,bytearray.length);
		os.flush();
		socket.close();
		System.out.println("File transfer is complete");
	}
	public static void append(File aFile, String content) {
		FileWriter fw;
		try {
			fw = new FileWriter(aFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			fw.append(content);
			fw.close();

			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
