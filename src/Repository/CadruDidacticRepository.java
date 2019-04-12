package Repository;

import Domain.CadruDidactic;
import Domain.Validator.Validator;

import java.util.List;

public class CadruDidacticRepository extends FileRepository<String, CadruDidactic> {


    public CadruDidacticRepository(String file, Validator<CadruDidactic> validator) {
        super(file, validator);
    }

    //@Override

    /**
     *
     * @param attr
     * @return Student
     */
    public CadruDidactic extractEntity(List<String> attr) {
        String id=attr.get(0);
        String nume=attr.get(1);
        String email=attr.get(2);
        String materie=attr.get(3);
        CadruDidactic cd=new CadruDidactic(id,nume, email, materie);
        return cd;
    }

}
