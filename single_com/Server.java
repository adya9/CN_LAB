
// A Java program for a Server
import java.net.*;
import java.io.*;

public class Server {
	// initialize socket and input stream
	private Socket socket = null;  //refernece to the client socket connection 
	private ServerSocket server = null;  //refernece to the server socket connection

	private DataInputStream in = null;

	// constructor with port
	public Server(int port) {
		// starts server and waits for a connection
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");

			// takes input from the client socket
			in = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));

			String line = "";

			// reads message from client until "Over" is sent
			while (!line.equalsIgnoreCase("over")) {
				line = in.readUTF(); //reads data from the client using this
				System.out.println(line);

			}

			System.out.println("Closing connection");

			// close connection
			socket.close();

			in.close();

		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String args[]) {
		Server server = new Server(5000);
	}
}
