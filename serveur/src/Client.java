import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class Client implements Serializable {

    boolean good = false;
    public Client(String serverhost, int PORT) {

        JFrame frame = new JFrame("Client");
        JLabel info = new JLabel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(500, 700);
        JPanel pane1 = new JPanel();
        ArrayList<JTextField> listeinput = new ArrayList<>();
        ArrayList<JTextField> listeinputcpy = new ArrayList<>();


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
                while(true) {

                    good = false;
                    pane1.removeAll();
                    Object I = objIn.readObject();


                    listeinput.removeAll(listeinput);
                    inputclasse(I, I.getClass(), pane1, listeinput);





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
                    info.setHorizontalAlignment(SwingConstants.CENTER);
                    info.setPreferredSize(new Dimension(500,30));
                    ok.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {

                            info.setText("");
                            listeinputcpy.removeAll(listeinputcpy);
                            listeinputcpy.addAll(listeinput);
                            if (inputclasse2(I, I.getClass(), listeinputcpy, pane1, info)) {
                                good = true;
                                info.setText("BON");

                            }
                            frame.setVisible(true);



                        }
                    });
                    ok.setPreferredSize(new Dimension(130, 70));

                    pane1.add(ok);
                    pane1.add(info);
                    frame.setVisible(true);
                    while (good == false) {
                        try {
                            Thread.sleep(10);

                        } catch (InterruptedException e) {
                        }
                    }
                    //objOut.writeObject(I);

                    objOut.writeObject(I);
                }



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

                // System.out.println(q.getName() + " : Veuillez entrer un entier");
                //q.setInt(I,sc.nextInt());
            }
            if (q.getType() == float.class) {
                JLabel label = new JLabel(q.getName() + " : Veuillez entrer un float");
                JTextField txt = new JTextField();
                listeinput.add(txt);
                txt.setPreferredSize(new Dimension(150, 30));
                pane1.add(label);
                pane1.add(txt);
                //  System.out.println(q.getName() + " :Veuillez entrer un float");
                //q.setFloat(I,sc.nextFloat());
            }
            if (q.getType() == String.class) {
                JLabel label = new JLabel(q.getName() + " : Veuillez entrer une chaine de caractère");
                JTextField txt = new JTextField();
                listeinput.add(txt);
                txt.setPreferredSize(new Dimension(150, 30));
                pane1.add(label);
                pane1.add(txt);
                // System.out.println(q.getName() + " :Veuillez entrer une chaine de caractère");
                //q.set(I,sc.next());
            }
            if (q.getType() == Object.class) {
                //System.out.println("found a class my friende => "+q);
                JLabel label = new JLabel("Voici L'object : "+q.getName() + " ");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setPreferredSize(new Dimension(500,30));
                pane1.add(label);

                inputclasse(q.get(I), q.get(I).getClass(), pane1, listeinput);

                label = new JLabel("----Fin de l'Object----");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setPreferredSize(new Dimension(500,30));
                pane1.add(label);
            }


            //System.out.println("age = "+classe.getMethod("getName").invoke(I));
            //System.out.println(" nom methode => "+q.getName()+" parametres => "+q.getParameterTypes()[0]);
            //System.out.println("age = "+classe.getMethod("getName").invoke(I));
            // System.out.println(" nom methode => "+q.getName()+" parametres => "+q.getParameterTypes()[0]);
        }

    }


    public boolean inputclasse2(Object I, Class classe, ArrayList<JTextField> listeinputcpy,JPanel pane1,JLabel label) {
        //Scanner sc = new Scanner(System.in);

        for (Field q : classe.getDeclaredFields()) {
            q.setAccessible(true);

            if (listeinputcpy.get(0).getText().isEmpty()){
                label.setText("Veuillez remplire les elements");
                //label.setHorizontalAlignment(SwingConstants.CENTER);
                //label.setPreferredSize(new Dimension(500,30));
                //pane1.add(label);
                return false;
            }
            else {
                if (q.getType() == int.class) {

                    try {
                        int i = Integer.parseInt(listeinputcpy.get(0).getText());
                        listeinputcpy.remove(0);

                        // System.out.println(q.getName() + " : Veuillez entrer un entier");

                        try {
                            q.setInt(I, i);
                            System.out.println(q.getName() + " set "+q.getInt(I));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }catch (NumberFormatException e){
                        label.setText("Veuillez devier entrer un entier dans "+q.getName());

                        return false;
                    }

                }
                if (q.getType() == float.class) {
                    try{
                        float i = Float.parseFloat(listeinputcpy.get(0).getText());
                        listeinputcpy.remove(0);
                        // System.out.println(q.getName() + " :Veuillez entrer un float");
                        try {
                            q.setFloat(I, i);
                            System.out.println(q.getName() + " set "+q.getFloat(I));

                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }catch (NumberFormatException e){
                        label.setText("Veuillez devier entrer un float dans "+q.getName());

                        return false;
                    }
                }
                if (q.getType() == String.class) {
                    //  System.out.println(q.getName() + " :Veuillez entrer une chaine de caractère");
                    try {
                        q.set(I, listeinputcpy.get(0).getText());
                        System.out.println(q.getName() + " set "+q.get(I));

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    listeinputcpy.remove(0);
                }
                if (q.getType() == Object.class) {
                    //  System.out.println("found a class => " + q);
                    try {
                        if (inputclasse2(q.get(I), q.get(I).getClass(), listeinputcpy,pane1,label) == false){
                            return false;
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
        return true;
    }
}