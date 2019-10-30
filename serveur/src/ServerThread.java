import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread implements Serializable {

    private Socket client;
    private Object I;
    private ArrayList<Object> liste1 ;
    private ArrayList<Object> liste2 ;


    public ServerThread(Socket client , ArrayList<Object> liste1 ,ArrayList<Object>  liste2) {
        this.client = client;
        this.liste1 = liste1;
        this.liste2 = liste2;
    }

    @Override
    public void run() {

        try {

            System.out.println("\n---------------------\nNew client connected \n ip adress => " + client.getLocalAddress());
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();

            ObjectOutputStream objOut = new ObjectOutputStream(out);
            ObjectInputStream objIn = new ObjectInputStream(in);

            I = liste1.get(0);
            I = liste1.remove(0);


            objOut.writeObject(I);
            I = objIn.readObject();


            liste2.add(I);
            for (Object o: liste2) {
                for (Field f : o.getClass().getDeclaredFields() ) {
                    f.setAccessible(true);
                    System.out.println(f.getName()+" => "+f.get(o));
                }

            }


            //UnObjet O= (UnObjet)objIn.readObject(O);
            client.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
