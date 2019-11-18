import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread implements Serializable {

    private Socket client;
    private Object I;
    private Object I2;

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

        System.out.println("\n---------------------\nNew client connected \n ip adress => " + client.getLocalAddress());




        try {

            int i = 9;
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();





            while(i<10) {


                ObjectOutputStream objOut = new ObjectOutputStream(out);
                ObjectInputStream objIn = new ObjectInputStream(in);

                synchronized (liste1) {
                    I = liste1.remove(0);
                }

                objOut.writeObject(I);
                I2 = objIn.readObject();


                synchronized (liste2) {
                    liste2.add(I2);
                    I = null;
                    I2 = null;

                }


                System.out.println();
                System.out.println("--------- liste 2 --------");


                for (Object o : liste2) {

                    showobj(o);
                    System.out.println("--------");
                }
                objOut.flush();
            }



            //UnObjet O= (UnObjet)objIn.readObject(O);
            client.close();
        } catch (IOException | IllegalAccessException e) {


            if (I != null) {
                synchronized (liste1) {
                    liste1.add(0, I);
                }
            }
            if (I2 != null) {
                synchronized (liste2) {
                    liste2.add(I2);
                }
            }




            System.err.println(e.getMessage());
            //System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}

