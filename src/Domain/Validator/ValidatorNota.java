package Domain.Validator;

import Domain.Nota;

import static java.sql.Types.NULL;

public class ValidatorNota implements Validator<Nota> {
    @Override
    /**
     *
     * @param nt
     * @throws ValidationException
     * if the entity is not valid
     */
    public void validate(Nota nt) throws ValidationException{
        String err="";

        if(nt.getStudent().getID().compareTo("")==0)
            err=err+"ID-ul studentului nu poate fi vid!";
        if(nt.getLaborator().getID().compareTo("")==0)
            err=err+"ID-ul temei nu poate fi vid!";
        if(nt.getCadruDidactic().getID().compareTo("")==0)
            err=err+"ID-ul cadrului didactic nu poate fi vid!";
        if(nt.getNota()==NULL)
            err=err+"Nota nu poate fi nula!";
        if( nt.getNota()<1)
            err=err+"Nota trebuie sa fie intre 1 si 10!";
        if( nt.getNota()>10)
            err=err+"Nota trebuie sa fie intre 1 si 10!";
        if (!err.equals(""))
            throw new ValidationException(err);

    }

}
