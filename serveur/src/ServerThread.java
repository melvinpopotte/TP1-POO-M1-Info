import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread implements Serializable {

    private Socket client;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        try {

            System.out.println("\n---------------------\nNew client connected \n ip adress => " + client.getLocalAddress());
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();

            ObjectOutputStream objOut = new ObjectOutputStream(out);
            ObjectInputStream objIn = new ObjectInputStream(in);


            Object I = new ObjectServ();
            objOut.writeObject(I);



            //UnObjet O= (UnObjet)objIn.readObject(O);
            client.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }
}
