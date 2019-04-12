package Domain;
import javafx.util.Pair;

import Utils.Time;

import java.time.LocalDateTime;

public class Nota implements HasID<Pair<String, String>>{
    private Pair<String, String> id;
    private Student stud;
    private TemaLaborator laborator;
    private float nota;
    private String feedback;
    private CadruDidactic cadruDidactic;
    private int saptamanaPredare;
    private int deadline;

    /**
     *
     * @param s
     * @param l
     * @param n
     * @param f
     * @param c
     */
    public Nota(Student s, TemaLaborator l, float n, String f, CadruDidactic c, int sP, int d){
        id=new Pair<>(s.getID(), l.getID());
        stud=s;
        laborator=l;
        nota=n;
        feedback=f;
        cadruDidactic=c;
        saptamanaPredare= sP;
        deadline=d;
    }

    public Nota(String idS, String idL, float nta, String feedbck, int saptPredare, int deadln, String idCD){
        id=new Pair<>(idS, idL);
        stud=new Student();
        laborator=new TemaLaborator();
        cadruDidactic=new CadruDidactic();
        stud.setID(idS);
        laborator.setID(idL);
        cadruDidactic.setID(idCD);
        nota=nta;
        feedback=feedbck;
        saptamanaPredare=saptPredare;
        deadline=deadln;
    }

    public Nota(){
        stud=new Student();
        laborator=new TemaLaborator();
        cadruDidactic=new CadruDidactic();
    }

    /**
     *
     * @return
     */
    public CadruDidactic getCadruDidactic(){
        return cadruDidactic;
    }


    /**
     *
     * @return
     */
    public Student getStudent(){
        return stud;
    }

    /**
     *
     * @return
     */
    public TemaLaborator getLaborator(){
        return laborator;
    }

    /**
     *
     * @return
     */
    public float getNota(){
        return nota;
    }

    /**
     *
     * @return
     */
    public String getFeedback(){
        return feedback;
    }

    public int getSaptamanaPredare(){ return saptamanaPredare;}

    public void setSaptamanaPredare(int sP){saptamanaPredare=sP;}

    public int getDeadline(){ return deadline;}

    public void setDeadline(int d){ deadline=d;}

    @Override
    /**
     *
     */
    public Pair<String, String> getID() {
        return id;
    }

    @Override
    /**
     *
     */
    public void setID(Pair<String, String> i) {
        id= i;
    }

    /**
     *
     * @param s
     */
    public void setStudent(Student s){
        stud=s;
    }

    /**
     *
     * @param l
     */
    public void setLaborator(TemaLaborator l){
        laborator=l;
    }

    /**
     *
     * @param n
     */
    public void setNota(float n){
        nota=n;
    }

    /**
     *
     * @param f
     */
    public void setFeedback(String f){
        feedback=f;
    }

    /**
     *
     * @param c
     */
    public void setCadruDidactic(CadruDidactic c){
        cadruDidactic=c;
    }

    @Override
    /**
     *
     */
    public String toString() {
        return new String(stud.getID()+","+laborator.getID()+","+nota+","+feedback+","+saptamanaPredare+","+deadline+","+cadruDidactic.getID());
    }
}
