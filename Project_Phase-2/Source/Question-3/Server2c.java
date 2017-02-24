import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2c {

	public static void main(String[] args) throws Exception {
		
		int i=0;
		ServerSocket serverSocket = new ServerSocket(4567);
		try {
			while (true) {
				new ClientManager(serverSocket.accept(),"Client1"+i).start();
				i++;
			}
		} finally {
			serverSocket.close();
		}
	}

	private static class ClientManager extends Thread {
		
		private Socket socket;
		private BufferedReader in;
		
		private String Clientname;
		
		public ClientManager(Socket socket,String name) {
			this.socket = socket;
			Clientname=name;
		}

	
		public void run() {
			try {

				
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
			
					String msg = in.readLine(); 
					System.out.println("RECEIVED FROM CLIENT : "+ Clientname+"  and Message: "+msg);
					
					
				
			} catch (IOException e) {
				System.out.println(e);
			} finally {
			
				
				
				try {
					socket.close();
				} 
				catch (IOException e) {
				}
			}
		}
	}

}
