
import java.io.*;
import java.net.*;

public class File_com_Server {
    static ServerSocket serversocket=null;
    static Socket socket=null;
    static DataInputStream datainp=null;
    static DataOutputStream dataout=null;
    static DataInputStream in=null;

    public static void main(String[] args)
    {
        try{
            serversocket=new ServerSocket(900);
        System.out.println("Server started");
        socket=serversocket.accept();
        System.out.println("Cient started");
        datainp=new DataInputStream(socket.getInputStream());
        dataout=new DataOutputStream(socket.getOutputStream());
        in=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        String line="";

        while(!line.equalsIgnoreCase("over"))
        {
            line=in.readUTF();
            System.out.println(line);

        }

        receiveFile("new.txt");
        in.close();
        datainp.close();
        dataout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    // static void receiveFile(String path) throws Exception{
    //     int bytes=0;
    //     FileOutputStream fout=new FileOutputStream(path);
    //     long size=datainp.readLong();
    //     byte[] buffer=new byte[4*1024];

    //     while((size>0) && (bytes=datainp.read(buffer,0,(int)Math.min(buffer.length,size)))!=-1)
    //     {
    //         fout.write(buffer,0,bytes);
    //         size=bytes;
    //     }
    //     System.out.println("File Received");
    //     dataout.close();
    // }

    static void receiveFile(String filename) throws Exception
    {
        int bytes=0;
        FileOutputStream fout=new FileOutputStream(filename);
      
        long size = datainp.readLong();
        byte[] buffer=new byte[4*1024];
        while((size>0) && (bytes=datainp.read(buffer,0,(int)Math.min(buffer.length,size)))!=-1)
        {
            fout.write(buffer,0,bytes);
            size-=bytes;

        }
        System.out.println("File received successfully ");
        dataout.close();
    }
}
