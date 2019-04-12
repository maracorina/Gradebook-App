package Repository;

import Domain.Student;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorStudent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

public class StudentXMLRepository extends XMLRepository<String, Student> {


    public StudentXMLRepository(String file, ValidatorStudent validator) {
        super(validator, file);
    }



    @Override
    public Element createElementfromEntity(Document document, Student s) {
        Element e = document.createElement("Student");
        e.setAttribute("id", s.getID());

        Element nume = document.createElement("nume");
        nume.setTextContent(s.getNume());
        e.appendChild(nume);

        Element grupa = document.createElement("grupa");
        grupa.setTextContent(String.valueOf(s.getGrupa()));
        e.appendChild(grupa);

        Element email = document.createElement("email");
        email.setTextContent(s.getEmail());
        e.appendChild(email);

        return e;
    }

    @Override
    public Student createEntityFromElement(Element e) {
        String id = e.getAttribute("id");
        String nume =e.getElementsByTagName("nume")
                .item(0)
                .getTextContent();

        int grupa =Integer.parseInt(e.getElementsByTagName("grupa")
                .item(0)
                .getTextContent());

        String email =e.getElementsByTagName("email")
                .item(0)
                .getTextContent();

        return new Student(id, nume, grupa, email);
    }
}
