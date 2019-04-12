package Domain.Validator;

import Domain.CadruDidactic;
import Domain.Student;

import java.util.Optional;

public class ValidatorCadruDidactic implements Validator<CadruDidactic> {
    @Override
    /**
     *
     * @param cd
     * @throws ValidationException
     * if the entity is not valid
     */
    public void validate(CadruDidactic cd) throws ValidationException{
        String err="";

        if(cd.getID().compareTo("")==0)
            err=err+"ID-ul cadrului didactic nu poate fi null!\n";
        if(cd.getNume().compareTo("")==0)
            err=err+"Numele cadrului didactic nu poate fi null!\n";

        if (!err.equals(""))
            throw new ValidationException(err);

    }

}
