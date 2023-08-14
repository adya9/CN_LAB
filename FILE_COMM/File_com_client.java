
import java.io.*;
import java.net.*;
public class File_com_client {

    static DataOutputStream out=null;
    static DataInputStream in=null;

    static DataInputStream datainput=null;
    static DataOutputStream dataout=null;
    static Socket socket=null;
    public static void main(String[] args)
    {
        try{
            socket=new Socket("localhost",900);
            in=new DataInputStream(socket.getInputStream());
            out=new DataOutputStream(socket.getOutputStream());
            datainput=new DataInputStream(System.in);
            dataout=new DataOutputStream(socket.getOutputStream());
            String line="";
            while(!line.equalsIgnoreCase("over"))
            {
                line=datainput.readLine();
                dataout.writeUTF(line);
            }
            sendFile("C:/Users/ASUS/OneDrive/Desktop/demo.txt");
            in.close();
            out.close();
            datainput.close();
            dataout.close();
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
        out.writeLong(file.length());
        byte buffer[]=new byte[4*1024];
        while((bytes=fin.read(buffer))!=-1)
        {
            out.write(buffer,0,bytes);
            out.flush();
        }
        out.close();
    }
}
