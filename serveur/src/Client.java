import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class Client implements Serializable {


    public Client(String serverhost, int PORT) {

        JFrame frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(500, 700);
        JPanel pane1 = new JPanel();
        ArrayList<JTextField> listeinput = new ArrayList<>();
        Container contenu = frame.getContentPane();
        contenu.add(BorderLayout.CENTER, pane1);
        frame.setVisible(true);


        Socket s = null;
        try {
            System.out.println("Client lauched");
            s = new Socket(serverhost, PORT); // Création du socket

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
                Button ok = new Button("ok");
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            inputclasse2(I, classe, listeinput);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
                ok.setPreferredSize(new Dimension(130, 70));

                inputclasse(I, classe, pane1, listeinput);
                pane1.add(ok);
                frame.setVisible(true);
                objOut.writeObject(I);


            } catch (ClassNotFoundException | IllegalAccessException e) {
                System.err.println(e);
            }

            //UnObjet O= new UnObjet() ;
            //objOut.writeObject(O);


            s.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    public void inputclasse(Object I, Class classe, JPanel pane1, ArrayList<JTextField> listeinput) throws IllegalAccessException {
        //Scanner sc = new Scanner(System.in);

        for (Field q : classe.getDeclaredFields()) {
            q.setAccessible(true);

            if (q.getType() == int.class) {
                JLabel label = new JLabel(q.getName() + " : Veuillez entrer un entier");
                JTextField txt = new JTextField();
                listeinput.add(txt);
                txt.setPreferredSize(new Dimension(150, 30));
                pane1.add(label);
                pane1.add(txt);

                System.out.println(q.getName() + " : Veuillez entrer un entier");
                //q.setInt(I,sc.nextInt());
            }
            if (q.getType() == float.class) {
                JLabel label = new JLabel(q.getName() + " : Veuillez entrer un float");
                JTextField txt = new JTextField();
                listeinput.add(txt);
                txt.setPreferredSize(new Dimension(150, 30));
                pane1.add(label);
                pane1.add(txt);
                System.out.println(q.getName() + " :Veuillez entrer un float");
                //q.setFloat(I,sc.nextFloat());
            }
            if (q.getType() == String.class) {
                JLabel label = new JLabel(q.getName() + " : Veuillez entrer une chaine de caractère");
                JTextField txt = new JTextField();
                listeinput.add(txt);
                txt.setPreferredSize(new Dimension(150, 30));
                pane1.add(label);
                pane1.add(txt);
                System.out.println(q.getName() + " :Veuillez entrer une chaine de caractère");
                //q.set(I,sc.next());
            }
            if (q.getType() == Object.class) {
                //System.out.println("found a class my friende => "+q);
                inputclasse(q.get(I), q.get(I).getClass(), pane1, listeinput);
            }


            //System.out.println("age = "+classe.getMethod("getName").invoke(I));
            //System.out.println(" nom methode => "+q.getName()+" parametres => "+q.getParameterTypes()[0]);
            //System.out.println("age = "+classe.getMethod("getName").invoke(I));
            // System.out.println(" nom methode => "+q.getName()+" parametres => "+q.getParameterTypes()[0]);
        }

    }


    public void inputclasse2(Object I, Class classe, ArrayList<JTextField> listeinput) throws IllegalAccessException {
        //Scanner sc = new Scanner(System.in);

        for (Field q : classe.getDeclaredFields()) {
            q.setAccessible(true);

            if (q.getType() == int.class) {
                int i = Integer.parseInt(listeinput.get(0).getText());
                listeinput.remove(0);

                System.out.println(q.getName() + " : Veuillez entrer un entier");
                q.setInt(I, i);
            }
            if (q.getType() == float.class) {
                float i = Float.parseFloat(listeinput.get(0).getText());
                listeinput.remove(0);
                System.out.println(q.getName() + " :Veuillez entrer un float");
                q.setFloat(I, i);
            }
            if (q.getType() == String.class) {
                System.out.println(q.getName() + " :Veuillez entrer une chaine de caractère");
                q.set(I, listeinput.get(0).getText());
                listeinput.remove(0);
            }
            if (q.getType() == Object.class) {
                System.out.println("found a class my friende => " + q);
                inputclasse2(q.get(I), q.get(I).getClass(), listeinput);
            }


        }
    }
}