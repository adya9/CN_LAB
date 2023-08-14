
import java.io.*;
import java.net.Socket;
public class Client {
    static DataInputStream datainputstream=null;
    static DataOutputStream dataoutputstream=null;

    public static void main(String[] args)
    {
        try{
            Socket soc=new Socket("localhost",900);
            System.out.println("Client Started");

            datainputstream =new DataInputStream(soc.getInputStream());
            dataoutputstream=new DataOutputStream(soc.getOutputStream());

            sendFile("C:/Users/ASUS/OneDrive/Desktop/demo.txt");
            datainputstream.close();
            dataoutputstream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    static void sendFile(String path) throws Exception
    {
        File file=new File(path);
        FileInputStream fin=new FileInputStream(file);
        int bytes=0;
        dataoutputstream.writeLong(file.length());
        byte[] buffer=new byte[4*1024];
        while((bytes=fin.read(buffer))!=-1)
        {
            dataoutputstream.write(buffer,0,bytes);
            dataoutputstream.flush();
        }
        dataoutputstream.close();

    }
    
}
