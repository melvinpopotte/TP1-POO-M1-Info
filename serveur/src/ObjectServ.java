import java.io.Serializable;

public class ObjectServ implements Serializable {

    private String name ;
    private int age ;
    private Object test = new Testobj();
    private int testint;


    public ObjectServ() {
    }

    public String getName() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setage(int age) {
        this.age = age;
    }


}


