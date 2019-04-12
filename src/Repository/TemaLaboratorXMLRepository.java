package Repository;

import Domain.Student;
import Domain.TemaLaborator;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorStudent;
import Domain.Validator.ValidatorTemaLaborator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

public class TemaLaboratorXMLRepository extends XMLRepository<String, TemaLaborator> {


    public TemaLaboratorXMLRepository(String file, ValidatorTemaLaborator validator) {
        super(validator, file);
    }



    @Override
    public Element createElementfromEntity(Document document, TemaLaborator t) {
        Element e = document.createElement("TemaLaborator");
        e.setAttribute("id", t.getID());

        Element descriere = document.createElement("descriere");
        descriere.setTextContent(t.getDescriere());
        e.appendChild(descriere);

        Element start = document.createElement("start");
        start.setTextContent(String.valueOf(t.getStart()));
        e.appendChild(start);

        Element deadline = document.createElement("deadline");
        deadline.setTextContent(String.valueOf(t.getDeadline()));
        e.appendChild(deadline);

        return e;
    }

    @Override
    public TemaLaborator createEntityFromElement(Element e) {
        String id = e.getAttribute("id");
        String descriere =e.getElementsByTagName("descriere")
                .item(0)
                .getTextContent();

        int start =Integer.parseInt(e.getElementsByTagName("start")
                .item(0)
                .getTextContent());

        int deadline =Integer.parseInt(e.getElementsByTagName("deadline")
                .item(0)
                .getTextContent());

        return new TemaLaborator(id, descriere, start, deadline);
    }
}
