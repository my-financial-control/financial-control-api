package pedro.almeida.financialcontrol.domain.models;

public class Borrower {

    private String name;

    public Borrower(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "name='" + name + '\'' +
                '}';
    }

}
