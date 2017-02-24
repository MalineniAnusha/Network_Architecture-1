import java.io.*; 
import java.net.*; 

class ServerConnection { 

	public static void main(String argv[]) throws Exception 
	{ 
		String clientSentence; 
		String returnSentence=""; 
		System.out.println("*****************************************");		
		System.out.println("Running the Server");
		System.out.println("*****************************************");		
		ServerSocket welcomeSocket = new ServerSocket(56789); 

		while(true) { 
			Socket connectionSocket = welcomeSocket.accept(); 
			BufferedReader inFromClient = 
					new BufferedReader(new
							InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream  outToClient = 
					new DataOutputStream(connectionSocket.getOutputStream()); 

			clientSentence = inFromClient.readLine(); 
			System.out.println("RECEIVED FROM CLIENT : "+clientSentence);
			if(clientSentence.contains("Hello from Client")){
				String[] parts = clientSentence.split("-");
				returnSentence = "Hello from Server-"+parts[1]+ '\n'; 
			}
			else if(clientSentence.startsWith("EXIT")){
				String[] parts = clientSentence.split("-");
				returnSentence = "EXIT-"+parts[1]+ '\n'; 
				outToClient.writeBytes(returnSentence);
				break;
			}
			else{
					returnSentence = clientSentence+ '\n'; 
			}
			outToClient.writeBytes(returnSentence); 
		} 
	} 
} 

