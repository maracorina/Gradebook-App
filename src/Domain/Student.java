package Domain;

import Domain.HasID;

import java.util.Arrays;
import java.util.List;

public class Student implements HasID<String> {
    private String id;
    private String nume;
    private int grupa;
    private String email;

    public Student(String i, String n, int g, String e){
        id=i;
        nume=n;
        grupa=g;
        email=e;
    }

    /**
     *
     * @param linie
     * @return
     */
    public Student(String linie){
        List<String> attr= Arrays.asList(linie.split(","));
        String id=attr.get(0);
        String nume=attr.get(1);
        int grupa=Integer.parseInt(attr.get(2));
        String email=attr.get(3);
        Student st=new Student(id,nume, grupa, email);
    }

    public Student(){}

    @Override
    /**
     *
     */
    public String toString() {
        return new String(id+","+nume+","+grupa+","+email);
    }


    /**
     *
     * @return
     */
    public String getNume(){
        return nume;
    }

    /**
     *
     * @return
     */
    public int getGrupa(){
        return grupa;
    }

    /**
     *
     * @return
     */
    public String getEmail(){
        return email;
    }


    @Override
    /**
     *
     */
    public String getID() {
        return id;
    }

    @Override
    /**
     *
     */
    public void setID(String o) {
        id= o;
    }

    /**
     *
     * @param n
     */
    public void setNume(String n){
        nume=n;
    }

    /**
     *
     * @param g
     */
    public void setGrupa(int g){
        grupa=g;
    }

    /**
     *
     * @param e
     */
    public void setEmail(String e){
        email=e;
    }
}
