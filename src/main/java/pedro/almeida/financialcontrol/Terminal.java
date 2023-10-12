package pedro.almeida.financialcontrol;

import pedro.almeida.financialcontrol.usecases.*;
import pedro.almeida.financialcontrol.domain.models.*;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;
import pedro.almeida.financialcontrol.domain.usecases.*;
import pedro.almeida.financialcontrol.repositories.inmemory.BorrowingInMemoryRepository;
import pedro.almeida.financialcontrol.repositories.inmemory.TransactionsInMemoryRepository;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Terminal {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Transactions transactions = new TransactionsInMemoryRepository();
    private static final Borrowings borrowings = new BorrowingInMemoryRepository();

    public static void main(String[] args) {
        while (true) {
            showMenu();
            if (!inputOption()) break;
        }
    }

    private static void showMenu() {
        System.out.println("============== Controle Financeiro ==============".toUpperCase());
        System.out.println("1. Registrar transação");
        System.out.println("2. Consultar todas as transações");
        System.out.println("3. Consultar saldo");
        System.out.println("4. Consultar transações por mês corrente");
        System.out.println("5. Registrar empréstimo");
        System.out.println("6. Registrar pagamento de parcela de empréstimo");
        System.out.println("7. Consultar empréstimos");
        System.out.println("8. Sair");
        System.out.print("---> Digite a opção desejada: ");
    }

    private static boolean inputOption() {
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                registerTransaction();
                return true;
            case 2:
                findAllTransaction();
                return true;
            case 3:
                checkBalance();
                return true;
            case 4:
                findTransactionsByMonth();
                return true;
            case 5:
                registerBorrowing();
                return true;
            case 6:
                payBorrowingParcel();
                return true;
            case 7:
                findAllBorrowings();
                return true;
            case 8:
                System.out.println("Até a próxima!");
                return false;
            default:
                System.out.println("Opção inválida");
                return true;
        }
    }

    private static void registerTransaction() {
        System.out.println("============== Registrar Transação ==============");
        RegisterTransaction registerTransaction = new RegisterTransactionUseCase(transactions);

        scanner.nextLine();
        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Descrição: ");
        String description = scanner.nextLine();

        System.out.print("Valor: ");
        BigDecimal value = new BigDecimal(scanner.nextLine());

        TransactionType transactionType;
        while (true) {
            System.out.print("Tipo (Despesa/Crédito - D/C): ");
            String type = scanner.nextLine();
            if (type.startsWith("D") || type.startsWith("d")) {
                transactionType = TransactionType.EXPENSE;
                break;
            } else if (type.startsWith("C") || type.startsWith("c")) {
                transactionType = TransactionType.CREDIT;
                break;
            } else {
                System.out.println("Tipo inválido! Digite novamente.\n");
            }
        }

        Month currentMonth;
        while (true) {
            try {
                System.out.print("Mês corrente (número de 1 à 12): ");
                currentMonth = Month.of(scanner.nextInt());
                break;
            } catch (DateTimeException ex) {
                System.out.println("Mês inválido, digite novamente.\n");
            }
        }
        scanner.nextLine();

        LocalDate date;
        while (true) {
            System.out.print("Data (dd/MM/aaaa): ");
            try {
                date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                break;
            } catch (DateTimeParseException ex) {
                System.out.println("Data inválida, digite novamente.\n");
            }
        }

        Transaction transaction = new Transaction(title, description, value, transactionType, currentMonth, date);
        registerTransaction.execute(transaction);
    }


    private static void findAllTransaction() {
        FindAllTransactions findAllTransactions = new FindAllTransactionsUseCase(transactions);
        List<Transaction> allTransactions = findAllTransactions.execute();
        showTable(
                "TRANSAÇÕES",
                List.of("ID", "TÍTULO", "DESCRIÇÃO", "VALOR", "MÊS CORRENTE", "DATA"),
                allTransactions.stream()
                        .map(
                                transaction -> List.of(transaction.getId().toString(), transaction.getTitle(), transaction.getDescription(), transaction.getValue().toString(), transaction.getCurrentMonth().toString(), transaction.getDate().toString())
                        ).toList(),
                50
        );
    }

    private static void findTransactionsByMonth() {
        System.out.println("============== Visualizar transações por mês corrente ==============");
        FindAllTransactions findTransactionsByMonth = new FindAllTransactionsUseCase(transactions);

        Month currentMonth;
        while (true) {
            try {
                System.out.print("Mês corrente (número de 1 à 12): ");
                currentMonth = Month.of(scanner.nextInt());
                break;
            } catch (DateTimeException ex) {
                System.out.println("Mês inválido, digite novamente.\n");
            }
        }

        List<Transaction> allTransactionsByMonth = findTransactionsByMonth.execute(currentMonth, LocalDate.now().getYear());
        showTable(
                "TRANSAÇÕES",
                List.of("ID", "TÍTULO", "DESCRIÇÃO", "VALOR", "MÊS CORRENTE", "DATA"),
                allTransactionsByMonth.stream()
                        .map(
                                transaction -> List.of(transaction.getId().toString(), transaction.getTitle(), transaction.getDescription(), transaction.getValue().toString(), transaction.getCurrentMonth().toString(), transaction.getDate().toString())
                        ).toList(),
                50
        );
    }

    private static void checkBalance() {
        System.out.println("============== Saldo ==============");
        CheckBalance checkBalance = new CheckBalanceUseCase(transactions, borrowings);
        System.out.println("Saldo: " + checkBalance.execute());
    }

    private static void registerBorrowing() {
        System.out.println("============== Registrar Empréstimo ==============");
        RegisterBorrowing registerBorrowing = new RegisterBorrowingUseCase(borrowings);
        scanner.nextLine();
        System.out.print("Devedor: ");
        Borrower borrower = new Borrower(scanner.nextLine());
        System.out.print("Valor: ");
        BigDecimal value = new BigDecimal(scanner.nextLine());
        Borrowing borrowing = new Borrowing(borrower, value);
        registerBorrowing.execute(borrowing);
    }

    private static void findAllBorrowings() {
        FindAllBorrowings findAllBorrowings = new FindAllBorrowingsUseCase(borrowings);
        List<Borrowing> allBorrowings = findAllBorrowings.execute();
        showTable(
                "EMPRÉSTIMOS",
                List.of("ID", "DEVEDOR", "VALOR TOTAL", "VALOR PAGO", "DATA"),
                allBorrowings.stream()
                        .map(
                            borrowing -> List.of(borrowing.getId().toString(), borrowing.getBorrower().getName(), borrowing.getValue().toString(), borrowing.sumParcels().toString(), borrowing.getDate().toString())
                        ).toList(),
                50
        );
    }

    private static void payBorrowingParcel() {
        System.out.println("============== Pagar parcela do empréstimo ==============");
        PayParcelBorrowing payParcelBorrowing = new PayParcelBorrowingUseCase(borrowings);

        scanner.nextLine();
        System.out.print("ID do empréstimo: ");
        String idBorrowing = scanner.nextLine();

        System.out.print("Valor a ser pago: ");
        BigDecimal value = new BigDecimal(scanner.nextLine());
        ParcelBorrowing parcel = new ParcelBorrowing(value);

        payParcelBorrowing.execute(UUID.fromString(idBorrowing), parcel);
    }

    private static void showTable(String tableTitle, List<String> titles, List<List<String>> content, int maxCellLength) {
        // Encontrar o tamanho máximo de cada coluna
        int[] columnWidths = new int[titles.size()];
        for (int i = 0; i < titles.size(); i++) {
            columnWidths[i] = titles.get(i).length();
        }
        for (List<String> row : content) {
            for (int i = 0; i < row.size(); i++) {
                int cellLength = row.get(i).length();
                if (cellLength > columnWidths[i]) {
                    columnWidths[i] = Math.min(cellLength, maxCellLength);
                }
            }
        }

        // Calcular a largura total da tabela
        int tableWidth = 0;
        for (int columnWidth : columnWidths) {
            tableWidth += columnWidth + 3; // 3 caracteres de espaço entre colunas
        }

        // Imprimir título geral da tabela
        int titlePadding = (tableWidth - tableTitle.length()) / 2;
        String titlePaddingStr = "=".repeat(titlePadding);
        System.out.println(titlePaddingStr + tableTitle + titlePaddingStr);

        // Imprimir cabeçalho da tabela
        for (int i = 0; i < titles.size(); i++) {
            System.out.printf("%-" + columnWidths[i] + "s | ", limitCellLength(titles.get(i), columnWidths[i]));
        }
        System.out.println();

        // Imprimir linha de separação
        for (int i = 0; i < titles.size(); i++) {
            for (int j = 0; j < columnWidths[i] + 3; j++) {
                System.out.print("-");
            }
        }
        System.out.println();

        // Imprimir conteúdo da tabela
        for (List<String> row : content) {
            for (int i = 0; i < row.size(); i++) {
                String cellValue = row.get(i);
                System.out.printf("%-" + columnWidths[i] + "s | ", limitCellLength(cellValue, columnWidths[i]));
            }
            System.out.println();
        }

        // Imprimir caracteres "=" para fechar a tabela
        for (int i = 0; i < tableWidth; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    private static String limitCellLength(String cellValue, int maxCellLength) {
        if (cellValue.length() <= maxCellLength) {
            return cellValue;
        } else {
            return cellValue.substring(0, maxCellLength - 3) + "...";
        }
    }

}
