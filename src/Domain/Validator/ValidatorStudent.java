package Domain.Validator;

import Domain.Student;

public class ValidatorStudent implements Validator<Student> {
    @Override
    /**
     *
     * @param st
     * @throws ValidationException
     * if the entity is not valid
     */
    public void validate(Student st) throws ValidationException{
        String err="";

        if(st.getID().compareTo("")==0)
            err=err+"ID-ul studentului nu poate fi null!\n";
        if(st.getNume().compareTo("")==0)
            err=err+"Numele studentului nu poate fi null!\n";
        if(st.getGrupa() <1)
            err=err+"Grupa studentului nu poate fi mai mica decat 1!\n";
        if(st.getGrupa() >7)
            err=err+"Grupa studentului nu poate fi mai mare decat 7!\n";
        if (!err.equals(""))
            throw new ValidationException(err);
    }
}
