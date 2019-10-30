import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;

public class ServerThread extends Thread implements Serializable {

    private Socket client;
    private Object I;

    public ServerThread(Socket client , Object I) {
        this.client = client;
        this.I = I;
    }

    @Override
    public void run() {

        try {

            System.out.println("\n---------------------\nNew client connected \n ip adress => " + client.getLocalAddress());
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();

            ObjectOutputStream objOut = new ObjectOutputStream(out);
            ObjectInputStream objIn = new ObjectInputStream(in);



            System.out.println();
            for (Field f : I.getClass().getDeclaredFields() ) {
                f.setAccessible(true);
                System.out.println("Before : "+f.getName()+" => "+f.get(I));
            }
            System.out.println("______________\n");
            objOut.writeObject(I);
            I = objIn.readObject();
            for (Field f : I.getClass().getDeclaredFields() ) {
                f.setAccessible(true);
                System.out.println(f.getName()+" => "+f.get(I));
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
