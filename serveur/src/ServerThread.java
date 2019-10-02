import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread implements Serializable {

    private Socket client;

    public ServerThread(Socket client) {
        System.out.println("\nNew Thread =>");
        this.client = client;
    }

    @Override
    public void run() {

        try {

            System.out.println("---------------------\nNew client connected \n ip adress => " + client.getLocalAddress()+"\n");
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            //ObjectInputStream objIn = new ObjectInputStream(in);
            ObjectOutputStream objOut = new ObjectOutputStream(out);

            Integer I = new Integer(3);
            objOut.writeObject(I);



            //UnObjet O= (UnObjet)objIn.readObject(O);
            client.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }
}
