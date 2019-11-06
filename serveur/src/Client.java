import java.lang.reflect.Field;
import java.net.*;
import java.io.*;
import java.util.Scanner;


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


                //System.out.println("L'object  a "+classe.getDeclaredFields().length+" elements");
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

                inputclasse(I,classe);
                objOut.writeObject(I);



            }
            catch (ClassNotFoundException | IllegalAccessException e){
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


    public void inputclasse(Object I,Class classe) throws IllegalAccessException {
        Scanner sc = new Scanner(System.in);

        for (Field q: classe.getDeclaredFields() ) {
            q.setAccessible(true);

            if (q.getType() == int.class){
                System.out.println(q.getName()+" : Veuillez entrer un entier");
                q.setInt(I,sc.nextInt());
            }
            if (q.getType() == float.class){
                System.out.println(q.getName()+" :Veuillez entrer un float");
                q.setFloat(I,sc.nextFloat());
            }
            if (q.getType() == String.class){
                System.out.println(q.getName()+" :Veuillez entrer une chaine de caractère");
                q.set(I,sc.next());
            }
            if(q.getType() == Object.class){
                System.out.println("found a class my friende => "+q);
                inputclasse(q.get(I),q.get(I).getClass());
            }




            //System.out.println("age = "+classe.getMethod("getName").invoke(I));
            //System.out.println(" nom methode => "+q.getName()+" parametres => "+q.getParameterTypes()[0]);
            //System.out.println("age = "+classe.getMethod("getName").invoke(I));
            // System.out.println(" nom methode => "+q.getName()+" parametres => "+q.getParameterTypes()[0]);
        }

    }
}
