package Repository;

import Domain.Nota;
import Domain.Validator.Validator;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.List;

public class NotaRepository extends FileRepository<Pair<String, String>, Nota> {


    public NotaRepository(String file, Validator<Nota> validator) {
        super(file, validator);
    }

    //@Override

    /**
     *
     * @param attr
     * @return TemaLaborator
     */
    public Nota extractEntity(List<String> attr) {
        String idS=attr.get(0);
        String idL=attr.get(1);
        float nota=Float.parseFloat(attr.get(2));
        String feedback=attr.get(3);
        int saptamanaPredare=Integer.parseInt(attr.get(4));
        int deadline=Integer.parseInt(attr.get(5));
        String idCD=attr.get(6);

        Nota nt=new Nota(idS, idL, nota, feedback, saptamanaPredare, deadline, idCD);
        return nt;
    }


}
