package Domain;

public class NotaDTOTable {
    private String studentName;
    private float nota1;
    private float nota2;
    private float nota3;
    private float nota4;
    private float nota5;
    private float nota6;
    private float nota7;

    public NotaDTOTable(String studentName, float nota1, float nota2, float nota3, float nota4, float nota5, float nota6, float nota7) {
        this.studentName = studentName;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
        this.nota5 = nota5;
        this.nota6 = nota6;
        this.nota7 = nota7;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


    public float getNota1() {
        return nota1;
    }

    public void setNota1(float nota) {
        this.nota1 = nota;
    }

    public float getNota2() {
        return nota2;
    }

    public void setNota2(float nota) {
        this.nota2 = nota;
    }

    public float getNota3() {
        return nota3;
    }

    public void setNota3(float nota) {
        this.nota3 = nota;
    }

    public float getNota4() {
        return nota4;
    }

    public void setNota4(float nota) {
        this.nota4 = nota;
    }

    public float getNota5() {
        return nota5;
    }

    public void setNota5(float nota) {
        this.nota5 = nota;
    }

    public float getNota6() {
        return nota6;
    }

    public void setNota6(float nota) {
        this.nota6 = nota;
    }

    public float getNota7() {
        return nota7;
    }

    public void setNota7(float nota) {
        this.nota7 = nota;
    }
}
