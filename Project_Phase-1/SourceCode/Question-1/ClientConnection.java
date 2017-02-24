import java.io.*; 
import java.net.*; 
import java.util.Scanner;

import javax.naming.InitialContext;
class ClientConnection { 

	public static void main(String argv[]) throws Exception 
	{ 
		String in_flow_message = ""; 
		String messageFromServer=""; 
		int flag=0;
		BufferedReader fromUser = 
				new BufferedReader(new InputStreamReader(System.in)); 
		System.out.println("*****************************************");		
		System.out.println("Running the Client");
		System.out.println("*****************************************");		
		String firstMessage="";		
		Socket clientSocket=null; 
			if(flag==0){
				System.out.println("PLEASE ENTER THE MESSAGE : ");
				firstMessage = fromUser.readLine(); 
			}
			if(firstMessage.startsWith("Hello from Client")){
				in_flow_message=firstMessage;
			
			while(!messageFromServer.startsWith("EXIT" )){
				
				clientSocket= new Socket("Server.Project-1.ch-geni-net.instageni.umkc.edu", 56789);
				DataOutputStream outToServer = 
						new DataOutputStream(clientSocket.getOutputStream()); 
				BufferedReader inFromServer = 
						new BufferedReader(new
								InputStreamReader(clientSocket.getInputStream())); 
				if(flag==1){
					System.out.println("PLEASE ENTER THE MESSAGE : ");
					in_flow_message = fromUser.readLine(); 
				}
				outToServer.writeBytes(in_flow_message + '\n'); 

				messageFromServer = inFromServer.readLine(); 

				System.out.println("MESSAGE FROM SERVER: " + messageFromServer); 
				flag=1;
			}           
			clientSocket.close(); 
		}
        else{
             System.out.println("Enter give the correct initial message");
            }
		
	} 
} 