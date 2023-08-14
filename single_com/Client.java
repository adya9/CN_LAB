
// A Java program for a Client
import java.io.*;   //for handling the input output
import java.net.*;   // for handling the networking operation

public class Client {
	// initialize socket and input output streams
	private Socket socket = null;      //refernce to the client's socket connection
	private DataInputStream input = null;   //used for reading the data over the socket 
	private DataOutputStream out = null;    //used for writing the data over the socket 

	// constructor to put ip address and port
	public Client(String address, int port) {
		// establish a connection
		try {
			socket = new Socket(address, port);   //socket is initiallised to specified addresss and a port 
			System.out.println("Connected");

			// initialized the input stream to takes input from terminal
			input = new DataInputStream(System.in);  

			// to write the data to the outputstream of the socket
			out = new DataOutputStream(socket.getOutputStream());

			// define line to store the user input
			String line = "";

			// keep reading until "Over" is input
			while (!line.equalsIgnoreCase("over")) {

				line = input.readLine(); //reads line of input from user using this line
				out.writeUTF(line); //sends each line as UTF-8 encoded data to the server

			}

			// close the connection

			input.close();

			out.close();
			socket.close();
		} catch (UnknownHostException u) {
			System.out.println(u);
			return;
		} catch (IOException i) {
			System.out.println(i);
			return;
		}
	}

	public static void main(String args[]) {
		Client client = new Client("localhost", 5000);
	}
}
