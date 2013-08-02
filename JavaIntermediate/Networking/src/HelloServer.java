import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public final class HelloServer {
	public static void main(String[]args){
	ServerSocket server;
		try {
			server = new ServerSocket(9090);
			while(true) {
				Socket client = server.accept();
				BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter output = new PrintWriter(client.getOutputStream(), true);
				
				output.println("Hello!");
				
				client.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
