import java.io.*; 
import java.net.*; 

class Server2b { 


	public static void main(String argv[]) throws Exception 
	{ 
		int flag=0;
		BufferedReader inFromUser = 
				new BufferedReader(new InputStreamReader(System.in)); 
		String clientSentence=""; 
		String returnSentence=""; 
		System.out.println("Server running");
		//ServerSocket welcomeSocket = new ServerSocket(4567);
		
		while(true){
			String sentence;
			ServerSocket welcomeSocket = new ServerSocket(1234);
			while(true&flag==0) { 
				Socket connectionSocket = welcomeSocket.accept();
				BufferedReader inFromClient = 
						new BufferedReader(new
								InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream  outToClient = 
						new DataOutputStream(connectionSocket.getOutputStream()); 

				clientSentence = inFromClient.readLine(); 
				System.out.println("RECEIVED FROM CLIENT : "+clientSentence);
				
				if(clientSentence.equals("exit")){
					sentence="exit";
					returnSentence = sentence+ '\n'; 
					System.out.println("Waiting for client");
				

					outToClient.writeBytes(returnSentence);
					outToClient.close();
					connectionSocket.close(); 
					welcomeSocket.close();
					flag=1;
				}
				else{
					outToClient.close();
				}

			} 
			flag=0;
		} 
	}
} 

