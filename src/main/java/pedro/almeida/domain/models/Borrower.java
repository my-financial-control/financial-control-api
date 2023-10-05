package pedro.almeida.domain.models;

public class Borrower {

    private String name;

    public Borrower(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "name='" + name + '\'' +
                '}';
    }

}
