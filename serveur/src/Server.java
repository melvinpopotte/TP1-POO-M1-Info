import java.net.*;
import java.io.*;


public class Server implements Serializable {



    public Server(int PORT)  {
        try {
            // On écoute sur le port <PORT>
            ServerSocket ecoute=new ServerSocket(PORT);
            System.out.println("Server launched");

            while (true) {
                // On accepte une demande de connexion d'un client
                Socket client=ecoute.accept();
                System.out.println("\n---------------------\nNew client connected \n ip adress => "+client.getLocalAddress());
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();
                //ObjectInputStream objIn = new ObjectInputStream(in);
                ObjectOutputStream objOut = new ObjectOutputStream(out);

                Integer I= new Integer(3);
                objOut.writeObject(I);


                //UnObjet O= (UnObjet)objIn.readObject(O);
                client.close();

            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
