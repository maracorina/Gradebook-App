package Domain;

public class CadruDidactic implements HasID<String> {
    private String id;
    private String nume;
    private String email;
    private String materie;

    /**
     * @param i
     * @param n
     * @param e
     * @param m
     */
    public CadruDidactic(String i, String n, String e, String m){
        id=i;
        nume=n;
        email=e;
        materie=m;
    }

    public CadruDidactic(){}


    @Override
    /**
     *
     */
    public String toString() {
        return new String(id+","+nume+","+email+","+materie);
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
    public String getMaterie(){
        return materie;
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
     * @param m
     */
    public void setMaterie(String m){
        materie=m;
    }

    /**
     *
     * @param e
     */
    public void setEmail(String e){
        email=e;
    }
}
