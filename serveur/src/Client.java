import java.net.*;
import java.io.*;


public class Client implements Serializable {


    public Client(String serverhost,int PORT)  {

        Socket s=null;
        try {
            System.out.println("Client lauched");
            s=new Socket(serverhost,PORT); // Création du socket

            // Récupération des flux d’entrée/sortie
            System.out.println("connected");

            //---------------------------------------------------------
             InputStream in = s.getInputStream();
            //OutputStream out = s.getOutputStream();

            ObjectInputStream objIn = new ObjectInputStream(in);

            //ObjectOutputStream objOut = new ObjectOutputStream(out);
            //---------------------------------------------------------



            try {
                Integer I = (Integer) objIn.readObject();
                System.out.println("value received => "+I);

            }
            catch (ClassNotFoundException e){
                System.err.println(e);
            }

            //UnObjet O= new UnObjet() ;
            //objOut.writeObject(O);



            s.close();
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }
}
