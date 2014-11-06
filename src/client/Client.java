package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Panel implements Runnable {
	
	/**
	 * Inital Version. 
	 */
	private static final long serialVersionUID = 1;
	
	private Button submitButton;
	private Button clearButton;
	private TextField messageField;
	private TextArea messageArea;
	private DataInputStream din;
	private DataOutputStream dout;
	
	
	
	// creates a java applet
	public Client (String host, int port) {
		// screen setup
		setupUI();
		
		// receive msgs when a line is typed and 
		// returned, using an anonymous class as
		// a callback
		messageField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processMessage(e.getActionCommand());
			}
		});
		
		// Connect to the server
		try {
			
			Socket socket = new Socket(host, port);
			
			// Connection!
			System.out.println("Connected to " + socket);
			
			// Create DataInput/Output streams from the connection
			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());
			
			// Create a background thread for receiving messages
			new Thread(this).start();
			
			
		} catch (IOException e) {
			System.out.println("IOException in Client: " + e);
		}
	
	}
	
	private void processMessage(String message) {
		try {
			// Send to server
			dout.writeUTF(message);
			
			// Clear messageField
			messageField.setText("");
		} catch (IOException e) {
			System.out.println("IOException in Client: " + e);
		}
		
	}

	private void setupUI() {
		setLayout(new BorderLayout());
		
		submitButton = new Button("Submit");
		clearButton = new Button("Clear Messages");
		messageArea = new TextArea("", 5, 40);
		messageField = new TextField("Type your message here");
		add(messageArea, "Centre");
		add(messageField, "South");
		add(submitButton, "South");
		add(clearButton, "South");
		
	}

	@Override
	public void run() {
		try {
			
			// receive messages one-by-one forever, 
			while (true) {
				
				// get the next message
				String message = din.readUTF();
				
				// append to our messageArea
				messageArea.append(message + "\n");
				
				// and goes back to waiting.
			}
		} catch (IOException e) {
			System.out.println("IOException in Client: " + e);
		}
		
	}
	

}
