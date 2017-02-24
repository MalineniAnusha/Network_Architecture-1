import java.io.*; 
import java.net.*; 
import java.util.Scanner;
class Client2a { 

	public static void main(String argv[]) throws Exception 
	{ 
		String sentence = ""; 
		String serverSentence=""; 

		BufferedReader inFromUser = 
				new BufferedReader(new InputStreamReader(System.in)); 
		System.out.println("Client is running");
		System.out.println("*****************************************");
		
		

		
			//opening socket connection
			Socket clientSocket=null; 

			while(!sentence.equals("exit")){
				System.out.println("ENTER MESSAGE : ");
				clientSocket= new Socket("152.54.14.22", 4567);
				DataOutputStream outToServer = 
						new DataOutputStream(clientSocket.getOutputStream()); 
				BufferedReader inFromServer = 
						new BufferedReader(new
								InputStreamReader(clientSocket.getInputStream())); 

				sentence = inFromUser.readLine(); 

				outToServer.writeBytes(sentence + '\n'); 

				serverSentence = inFromServer.readLine(); 

				System.out.println("FROM SERVER: " + serverSentence); 

			}           
			clientSocket.close(); 
		
		
		

	} 
} 