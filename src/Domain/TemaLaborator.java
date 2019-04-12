package Domain;

import java.util.Arrays;
import java.util.List;
import Utils.Time;

public class TemaLaborator implements HasID<String>{
    private String id;
    private String descriere;
    private int start;
    private int deadline;


    public static final long PrimaSaptamana=1;

    /**
     *
     * @return
     */
    public static long getPrimaSaptamana(){
        return PrimaSaptamana;
    }

    /**
     *
     * @param i
     * @param descr
     * @param s
     * @param d
     */
    public TemaLaborator(String i, String descr, int s, int d){
        id=i;
        descriere=descr;
        start=s;
        deadline=d;
    }

    /**
     *
     * @param linie
     */
    public TemaLaborator(String linie){
        List<String> attr= Arrays.asList(linie.split(","));
        String id=attr.get(0);
        String nume=attr.get(1);
        int grupa=Integer.parseInt(attr.get(2));
        String email=attr.get(3);
        Student st=new Student(id,nume, grupa, email);
    }

    public TemaLaborator(){}


    /**
     *
     * @return
     */
    public String getDescriere(){
        return descriere;
    }

    /**
     *
     * @return
     */
    public int getStart(){
        return start;
    }

    /**
     *
     * @return
     */
    public int getDeadline(){
        return deadline;
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
     * @param d
     */
    public void setDeascriere(String d){
        descriere=d;
    }

    /**
     *
     * @param s
     */
    public void setStart(int s){
        start=s;
    }

    /**
     *
     * @param d
     */
    public void setDeadline(int d){
        deadline=d;
    }

    @Override
    /**
     *
     */
    public String toString() {
        return new String(id+","+descriere+","+start+","+deadline);
    }
}
