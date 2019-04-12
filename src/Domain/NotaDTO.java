package Domain;

public class NotaDTO {
    private String studentName;
    private String temaId;
    private float nota;

    public NotaDTO(String studentName, String temaDesc, float nota) {
        this.studentName = studentName;
        this.temaId = temaDesc;
        this.nota = nota;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTemaId() {
        return temaId;
    }

    public void setTemaId(String temaId) {
        this.temaId = temaId;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}
