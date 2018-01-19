package amh.kata.bankaccount.entities;

public class Employe {

    private Long idEmploye;
    private String name;

    public Employe() {
    }

    public Employe(Long idEmploye, String name) {
        this.idEmploye = idEmploye;
        this.name = name;
    }

    public Long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
