import java.lang.reflect.Field;
import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class Server implements Serializable {
    //premiere liste d'object vides  a envoyer au client on retire un element de la liste il est remplis par le client et renvoyer
    //deuxieme liste d'object contient les objects remplis par le client

    ArrayList<Object> liste1 = new ArrayList<Object>();
    ArrayList<Object> liste2 = new ArrayList<Object>();

    Object obj = new ObjectServ();
    //Object obj1 = new Testobj();


    public Server(int PORT)  {

        //System.out.println(obj.getClass().getDeclaredFields()[0].getName());
        for (int i = 0; i < 50 ; i++) {
            liste1.add( obj );
        }



        try {
            // On écoute sur le port <PORT>
            ServerSocket ecoute=new ServerSocket(PORT);
            System.out.println("Server launched");

            while (true) {
                // On accepte une demande de connexion d'un client
                Socket client=ecoute.accept();
                Thread th = new ServerThread(client,liste1,liste2);
                th.start();




                /*
                System.out.println("\n---------------------\nNew client connected \n ip adress => "+client.getLocalAddress());
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();
                ObjectOutputStream objOut = new ObjectOutputStream(out);
                //ObjectInputStream objIn = new ObjectInputStream(in);

                Integer I= new Integer(3);
                objOut.writeObject(I);


                //UnObjet O= (UnObjet)objIn.readObject(O);
                client.close();


                 */
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    }
