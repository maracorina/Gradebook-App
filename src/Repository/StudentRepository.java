package Repository;

import Domain.Student;
import Domain.Validator.Validator;

import java.time.LocalDateTime;
import java.util.List;

public class StudentRepository extends FileRepository<String, Student> {


    public StudentRepository(String file, Validator<Student> validator) {
        super(file, validator);
    }

    //@Override

    /**
     *
     * @param attr
     * @return Student
     */
    public Student extractEntity(List<String> attr) {
        String id=attr.get(0);
        String nume=attr.get(1);
        int grupa=Integer.parseInt(attr.get(2));
        String email=attr.get(3);
        Student st=new Student(id,nume, grupa, email);
        return st;
    }

}
