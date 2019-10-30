import java.io.Serializable;

public class ObjectServ implements Serializable {

    private String name = "Melvin";
    private int age = 10;


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
