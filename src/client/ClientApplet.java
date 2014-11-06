package client;

import java.applet.*;
import java.awt.*;

public class ClientApplet extends Applet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	public void init() {
		String host = getParameter("host");
		int port = Integer.parseInt(getParameter("port"));
		setLayout(new BorderLayout());
		add("Centre", new Client(host, port));
	}

}
