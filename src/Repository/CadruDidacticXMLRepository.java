package Repository;

import Domain.CadruDidactic;
import Domain.Student;
import Domain.TemaLaborator;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorCadruDidactic;
import Domain.Validator.ValidatorStudent;
import Domain.Validator.ValidatorTemaLaborator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

public class CadruDidacticXMLRepository extends XMLRepository<String, CadruDidactic> {


    public CadruDidacticXMLRepository(String file, ValidatorCadruDidactic validator) {
        super(validator, file);
    }



    @Override
    public Element createElementfromEntity(Document document, CadruDidactic cd) {
        Element e = document.createElement("CadruDidactic");
        e.setAttribute("id", cd.getID());

        Element nume = document.createElement("nume");
        nume.setTextContent(cd.getNume());
        e.appendChild(nume);

        Element email = document.createElement("email");
        email.setTextContent(cd.getEmail());
        e.appendChild(email);

        Element materie = document.createElement("materie");
        materie.setTextContent(cd.getMaterie());
        e.appendChild(materie);

        return e;
    }

    @Override
    public CadruDidactic createEntityFromElement(Element e) {
        String id = e.getAttribute("id");
        String nume =e.getElementsByTagName("nume")
                .item(0)
                .getTextContent();

        String email =e.getElementsByTagName("email")
                .item(0)
                .getTextContent();

        String materie =e.getElementsByTagName("materie")
                .item(0)
                .getTextContent();

        return new CadruDidactic(id, nume, email, materie);
    }
}
