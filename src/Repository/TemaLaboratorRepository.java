package Repository;

import Domain.Student;
import Domain.TemaLaborator;
import Domain.Validator.Validator;

import java.util.List;

public class TemaLaboratorRepository extends FileRepository<String, TemaLaborator> {


    public TemaLaboratorRepository(String file, Validator<TemaLaborator> validator) {
        super(file, validator);
    }

    //@Override

    /**
     *
     * @param attr
     * @return TemaLaborator
     */
    public TemaLaborator extractEntity(List<String> attr) {
        String id=attr.get(0);
        String descriere=attr.get(1);
        int start=Integer.parseInt(attr.get(2));
        int deadline=Integer.parseInt(attr.get(3));
        TemaLaborator t=new TemaLaborator(id,descriere, start, deadline);
        return t;
    }


}