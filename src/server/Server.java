package server;

import java.io.*;
import java.net.*;

public class Server {
	
	private OutputStream outputStream;

	public static void main(String[] args) throws IOException {
		
		// Get the port # from the command line
		int port = Integer.parseInt(args[0]);
		
		// Create a Server object, which will automatically begin
		// accepting connections
		new Server (port);
		
	}
	
	public Server(int port) throws IOException {
		listen(port);		
	}
	
	public void listen (int port) throws IOException {
		// Create the ServerSocket 
		ServerSocket ss = new ServerSocket (port);
		
		// Print we're ready to go
		System.out.println("Listening on ServerSocket " + ss);
		
		// keep listening forever, using the while loop
		// as a blocking lock
		
		while (true) {
			
			// Get the next connection
			Socket s = ss.accept();
			
			// Print we have the connection
			System.out.println("Connected to " + s );
			
			// Create a DataOutputStream for writing data
			// to the other side. 
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			
			// Save this stream so it doesn't need to be remade
			((Object) outputStream).put(s, dout);
			
			// Create a new thread for this connection, and then
			// forget about it. This thread will deal with the
			// new connection, and is an object of type ServerThread
			ServerThread sthread = new ServerThread (this, s);
			
		}
	}
	
	public void sendToAll(String message) {
		// TODO
	}
	
	public void removeConnection(Socket socket) {
		// TODO
	}

}
