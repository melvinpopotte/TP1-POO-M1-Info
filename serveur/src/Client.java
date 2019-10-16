import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
             OutputStream out = s.getOutputStream();

            ObjectOutputStream objOut = new ObjectOutputStream(out);
            ObjectInputStream objIn = new ObjectInputStream(in);
            //---------------------------------------------------------



            try {
                Object I = (Object) objIn.readObject();
                Class classe = I.getClass();


                System.out.println("L'object  a "+classe.getDeclaredFields().length+" elements");
             /*
                Field[] variables = classe.getDeclaredFields();
               // for (Field f: variables) {
                    System.out.println("Nom :"+variables[1]);

                System.out.println("age = "+classe.getMethod("getAge").invoke(I));
                Method m = classe.getMethod("set"+variables[1].getName(),int.class );
                    m.invoke(I,10);
                    System.out.println("age = "+classe.getMethod("getAge").invoke(I));

               // }



              */

                for (Method q: classe.getMethods() ) {
                    if (q.getName().contains("setage")){
                        System.out.println("age = "+classe.getMethod("getAge").invoke(I));

                        q.invoke(I,10);
                        System.out.println("age = "+classe.getMethod("getAge").invoke(I));

                    }
                   // System.out.println(" nom methode => "+q.getName()+" parametres => "+q.getParameterTypes()[0]);
                }




            }
            catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e){
                System.err.println(e);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
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
