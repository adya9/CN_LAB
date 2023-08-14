
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    static DataInputStream datainputstream=null;
    static DataOutputStream dataoutputstream=null;

    public static void main(String[] args)
    {
        try{
            ServerSocket ss= new ServerSocket(900);
            System.out.println("Server started");

			System.out.println("Waiting for a client ...");
            Socket soc=ss.accept();
            System.out.println("Client accepted");
            datainputstream=new DataInputStream(soc.getInputStream());
            dataoutputstream=new DataOutputStream(soc.getOutputStream());

            receivefile("New.txt");
            datainputstream.close();
            dataoutputstream.close();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    static void receivefile(String filename) throws Exception
    {
        int bytes=0;
        FileOutputStream fout=new FileOutputStream(filename);
      
        long size = datainputstream.readLong();
        byte[] buffer=new byte[4*1024];
        while((size>0) && (bytes=datainputstream.read(buffer,0,(int)Math.min(buffer.length,size)))!=-1)
        {
            fout.write(buffer,0,bytes);
            size-=bytes;

        }
        System.out.println("File received successfully ");
        dataoutputstream.close();
    }

    
}
