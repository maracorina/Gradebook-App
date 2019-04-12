package Domain.Validator;

import Domain.CadruDidactic;
import Domain.Student;
import Domain.TemaLaborator;

public class ValidatorTemaLaborator implements Validator<TemaLaborator> {
    @Override
    /**
     *
     * @param lab
     * @throws ValidationException
     * if the entity is not valid
     */
    public void validate(TemaLaborator lab) throws ValidationException{
        String err="";

        if(lab.getID().compareTo("")==0)
            err=err+"ID-ul laboratorului nu poate fi null!\n";
        if(lab.getStart()>lab.getDeadline())
            err=err+"Saptamana de start nu poate fi mai mare decat deadline-ul laboratorului!\n";
        if(lab.getStart()<TemaLaborator.getPrimaSaptamana())
            err=err+"Saptamana de start nu poate fi inaintea primei saptamani din semestru!\n";
        if(lab.getDeadline()- lab.getStart()>13)
            err=err+"Semestrul are doar 14 saptamani!\n";

        if (!err.equals(""))
            throw new ValidationException(err);

    }

}
