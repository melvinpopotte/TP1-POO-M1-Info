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

    public void showobj(Object o) throws IllegalAccessException{
        for (Field f : o.getClass().getDeclaredFields() ) {
            f.setAccessible(true);
            if (f.getType() != Object.class)
            System.out.println(f.getName()+" => "+f.get(o));
            else{
                showobj(f.get(o));
            }
        }
    }

    @Override
    public void run() {

        try {

            System.out.println("\n---------------------\nNew client connected \n ip adress => " + client.getLocalAddress());
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();

            ObjectOutputStream objOut = new ObjectOutputStream(out);
            ObjectInputStream objIn = new ObjectInputStream(in);

            synchronized (liste1) {
                I = liste1.get(0);
                I = liste1.remove(0);
            }

            objOut.writeObject(I);
            I = objIn.readObject();


            synchronized (liste2){
                liste2.add(I);
            }

            System.out.println();
            for (Object o: liste2) {

                showobj(o);
                System.out.println("$%--------%$");
            }


            //UnObjet O= (UnObjet)objIn.readObject(O);
            client.close();
        } catch (IOException | IllegalAccessException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        }


    }

