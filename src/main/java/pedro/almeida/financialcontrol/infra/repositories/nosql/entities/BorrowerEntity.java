package pedro.almeida.financialcontrol.infra.repositories.nosql.entities;

public class BorrowerEntity {

    private String name;

    public BorrowerEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
