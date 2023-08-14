import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MC_Client {
    //initializing constant and variables
    private static final String server_host = "localhost";
    private static final int server_port = 22554;

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public void start() {
        try {
            socket = new Socket(server_host, server_port);
            //if connection is successful it sets up the input and output stream
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            System.out.println("> Connected to the server.");
            
            //for receiving continious message we use Thread
            //It reads data from the server's input stream, converts it to a String, and prints it to the console as a received message
            Thread receiveThread = new Thread(this::receiveMessages);
            receiveThread.start();

            sendUsername();  //client ask the user to enter the user name
            sendMessageLoop();
        } catch (IOException e) {
            System.out.println("> Error: Failed to connect to the server.");
        } finally {
            closeConnection();
        }
    }

    private void sendUsername() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> Please enter your username: ");
        String username = scanner.nextLine();
        outputStream.write(username.getBytes());  //send user name to the server by writting the username as bytes to the output stream
    }

    private void sendMessageLoop() throws IOException {

        Scanner scanner = new Scanner(System.in);
        String message;

        while (true) {
            message = scanner.nextLine();
            outputStream.write(message.getBytes()); //send message to the server 
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
        }
    }

    private void receiveMessages() {
        byte[] buffer = new byte[1024];  //temp store the incoming data from the server
        int bytesRead;

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String message = new String(buffer, 0, bytesRead).trim();
                System.out.println("> "+message);
            }
        } catch (IOException e) {
            System.out.println("> Disconnected from the server.");
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MC_Client client = new MC_Client();
        client.start();
  }
}
